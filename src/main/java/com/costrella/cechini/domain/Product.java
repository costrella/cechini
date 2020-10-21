package com.costrella.cechini.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "ean_art")
    private String eanArt;

    @Column(name = "ean_pack")
    private String eanPack;

    @Column(name = "pack_count_palette")
    private Integer packCountPalette;

    @Column(name = "art_count_palette")
    private Integer artCountPalette;

    @Column(name = "layer_count_palette")
    private Integer layerCountPalette;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new HashSet<>();

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Product capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getEanArt() {
        return eanArt;
    }

    public Product eanArt(String eanArt) {
        this.eanArt = eanArt;
        return this;
    }

    public void setEanArt(String eanArt) {
        this.eanArt = eanArt;
    }

    public String getEanPack() {
        return eanPack;
    }

    public Product eanPack(String eanPack) {
        this.eanPack = eanPack;
        return this;
    }

    public void setEanPack(String eanPack) {
        this.eanPack = eanPack;
    }

    public Integer getPackCountPalette() {
        return packCountPalette;
    }

    public Product packCountPalette(Integer packCountPalette) {
        this.packCountPalette = packCountPalette;
        return this;
    }

    public void setPackCountPalette(Integer packCountPalette) {
        this.packCountPalette = packCountPalette;
    }

    public Integer getArtCountPalette() {
        return artCountPalette;
    }

    public Product artCountPalette(Integer artCountPalette) {
        this.artCountPalette = artCountPalette;
        return this;
    }

    public void setArtCountPalette(Integer artCountPalette) {
        this.artCountPalette = artCountPalette;
    }

    public Integer getLayerCountPalette() {
        return layerCountPalette;
    }

    public Product layerCountPalette(Integer layerCountPalette) {
        this.layerCountPalette = layerCountPalette;
        return this;
    }

    public void setLayerCountPalette(Integer layerCountPalette) {
        this.layerCountPalette = layerCountPalette;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Product orderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public Product addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setProduct(this);
        return this;
    }

    public Product removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setProduct(null);
        return this;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", capacity=" + getCapacity() +
            ", eanArt='" + getEanArt() + "'" +
            ", eanPack='" + getEanPack() + "'" +
            ", packCountPalette=" + getPackCountPalette() +
            ", artCountPalette=" + getArtCountPalette() +
            ", layerCountPalette=" + getLayerCountPalette() +
            "}";
    }
}
