/*
 * Copyright 2015-2020 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.opencga.core.models.sample;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.opencb.biodata.models.clinical.Phenotype;
import org.opencb.commons.annotations.DataClass;
import org.opencb.commons.annotations.DataField;
import org.opencb.opencga.core.api.FieldConstants;
import org.opencb.opencga.core.models.common.Annotable;
import org.opencb.opencga.core.models.common.AnnotationSet;
import org.opencb.opencga.core.models.common.CustomStatus;
import org.opencb.opencga.core.models.common.ExternalSource;

import java.util.*;

/**
 * Sample data model hosts information about any biological material, normally extracted from an _Individual_, that is used for a particular
 * analysis. This is the main data model, it stores the most basic and important information.
 */
@DataClass(id = "Sample", since = "1.0",
        description = "Sample data model hosts information about any biological material, normally extracted from " +
                "an _Individual_, that is used for a particular analysis. This is the main data model, it stores the " +
                "most basic and important information.")
public class Sample extends Annotable {

    /**
     * Sample ID in the study, this must be unique in the study but can be repeated in different studies. This is a mandatory parameter when
     * creating a new sample, this ID cannot be changed at the moment.
     *
     * @apiNote Required, Immutable, Unique
     */
    @DataField(id = "id", required = true, indexed = true, unique = true, immutable = true,
            description = FieldConstants.SAMPLE_ID_DESCRIPTION)
    private String id;

    /**
     * Generic: Unique 32-character identifier assigned automatically by OpenCGA.
     *
     * @apiNote Immutable, Unique
     */
    @DataField(id = "uuid", managed = true, indexed = true, unique = true, immutable = true,
            description = FieldConstants.GENERIC_UUID_DESCRIPTION)
    private String uuid;

    /**
     * Generic: Unique 32-character identifier assigned automatically by OpenCGA.
     *
     * @apiNote Immutable, Unique
     */
    @DataField(id = "source", since = "2.2",
            description = FieldConstants.SAMPLE_EXTERNAL_SOURCE_DESCRIPTION)
    private ExternalSource source;

    /**
     * Describes how the sample was processed in the lab.
     */
    @DataField(id = "processing", since = "2.0",
            description = FieldConstants.SAMPLE_PROCESSING_DESCRIPTION)
    private SampleProcessing processing;

    /**
     * Describes how the sample was collected.
     *
     * @apiNote
     * @implNote Describes how the sample was collected.
     * @since 2.1
     */
    @DataField(id = "collection", since = "2.0",
            description = FieldConstants.SAMPLE_SAMPLE_COLLECTION_DESCRIPTION)
    private SampleCollection collection;

    /**
     * Contains different metrics to evaluate the quality of the sample.
     *
     * @apiNote
     * @implNote The sample collection is a list of samples
     * @see [ZetaGenomics] (https://www.zettagenomics.com)
     * @since 2.1
     */
    @DataField(id = "qualityControl", since = "2.0",
            description = FieldConstants.SAMPLE_QUALITY_CONTROL_DESCRIPTION)
    private SampleQualityControl qualityControl;

    /**
     * An integer describing the current data release.
     *
     * @apiNote Immutable
     */
    @DataField(id = "release", managed = true, indexed = true,
            description = FieldConstants.GENERIC_RELEASE_DESCRIPTION)
    private int release;

    /**
     * Generic: Autoincremental version assigned to the registered entry. By default, updates does not create new versions. To enable
     * versioning, users must set the `incVersion` flag from the /update web service when updating the document.
     *
     * @apiNote Immutable
     */
    @DataField(id = "version", managed = true, indexed = true,
            description = FieldConstants.GENERIC_VERSION_DESCRIPTION)
    private int version;

    /**
     * Generic: Autogenerated date following the format YYYYMMDDhhmmss containing the date when the entry was first registered.
     *
     * @apiNote Immutable
     */
    @DataField(id = "creationDate", indexed = true, since = "1.0",
            description = FieldConstants.GENERIC_CREATION_DATE_DESCRIPTION)
    private String creationDate;

    /**
     * Generic: Autogenerated date following the format YYYYMMDDhhmmss containing the date when the entry was last modified.
     *
     * @apiNote Immutable
     */
    @DataField(id = "modificationDate", indexed = true, since = "1.0",
            description = FieldConstants.GENERIC_MODIFICATION_DATE_DESCRIPTION)
    private String modificationDate;

    /**
     * Generic: Users may provide a description for the entry.
     */
    @DataField(id = "description", defaultValue = "No description available",
            description = FieldConstants.GENERIC_DESCRIPTION_DESCRIPTION)
    private String description;

    /**
     * Indicates if the sample is somatic or germline (default)
     *
     * @apiNote
     */
    @DataField(id = "somatic", indexed = true,
            description = FieldConstants.SAMPLE_SOMATIC_DESCRIPTION)
    private boolean somatic;

    @DataField(id = "phenotypes", alias = {"phenotypes.id", "phenotypesId"},
            description = FieldConstants.GENERIC_PHENOTYPES_DESCRIPTION)
    private List<Phenotype> phenotypes;

    @DataField(id = "individualId", indexed = true, alias = {"individual", "individual.id"},
            description = FieldConstants.SAMPLE_INDIVIDUAL_ID_DESCRIPTION)
    private String individualId;

    @DataField(id = "fileIds", indexed = true, alias = {"file", "file.id"},
            description = FieldConstants.SAMPLE_FILE_IDS_DESCRIPTION)
    private List<String> fileIds;

    @DataField(id = "cohortIds", indexed = true,
            description = FieldConstants.SAMPLE_COHORT_IDS_DESCRIPTION)
    private List<String> cohortIds;

    /**
     * Generic: Object to define the status of the entry.
     */
    @DataField(id = "status", since = "2.0",
            description = FieldConstants.SAMPLE_COHORT_IDS_DESCRIPTION)
    private CustomStatus status;

    /**
     * Generic: Field automatically managed by OpenCGA containing relevant information of the entry. This field is used for internal
     * purposes and is visible for users.
     *
     * @apiNote Immutable
     */
    @DataField(id = "internal", managed = true, since = "2.0",
            description = FieldConstants.SAMPLE_INTERNAL_DESCRIPTION)
    private SampleInternal internal;

    /**
     * @implNote This field is not meant to be queried. It should only contain extra information. To store additional information meant to
     * be queried, please use annotationSets.
     */
    @DataField(id = "attributes", since = "1.0",
            description = FieldConstants.GENERIC_ATTRIBUTES_DESCRIPTION)
    private Map<String, Object> attributes;

    public Sample() {
    }

    public Sample(String id, String individualId, String description, int release) {
        this(id, null, null, null, null, release, 1, "", "", description, false, new LinkedList<>(), individualId, new LinkedList<>(),
                new CustomStatus(), null, new LinkedList<>(), new HashMap<>());
    }

    public Sample(String id, String creationDate, String modificationDate, String individualId, ExternalSource source,
                  SampleProcessing processing, SampleCollection collection, int release, int version, String description, boolean somatic,
                  List<Phenotype> phenotypes, List<AnnotationSet> annotationSets, CustomStatus status, SampleInternal internal,
                  Map<String, Object> attributes) {
        this(id, null, source, processing, collection, release, version, creationDate, modificationDate, description, somatic, phenotypes,
                individualId, new LinkedList<>(), status, internal, annotationSets, attributes);
    }

    public Sample(String id, String uuid, ExternalSource source, SampleProcessing processing, SampleCollection collection, int release,
                  int version, String creationDate, String modificationDate, String description, boolean somatic,
                  List<Phenotype> phenotypes, String individualId, List<String> fileIds, CustomStatus status, SampleInternal internal,
                  List<AnnotationSet> annotationSets, Map<String, Object> attributes) {
        this(id, uuid, source, processing, collection, null, release, version, creationDate, modificationDate, description,
                somatic, phenotypes, individualId, Collections.emptyList(), fileIds, status, internal, annotationSets, attributes);
    }

    public Sample(String id, String uuid, ExternalSource source, SampleProcessing processing, SampleCollection collection,
                  SampleQualityControl qualityControl, int release, int version, String creationDate, String modificationDate,
                  String description, boolean somatic, List<Phenotype> phenotypes, String individualId, List<String> cohortIds,
                  List<String> fileIds, CustomStatus status, SampleInternal internal, List<AnnotationSet> annotationSets,
                  Map<String, Object> attributes) {
        this.id = id;
        this.uuid = uuid;
        this.source = source;
        this.processing = processing;
        this.collection = collection;
        this.qualityControl = qualityControl;
        this.release = release;
        this.version = version;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.description = description;
        this.somatic = somatic;
        this.phenotypes = phenotypes;
        this.individualId = individualId;
        this.fileIds = fileIds;
        this.cohortIds = cohortIds;
        this.status = status;
        this.internal = internal;
        this.annotationSets = annotationSets;
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sample{");
        sb.append("id='").append(id).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", source=").append(source);
        sb.append(", processing=").append(processing);
        sb.append(", collection=").append(collection);
        sb.append(", qualityControl=").append(qualityControl);
        sb.append(", release=").append(release);
        sb.append(", version=").append(version);
        sb.append(", creationDate='").append(creationDate).append('\'');
        sb.append(", modificationDate='").append(modificationDate).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", somatic=").append(somatic);
        sb.append(", phenotypes=").append(phenotypes);
        sb.append(", individualId='").append(individualId).append('\'');
        sb.append(", fileIds=").append(fileIds);
        sb.append(", cohortIds=").append(cohortIds);
        sb.append(", status=").append(status);
        sb.append(", internal=").append(internal);
        sb.append(", attributes=").append(attributes);
        sb.append(", annotationSets=").append(annotationSets);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Sample sample = (Sample) o;
        return new EqualsBuilder()
                .append(release, sample.release)
                .append(version, sample.version)
                .append(somatic, sample.somatic)
                .append(id, sample.id)
                .append(uuid, sample.uuid)
                .append(processing, sample.processing)
                .append(collection, sample.collection)
                .append(individualId, sample.individualId)
                .append(creationDate, sample.creationDate)
                .append(modificationDate, sample.modificationDate)
                .append(description, sample.description)
                .append(phenotypes, sample.phenotypes)
                .append(status, sample.status)
                .append(internal, sample.internal)
                .append(attributes, sample.attributes)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(uuid)
                .append(processing)
                .append(collection)
                .append(individualId)
                .append(release)
                .append(version)
                .append(creationDate)
                .append(modificationDate)
                .append(description)
                .append(somatic)
                .append(phenotypes)
                .append(status)
                .append(internal)
                .append(attributes)
                .toHashCode();
    }

    @Override
    public Sample setUid(long uid) {
        super.setUid(uid);
        return this;
    }

    @Override
    public Sample setStudyUid(long studyUid) {
        super.setStudyUid(studyUid);
        return this;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public Sample setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Sample setId(String id) {
        this.id = id;
        return this;
    }

    public ExternalSource getSource() {
        return source;
    }

    public Sample setSource(ExternalSource source) {
        this.source = source;
        return this;
    }

    public SampleProcessing getProcessing() {
        return processing;
    }

    public Sample setProcessing(SampleProcessing processing) {
        this.processing = processing;
        return this;
    }

    public SampleCollection getCollection() {
        return collection;
    }

    public Sample setCollection(SampleCollection collection) {
        this.collection = collection;
        return this;
    }

    public SampleQualityControl getQualityControl() {
        return qualityControl;
    }

    public Sample setQualityControl(SampleQualityControl qualityControl) {
        this.qualityControl = qualityControl;
        return this;
    }

    public String getIndividualId() {
        return individualId;
    }

    public Sample setIndividualId(String individualId) {
        this.individualId = individualId;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Sample setCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public Sample setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Sample setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isSomatic() {
        return somatic;
    }

    public Sample setSomatic(boolean somatic) {
        this.somatic = somatic;
        return this;
    }

    public int getRelease() {
        return release;
    }

    public Sample setRelease(int release) {
        this.release = release;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public Sample setVersion(int version) {
        this.version = version;
        return this;
    }

    public List<Phenotype> getPhenotypes() {
        return phenotypes;
    }

    public Sample setPhenotypes(List<Phenotype> phenotypes) {
        this.phenotypes = phenotypes;
        return this;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public Sample setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
        return this;
    }

    public CustomStatus getStatus() {
        return status;
    }

    public Sample setStatus(CustomStatus status) {
        this.status = status;
        return this;
    }

    public SampleInternal getInternal() {
        return internal;
    }

    public Sample setInternal(SampleInternal internal) {
        this.internal = internal;
        return this;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Sample setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public Sample setAnnotationSets(List<AnnotationSet> annotationSets) {
        super.setAnnotationSets(annotationSets);
        return this;
    }

    public List<String> getCohortIds() {
        return cohortIds;
    }

    public Sample setCohortIds(List<String> cohortIds) {
        this.cohortIds = cohortIds;
        return this;
    }
}
