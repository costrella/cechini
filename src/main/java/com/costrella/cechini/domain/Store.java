package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nip")
    private String nip;

    @Size(max = 2000)
    @Column(name = "jhi_desc", length = 2000)
    private String desc;

    @Column(name = "visited")
    private Boolean visited;

    @OneToMany(mappedBy = "store")
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private Set<Report> reports = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "stores", allowSetters = true)
    private Worker worker;

    @ManyToOne
    @JsonIgnoreProperties(value = "stores", allowSetters = true)
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties(value = "stores", allowSetters = true)
    private StoreGroup storegroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store id(Long id){
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public Store nip(String nip) {
        this.nip = nip;
        return this;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getDesc() {
        return desc;
    }

    public Store desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean isVisited() {
        return visited;
    }

    public Store visited(Boolean visited) {
        this.visited = visited;
        return this;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Store notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Store addNote(Note note) {
        this.notes.add(note);
        note.setStore(this);
        return this;
    }

    public Store removeNote(Note note) {
        this.notes.remove(note);
        note.setStore(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public Store reports(Set<Report> reports) {
        this.reports = reports;
        return this;
    }

    public Store addReport(Report report) {
        this.reports.add(report);
        report.setStore(this);
        return this;
    }

    public Store removeReport(Report report) {
        this.reports.remove(report);
        report.setStore(null);
        return this;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Worker getWorker() {
        return worker;
    }

    public Store worker(Worker worker) {
        this.worker = worker;
        return this;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Location getLocation() {
        return location;
    }

    public Store location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public StoreGroup getStoregroup() {
        return storegroup;
    }

    public Store storegroup(StoreGroup storeGroup) {
        this.storegroup = storeGroup;
        return this;
    }

    public void setStoregroup(StoreGroup storeGroup) {
        this.storegroup = storeGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        return id != null && id.equals(((Store) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Store{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nip='" + getNip() + "'" +
            ", desc='" + getDesc() + "'" +
            ", visited='" + isVisited() + "'" +
            "}";
    }
}
