package com.kostrzewa.cechini.data;

import android.content.Context;
import android.util.Log;

import com.kostrzewa.cechini.data.events.LoginFailed;
import com.kostrzewa.cechini.data.events.LoginSuccess;
import com.kostrzewa.cechini.model.WarehouseDTO;
import com.kostrzewa.cechini.model.WorkerDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerDataManagerImpl extends AbstractDataManager implements WorkerDataManager {
    private static final String TAG = "WorkerDataManagerImpl";

    public WorkerDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public void loginAsync(WorkerDTO workerDTO) {
        RetrofitClient.getInstance().getService().login(workerDTO).enqueue(new Callback<WorkerDTO>() {
            @Override
            public void onResponse(Call<WorkerDTO> call, Response<WorkerDTO> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new LoginSuccess(response.body()));
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        EventBus.getDefault().post(new LoginFailed(jObjError.getString("title")));
                    } catch (Exception e) {
                        EventBus.getDefault().post(new LoginFailed("Błąd a05: " + response.code()));
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
    public WorkerDTO getWorker() {
        return gson.fromJson(preferenceManager.getWorker(), WorkerDTO.class);
    }

    @Override
    public void setWorker(WorkerDTO workerDTO) {
        preferenceManager.setWorker(gson.toJson(workerDTO));
    }

    @Override
    public void updateFwVersion(Long workerId, String fwVersion) {
        RetrofitClient.getInstance().getService().updateFwVersion(workerId, fwVersion).enqueue(new Callback<WorkerDTO>() {
            @Override
            public void onResponse(Call<WorkerDTO> call, Response<WorkerDTO> response) {
                Log.d(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<WorkerDTO> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}