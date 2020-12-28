package com.kostrzewa.cechini.data;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kostrzewa.cechini.data.prefs.PreferenceManager;
import com.kostrzewa.cechini.data.prefs.PreferenceManagerImpl;

public abstract class AbstractDataManager {
    private static final String TAG = "AbstractDataManager";
    public final PreferenceManager preferenceManager;
    public final Gson gson;
    private Context context;

    public AbstractDataManager(Context context) {
        this.context = context;
        preferenceManager = new PreferenceManagerImpl(context);
        gson = new GsonBuilder().create();
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
