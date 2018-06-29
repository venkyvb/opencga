package org.opencb.opencga.catalog.stats.solr;

import org.apache.solr.client.solrj.beans.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wasim on 27/06/18.
 */
public class FamilySolrModel {

    @Field
    private long uid;

    @Field
    private String creationDate;

    @Field
    private String status;

    @Field
    private List<String> phenotypes;

    @Field
    private List<String> familyMembersUuid;

    @Field
    private int release;

    @Field
    private int version;

    @Field("annotations_*")
    private Map<String, Object> annotations;

    public FamilySolrModel() {
        this.annotations = new HashMap<>();
        this.phenotypes = new ArrayList<>();
        this.familyMembersUuid = new ArrayList<>();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FamilySolrModel{");
        sb.append("uid=").append(uid);
        sb.append(", creationDate='").append(creationDate).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", phenotypes=").append(phenotypes);
        sb.append(", familyMembersUuid=").append(familyMembersUuid);
        sb.append(", release=").append(release);
        sb.append(", version=").append(version);
        sb.append(", annotations=").append(annotations);
        sb.append('}');
        return sb.toString();
    }

    public long getUid() {
        return uid;
    }

    public FamilySolrModel setUid(long uid) {
        this.uid = uid;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public FamilySolrModel setCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FamilySolrModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public List<String> getPhenotypes() {
        return phenotypes;
    }

    public FamilySolrModel setPhenotypes(List<String> phenotypes) {
        this.phenotypes = phenotypes;
        return this;
    }

    public List<String> getFamilyMembersUuid() {
        return familyMembersUuid;
    }

    public FamilySolrModel setFamilyMembersUuid(List<String> familyMembersUuid) {
        this.familyMembersUuid = familyMembersUuid;
        return this;
    }

    public int getRelease() {
        return release;
    }

    public FamilySolrModel setRelease(int release) {
        this.release = release;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public FamilySolrModel setVersion(int version) {
        this.version = version;
        return this;
    }

    public Map<String, Object> getAnnotations() {
        return annotations;
    }

    public FamilySolrModel setAnnotations(Map<String, Object> annotations) {
        this.annotations = annotations;
        return this;
    }
}
