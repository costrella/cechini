package com.kostrzewa.cechini.rest;

import android.content.Context;

import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;

import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SessionCookieJar implements CookieJar {

    private final WorkerDataManager workerDataManager;

    public SessionCookieJar(Context context) {
        this.workerDataManager = new WorkerDataManagerImpl(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (url.encodedPath().endsWith("authentication")) {
            workerDataManager.saveCookies(url.host() + "_cookies", cookies);
        }
    }


    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (!url.encodedPath().endsWith("authentication")) {
            List<Cookie> cookies = workerDataManager.getCookies(url.host() + "_cookies");
            if (!cookies.isEmpty()) {
                return cookies;
            }
        }
        return Collections.emptyList();
    }
}