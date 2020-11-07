package com.kostrzewa.cechini.data;

import android.content.Context;
import android.util.Log;

import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDataManagerImpl extends AbstractDataManager implements StoreDataManager {

    private static final String TAG = "StoreDataManagerImpl";

    public StoreDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public List<StoreDTO> getMyStores() {
        List<StoreDTO> storeDTOS = new ArrayList<>();

        preferenceManager.getMyStores().stream().forEach(s -> storeDTOS.add(gson.fromJson(s, StoreDTO.class)));
        return storeDTOS;
    }

    @Override
    public void downloadMyStores() {
        RetrofitClient.getInstance().getService().getAllStores().enqueue(new Callback<List<StoreDTO>>() { //todo change to mystores
            @Override
            public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Set<String> myset = new HashSet<>();
                    response.body().stream().forEach(storeDTO -> myset.add(gson.toJson(storeDTO)));
                    preferenceManager.setMyStores(myset);
                }
            }

            @Override
            public void onFailure(Call<List<StoreDTO>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }


}
