package com.kostrzewa.cechini.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

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
    public void setAllStoreGroups(Set<String> myset) {
        sharedPreferences.edit().putStringSet("allStoreGroups", myset).apply();
    }

    @Override
    public Set<String> getAllStoreGroups() {
        return sharedPreferences.getStringSet("allStoreGroups", new HashSet<>());
    }
}
