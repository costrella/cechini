package com.costrella.cechini.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A StoreGroup.
 */
@Entity
@Table(name = "store_group")
public class StoreGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "storegroup")
    private Set<Store> stores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public StoreGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public StoreGroup stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public StoreGroup addStore(Store store) {
        this.stores.add(store);
        store.setStoregroup(this);
        return this;
    }

    public StoreGroup removeStore(Store store) {
        this.stores.remove(store);
        store.setStoregroup(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreGroup)) {
            return false;
        }
        return id != null && id.equals(((StoreGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
