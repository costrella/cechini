package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "report_date")
    private Instant reportDate;

    @Column(name = "jhi_desc")
    private String desc;

    @OneToOne
    @JoinColumn(unique = true)
    private Order order;

    @OneToMany(mappedBy = "report")
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(mappedBy = "report")
    private Set<Note> notes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reports", allowSetters = true)
    private Worker worker;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reports", allowSetters = true)
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Report number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Instant getReportDate() {
        return reportDate;
    }

    public Report reportDate(Instant reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public void setReportDate(Instant reportDate) {
        this.reportDate = reportDate;
    }

    public String getDesc() {
        return desc;
    }

    public Report desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Order getOrder() {
        return order;
    }

    public Report order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Report photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Report addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setReport(this);
        return this;
    }

    public Report removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setReport(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Report notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Report addNote(Note note) {
        this.notes.add(note);
        note.setReport(this);
        return this;
    }

    public Report removeNote(Note note) {
        this.notes.remove(note);
        note.setReport(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Worker getWorker() {
        return worker;
    }

    public Report worker(Worker worker) {
        this.worker = worker;
        return this;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Store getStore() {
        return store;
    }

    public Report store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        return id != null && id.equals(((Report) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
