package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate reportDate;

    @Column(name = "worker_desc")
    private String workerDesc;

    @Column(name = "manager_desc")
    private String managerDesc;

    @OneToMany(mappedBy = "report")
    private Set<Photo> photos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "reports", allowSetters = true)
    private Worker worker;

    @ManyToOne
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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public Report reportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getWorkerDesc() {
        return workerDesc;
    }

    public Report workerDesc(String workerDesc) {
        this.workerDesc = workerDesc;
        return this;
    }

    public void setWorkerDesc(String workerDesc) {
        this.workerDesc = workerDesc;
    }

    public String getManagerDesc() {
        return managerDesc;
    }

    public Report managerDesc(String managerDesc) {
        this.managerDesc = managerDesc;
        return this;
    }

    public void setManagerDesc(String managerDesc) {
        this.managerDesc = managerDesc;
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
            ", workerDesc='" + getWorkerDesc() + "'" +
            ", managerDesc='" + getManagerDesc() + "'" +
            "}";
    }
}
