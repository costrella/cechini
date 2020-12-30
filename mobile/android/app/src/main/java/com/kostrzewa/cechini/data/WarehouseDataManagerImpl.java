package com.kostrzewa.cechini.data;

import android.content.Context;
import android.util.Log;

import com.kostrzewa.cechini.data.events.WarehouseDownloadFailed;
import com.kostrzewa.cechini.data.events.WarehouseDownloadSuccess;
import com.kostrzewa.cechini.model.ProductDTO;
import com.kostrzewa.cechini.model.WarehouseDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehouseDataManagerImpl extends AbstractDataManager implements WarehouseDataManager {
    private static final String TAG = "WarehouseDataManagerImpl";

    public WarehouseDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        List<WarehouseDTO> warehouseDTOS = new ArrayList<>();
        preferenceManager.getAllWarehouses().stream().forEach(s -> warehouseDTOS.add(gson.fromJson(s, WarehouseDTO.class)));
        return warehouseDTOS;
    }

    @Override
    public void downloadWarehouse() {
        RetrofitClient.getInstance().getService().getAllWarehouses().enqueue(new Callback<List<WarehouseDTO>>() {
            @Override
            public void onResponse(Call<List<WarehouseDTO>> call, Response<List<WarehouseDTO>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Set<String> myset = new HashSet<>();
                    response.body().stream().forEach(v -> myset.add(gson.toJson(v)));
                    preferenceManager.setAllWarehouses(myset);
                    EventBus.getDefault().post(new WarehouseDownloadSuccess());
                    preferenceManager.setSychroTimeWarehouses();
                }
            }

            @Override
            public void onFailure(Call<List<WarehouseDTO>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                EventBus.getDefault().post(new WarehouseDownloadFailed());
            }
        });
    }

}
