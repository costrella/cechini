package com.costrella.cechini.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Photo} entity.
 */
public class PhotoDTO implements Serializable {
    
    private Long id;

    private String uri;

    private String valueContentType;


    private Long fileId;

    private Long reportId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getValueContentType() {
        return valueContentType;
    }

    public void setValueContentType(String valueContentType) {
        this.valueContentType = valueContentType;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long photoFileId) {
        this.fileId = photoFileId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotoDTO)) {
            return false;
        }

        return id != null && id.equals(((PhotoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoDTO{" +
            "id=" + getId() +
            ", uri='" + getUri() + "'" +
            ", valueContentType='" + getValueContentType() + "'" +
            ", fileId=" + getFileId() +
            ", reportId=" + getReportId() +
            "}";
    }
}
