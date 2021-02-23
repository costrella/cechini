package com.kostrzewa.cechini.model;

import java.io.Serializable;

public class WarehouseDTO implements Serializable {

    private Long id;

    private String name;

    private String mail;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarehouseDTO)) {
            return false;
        }

        return id != null && id.equals(((WarehouseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarehouseDTO{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                "}";
    }
}
