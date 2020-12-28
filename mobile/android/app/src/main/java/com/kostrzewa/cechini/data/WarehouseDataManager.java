package com.kostrzewa.cechini.data;

import com.kostrzewa.cechini.model.WarehouseDTO;

import java.util.List;

public interface WarehouseDataManager {

    List<WarehouseDTO> getAllWarehouses();

    void downloadWarehouse();
}
