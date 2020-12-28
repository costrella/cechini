package com.kostrzewa.cechini.data;

import com.kostrzewa.cechini.model.ProductDTO;

import java.util.List;

public interface ProductDataManager {

    List<ProductDTO> getAllProducts();

    void downloadProducts();
}
