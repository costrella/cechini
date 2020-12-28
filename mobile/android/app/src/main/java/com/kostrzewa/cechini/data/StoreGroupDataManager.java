package com.kostrzewa.cechini.data;

import com.kostrzewa.cechini.model.StoreGroupDTO;

import java.util.List;

public interface StoreGroupDataManager {

    List<StoreGroupDTO> getStoreGroups();

    void downloadStoreGroups();
}
