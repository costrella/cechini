package com.kostrzewa.cechini.model;

import java.io.Serializable;

public class StoreDTO implements Serializable, Comparable<StoreDTO> {

    private Long id;

    private String name;

    private String nip;

    private String address;

    private Long workerId;

    private boolean monthVisited;

    public boolean isMonthVisited() {
        return monthVisited;
    }

    public void setMonthVisited(boolean monthVisited) {
        this.monthVisited = monthVisited;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
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

    @Override
    public int compareTo(StoreDTO o) {
        return this.getName().compareTo(o.getName());
    }
}
