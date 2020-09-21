package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 2000)
    @Column(name = "value", length = 2000)
    private String value;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties(value = "notes", allowSetters = true)
    private Store store;

    @ManyToOne
    @JsonIgnoreProperties(value = "notes", allowSetters = true)
    private Worker workerNote;

    @ManyToOne
    @JsonIgnoreProperties(value = "notes", allowSetters = true)
    private Manager managerNote;

    @ManyToOne
    @JsonIgnoreProperties(value = "notes", allowSetters = true)
    private Report report;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public Note value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public Note date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Store getStore() {
        return store;
    }

    public Note store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Worker getWorkerNote() {
        return workerNote;
    }

    public Note workerNote(Worker worker) {
        this.workerNote = worker;
        return this;
    }

    public void setWorkerNote(Worker worker) {
        this.workerNote = worker;
    }

    public Manager getManagerNote() {
        return managerNote;
    }

    public Note managerNote(Manager manager) {
        this.managerNote = manager;
        return this;
    }

    public void setManagerNote(Manager manager) {
        this.managerNote = manager;
    }

    public Report getReport() {
        return report;
    }

    public Note report(Report report) {
        this.report = report;
        return this;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        return id != null && id.equals(((Note) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Note{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
