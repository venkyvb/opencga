package org.opencb.opencga.storage.hadoop.variant.index.sample;

import htsjdk.variant.vcf.VCFConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.opencb.biodata.models.core.Region;
import org.opencb.biodata.models.variant.StudyEntry;
import org.opencb.biodata.models.variant.avro.VariantType;
import org.opencb.biodata.models.variant.metadata.VariantFileHeaderComplexLine;
import org.opencb.commons.datastore.core.Query;
import org.opencb.commons.datastore.core.QueryOptions;
import org.opencb.opencga.core.config.storage.IndexFieldConfiguration;
import org.opencb.opencga.core.config.storage.SampleIndexConfiguration;
import org.opencb.opencga.storage.core.metadata.VariantStorageMetadataManager;
import org.opencb.opencga.storage.core.metadata.models.TaskMetadata;
import org.opencb.opencga.storage.core.variant.VariantStorageOptions;
import org.opencb.opencga.core.models.variant.VariantAnnotationConstants;
import org.opencb.opencga.storage.core.variant.dummy.DummyVariantStorageMetadataDBAdaptorFactory;
import org.opencb.opencga.storage.core.variant.query.Values;
import org.opencb.opencga.storage.core.variant.query.VariantQueryParser;
import org.opencb.opencga.storage.core.variant.query.VariantQueryUtils;
import org.opencb.opencga.storage.hadoop.variant.index.annotation.AnnotationIndexConverter;
import org.opencb.opencga.storage.hadoop.variant.index.core.IndexField;
import org.opencb.opencga.storage.hadoop.variant.index.core.RangeIndexField;
import org.opencb.opencga.storage.hadoop.variant.index.core.filters.IndexFieldFilter;
import org.opencb.opencga.storage.hadoop.variant.index.core.filters.RangeIndexFieldFilter;
import org.opencb.opencga.storage.hadoop.variant.index.family.GenotypeCodec;
import org.opencb.opencga.storage.hadoop.variant.index.query.RangeQuery;
import org.opencb.opencga.storage.hadoop.variant.index.query.SampleAnnotationIndexQuery;
import org.opencb.opencga.storage.hadoop.variant.index.query.SampleFileIndexQuery;
import org.opencb.opencga.storage.hadoop.variant.index.query.SampleIndexQuery;

import java.util.*;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.opencb.opencga.storage.core.variant.adaptors.VariantQueryParam.*;
import static org.opencb.opencga.core.models.variant.VariantAnnotationConstants.ANTISENSE;
import static org.opencb.opencga.core.models.variant.VariantAnnotationConstants.PROTEIN_CODING;
import static org.opencb.opencga.storage.core.variant.query.VariantQueryUtils.*;
import static org.opencb.opencga.storage.hadoop.variant.index.IndexUtils.EMPTY_MASK;
import static org.opencb.opencga.storage.hadoop.variant.index.annotation.AnnotationIndexConverter.*;
import static org.opencb.opencga.storage.hadoop.variant.index.core.RangeIndexField.DELTA;
import static org.opencb.opencga.storage.hadoop.variant.index.sample.SampleIndexQueryParser.groupRegions;
import static org.opencb.opencga.storage.hadoop.variant.index.sample.SampleIndexQueryParser.validSampleIndexQuery;

/**
 * Created on 08/01/19.
 *
 * @author Jacobo Coll &lt;jacobo167@gmail.com&gt;
 */
public class SampleIndexQueryParserTest {

    private SampleIndexQueryParser sampleIndexQueryParser;
    private VariantStorageMetadataManager mm;
    private int studyId;
    private FileIndexSchema fileIndex;
    private double[] qualThresholds;
    private double[] dpThresholds;
    private SampleIndexConfiguration configuration;

    @Before
    public void setUp() throws Exception {
        configuration = SampleIndexConfiguration.defaultConfiguration()
                .addPopulation(new SampleIndexConfiguration.Population("s1", "ALL"))
                .addPopulation(new SampleIndexConfiguration.Population("s2", "ALL"))
                .addPopulation(new SampleIndexConfiguration.Population("s3", "ALL"))
                .addPopulation(new SampleIndexConfiguration.Population("s4", "ALL"));

        SampleIndexSchema schema = new SampleIndexSchema(configuration);
        fileIndex = schema.getFileIndex();
        qualThresholds = fileIndex.getCustomField(IndexFieldConfiguration.Source.FILE, StudyEntry.QUAL).getConfiguration().getThresholds();
        dpThresholds = fileIndex.getCustomField(IndexFieldConfiguration.Source.SAMPLE, VCFConstants.DEPTH_KEY).getConfiguration().getThresholds();

        DummyVariantStorageMetadataDBAdaptorFactory.clear();
        mm = new VariantStorageMetadataManager(new DummyVariantStorageMetadataDBAdaptorFactory());
        sampleIndexQueryParser = new SampleIndexQueryParser(mm, schema);
        studyId = mm.createStudy("study").getId();
        mm.addIndexedFiles(studyId, Arrays.asList(mm.registerFile(studyId, "F1", Arrays.asList("S1", "S2", "S3"))));

        mm.addIndexedFiles(studyId, Arrays.asList(mm.registerFile(studyId, "fam1", Arrays.asList("fam1_child", "fam1_father", "fam1_mother"))));
        mm.addIndexedFiles(studyId, Arrays.asList(mm.registerFile(studyId, "fam2_child", Arrays.asList("fam2_child"))));
        mm.addIndexedFiles(studyId, Arrays.asList(mm.registerFile(studyId, "fam2_father", Arrays.asList("fam2_father"))));
        mm.addIndexedFiles(studyId, Arrays.asList(mm.registerFile(studyId, "fam2_mother", Arrays.asList("fam2_mother"))));
        for (String family : Arrays.asList("fam1", "fam2")) {
            mm.updateSampleMetadata(studyId, mm.getSampleId(studyId, family + "_child"),
                    sampleMetadata -> sampleMetadata
                            .setFamilyIndexStatus(TaskMetadata.Status.READY)
                            .setFather(mm.getSampleId(studyId, family + "_father"))
                            .setMother(mm.getSampleId(studyId, family + "_mother")));
        }

        mm.updateStudyMetadata("study", studyMetadata -> {
            studyMetadata.getVariantHeader().getComplexLines().add(new VariantFileHeaderComplexLine("FORMAT", "DP", "", "1", "", Collections.emptyMap()));
            studyMetadata.getAttributes().put(VariantStorageOptions.LOADED_GENOTYPES.key(), "./.,0/0,0/1,1/1");
            return studyMetadata;
        });
    }

    private SampleIndexQuery parse(final Query query) {
        Query newQuery = new VariantQueryParser(null, mm).preProcessQuery(query, new QueryOptions());
        query.clear();
        query.putAll(newQuery);
        return sampleIndexQueryParser.parse(query);
    }

    private SampleFileIndexQuery parseFileQuery(Query query, String sample, Function<String, List<String>> filesFromSample) {
        return parseFileQuery(query, sample, filesFromSample, false);
    }

    private SampleFileIndexQuery parseFileQuery(Query query, String sample, Function<String, List<String>> filesFromSample, boolean multiFileSample) {
        return sampleIndexQueryParser.parseFileQuery(query, sample, multiFileSample, false, filesFromSample, false);
    }

    private byte parseAnnotationMask(Query query) {
        return sampleIndexQueryParser.parseAnnotationIndexQuery(query).getAnnotationIndexMask();
    }

    private byte parseAnnotationMask(Query query, boolean completeIndex) {
        return sampleIndexQueryParser.parseAnnotationIndexQuery(query, completeIndex).getAnnotationIndexMask();
    }

    private SampleAnnotationIndexQuery parseAnnotationIndexQuery(Query query) {
        return sampleIndexQueryParser.parseAnnotationIndexQuery(query);
    }

    private SampleAnnotationIndexQuery parseAnnotationIndexQuery(Query query, boolean completeIndex) {
        return sampleIndexQueryParser.parseAnnotationIndexQuery(query, completeIndex);
    }

    @Test
    public void validSampleIndexQueryTest() {
        // Single sample
        assertTrue(validSampleIndexQuery(new Query(SAMPLE.key(), "S1")));
        assertTrue(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:1/1")));
        assertTrue(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:./1")));
        assertTrue(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:1/1,0/1")));
        assertFalse(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:!1/1"))); // Negated
        assertFalse(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:0/0")));
        assertFalse(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:./0")));
        assertFalse(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:./.")));
        assertFalse(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:1/1,./.")));

        // ALL samples (and)
        assertTrue(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:1/1,./.;S2:0/1"))); // Any valid
        assertFalse(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:1/1,./.;S2:./.")));

        // ANY sample (or)
        assertTrue(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:1/1,S2:0/1"))); // all must be valid
        assertFalse(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:1/1,./.,S2:0/1")));
        assertFalse(validSampleIndexQuery(new Query(GENOTYPE.key(), "S1:1/1,./.,S2:./.")));

    }

    @Test
    public void parseSampleIndexQuery() {
        Query query;
        SampleIndexQuery indexQuery;
        Map<String, List<String>> gtMap;

        query = new Query(GENOTYPE.key(), "S1:1/1,0/1");
        indexQuery = parse(query);
        gtMap = Collections.singletonMap("S1", Arrays.asList("1/1", "0/1"));
        assertEquals(gtMap, indexQuery.getSamplesMap());
        assertFalse(query.containsKey(GENOTYPE.key()));

        query = new Query(GENOTYPE.key(), "S1:1/1,0/1;S2:0/0");
        indexQuery = parse(query);
        gtMap = new HashMap<>();
        gtMap.put("S1", Arrays.asList("1/1", "0/1"));
        gtMap.put("S2", Arrays.asList("0/1", "1/1"));
        assertEquals(gtMap, indexQuery.getSamplesMap());
        assertTrue(query.containsKey(GENOTYPE.key()));

        query = new Query(GENOTYPE.key(), "S1:1/1,0/1;S2:0/0,0/1");
        indexQuery = parse(query);
        gtMap = new HashMap<>();
        gtMap.put("S1", Arrays.asList("1/1", "0/1"));
        gtMap.put("S2", Arrays.asList("1/1"));
        assertEquals(gtMap, indexQuery.getSamplesMap());
        assertTrue(query.containsKey(GENOTYPE.key()));

        query = new Query(GENOTYPE.key(), "S1:1/1,0/1;S2:0/0,0/1,1/1");
        indexQuery = parse(query);
        gtMap = new HashMap<>();
        gtMap.put("S1", Arrays.asList("1/1", "0/1"));
        gtMap.put("S2", Collections.emptyList());
        assertEquals(gtMap, indexQuery.getSamplesMap());
        assertTrue(query.containsKey(GENOTYPE.key()));
    }

    @Test
    public void parseVariantTpeQuery() {
        Query q;
        SampleIndexQuery sampleIndexQuery;

        q = new Query(TYPE.key(), "INDEL").append(SAMPLE.key(), "S1");
        sampleIndexQuery = parse(q);
        assertTrue(CollectionUtils.isEmpty(sampleIndexQuery.getVariantTypes()));
        assertNull(q.get(TYPE.key()));

        q = new Query(TYPE.key(), "INDEL,SNV,INSERTION,DELETION,BREAKEND,MNV").append(SAMPLE.key(), "S1");
        sampleIndexQuery = parse(q);
        assertThat(sampleIndexQuery.getVariantTypes(), anyOf(nullValue(), is(Collections.emptyList())));
        assertNull(q.get(TYPE.key()));

        q = new Query(TYPE.key(), "INDEL,SNV").append(SAMPLE.key(), "S1");
        sampleIndexQuery = parse(q);
        assertTrue(CollectionUtils.isEmpty(sampleIndexQuery.getVariantTypes()));
        assertNull(q.get(TYPE.key()));

        q = new Query(TYPE.key(), "SNV").append(SAMPLE.key(), "S1");
        sampleIndexQuery = parse(q);
        assertTrue(CollectionUtils.isEmpty(sampleIndexQuery.getVariantTypes()));
        assertNull(q.get(TYPE.key()));

        q = new Query(TYPE.key(), "SNV,SNP").append(SAMPLE.key(), "S1");
        sampleIndexQuery = parse(q);
        assertTrue(CollectionUtils.isEmpty(sampleIndexQuery.getVariantTypes()));
        assertNull(q.get(TYPE.key()));

//        q = new Query(TYPE.key(), "SNP").append(SAMPLE.key(), "S1");
//        sampleIndexQuery = parse(q);
//        // Filter by SNV, as it can not filter by SNP
//        assertEquals(Collections.singleton(VariantType.SNV), sampleIndexQuery.getVariantTypes());
//        assertEquals(VariantType.SNP.name(), q.getString(TYPE.key()));


        q = new Query(TYPE.key(), "MNV").append(SAMPLE.key(), "S1");
        sampleIndexQuery = parse(q);
        assertTrue(CollectionUtils.isEmpty(sampleIndexQuery.getVariantTypes()));
        assertNull(q.get(TYPE.key()));

//        q = new Query(TYPE.key(), "MNV,MNP").append(SAMPLE.key(), "S1");
//        sampleIndexQuery = parse(q);
//        assertTrue(CollectionUtils.isEmpty(sampleIndexQuery.getVariantTypes()));
//        assertNull(q.get(TYPE.key()));
//
//        q = new Query(TYPE.key(), "MNP").append(SAMPLE.key(), "S1");
//        sampleIndexQuery = parse(q);
//        // Filter by MNV, as it can not filter by MNP
//        assertEquals(Collections.singleton(VariantType.MNV), sampleIndexQuery.getVariantTypes());
//        assertEquals(VariantType.MNP.name(), q.getString(TYPE.key()));

    }

    @Test
    public void parseFileFilterTest() {
        testFilter("PASS", true, true);
        testFilter("LowQual", false, false);
        testFilter( "LowGQX,LowQual", false, false);
        testFilter( "LowGQX;LowQual", false, false);
        testFilter( "PASS,LowQual", null, false);
//        testFilter( "PASS;LowQual", null, false);
    }

    @Test
    public void parseFileFilterNegatedTest() {
        testFilter("!PASS", false, true);
        testFilter("!LowQual", null, false);
        testFilter("!LowGQX;!LowQual", null, false);
    }

    private void testFilter(String value, Boolean pass, boolean covered) {
        Query query = new Query(FILTER.key(), value);
        SampleFileIndexQuery q = parseFileQuery(query, "", null);
        if (pass == null) {
            assertTrue(q.isEmpty());
        } else {
            IndexFieldFilter filter = q.getFilters().stream().filter(f -> f.getIndex().getKey().equals(StudyEntry.FILTER)).findFirst().orElse(null);
            assertNotNull(filter);
            if (pass) {
                assertTrue(filter.test(((IndexField<String>) filter.getIndex()).encode("PASS")));
            } else {
                assertFalse(filter.test(((IndexField<String>) filter.getIndex()).encode("PASS")));
            }
        }
        assertEquals(!covered, isValidParam(query, FILTER));
    }

    @Test
    public void parseFileQualTest() {
        int qualThresholdMatches = 0;
        for (Double qual : Arrays.asList(0.0, 5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 40.0)) {
            Query query;
            SampleFileIndexQuery fileQuery;

            query = new Query(QUAL.key(), "=" + qual);
            fileQuery = parseFileQuery(query, "", null);
            checkQualFilter("=" + qual, qual, qual + DELTA, fileQuery);
            assertTrue(isValidParam(query, QUAL));

            query = new Query(QUAL.key(), "<=" + qual);
            fileQuery = parseFileQuery(query, "", null);
            checkQualFilter("<=" + qual, Double.MIN_VALUE, qual + DELTA, fileQuery);
            assertTrue(isValidParam(query, QUAL));

            query = new Query(QUAL.key(), "<" + qual);
            fileQuery = parseFileQuery(query, "", null);
            checkQualFilter("<" + qual, Double.MIN_VALUE, qual, fileQuery);
            if (Arrays.binarySearch(qualThresholds, qual) >= 0) {
                assertFalse(isValidParam(query, QUAL));
                qualThresholdMatches++;
            } else {
                assertTrue(isValidParam(query, QUAL));
            }

            query = new Query(QUAL.key(), ">=" + qual);
            fileQuery = parseFileQuery(query, "", null);
            checkQualFilter(">=" + qual, qual, RangeIndexField.MAX, fileQuery);
            // Qual == 0 is not an special case anymore
            if (/*qual == 0 || */Arrays.binarySearch(qualThresholds, qual) >= 0) {
                assertFalse(">=" + qual, isValidParam(query, QUAL));
            } else {
                assertTrue(">=" + qual, isValidParam(query, QUAL));
            }

            query = new Query(QUAL.key(), ">" + qual);
            fileQuery = parseFileQuery(query, "", null);
            checkQualFilter(">" + qual, qual + DELTA, RangeIndexField.MAX, fileQuery);
            assertTrue(isValidParam(query, QUAL));
        }
        assertEquals(qualThresholds.length, qualThresholdMatches);
    }

    protected void checkQualFilter(String message, double minValueInclusive, double maxValueExclusive, SampleFileIndexQuery fileQuery) {
        RangeIndexFieldFilter qualQuery = (RangeIndexFieldFilter) fileQuery.getFilter(IndexFieldConfiguration.Source.FILE, StudyEntry.QUAL);

        assertEquals(message, minValueInclusive, qualQuery.getMinValueInclusive(), 0);
        assertEquals(message, maxValueExclusive, qualQuery.getMaxValueExclusive(), 0);
        assertEquals(message, RangeIndexField.getRangeCode(minValueInclusive, qualThresholds), qualQuery.getMinCodeInclusive());
        assertEquals(message, RangeIndexFieldFilter.getRangeCodeExclusive(maxValueExclusive, qualThresholds), qualQuery.getMaxCodeExclusive());
    }

    @Test
    public void parseFileDPTest() {
        SampleFileIndexQuery fileQuery;
        for (Double dp : Arrays.asList(3.0, 5.0, 10.0, 15.0, 20.0, 30.0, 35.0)) {
            for (Pair<String, String> pair : Arrays.asList(/*Pair.of(FILE_DATA.key(), "F1"), */Pair.of(SAMPLE_DATA.key(), "S1"))) {
                fileQuery = parseFileQuery(new Query(pair.getKey(), pair.getValue() + ":DP=" + dp), "S1", n -> Collections.singletonList("F1"));
                checkDPFilter("=" + dp, dp, dp + DELTA, fileQuery);

                fileQuery = parseFileQuery(new Query(pair.getKey(), pair.getValue() + ":DP<=" + dp), "S1", n -> Collections.singletonList("F1"));
                checkDPFilter("<=" + dp, Double.MIN_VALUE, dp + DELTA, fileQuery);

                fileQuery = parseFileQuery(new Query(pair.getKey(), pair.getValue() + ":DP<" + dp), "S1", n -> Collections.singletonList("F1"));
                checkDPFilter("<" + dp, Double.MIN_VALUE, dp, fileQuery);

                fileQuery = parseFileQuery(new Query(pair.getKey(), pair.getValue() + ":DP>=" + dp), "S1", n -> Collections.singletonList("F1"));
                checkDPFilter(">=" + dp, dp, RangeIndexField.MAX, fileQuery);

                fileQuery = parseFileQuery(new Query(pair.getKey(), pair.getValue() + ":DP>" + dp), "S1", n -> Collections.singletonList("F1"));
                checkDPFilter(">" + dp, dp + DELTA, RangeIndexField.MAX, fileQuery);
            }
        }


        Query query = new Query(SAMPLE_DATA.key(), "S1:DP>=15");
        fileQuery = parseFileQuery(query, "S1", n -> Collections.singletonList("F1"));
        assertTrue(getDPFilter(fileQuery).isExactFilter());
        assertFalse(isValidParam(query, SAMPLE_DATA));

        query = new Query(SAMPLE_DATA.key(), "S1:DP>=15;S2:DP>34");
        fileQuery = parseFileQuery(query, "S1", n -> Collections.singletonList("F1"));
        assertTrue(getDPFilter(fileQuery).isExactFilter());
        fileQuery = parseFileQuery(query, "S2", n -> Collections.singletonList("F1"));
        assertFalse(getDPFilter(fileQuery).isExactFilter());
        assertEquals("S2:DP>34", query.getString(SAMPLE_DATA.key()));

        query = new Query(SAMPLE_DATA.key(), "S1:DP>=15,S2:DP>34");
        fileQuery = parseFileQuery(query, "S1", n -> Collections.singletonList("F1"));
        assertNull(getDPFilter(fileQuery));
        fileQuery = parseFileQuery(query, "S2", n -> Collections.singletonList("F1"));
        assertNull(getDPFilter(fileQuery));
        assertEquals("S1:DP>=15,S2:DP>34", query.getString(SAMPLE_DATA.key()));

        query = new Query(SAMPLE_DATA.key(), "S2:DP>34;S1:DP>=15;S3:DP>16");
        fileQuery = parseFileQuery(query, "S2", n -> Collections.singletonList("F1"));
        assertFalse(getDPFilter(fileQuery).isExactFilter());
        fileQuery = parseFileQuery(query, "S1", n -> Collections.singletonList("F1"));
        assertTrue(getDPFilter(fileQuery).isExactFilter());
        assertEquals("S2:DP>34;S3:DP>16", query.getString(SAMPLE_DATA.key()));
    }

    protected void checkDPFilter(String message, double minValueInclusive, double maxValueExclusive, SampleFileIndexQuery fileQuery) {
        RangeIndexFieldFilter qualQuery = getDPFilter(fileQuery);

        assertEquals(message, minValueInclusive, qualQuery.getMinValueInclusive(), 0);
        assertEquals(message, maxValueExclusive, qualQuery.getMaxValueExclusive(), 0);
        assertEquals(message, RangeIndexField.getRangeCode(minValueInclusive, dpThresholds), qualQuery.getMinCodeInclusive());
        assertEquals(message, RangeIndexFieldFilter.getRangeCodeExclusive(maxValueExclusive, dpThresholds), qualQuery.getMaxCodeExclusive());
    }

    private RangeIndexFieldFilter getDPFilter(SampleFileIndexQuery fileQuery) {
        return (RangeIndexFieldFilter) fileQuery.getFilter(IndexFieldConfiguration.Source.SAMPLE, VCFConstants.DEPTH_KEY);
    }

    @Test
    public void parseFileTest() {
        Query query = new Query(FILE.key(), "F1");
        SampleFileIndexQuery fileQuery = parseFileQuery(query, "S2", n -> Arrays.asList("F1", "F2"), true);
        assertTrue(fileQuery.getFilter(fileIndex.getFilePositionIndex().getSource(), fileIndex.getFilePositionIndex().getKey()).test(0));
        assertFalse(fileQuery.getFilter(fileIndex.getFilePositionIndex().getSource(), fileIndex.getFilePositionIndex().getKey()).test(1));
        print(fileQuery);

        query = new Query(FILE.key(), "F2");
        fileQuery = parseFileQuery(query, "S2", n -> Arrays.asList("F1", "F2"), true);
        assertFalse(fileQuery.getFilter(fileIndex.getFilePositionIndex().getSource(), fileIndex.getFilePositionIndex().getKey()).test(0));
        assertTrue(fileQuery.getFilter(fileIndex.getFilePositionIndex().getSource(), fileIndex.getFilePositionIndex().getKey()).test(1));
        print(fileQuery);
    }

    @Test
    public void parseFileDataTest() {
        Query query;
        Values<SampleFileIndexQuery> fileQuery;

        query = new Query(FILE_DATA.key(), "F1:FILTER=PASS");
        fileQuery = sampleIndexQueryParser.parseFilesQuery(query, "S1", true, false, n -> Arrays.asList("F1", "F2"), false);
        assertEquals(1, fileQuery.size());
        assertFalse(VariantQueryUtils.isValidParam(query, FILE_DATA));

        query = new Query(FILE_DATA.key(), "F1:FILTER=PASS");
        fileQuery = sampleIndexQueryParser.parseFilesQuery(query, "S1", true, false, n -> Arrays.asList("F1", "F2"), false);
        assertEquals(1, fileQuery.size());
        assertFalse(VariantQueryUtils.isValidParam(query, FILE_DATA));

        query = new Query(FILE_DATA.key(), "F1:FILTER=PASS" + OR + "F2:FILTER=PASS");
        fileQuery = sampleIndexQueryParser.parseFilesQuery(query, "S1", true, false, n -> Arrays.asList("F1", "F2"), false);
        assertEquals(2, fileQuery.size());
        assertFalse(VariantQueryUtils.isValidParam(query, FILE_DATA));

        query = new Query(FILE_DATA.key(), "F1:FILTER=PASS" + OR + "F3:FILTER=PASS");
        fileQuery = sampleIndexQueryParser.parseFilesQuery(query, "S1", true, false, n -> Arrays.asList("F1", "F2"), false);
        assertEquals(1, fileQuery.size());
        assertTrue(VariantQueryUtils.isValidParam(query, FILE_DATA));
    }

    private void print(SampleFileIndexQuery fileQuery) {
        System.out.println("File query for sample '" + fileQuery.getSampleName() + "' with " + fileQuery.getFilters().size() + " filters");
        for (IndexFieldFilter filter : fileQuery.getFilters()) {
            System.out.println("Filter : " + filter);
        }
    }

    @Test
    public void parseFamilyQuery() {
        Query query;
        SampleIndexQuery indexQuery;

        query = new Query(SAMPLE.key(), "fam1_child;fam1_father;fam1_mother");
        indexQuery = parse(query);
        assertEquals(Collections.singleton("fam1_child"), indexQuery.getSamplesMap().keySet());
        assertEquals(1, indexQuery.getFatherFilterMap().size());
        assertFalse(isValidParam(query, GENOTYPE));

        query = new Query(GENOTYPE.key(), "fam2_child:0/1;fam2_father:0/1;fam2_mother:0/1,0/0");
        indexQuery = parse(query);
        assertEquals(Collections.singleton("fam2_child"), indexQuery.getSamplesMap().keySet());
        assertEquals(1, indexQuery.getFatherFilterMap().size());
        assertEquals(1, indexQuery.getMotherFilterMap().size());
        assertEquals(true, indexQuery.getMotherFilter("fam2_child")[GenotypeCodec.HOM_REF_UNPHASED]);
        assertEquals(true, indexQuery.getMotherFilter("fam2_child")[GenotypeCodec.HET_REF_UNPHASED]);
        // Family2 members are from different files. Can't exclude genotype filter
        assertTrue(isValidParam(query, GENOTYPE));

        query = new Query(GENOTYPE.key(), "fam1_child:0/1;fam1_father:0/1;fam1_mother:0/1,0/0");
        indexQuery = parse(query);
        assertEquals(Collections.singleton("fam1_child"), indexQuery.getSamplesMap().keySet());
        assertEquals(1, indexQuery.getFatherFilterMap().size());
        assertEquals(1, indexQuery.getMotherFilterMap().size());
        assertEquals(true, indexQuery.getMotherFilter("fam1_child")[GenotypeCodec.HOM_REF_UNPHASED]);
        assertEquals(true, indexQuery.getMotherFilter("fam1_child")[GenotypeCodec.HET_REF_UNPHASED]);
        assertFalse(isValidParam(query, GENOTYPE));

        // Can not use family query with OR operator
        query = new Query(SAMPLE.key(), "fam1_child,fam1_father,fam1_mother");
        indexQuery = parse(query);
        assertEquals(3, indexQuery.getSamplesMap().size());

        // Can not use family query with non valid genotypes in the child
        query = new Query(GENOTYPE.key(), "fam1_child:0/0;fam1_father:0/1;fam1_mother:0/1");
        indexQuery = parse(query);
        assertEquals(3, indexQuery.getSamplesMap().size());
        assertTrue(indexQuery.isNegated("fam1_child"));
        assertFalse(indexQuery.isNegated("fam1_father"));
        assertFalse(indexQuery.isNegated("fam1_mother"));
    }

    @Test
    public void parseFamilyQuery_filter() {
        Query query;
        SampleIndexQuery indexQuery;

        query = new Query(SAMPLE.key(), "fam1_child;fam1_father;fam1_mother").append(FILTER.key(), "PASS");
        indexQuery = parse(query);
        assertEquals(Collections.singleton("fam1_child"), indexQuery.getSamplesMap().keySet());
        assertEquals(1, indexQuery.getFatherFilterMap().size());
        assertFalse(isValidParam(query, FILTER));
        assertFalse(isValidParam(query, GENOTYPE));

        // Samples from family2 are not in the same file, so we can not remove the FILTER parameter
        query = new Query(SAMPLE.key(), "fam2_child;fam2_father;fam2_mother").append(FILTER.key(), "PASS");
        indexQuery = parse(query);
        assertEquals(Collections.singleton("fam2_child"), indexQuery.getSamplesMap().keySet());
        assertEquals(1, indexQuery.getFatherFilterMap().size());
        assertTrue(isValidParam(query, FILTER));
        assertFalse(isValidParam(query, GENOTYPE));
    }

    @Test
    public void parseFamilyQuery_dp() {
        Query query;
        SampleIndexQuery indexQuery;

        query = new Query()
                .append(SAMPLE.key(), "fam1_child;fam1_father;fam1_mother")
                .append(SAMPLE_DATA.key(), "fam1_father:DP>15;fam1_child:DP>=15;fam1_mother:DP>15");
        indexQuery = parse(query);

        assertEquals(Collections.singleton("fam1_child"), indexQuery.getSamplesMap().keySet());
        assertEquals(1, indexQuery.getFatherFilterMap().size());
        assertTrue(indexQuery.getSampleFileIndexQuery("fam1_child").get(0).getFilter(IndexFieldConfiguration.Source.SAMPLE, "DP").isExactFilter());
        assertEquals("fam1_father:DP>15;fam1_mother:DP>15", query.getString(SAMPLE_DATA.key()));

    }

    @Test
    public void parseAnnotationMaskTest() {
        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query()));
        for (VariantType value : VariantType.values()) {
            assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(TYPE.key(), value)));
        }

        assertEquals(INTERGENIC_MASK | PROTEIN_CODING_MASK, parseAnnotationMask(new Query(ANNOT_BIOTYPE.key(), "protein_coding")));
        assertEquals(INTERGENIC_MASK, parseAnnotationMask(new Query(ANNOT_BIOTYPE.key(), "protein_coding,miRNA"))); // Do not use PROTEIN_CODING_MASK when filtering by other biotypes
        assertEquals(INTERGENIC_MASK, parseAnnotationMask(new Query(ANNOT_BIOTYPE.key(), "other_than_protein_coding")));
        assertEquals(LOF_EXTENDED_MASK, parseAnnotationMask(new Query(ANNOT_PROTEIN_SUBSTITUTION.key(), "sift<0.1")));
        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(ANNOT_PROTEIN_SUBSTITUTION.key(), "sift<<0.1")));

        assertEquals(INTERGENIC_MASK | MISSENSE_VARIANT_MASK | LOF_EXTENDED_MASK, parseAnnotationMask(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "missense_variant")));
        assertEquals(INTERGENIC_MASK | LOF_EXTENDED_MASK, parseAnnotationMask(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_lost,missense_variant"))); // Do not use MISSENSE_VARIANT_MASK when filtering by other CTs
        assertEquals(INTERGENIC_MASK, parseAnnotationMask(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_lost,missense_variant,3_prime_UTR_variant"))); // Do not use LOF_MASK  nor LOF_EXTENDED_MASK when filtering by other CTs
        assertEquals(INTERGENIC_MASK | LOF_MASK | LOF_EXTENDED_MASK, parseAnnotationMask(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_lost")));
        assertEquals(INTERGENIC_MASK | LOF_MASK | LOF_EXTENDED_MASK, parseAnnotationMask(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_lost,stop_gained")));

//        assertEquals(INTERGENIC_MASK | LOF_EXTENDED_MASK | LOF_EXTENDED_BASIC_MASK, parseAnnotationMask(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "missense_variant,stop_lost,stop_gained")
//                .append(ANNOT_TRANSCRIPT_FLAG.key(), "basic")));
//        assertEquals(INTERGENIC_MASK | LOF_MASK | LOF_EXTENDED_MASK | LOF_EXTENDED_BASIC_MASK, parseAnnotationMask(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_lost,stop_gained")
//                .append(ANNOT_TRANSCRIPT_FLAG.key(), "basic")));

        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "1kG_phase3:ALL<0.01")));
        assertEquals(POP_FREQ_ANY_001_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "1kG_phase3:ALL<0.001")));
        assertEquals(POP_FREQ_ANY_001_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "GNOMAD_GENOMES:ALL<0.001")));
        assertEquals(POP_FREQ_ANY_001_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "GNOMAD_GENOMES:ALL<0.0005")));
        assertEquals(POP_FREQ_ANY_001_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "1kG_phase3:ALL<0.001,GNOMAD_GENOMES:ALL<0.001")));
        assertEquals(POP_FREQ_ANY_001_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "1kG_phase3:ALL<0.001;GNOMAD_GENOMES:ALL<0.001")));

        // Mix
        assertEquals(POP_FREQ_ANY_001_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "1kG_phase3:ALL<0.1;GNOMAD_GENOMES:ALL<0.001")));
        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "1kG_phase3:ALL<0.1,GNOMAD_GENOMES:ALL<0.001")));
        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "1kG_phase3:ALL<0.1;GNOMAD_GENOMES:ALL<0.1")));


        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(),
                  "GNOMAD_EXOMES:AFR<0.01;"
                + "GNOMAD_EXOMES:AMR<0.01;"
                + "GNOMAD_EXOMES:EAS<0.01;"
                + "GNOMAD_EXOMES:FIN<0.01;"
                + "GNOMAD_EXOMES:NFE<0.01;"
                + "GNOMAD_EXOMES:ASJ<0.01;"
                + "GNOMAD_EXOMES:OTH<0.01;"
                + "1kG_phase3:AFR<0.01;"
                + "1kG_phase3:AMR<0.01;"
                + "1kG_phase3:EAS<0.01;"
                + "1kG_phase3:EUR<0.01;"
                + "1kG_phase3:SAS<0.01")));

        // Removing any populations should not use the filter
        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(),
                  "GNOMAD_EXOMES:AFR<0.01;"
                + "GNOMAD_EXOMES:AMR<0.01;"
//                + "GNOMAD_EXOMES:EAS<0.01;"
                + "GNOMAD_EXOMES:FIN<0.01;"
                + "GNOMAD_EXOMES:NFE<0.01;"
                + "GNOMAD_EXOMES:ASJ<0.01;"
                + "GNOMAD_EXOMES:OTH<0.01;"
                + "1kG_phase3:AFR<0.01;"
                + "1kG_phase3:AMR<0.01;"
                + "1kG_phase3:EAS<0.01;"
                + "1kG_phase3:EUR<0.01;"
                + "1kG_phase3:SAS<0.01")));

        // With OR instead of AND should not use the filter
        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(),
                  "GNOMAD_EXOMES:AFR<0.01,"
                + "GNOMAD_EXOMES:AMR<0.01,"
                + "GNOMAD_EXOMES:EAS<0.01,"
                + "GNOMAD_EXOMES:FIN<0.01,"
                + "GNOMAD_EXOMES:NFE<0.01,"
                + "GNOMAD_EXOMES:ASJ<0.01,"
                + "GNOMAD_EXOMES:OTH<0.01,"
                + "1kG_phase3:AFR<0.01,"
                + "1kG_phase3:AMR<0.01,"
                + "1kG_phase3:EAS<0.01,"
                + "1kG_phase3:EUR<0.01,"
                + "1kG_phase3:SAS<0.01")));

        // With any filter greater than 0.1, should not use the filter
        assertEquals(EMPTY_MASK, parseAnnotationMask(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(),
                  "GNOMAD_EXOMES:AFR<0.01;"
                + "GNOMAD_EXOMES:AMR<0.02;" // Increased from 0.01 to 0.02
                + "GNOMAD_EXOMES:EAS<0.01;"
                + "GNOMAD_EXOMES:FIN<0.01;"
                + "GNOMAD_EXOMES:NFE<0.01;"
                + "GNOMAD_EXOMES:ASJ<0.01;"
                + "GNOMAD_EXOMES:OTH<0.01;"
                + "1kG_phase3:AFR<0.01;"
                + "1kG_phase3:AMR<0.01;"
                + "1kG_phase3:EAS<0.01;"
                + "1kG_phase3:EUR<0.01;"
                + "1kG_phase3:SAS<0.01")));

    }

    @Test
    public void parseIntergenicTest() {
        checkIntergenic(null, new Query());

        checkIntergenic(false, new Query(ANNOT_BIOTYPE.key(), "protein_coding"));
        checkIntergenic(false, new Query(ANNOT_BIOTYPE.key(), "miRNA"));

        checkIntergenic(false, new Query(GENE.key(), "BRCA2"));
        checkIntergenic(false, new Query(ANNOT_XREF.key(), "BRCA2"));
        checkIntergenic(false, new Query(ANNOT_XREF.key(), "ENSG000000"));
        checkIntergenic(false, new Query(ANNOT_XREF.key(), "ENST000000"));

        checkIntergenic(null, new Query(ANNOT_XREF.key(), "1:1234:A:C"));
        checkIntergenic(null, new Query(ANNOT_XREF.key(), "rs12345"));
        checkIntergenic(null, new Query(ID.key(), "1:1234:A:C"));
        checkIntergenic(null, new Query(ID.key(), "rs12345"));

        checkIntergenic(null, new Query(GENE.key(), "BRCA2").append(REGION.key(), "1:123-1234"));
        checkIntergenic(null, new Query(GENE.key(), "BRCA2").append(ID.key(), "rs12345"));
        checkIntergenic(false, new Query(GENE.key(), "BRCA2").append(REGION.key(), "1:123-1234").append(ANNOT_BIOTYPE.key(), "protein_coding"));
        checkIntergenic(false, new Query(GENE.key(), "BRCA2").append(ID.key(), "rs12345").append(ANNOT_BIOTYPE.key(), "protein_coding"));

        checkIntergenic(false, new Query(ANNOT_CONSEQUENCE_TYPE.key(), "missense_variant"));
        checkIntergenic(false, new Query(ANNOT_CONSEQUENCE_TYPE.key(), "missense_variant,synonymous_variant"));
        checkIntergenic(true, new Query(ANNOT_CONSEQUENCE_TYPE.key(), "intergenic_variant"));
        checkIntergenic(null, new Query(ANNOT_CONSEQUENCE_TYPE.key(), "missense_variant,intergenic_variant"));
        checkIntergenic(null, new Query(ANNOT_CONSEQUENCE_TYPE.key(), "intergenic_variant,missense_variant"));

        // Nonsense combination
        checkIntergenic(false, new Query(ANNOT_CONSEQUENCE_TYPE.key(), "intergenic_variant").append(ANNOT_BIOTYPE.key(), "protein_coding"));
    }

    private void checkIntergenic(Boolean intergenic, Query query) {
        SampleAnnotationIndexQuery annotationIndexQuery = parseAnnotationIndexQuery(query);
        if (intergenic == null) {
            assertEquals(EMPTY_MASK, INTERGENIC_MASK & annotationIndexQuery.getAnnotationIndexMask());
        } else {
            assertEquals(INTERGENIC_MASK, INTERGENIC_MASK & annotationIndexQuery.getAnnotationIndexMask());
            if (intergenic) {
                assertEquals(INTERGENIC_MASK, INTERGENIC_MASK & annotationIndexQuery.getAnnotationIndex());
            } else {
                assertEquals(EMPTY_MASK, INTERGENIC_MASK & annotationIndexQuery.getAnnotationIndex());
            }
        }
    }

//    @Test
//    public void parseConsequenceTypeMaskTest() {
//        assertEquals(EMPTY_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "synonymous_variant")).getConsequenceTypeMask());
//        assertEquals(CT_MISSENSE_VARIANT_MASK | CT_START_LOST_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "missense_variant,start_lost")).getConsequenceTypeMask());
//        assertEquals(CT_START_LOST_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "start_lost")).getConsequenceTypeMask());
//        assertEquals(CT_STOP_GAINED_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_gained")).getConsequenceTypeMask());
//        assertEquals(CT_STOP_LOST_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_lost")).getConsequenceTypeMask());
//        assertEquals(CT_STOP_GAINED_MASK | CT_STOP_LOST_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_gained,stop_lost")).getConsequenceTypeMask());
//
//        assertEquals(CT_MISSENSE_VARIANT_MASK | CT_STOP_LOST_MASK | CT_UTR_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_lost,missense_variant,3_prime_UTR_variant")).getConsequenceTypeMask());
//        assertEquals(INTERGENIC_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "stop_lost,missense_variant,3_prime_UTR_variant")).getAnnotationIndexMask());
//
//        // CT Filter covered by summary
//        assertEquals(EMPTY_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), "missense_variant")).getConsequenceTypeMask());
//        assertEquals(((short) LOF_SET.stream().mapToInt(AnnotationIndexConverter::getMaskFromSoName).reduce((a, b) -> a | b).getAsInt()),
//                parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), LOF_SET)).getConsequenceTypeMask());
//        assertEquals(EMPTY_MASK, parseAnnotationIndexQuery(new Query(ANNOT_CONSEQUENCE_TYPE.key(), LOF_EXTENDED_SET)).getConsequenceTypeMask());
//
//    }
//
//    @Test
//    public void parseBiotypeTypeMaskTest() {
//        assertEquals(BT_OTHER_NON_PSEUDOGENE, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "other_biotype")).getBiotypeMask());
//        assertEquals(BT_PROTEIN_CODING_MASK | BT_MIRNA_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "protein_coding,miRNA")).getBiotypeMask());
//        assertEquals(BT_MIRNA_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "miRNA")).getBiotypeMask());
//        assertEquals(BT_LNCRNA_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "lncRNA")).getBiotypeMask());
//        assertEquals(BT_LNCRNA_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "lincRNA")).getBiotypeMask());
//        assertEquals(BT_LNCRNA_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "lncRNA,lincRNA")).getBiotypeMask());
//        assertEquals(BT_OTHER_NON_PSEUDOGENE | BT_PROTEIN_CODING_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "other_biotype,protein_coding")).getBiotypeMask());
//        assertEquals(0, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "other_biotype,protein_coding,pseudogene")).getBiotypeMask());
//
//        // Ensure PROTEIN_CODING_MASK is not added to the summary
//        assertEquals(INTERGENIC_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "protein_coding,miRNA")).getAnnotationIndexMask());
//        assertEquals(BT_PROTEIN_CODING_MASK | BT_MIRNA_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "protein_coding,miRNA")).getBiotypeMask());
//
//        // Biotype Filter covered by summary
//        assertEquals(EMPTY_MASK, parseAnnotationIndexQuery(new Query(ANNOT_BIOTYPE.key(), "protein_coding")).getBiotypeMask());
//    }

    @Test
    public void parsePopFreqQueryTest() {
        double[] default_ranges = configuration.getAnnotationIndexConfiguration().getPopulationFrequency().getThresholds();
        for (int i = 0; i < default_ranges.length; i++) {
            RangeIndexFieldFilter q;
            double r = default_ranges[i];
//            System.out.println("--------------");
//            System.out.println(r);

            final double d = DELTA * 10;

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL<" + (r - d))).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(0, q.getMinCodeInclusive());
            assertEquals(i + 1, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL<=" + (r - d))).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(0, q.getMinCodeInclusive());
            assertEquals(i + 1, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL<" + r)).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(0, q.getMinCodeInclusive());
            assertEquals(i + 1, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL<=" + r)).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(0, q.getMinCodeInclusive());
            assertEquals(i + 2, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL<" + (r + d))).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(0, q.getMinCodeInclusive());
            assertEquals(i + 2, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608


            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL>=" + r)).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(i + 1, q.getMinCodeInclusive());
            assertEquals(4, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL>" + r)).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(i + 1, q.getMinCodeInclusive());
            assertEquals(4, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL>" + (r + d))).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(i + 1, q.getMinCodeInclusive());
            assertEquals(4, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL>=" + (r + d))).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(i + 1, q.getMinCodeInclusive());
            assertEquals(4, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL>" + (r - d))).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(i, q.getMinCodeInclusive());
            assertEquals(4, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

            q = (RangeIndexFieldFilter) parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s" + (i % 4 + 1) + ":ALL>=" + (r - d))).getPopulationFrequencyFilter().getFilters().get(0);
//            System.out.println(q);
            assertEquals(i, q.getMinCodeInclusive());
            assertEquals(4, q.getMaxCodeExclusive());
//            assertEquals(i % 4 + 1 + 1, q.getPosition()); //FIXME _ 20210608

        }
        SampleAnnotationIndexQuery q = parseAnnotationIndexQuery(new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s8:NONE>0.1"));
        assertEquals(0, q.getPopulationFrequencyFilter().getFilters().size());

        Query query = new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s2:ALL<0.01;s8:NONE<0.01");
        q = parseAnnotationIndexQuery(query, true);
        assertEquals(1, q.getPopulationFrequencyFilter().getFilters().size());
        assertEquals(null, q.getPopulationFrequencyFilter().getOp());
        assertEquals(true, q.getPopulationFrequencyFilter().isExactFilter());
        assertEquals(false, query.isEmpty());

        // Partial OR queries can not be used
        query = new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s2:ALL<0.01,s8:NONE<0.01");
        q = parseAnnotationIndexQuery(query, true);
        assertEquals(0, q.getPopulationFrequencyFilter().getFilters().size());
        assertEquals(null, q.getPopulationFrequencyFilter().getOp());
        assertEquals(false, q.getPopulationFrequencyFilter().isExactFilter());
        assertEquals(false, query.isEmpty());

        query = new Query(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s2:ALL<0.01,s3:ALL<0.01");
        q = parseAnnotationIndexQuery(query, true);
        assertEquals(2, q.getPopulationFrequencyFilter().getFilters().size());
        assertEquals(QueryOperation.OR, q.getPopulationFrequencyFilter().getOp());
        assertEquals(true, q.getPopulationFrequencyFilter().isExactFilter());
        System.out.println("query.toJson() = " + query.toJson());
        assertEquals(true, query.isEmpty());
    }

    @Test
    public void testGetRangeQuery() {
        RangeQuery rangeQuery;

        double[] thresholds = {1, 2, 3};
        double[] thresholds2 = {0.5, 1.5, 2.5};
        for (int i = 0; i <= thresholds.length; i++) {
            rangeQuery = sampleIndexQueryParser.getRangeQuery(">", i, thresholds, 0, 100);
            assertEquals(i, rangeQuery.getMinCodeInclusive());
            assertEquals(4, rangeQuery.getMaxCodeExclusive());
            assertEquals(false, rangeQuery.isExactQuery());

            rangeQuery = sampleIndexQueryParser.getRangeQuery(">=", i, thresholds, 0, 100);
            assertEquals(i, rangeQuery.getMinCodeInclusive());
            assertEquals(4, rangeQuery.getMaxCodeExclusive());
            assertEquals(true, rangeQuery.isExactQuery());

            rangeQuery = sampleIndexQueryParser.getRangeQuery(">=", i, thresholds2, 0, 100);
            assertEquals(i, rangeQuery.getMinCodeInclusive());
            assertEquals(4, rangeQuery.getMaxCodeExclusive());
            assertEquals(i == 0, rangeQuery.isExactQuery());

            rangeQuery = sampleIndexQueryParser.getRangeQuery(">=", i, thresholds2, -1, 100);
            assertEquals(i, rangeQuery.getMinCodeInclusive());
            assertEquals(4, rangeQuery.getMaxCodeExclusive());
            assertEquals(false, rangeQuery.isExactQuery());

            rangeQuery = sampleIndexQueryParser.getRangeQuery("<", i, thresholds, 0, 100);
            assertEquals(0, rangeQuery.getMinCodeInclusive());
            assertEquals(Math.max(i, 1), rangeQuery.getMaxCodeExclusive());
            assertEquals(i != 0, rangeQuery.isExactQuery());

            rangeQuery = sampleIndexQueryParser.getRangeQuery("<", i, thresholds2, 0, 100);
            assertEquals(0, rangeQuery.getMinCodeInclusive());
            assertEquals(i + 1, rangeQuery.getMaxCodeExclusive());
            assertEquals(false, rangeQuery.isExactQuery());

            rangeQuery = sampleIndexQueryParser.getRangeQuery("<", i, thresholds2, 0, 3);
            assertEquals(0, rangeQuery.getMinCodeInclusive());
            assertEquals(i + 1, rangeQuery.getMaxCodeExclusive());
            assertEquals(i == 3, rangeQuery.isExactQuery());

            rangeQuery = sampleIndexQueryParser.getRangeQuery("<=", i, thresholds, 0, 100);
            assertEquals(0, rangeQuery.getMinCodeInclusive());
            assertEquals(i + 1, rangeQuery.getMaxCodeExclusive());
            assertEquals(false, rangeQuery.isExactQuery());
        }

        rangeQuery = sampleIndexQueryParser.getRangeQuery("<", 100, thresholds, 0, 100);
        assertEquals(0, rangeQuery.getMinCodeInclusive());
        assertEquals(4, rangeQuery.getMaxCodeExclusive());
        assertEquals(true, rangeQuery.isExactQuery());

        rangeQuery = sampleIndexQueryParser.getRangeQuery("<", 50, thresholds, 0, 100);
        assertEquals(0, rangeQuery.getMinCodeInclusive());
        assertEquals(4, rangeQuery.getMaxCodeExclusive());
        assertEquals(false, rangeQuery.isExactQuery());

        rangeQuery = sampleIndexQueryParser.getRangeQuery(">=", -100, thresholds, -100, 100);
        assertEquals(0, rangeQuery.getMinCodeInclusive());
        assertEquals(4, rangeQuery.getMaxCodeExclusive());
        assertEquals(true, rangeQuery.isExactQuery());

        rangeQuery = sampleIndexQueryParser.getRangeQuery(">=", -50, thresholds, -100, 100);
        assertEquals(0, rangeQuery.getMinCodeInclusive());
        assertEquals(4, rangeQuery.getMaxCodeExclusive());
        assertEquals(false, rangeQuery.isExactQuery());
    }

    @Test
    public void testCoveredQuery_ct() {
        Query query;
        SampleAnnotationIndexQuery indexQuery;

        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), "missense_variant");
        parseAnnotationIndexQuery(query, true);
        assertTrue(query.isEmpty());

        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantQueryUtils.LOF_SET));
        parseAnnotationIndexQuery(query, true);
        assertTrue(query.isEmpty());

        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantQueryUtils.LOF_EXTENDED_SET));
        parseAnnotationIndexQuery(query, true);
        assertTrue(query.isEmpty());

        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantQueryUtils.LOF_EXTENDED_SET));
        parseAnnotationIndexQuery(query, false);
        assertFalse(query.isEmpty()); // Not all samples annotated

        // Use CT column
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantAnnotationConstants.STOP_LOST));
        parseAnnotationIndexQuery(query, true);
        assertTrue(query.isEmpty());

        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, new ArrayList<>(VariantQueryUtils.LOF_EXTENDED_SET).subList(2, 4)));
        parseAnnotationIndexQuery(query, true);
        assertTrue(query.isEmpty());

        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantAnnotationConstants.STOP_LOST));
        parseAnnotationIndexQuery(query, false);
        indexQuery = parseAnnotationIndexQuery(query, false);
        assertFalse(indexQuery.getConsequenceTypeFilter().isNoOp());
        assertFalse(query.isEmpty()); // Index not complete

//        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantAnnotationConstants.MATURE_MIRNA_VARIANT));
//        indexQuery = parseAnnotationIndexQuery(query, true);
//        assertFalse(indexQuery.getConsequenceTypeFilter().isNoOp());
//        assertFalse(query.isEmpty()); // Imprecise CT value
    }

    @Test
    public void testCoveredQuery_ct_tf() {
        Query query;
        SampleAnnotationIndexQuery indexQuery;
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), VariantAnnotationConstants.MISSENSE_VARIANT).append(ANNOT_TRANSCRIPT_FLAG.key(), "basic");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertFalse(indexQuery.getConsequenceTypeFilter().isNoOp());
        assertFalse(indexQuery.getTranscriptFlagFilter().isNoOp());
        assertFalse(indexQuery.getCtTfFilter().isNoOp());
        assertTrue(query.isEmpty());
    }

    @Test
    public void testCoveredQuery_tf() {
        Query query;
        SampleAnnotationIndexQuery indexQuery;
        query = new Query().append(ANNOT_TRANSCRIPT_FLAG.key(), "basic");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertTrue(indexQuery.getConsequenceTypeFilter().isNoOp());
        assertFalse(indexQuery.getTranscriptFlagFilter().isNoOp());
        assertTrue(indexQuery.getTranscriptFlagFilter().isExactFilter());
        assertTrue(indexQuery.getCtTfFilter().isNoOp());
        assertTrue(query.isEmpty());

        // Imprecise query
        query = new Query().append(ANNOT_TRANSCRIPT_FLAG.key(), "seleno");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertTrue(indexQuery.getConsequenceTypeFilter().isNoOp());
        assertFalse(indexQuery.getTranscriptFlagFilter().isNoOp());
        assertFalse(indexQuery.getTranscriptFlagFilter().isExactFilter());
        assertTrue(indexQuery.getCtTfFilter().isNoOp());
        assertFalse(query.isEmpty());
    }


    @Test
    public void testCoveredQuery_biotype() {
        Query query;
        SampleAnnotationIndexQuery indexQuery;

        query = new Query().append(ANNOT_BIOTYPE.key(), PROTEIN_CODING);
        parseAnnotationIndexQuery(query, true);
        assertTrue(query.isEmpty());

        query = new Query().append(ANNOT_BIOTYPE.key(), PROTEIN_CODING + "," + VariantAnnotationConstants.MIRNA);
        parseAnnotationIndexQuery(query, true);
        assertTrue(query.isEmpty());

        query = new Query().append(ANNOT_BIOTYPE.key(), PROTEIN_CODING + "," + VariantAnnotationConstants.MIRNA);
        parseAnnotationIndexQuery(query, false);
        assertFalse(query.isEmpty()); // Index not complete

        query = new Query().append(ANNOT_BIOTYPE.key(), VariantAnnotationConstants.LINCRNA);
        parseAnnotationIndexQuery(query, true);
        assertFalse(query.isEmpty()); // Imprecise BT value
    }

    @Test
    public void testCoveredQuery_popFreq() {
        Query query;
        SampleAnnotationIndexQuery indexQuery;

        // Query fully covered by summary index.
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), String.join(OR, new ArrayList<>(AnnotationIndexConverter.POP_FREQ_ANY_001_FILTERS)));
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(0, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertEquals(true, indexQuery.getPopulationFrequencyFilter().isNoOp());
        assertTrue(query.isEmpty());

        // Partial summary usage. Also use PopFreqIndex. Clear query
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "GNOMAD_GENOMES:ALL<" + POP_FREQ_THRESHOLD_001);
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(1, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertTrue(query.isEmpty());

        // Partial summary usage, filter more restrictive. Also use PopFreqIndex. Clear query
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "GNOMAD_GENOMES:ALL<" + POP_FREQ_THRESHOLD_001 / 2);
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(1, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertFalse(query.isEmpty());

        // Summary filter less restrictive. Do not use summary. Only use PopFreqIndex. Do not clear query
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "GNOMAD_GENOMES:ALL<" + POP_FREQ_THRESHOLD_001 * 2);
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(1, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertFalse(query.isEmpty());

        // Summary index query plus a new filter. Use only popFreqIndex
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), String.join(OR, new ArrayList<>(AnnotationIndexConverter.POP_FREQ_ANY_001_FILTERS)) + OR + "s1:ALL<0.005");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(QueryOperation.OR, indexQuery.getPopulationFrequencyFilter().getOp());
        assertEquals(3, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertTrue(query.isEmpty());

        // Summary index query with AND instead of OR filter. Use both, summary and popFreqIndex
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), String.join(AND, new ArrayList<>(AnnotationIndexConverter.POP_FREQ_ANY_001_FILTERS)));
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(QueryOperation.AND, indexQuery.getPopulationFrequencyFilter().getOp());
        assertEquals(2, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertTrue(query.isEmpty());

        // Summary index query with AND instead of OR filter plus a new filter. Use both, summary and popFreqIndex. Leave eextra filter in query
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), String.join(AND, new ArrayList<>(AnnotationIndexConverter.POP_FREQ_ANY_001_FILTERS)) + AND + "s1:ALL<0.05");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(QueryOperation.AND, indexQuery.getPopulationFrequencyFilter().getOp());
        assertEquals(3, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertEquals("s1:ALL<0.05", query.getString(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key()));

        // Summary index query with AND instead of OR filter plus a new filter. Use both, summary and popFreqIndex. Clear covered query
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), String.join(AND, new ArrayList<>(AnnotationIndexConverter.POP_FREQ_ANY_001_FILTERS)) + AND + "s1:ALL<0.005");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(QueryOperation.AND, indexQuery.getPopulationFrequencyFilter().getOp());
        assertEquals(3, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertTrue(query.isEmpty());

        // Intersect (AND) with an extra study not in index. Don't use summary, PopFreqIndex, and leave other filters in the query
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "s1:ALL<" + POP_FREQ_THRESHOLD_001 + AND + "OtherStudy:ALL<0.8");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(null, indexQuery.getPopulationFrequencyFilter().getOp());
        assertEquals(1, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertEquals("s1:ALL", indexQuery.getPopulationFrequencyFilter().getFilters().get(0).getIndex().getConfiguration().getKey());
        assertEquals("OtherStudy:ALL<0.8", query.getString(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key()));

        // Intersect (AND) with an extra study not in index. Use summary , PopFreqIndex, and leave other filters in the query
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "GNOMAD_GENOMES:ALL<" + POP_FREQ_THRESHOLD_001 + AND + "OtherStudy:ALL<0.8");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(POP_FREQ_ANY_001_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(null, indexQuery.getPopulationFrequencyFilter().getOp());
        assertEquals(1, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertEquals("GNOMAD_GENOMES:ALL", indexQuery.getPopulationFrequencyFilter().getFilters().get(0).getIndex().getConfiguration().getKey());
        assertEquals("OtherStudy:ALL<0.8", query.getString(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key()));

        // Union (OR) with an extra study not in index. Do not use index at all
        query = new Query().append(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key(), "GNOMAD_GENOMES:ALL<" + POP_FREQ_THRESHOLD_001 + OR + "OtherStudy:ALL<0.8");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndexMask() & POP_FREQ_ANY_001_MASK);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndex() & POP_FREQ_ANY_001_MASK);
        assertEquals(null, indexQuery.getPopulationFrequencyFilter().getOp());
        assertEquals(0, indexQuery.getPopulationFrequencyFilter().getFilters().size());
        assertEquals("GNOMAD_GENOMES:ALL<" + POP_FREQ_THRESHOLD_001 + OR + "OtherStudy:ALL<0.8", query.getString(ANNOT_POPULATION_ALTERNATE_FREQUENCY.key()));
    }

    @Test
    public void testCoveredQuery_combined() {
        Query query;
        SampleAnnotationIndexQuery indexQuery;

        //  LoFE + gene -> Use LOFE mask. Do not clear query, as it's using genes
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantQueryUtils.LOF_EXTENDED_SET))
                .append(GENE.key(), "BRCA2");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndex() & LOFE_PROTEIN_CODING_MASK);
        assertTrue(VariantQueryUtils.isValidParam(query, ANNOT_CONSEQUENCE_TYPE));
        assertTrue(VariantQueryUtils.isValidParam(query, GENE));

        // LoFE + protein_coding -> Use summary mask. Fully covered (clear query)
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantQueryUtils.LOF_EXTENDED_SET))
                .append(ANNOT_BIOTYPE.key(), "protein_coding");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(LOFE_PROTEIN_CODING_MASK, indexQuery.getAnnotationIndex() & LOFE_PROTEIN_CODING_MASK);
        assertFalse(VariantQueryUtils.isValidParam(query, ANNOT_CONSEQUENCE_TYPE));
        assertFalse(VariantQueryUtils.isValidParam(query, ANNOT_BIOTYPE));

        //  LoFE + protein_coding + gene -> Use summary mask. Do not clear query, as it's using genes
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantQueryUtils.LOF_EXTENDED_SET))
                .append(ANNOT_BIOTYPE.key(), "protein_coding")
                .append(GENE.key(), "BRCA2");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(LOFE_PROTEIN_CODING_MASK, indexQuery.getAnnotationIndex() & LOFE_PROTEIN_CODING_MASK);
        assertTrue(VariantQueryUtils.isValidParam(query, ANNOT_CONSEQUENCE_TYPE));
        assertTrue(VariantQueryUtils.isValidParam(query, ANNOT_BIOTYPE));
        assertTrue(VariantQueryUtils.isValidParam(query, GENE));

        // LoFE subset + protein_coding -> Use summary mask.
        // Not fully covered by Summary, still has to use both CT and BT indices
        // The combination is covered, so the params should be removed from the query
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, new ArrayList<>(VariantQueryUtils.LOF_EXTENDED_SET).subList(0, 5)))
                .append(ANNOT_BIOTYPE.key(), "protein_coding");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(LOFE_PROTEIN_CODING_MASK, indexQuery.getAnnotationIndex() & LOFE_PROTEIN_CODING_MASK);
        assertFalse(indexQuery.getBiotypeFilter().isNoOp());
        assertFalse(indexQuery.getConsequenceTypeFilter().isNoOp());
        assertFalse(VariantQueryUtils.isValidParam(query, ANNOT_CONSEQUENCE_TYPE));
        assertFalse(VariantQueryUtils.isValidParam(query, ANNOT_BIOTYPE));

        // LoFE + protein_coding + others -> Can not use summary mask
        // Not fully covered by Summary, still has to use both CT and BT indices
        // The combination is covered, so the params should be removed from the query
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantQueryUtils.LOF_EXTENDED_SET))
                .append(ANNOT_BIOTYPE.key(), "protein_coding,miRNA");
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertEquals(EMPTY_MASK, indexQuery.getAnnotationIndex() & LOFE_PROTEIN_CODING_MASK);
        assertFalse(indexQuery.getBiotypeFilter().isNoOp());
        assertFalse(indexQuery.getConsequenceTypeFilter().isNoOp());
        assertFalse(VariantQueryUtils.isValidParam(query, ANNOT_CONSEQUENCE_TYPE));
        assertFalse(VariantQueryUtils.isValidParam(query, ANNOT_BIOTYPE));

        // Use of imprecise CT.
        // Has to use both CT and BT indices
        // The combination is covered
        // The params can not be removed from the query, as the CT is filter is only an approximation
        // BT has to remain to check the combination.
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), VariantAnnotationConstants.FIVE_PRIME_UTR_VARIANT)
                .append(ANNOT_BIOTYPE.key(), "protein_coding,miRNA");
        indexQuery = parseAnnotationIndexQuery(query, true);
        SampleIndexDBAdaptor.printQuery(indexQuery);
        assertFalse(indexQuery.getBiotypeFilter().isNoOp());
        assertFalse(indexQuery.getConsequenceTypeFilter().isNoOp());
        assertFalse(indexQuery.getConsequenceTypeFilter().isExactFilter());
        assertTrue(VariantQueryUtils.isValidParam(query, ANNOT_CONSEQUENCE_TYPE));
        assertTrue(VariantQueryUtils.isValidParam(query, ANNOT_BIOTYPE));

        // Use of imprecise BT.
        // Has to use both CT and BT indices
        // The combination is covered
        // The params can not be removed from the query, as the BT is filter is only an approximation.
        // CT has to remain to check the combination.
        query = new Query().append(ANNOT_CONSEQUENCE_TYPE.key(), String.join(OR, VariantQueryUtils.LOF_EXTENDED_SET))
                .append(ANNOT_BIOTYPE.key(), ANTISENSE + "," + PROTEIN_CODING);
        indexQuery = parseAnnotationIndexQuery(query, true);
        assertFalse(indexQuery.getBiotypeFilter().isNoOp());
        assertFalse(indexQuery.getConsequenceTypeFilter().isNoOp());
        assertFalse(indexQuery.getBiotypeFilter().isExactFilter());
        assertTrue(VariantQueryUtils.isValidParam(query, ANNOT_CONSEQUENCE_TYPE));
        assertTrue(VariantQueryUtils.isValidParam(query, ANNOT_BIOTYPE));

    }

    @Test
    public void testGroupRegions() {
        List<List<Region>> groups = groupRegions(Arrays.asList(
                new Region("8", 144_700_000, 144_995_738),
                new Region("6", 33_200_000, 34_800_000),
                new Region("8", 144_671_680, 144_690_000),
                new Region("6", 31_200_000, 31_800_000),
                new Region("8", 145_100_000, 146_100_000)));
        assertEquals(Arrays.asList(
                Arrays.asList(new Region("6", 31_200_000, 31_800_000)),
                Arrays.asList(new Region("6", 33_200_000, 34_800_000)),
                Arrays.asList(new Region("8", 144_671_680, 144_690_000),
                        new Region("8", 144_700_000, 144_995_738),
                        new Region("8", 145_100_000, 146_100_000))
        ), groups);

        groups = groupRegions(Arrays.asList(
                new Region("6", 33_200_000, 34_800_000),
                new Region("6", 31_200_000, 33_800_000),
                new Region("8", 144_671_680, 144_690_000),
                new Region("8", 144_700_000, 144_995_738),
                new Region("8", 145_100_000, 146_100_000)));
        assertEquals(Arrays.asList(
                Arrays.asList(new Region("6", 31_200_000, 34_800_000)),
                Arrays.asList(new Region("8", 144_671_680, 144_690_000),
                        new Region("8", 144_700_000, 144_995_738),
                        new Region("8", 145_100_000, 146_100_000))
        ), groups);
    }
}