package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
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
    private LocalDate hiredDate;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "target")
    private Long target;

    @OneToMany(mappedBy = "worker")
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    private Set<Route> routes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "workers", allowSetters = true)
    private Status status;

    @ManyToMany(mappedBy = "workers")
    @JsonIgnore
    private Set<Manager> managers = new HashSet<>();

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

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public Worker hiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
        return this;
    }

    public void setHiredDate(LocalDate hiredDate) {
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

    public Set<Order> getOrders() {
        return orders;
    }

    public Worker orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Worker addOrder(Order order) {
        this.orders.add(order);
        order.setWorker(this);
        return this;
    }

    public Worker removeOrder(Order order) {
        this.orders.remove(order);
        order.setWorker(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
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

    public Set<Route> getRoutes() {
        return routes;
    }

    public Worker routes(Set<Route> routes) {
        this.routes = routes;
        return this;
    }

    public Worker addRoute(Route route) {
        this.routes.add(route);
        route.setWorker(this);
        return this;
    }

    public Worker removeRoute(Route route) {
        this.routes.remove(route);
        route.setWorker(null);
        return this;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public Status getStatus() {
        return status;
    }

    public Worker status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
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
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", target=" + getTarget() +
            "}";
    }
}
