package com.kostrzewa.cechini.data;

import android.content.Context;
import android.util.Log;

import com.kostrzewa.cechini.model.StoreGroupDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreGroupDataManagerImpl extends AbstractDataManager implements StoreGroupDataManager {

    private static final String TAG = "StoreDataManagerImpl";

    public StoreGroupDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public List<StoreGroupDTO> getStoreGroups() {
        List<StoreGroupDTO> storeGroupDTOS = new ArrayList<>();

        for (String s : preferenceManager.getAllStoreGroups()) {
            storeGroupDTOS.add(gson.fromJson(s, StoreGroupDTO.class));
        }
        return storeGroupDTOS;
    }

    @Override
    public void downloadStoreGroups() {
        RetrofitClient.getInstance(getContext()).getService().getAllStoreGroups().enqueue(new Callback<List<StoreGroupDTO>>() {
            @Override
            public void onResponse(Call<List<StoreGroupDTO>> call, Response<List<StoreGroupDTO>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Set<String> myset = new HashSet<>();
                    for (StoreGroupDTO storeDTO : response.body()) {
                        myset.add(gson.toJson(storeDTO));
                    }
                    preferenceManager.setAllStoreGroups(myset);
                }
            }

            @Override
            public void onFailure(Call<List<StoreGroupDTO>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

}
