package com.costrella.cechini.service.excel;

public class OrderItem {

    private int lp;
    private String productName;
    private String ean;
    private int count;

    public OrderItem(int lp, String productName, String ean, int count) {
        this.lp = lp;
        this.productName = productName;
        this.ean = ean;
        this.count = count;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
