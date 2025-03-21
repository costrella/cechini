package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Pomysły: notyfikacje
 */
@Entity
@Table(name = "worker")
public class Worker implements Serializable {

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

    @Size(max = 2000)
    @Column(name = "jhi_desc", length = 2000)
    private String desc;

    @Column(name = "phone")
    private String phone;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "target")
    private Long target;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "workerNote")
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    private Set<Track> tracks = new HashSet<>();

    @ManyToMany(mappedBy = "workers")
    @JsonIgnore
    private Set<Manager> managers = new HashSet<>();

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    private User user;

    @ManyToOne
    private Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public Worker id(Long id){
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Worker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Worker surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Instant getHiredDate() {
        return hiredDate;
    }

    public Worker hiredDate(Instant hiredDate) {
        this.hiredDate = hiredDate;
        return this;
    }

    public void setHiredDate(Instant hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getDesc() {
        return desc;
    }

    public Worker desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public Worker phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public Worker login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Worker password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTarget() {
        return target;
    }

    public Worker target(Long target) {
        this.target = target;
        return this;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Boolean isActive() {
        return active;
    }

    public Worker active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Worker notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Worker addNote(Note note) {
        this.notes.add(note);
        note.setWorkerNote(this);
        return this;
    }

    public Worker removeNote(Note note) {
        this.notes.remove(note);
        note.setWorkerNote(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public Worker stores(Set<Store> stores) {
        this.stores = stores;
        return this;
    }

    public Worker addStore(Store store) {
        this.stores.add(store);
        store.setWorker(this);
        return this;
    }

    public Worker removeStore(Store store) {
        this.stores.remove(store);
        store.setWorker(null);
        return this;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public Worker reports(Set<Report> reports) {
        this.reports = reports;
        return this;
    }

    public Worker addReport(Report report) {
        this.reports.add(report);
        report.setWorker(this);
        return this;
    }

    public Worker removeReport(Report report) {
        this.reports.remove(report);
        report.setWorker(null);
        return this;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public Worker tracks(Set<Track> tracks) {
        this.tracks = tracks;
        return this;
    }

    public Worker addTrack(Track track) {
        this.tracks.add(track);
        track.setWorker(this);
        return this;
    }

    public Worker removeTrack(Track track) {
        this.tracks.remove(track);
        track.setWorker(null);
        return this;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    public Set<Manager> getManagers() {
        return managers;
    }

    public Worker managers(Set<Manager> managers) {
        this.managers = managers;
        return this;
    }

    public Worker addManager(Manager manager) {
        this.managers.add(manager);
        manager.getWorkers().add(this);
        return this;
    }

    public Worker removeManager(Manager manager) {
        this.managers.remove(manager);
        manager.getWorkers().remove(this);
        return this;
    }

    public void setManagers(Set<Manager> managers) {
        this.managers = managers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Worker)) {
            return false;
        }
        return id != null && id.equals(((Worker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Worker{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", hiredDate='" + getHiredDate() + "'" +
            ", desc='" + getDesc() + "'" +
            ", phone='" + getPhone() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", target=" + getTarget() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
