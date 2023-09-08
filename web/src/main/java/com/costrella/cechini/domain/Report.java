package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Column(name = "report_date")
    private Instant reportDate;

    @Size(max = 2000)
    @Column(name = "jhi_desc", length = 2000)
    private String desc;

    @Size(max = 2000)
    @Column(name = "manager_note", length = 2000)
    private String managerNote;

    //    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Order order;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "report", fetch = FetchType.LAZY)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "report")
    private Set<Note> notes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reports", allowSetters = true)
    private Worker worker;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnoreProperties(value = "reports", allowSetters = true)
    private Store store;

    @Column(name = "read_by_worker")
    private Boolean readByWorker;

    @Column(name = "read_by_manager")
    private Boolean readByManager;

    public Boolean getReadByWorker() {
        return readByWorker;
    }

    public void setReadByWorker(Boolean readByWorker) {
        this.readByWorker = readByWorker;
    }

    public Boolean getReadByManager() {
        return readByManager;
    }

    public void setReadByManager(Boolean readByManager) {
        this.readByManager = readByManager;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Report id(Long id) {
        this.id = id;
        return this;
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

    public String getManagerNote() {
        return managerNote;
    }

    public Report managerNote(String managerNote) {
        this.managerNote = managerNote;
        return this;
    }

    public void setManagerNote(String managerNote) {
        this.managerNote = managerNote;
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
            ", reportDate='" + getReportDate() + "'" +
            ", desc='" + getDesc() + "'" +
            ", managerNote='" + getManagerNote() + "'" +
            "}";
    }
}
