package com.kostrzewa.cechini.data.prefs;

import java.util.Set;

public interface PreferenceManager {

    Set<String> getMyStores();

    void setMyStores(Set<String> myStores);

    Set<String> getAllProducts();

    void setAllProducts(Set<String> products);
}
