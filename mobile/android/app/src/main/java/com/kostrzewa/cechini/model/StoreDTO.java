package com.kostrzewa.cechini.model;

import java.io.Serializable;

public class StoreDTO implements Serializable {
    
    private Long id;

    private String name;

    private String address;

    private Long storegroupId;

    public Long getStoregroupId() {
        return storegroupId;
    }

    public void setStoregroupId(Long storegroupId) {
        this.storegroupId = storegroupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreDTO)) {
            return false;
        }

        return id != null && id.equals(((StoreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
