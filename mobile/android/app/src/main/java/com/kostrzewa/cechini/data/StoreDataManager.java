package com.kostrzewa.cechini.data;

import com.kostrzewa.cechini.model.StoreDTO;

import java.util.List;

public interface StoreDataManager {

    List<StoreDTO> getMyStores();

    void downloadMyStores();

    void addNewStore(StoreDTO storeDTO);

    void editStore(StoreDTO storeDTO, StoreDTO old);
}
