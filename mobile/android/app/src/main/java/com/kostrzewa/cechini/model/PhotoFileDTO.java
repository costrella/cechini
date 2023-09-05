package com.kostrzewa.cechini.model;

import java.io.Serializable;

public class PhotoFileDTO implements Serializable {

    private Long id;

    private byte[] value;

    //    private String value;
    //
    //    public String getValue() {
    //        return value;
    //    }
    //
    //    public void setValue(String value) {
    //        this.value = value;
    //    }

    private String valueContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getValueContentType() {
        return valueContentType;
    }

    public void setValueContentType(String valueContentType) {
        this.valueContentType = valueContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhotoFileDTO)) {
            return false;
        }

        return id != null && id.equals(((PhotoFileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoFileDTO{" +
                "id=" + getId() +
//                ", value='" + getValue() + "'" +
                "}";
    }
}
