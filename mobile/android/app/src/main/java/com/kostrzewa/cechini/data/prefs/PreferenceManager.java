package com.kostrzewa.cechini.data.prefs;

import java.util.Set;

public interface PreferenceManager {

    String getCookies(String host);

    void setCookies(String host, String jsonCookies);

    Set<String> getMyStores();

    void setMyStores(Set<String> myStores);

    Set<String> getAllProducts();

    void setAllProducts(Set<String> products);

    Set<String> getAllWarehouses();

    void setAllWarehouses(Set<String> myset);

    void setReportsNotSend(Set<String> myset);

    Set<String> getReportsNotSend();

    void setCommentsNotSend(Set<String> myset);

    Set<String> getCommentsNotSend();

    void setAllStoreGroups(Set<String> myset);

    Set<String> getAllStoreGroups();

    void setMyReports(Set<String> myset);

    Set<String> getMyReports();

    void setWorker(String worker);

    String getWorker();

    void setSychroTimeMyStores();

    String getSychroTimeMyStores();

    String getSychroTimeProducts();

    String getSychroTimeMyReports();

    String getSychroTimeWarehouses();

    String getSychroTimeOrders();

    void setSychroTimeWarehouses();

    void setSychroTimeMyReports();

    void setSychroTimeProducts();

    void setSychroTimeMyOrders();
}
