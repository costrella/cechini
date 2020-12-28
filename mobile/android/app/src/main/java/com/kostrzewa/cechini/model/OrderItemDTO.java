package com.kostrzewa.cechini.model;

import java.io.Serializable;

public class OrderItemDTO implements Serializable {

    private Long id;

    private Integer artCount;

    private Integer packCount;

    private Long productId;

    private String productName;

    //todo
    private Integer productCapacity;

    private String productEanPack;

    private Integer productPackCountPalette;

    private Integer productArtCountPalette;

    private Integer productLayerCountPalette;

    private Long orderId;

    public Integer getProductCapacity() {
        return productCapacity;
    }

    public void setProductCapacity(Integer productCapacity) {
        this.productCapacity = productCapacity;
    }

    public String getProductEanPack() {
        return productEanPack;
    }

    public void setProductEanPack(String productEanPack) {
        this.productEanPack = productEanPack;
    }

    public Integer getProductPackCountPalette() {
        return productPackCountPalette;
    }

    public void setProductPackCountPalette(Integer productPackCountPalette) {
        this.productPackCountPalette = productPackCountPalette;
    }

    public Integer getProductArtCountPalette() {
        return productArtCountPalette;
    }

    public void setProductArtCountPalette(Integer productArtCountPalette) {
        this.productArtCountPalette = productArtCountPalette;
    }

    public Integer getProductLayerCountPalette() {
        return productLayerCountPalette;
    }

    public void setProductLayerCountPalette(Integer productLayerCountPalette) {
        this.productLayerCountPalette = productLayerCountPalette;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getArtCount() {
        return artCount;
    }

    public void setArtCount(Integer artCount) {
        this.artCount = artCount;
    }

    public Integer getPackCount() {
        return packCount;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + getId() +
                ", artCount=" + getArtCount() +
                ", packCount=" + getPackCount() +
                ", productId=" + getProductId() +
                ", productName='" + getProductName() + "'" +
                ", orderId=" + getOrderId() +
                "}";
    }
}
