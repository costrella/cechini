package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @OneToMany(mappedBy = "location")
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "location")
    private Set<Track> tracks = new HashSet<>();

    @OneToMany(mappedBy = "location")
    private Set<Warehouse> warehouses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "locations", allowSetters = true)
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public Location street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLat() {
        return lat;
    }

    public Location lat(String lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public Location lng(String lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public Location stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Location addStore(Store store) {
        this.stores.add(store);
        store.setLocation(this);
        return this;
    }

    public Location removeStore(Store store) {
        this.stores.remove(store);
        store.setLocation(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public Location tracks(Set<Track> tracks) {
        this.tracks = tracks;
        return this;
    }

    public Location addTrack(Track track) {
        this.tracks.add(track);
        track.setLocation(this);
        return this;
    }

    public Location removeTrack(Track track) {
        this.tracks.remove(track);
        track.setLocation(null);
        return this;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }

    public Location warehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
        return this;
    }

    public Location addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
        warehouse.setLocation(this);
        return this;
    }

    public Location removeWarehouse(Warehouse warehouse) {
        this.warehouses.remove(warehouse);
        warehouse.setLocation(null);
        return this;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public City getCity() {
        return city;
    }

    public Location city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", lat='" + getLat() + "'" +
            ", lng='" + getLng() + "'" +
            "}";
    }
}
