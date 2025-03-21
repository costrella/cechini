package com.kostrzewa.cechini.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SessionCookieJar implements CookieJar {

    public static List<Cookie> cookies = new ArrayList<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//        this.cookies.addAll(cookies);

        if (url.encodedPath().endsWith("authentication")) {
            this.cookies = new ArrayList<>(cookies);
        }
    }


    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (!url.encodedPath().endsWith("authentication") && cookies != null) {
            return cookies;
        }
        return Collections.emptyList();
    }
}