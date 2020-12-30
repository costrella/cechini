package com.kostrzewa.cechini.data.events;

public class ProductsDownloadFailed {
    String error;

    public ProductsDownloadFailed(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
