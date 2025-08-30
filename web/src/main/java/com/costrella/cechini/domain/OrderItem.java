package com.costrella.cechini.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "art_count")
    private Integer artCount;

    @Column(name = "pack_count")
    private Integer packCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = "orderItems", allowSetters = true)
    private Order order;

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

    public Integer getArtCount() {
        return artCount;
    }

    public OrderItem artCount(Integer artCount) {
        this.artCount = artCount;
        return this;
    }

    public void setArtCount(Integer artCount) {
        this.artCount = artCount;
    }

    public Integer getPackCount() {
        return packCount;
    }

    public OrderItem packCount(Integer packCount) {
        this.packCount = packCount;
        return this;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public Product getProduct() {
        return product;
    }

    public OrderItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public OrderItem order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return id != null && id.equals(((OrderItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", artCount=" + getArtCount() +
            ", packCount=" + getPackCount() +
            "}";
    }
}
