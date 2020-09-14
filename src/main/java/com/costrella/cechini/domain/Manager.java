package com.costrella.cechini.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Manager.
 */
@Entity
@Table(name = "manager")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "hired_date")
    private Instant hiredDate;

    @ManyToMany
    @JoinTable(name = "manager_worker",
               joinColumns = @JoinColumn(name = "manager_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "worker_id", referencedColumnName = "id"))
    private Set<Worker> workers = new HashSet<>();

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

    public Manager name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Manager surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Instant getHiredDate() {
        return hiredDate;
    }

    public Manager hiredDate(Instant hiredDate) {
        this.hiredDate = hiredDate;
        return this;
    }

    public void setHiredDate(Instant hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public Manager workers(Set<Worker> workers) {
        this.workers = workers;
        return this;
    }

    public Manager addWorker(Worker worker) {
        this.workers.add(worker);
        worker.getManagers().add(this);
        return this;
    }

    public Manager removeWorker(Worker worker) {
        this.workers.remove(worker);
        worker.getManagers().remove(this);
        return this;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }
        return id != null && id.equals(((Manager) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Manager{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", hiredDate='" + getHiredDate() + "'" +
            "}";
    }
}
