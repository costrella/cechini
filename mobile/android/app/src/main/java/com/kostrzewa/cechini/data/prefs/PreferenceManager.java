package com.kostrzewa.cechini.data.prefs;

import java.util.BitSet;
import java.util.Set;

public interface PreferenceManager {

    Set<String> getMyStores();

    void setMyStores(Set<String> myStores);

    Set<String> getAllProducts();

    void setAllProducts(Set<String> products);

    Set<String> getAllWarehouses();

    void setAllWarehouses(Set<String> myset);

    void setReportsNotSend(Set<String> myset);

    Set<String> getReportsNotSend();

    void setAllStoreGroups(Set<String> myset);

    Set<String> getAllStoreGroups();

    void setWorker(String worker);

    String getWorker();
}
