package com.kostrzewa.cechini.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.kostrzewa.cechini.util.DateUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PreferenceManagerImpl implements PreferenceManager {

    SharedPreferences sharedPreferences;

    public PreferenceManagerImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences("cechini", Context.MODE_PRIVATE);
    }

    @Override
    public Set<String> getMyStores() {
        return sharedPreferences.getStringSet("myStores", new HashSet<>());
    }

    @Override
    public void setMyStores(Set<String> myStores) {
        sharedPreferences.edit().putStringSet("myStores", myStores).apply();
    }

    @Override
    public Set<String> getAllProducts() {
        return sharedPreferences.getStringSet("allProducts", new HashSet<>());
    }

    @Override
    public void setAllProducts(Set<String> products) {
        sharedPreferences.edit().putStringSet("allProducts", products).apply();
    }

    @Override
    public Set<String> getAllWarehouses() {
        return sharedPreferences.getStringSet("allWarehouses", new HashSet<>());
    }

    @Override
    public void setAllWarehouses(Set<String> myset) {
        sharedPreferences.edit().putStringSet("allWarehouses", myset).apply();
    }

    @Override
    public void setReportsNotSend(Set<String> myset) {
        sharedPreferences.edit().putStringSet("reportsNotSend", myset).apply();
    }

    @Override
    public Set<String> getReportsNotSend() {
        return sharedPreferences.getStringSet("reportsNotSend", new HashSet<>());
    }

    @Override
    public Set<String> getCommentsNotSend() {
        return sharedPreferences.getStringSet("commentsNotSend", new HashSet<>());
    }

    @Override
    public void setCommentsNotSend(Set<String> myset) {
        sharedPreferences.edit().putStringSet("commentsNotSend", myset).apply();
    }

    @Override
    public void setAllStoreGroups(Set<String> myset) {
        sharedPreferences.edit().putStringSet("allStoreGroups", myset).apply();
    }

    @Override
    public Set<String> getAllStoreGroups() {
        return sharedPreferences.getStringSet("allStoreGroups", new HashSet<>());
    }

    @Override
    public void setMyReports(Set<String> myset) {
        sharedPreferences.edit().putStringSet("myReports", myset).apply();
    }

    @Override
    public Set<String> getMyReports() {
        return sharedPreferences.getStringSet("myReports", new HashSet<>());
    }

    @Override
    public void setWorker(String worker) {
        sharedPreferences.edit().putString("worker", worker).apply();
    }

    @Override
    public String getWorker() {
        return sharedPreferences.getString("worker", null);
    }

    @Override
    public void setSychroTimeMyStores() {
        sharedPreferences.edit().putString("synchroTimeMyStores", DateUtils.parse(new Date())).apply();
    }

    @Override
    public String getSychroTimeMyStores() {
        return sharedPreferences.getString("synchroTimeMyStores", "brak");
    }

    @Override
    public void setSychroTimeProducts() {
        sharedPreferences.edit().putString("synchroTimeProducts", DateUtils.parse(new Date())).apply();
    }

    @Override
    public String getSychroTimeProducts() {
        return sharedPreferences.getString("synchroTimeProducts", "brak");
    }

    @Override
    public void setSychroTimeMyReports() {
        sharedPreferences.edit().putString("synchroTimeMyReports", DateUtils.parse(new Date())).apply();
    }

    @Override
    public String getSychroTimeMyReports() {
        return sharedPreferences.getString("synchroTimeMyReports", "brak");
    }

    @Override
    public void setSychroTimeMyOrders() {
        sharedPreferences.edit().putString("synchroTimeMyOrders", DateUtils.parse(new Date())).apply();
    }

    @Override
    public String getSychroTimeOrders() {
        return sharedPreferences.getString("synchroTimeMyOrders", "brak");
    }

    @Override
    public void setSychroTimeWarehouses() {
        sharedPreferences.edit().putString("synchroTimeWarehouses", DateUtils.parse(new Date())).apply();
    }

    @Override
    public String getSychroTimeWarehouses() {
        return sharedPreferences.getString("synchroTimeWarehouses", "brak");
    }
}
