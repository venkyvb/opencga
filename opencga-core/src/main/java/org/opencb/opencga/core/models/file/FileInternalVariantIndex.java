package org.opencb.opencga.core.models.file;

public class FileInternalVariantIndex {

    private VariantIndexStatus status;
    private int release;
    private Transform transform;

    public FileInternalVariantIndex() {
    }

    public FileInternalVariantIndex(VariantIndexStatus status, int release, Transform transform) {
        this.status = status;
        this.release = release;
        this.transform = transform;
    }

    public static FileInternalVariantIndex init() {
        return new FileInternalVariantIndex(VariantIndexStatus.init(), -1, Transform.init());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileInternalVariantIndex{");
        sb.append("status=").append(status);
        sb.append(", release=").append(release);
        sb.append(", transform=").append(transform);
        sb.append('}');
        return sb.toString();
    }

    public VariantIndexStatus getStatus() {
        return status;
    }

    public FileInternalVariantIndex setStatus(VariantIndexStatus status) {
        this.status = status;
        return this;
    }

    public int getRelease() {
        return release;
    }

    public FileInternalVariantIndex setRelease(int release) {
        this.release = release;
        return this;
    }

    public Transform getTransform() {
        return transform;
    }

    public FileInternalVariantIndex setTransform(Transform transform) {
        this.transform = transform;
        return this;
    }

    public static class Transform {

        private String fileId;
        private String metadataFileId;

        public Transform() {
        }

        public Transform(String fileId, String metadataFileId) {
            this.fileId = fileId;
            this.metadataFileId = metadataFileId;
        }

        public static Transform init() {
            return new Transform("", "");
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Transform{");
            sb.append("fileId='").append(fileId).append('\'');
            sb.append(", metadataFileId='").append(metadataFileId).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getFileId() {
            return fileId;
        }

        public Transform setFileId(String fileId) {
            this.fileId = fileId;
            return this;
        }

        public String getMetadataFileId() {
            return metadataFileId;
        }

        public Transform setMetadataFileId(String metadataFileId) {
            this.metadataFileId = metadataFileId;
            return this;
        }
    }

}
