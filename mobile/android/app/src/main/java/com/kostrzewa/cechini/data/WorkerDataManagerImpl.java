package com.kostrzewa.cechini.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.kostrzewa.cechini.LoginActivity;
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.events.LoginFailed;
import com.kostrzewa.cechini.data.events.LoginSuccess;
import com.kostrzewa.cechini.model.WorkerDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerDataManagerImpl extends AbstractDataManager implements WorkerDataManager {
    private static final String TAG = "WorkerDataManagerImpl";

    public WorkerDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public void saveCookies(String host, List<Cookie> cookies) {
        String jsonCookies = gson.toJson(cookies);
        preferenceManager.setCookies(host, jsonCookies);
    }

    @Override
    public List<Cookie> getCookies(String host) {
        String jsonCookies = preferenceManager.getCookies(host);
        if (jsonCookies != null) {
            Type type = new TypeToken<List<Cookie>>() {
            }.getType();
            return gson.fromJson(jsonCookies, type);
        }
        return new ArrayList<>();
    }

    @Override
    public void loginAsync(WorkerDTO workerDTO) {
        RetrofitClient.getInstance(getContext()).getService().login2(workerDTO.getLogin(), workerDTO.getPassword()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
//                Headers headers = response.headers();
//                String token = headers.get("Set-Cookie");
//                EventBus.getDefault().post(new LoginSuccess(workerDTO));
                if (!response.isSuccessful()) {
                    EventBus.getDefault().post(new LoginFailed(getContext().getResources().getString(R.string.wrong_user_or_password) + "\n" +
                            getContext().getResources().getString(R.string.error)+ " a05A: " + response.code()));
                    return;
                }

                RetrofitClient.getInstance(getContext()).getService().login(workerDTO).enqueue(new Callback<WorkerDTO>() {
                    @Override
                    public void onResponse(Call<WorkerDTO> call, Response<WorkerDTO> response) {
                        if (response.isSuccessful()) {
                            EventBus.getDefault().post(new LoginSuccess(response.body()));
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                EventBus.getDefault().post(new LoginFailed(jObjError.getString("title")));
                            } catch (Exception e) {
                                EventBus.getDefault().post(new LoginFailed(getContext().getResources().getString(R.string.error) + " a05: " + response.code()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkerDTO> call, Throwable t) {
                        EventBus.getDefault().post(new LoginFailed("" + t.getMessage()));
                    }
                });
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                EventBus.getDefault().post(new LoginFailed("" + throwable.getMessage()));
            }
        });


    }

    @Override
    public WorkerDTO getWorker() {
        return gson.fromJson(preferenceManager.getWorker(), WorkerDTO.class);
    }

    @Override
    public void setWorker(WorkerDTO workerDTO) {
        preferenceManager.setWorker(gson.toJson(workerDTO));
    }

    @Override
    public void updateFwVersion(Long workerId, String fwVersion) {
        RetrofitClient.getInstance(getContext()).getService().updateFwVersion(workerId, fwVersion).enqueue(new Callback<WorkerDTO>() {
            @Override
            public void onResponse(Call<WorkerDTO> call, Response<WorkerDTO> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if(response.code() == 401){
                    //do nothing. in HomeFragment check 401 and do loginAsync
                }
            }

            @Override
            public void onFailure(Call<WorkerDTO> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}