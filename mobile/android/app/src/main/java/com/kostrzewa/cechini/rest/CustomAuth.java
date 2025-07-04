package com.kostrzewa.cechini.rest;


import android.content.Context;

import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.LoginFailed;
import com.kostrzewa.cechini.model.WorkerDTO;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class CustomAuth implements Authenticator {
    private Context context;

    public CustomAuth(Context context) {
        this.context = context;
    }

    @Override
    public Request authenticate(Route route, Response response) {
        WorkerDataManager workerDataManager = new WorkerDataManagerImpl(context);

        WorkerDTO workerDTO = workerDataManager.getWorker();
        if (workerDTO != null) {

            try {
                retrofit2.Response<Void> execute2 = RetrofitClient.getInstance(context).getService().login2(workerDTO.getLogin(), workerDTO.getPassword())
                        .execute();
                if (execute2.isSuccessful()) {
                    retrofit2.Response<WorkerDTO> execute = RetrofitClient.getInstance(context).getService().login(workerDTO).execute();
                    if (execute.isSuccessful()) {
                        return response.request();
                    }
                } else {
                    EventBus.getDefault().post(new LoginFailed("Błąd a05A_1: " + execute2.code()));
                    return null;
                }
            } catch (IOException e) {
                EventBus.getDefault().post(new LoginFailed("Błąd a05A_2"));
                throw new RuntimeException(e);
            }
        }
        EventBus.getDefault().post(new LoginFailed("Nieprawidłowy login lub hasło"));
        return null;
    }

}