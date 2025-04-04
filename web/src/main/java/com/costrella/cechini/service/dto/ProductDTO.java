package com.costrella.cechini.service.dto;

import com.costrella.cechini.domain.Tenant;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Double capacity;

    private String eanArt;

    private String eanPack;

    private Integer packCountPalette;

    private Integer artCountPalette;

    private Integer layerCountPalette;

    private Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public String getEanArt() {
        return eanArt;
    }

    public void setEanArt(String eanArt) {
        this.eanArt = eanArt;
    }

    public String getEanPack() {
        return eanPack;
    }

    public void setEanPack(String eanPack) {
        this.eanPack = eanPack;
    }

    public Integer getPackCountPalette() {
        return packCountPalette;
    }

    public void setPackCountPalette(Integer packCountPalette) {
        this.packCountPalette = packCountPalette;
    }

    public Integer getArtCountPalette() {
        return artCountPalette;
    }

    public void setArtCountPalette(Integer artCountPalette) {
        this.artCountPalette = artCountPalette;
    }

    public Integer getLayerCountPalette() {
        return layerCountPalette;
    }

    public void setLayerCountPalette(Integer layerCountPalette) {
        this.layerCountPalette = layerCountPalette;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
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
