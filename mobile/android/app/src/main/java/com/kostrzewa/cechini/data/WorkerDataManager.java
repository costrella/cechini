package com.kostrzewa.cechini.data;

import android.content.Context;

import com.kostrzewa.cechini.model.WorkerDTO;

import java.util.List;

import okhttp3.Cookie;

public interface WorkerDataManager {

    void loginAsync(WorkerDTO workerDTO);

    WorkerDTO getWorker();

    void setWorker(WorkerDTO workerDTO);

    void updateFwVersion(Long workerId, String fwVersion);

    void saveCookies(String host, List<Cookie> cookieList);

    List<Cookie> getCookies(String host);

}