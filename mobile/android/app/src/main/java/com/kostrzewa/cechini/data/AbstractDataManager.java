package com.kostrzewa.cechini.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kostrzewa.cechini.data.prefs.PreferenceManager;
import com.kostrzewa.cechini.data.prefs.PreferenceManagerImpl;

public abstract class AbstractDataManager {
    private static final String TAG = "AbstractDataManager";
    public final PreferenceManager preferenceManager;
    public final Gson gson;

    public AbstractDataManager(Context context) {
        preferenceManager = new PreferenceManagerImpl(context);
        gson = new GsonBuilder().create();
    }
}
