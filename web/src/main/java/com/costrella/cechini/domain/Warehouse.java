package com.costrella.cechini.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.costrella.cechini.domain.enumeration.OrderFileType;

/**
 * A Warehouse.
 */
@Entity
@Table(name = "warehouse")
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(name = "mail")
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_file_type")
    private OrderFileType orderFileType;

    @OneToMany(mappedBy = "warehouse")
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    private Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Warehouse id(Long id){
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Warehouse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public Warehouse mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public OrderFileType getOrderFileType() {
        return orderFileType;
    }

    public Warehouse orderFileType(OrderFileType orderFileType) {
        this.orderFileType = orderFileType;
        return this;
    }

    public void setOrderFileType(OrderFileType orderFileType) {
        this.orderFileType = orderFileType;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Warehouse orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Warehouse addOrder(Order order) {
        this.orders.add(order);
        order.setWarehouse(this);
        return this;
    }

    public Warehouse removeOrder(Order order) {
        this.orders.remove(order);
        order.setWarehouse(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warehouse)) {
            return false;
        }
        return id != null && id.equals(((Warehouse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Warehouse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mail='" + getMail() + "'" +
            ", orderFileType='" + getOrderFileType() + "'" +
            "}";
    }
}
