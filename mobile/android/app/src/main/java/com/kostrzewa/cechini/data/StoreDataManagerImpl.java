package com.kostrzewa.cechini.data;

import android.content.Context;
import android.util.Log;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.events.MyStoreDownloadFailed;
import com.kostrzewa.cechini.data.events.MyStoreDownloadSuccess;
import com.kostrzewa.cechini.data.events.StoreEditSuccess;
import com.kostrzewa.cechini.data.events.StoreSentFailed;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDataManagerImpl extends AbstractDataManager implements StoreDataManager {

    private static final String TAG = "StoreDataManagerImpl";
    private WorkerDataManager workerDataManager;

    public StoreDataManagerImpl(Context context) {
        super(context);
        workerDataManager = new WorkerDataManagerImpl(context);
    }

    @Override
    public void addNewStore(StoreDTO storeDTO) {
        RetrofitClient.getInstance(getContext()).getService().addStore(storeDTO).enqueue(new Callback<StoreDTO>() {
            @Override
            public void onResponse(Call<StoreDTO> call, Response<StoreDTO> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    StoreDTO body = response.body();

                    List<StoreDTO> currentStores = new ArrayList<>();
                    for (String s : preferenceManager.getMyStores()) {
                        currentStores.add(gson.fromJson(s, StoreDTO.class));
                    }
                    currentStores.add(body);

                    //save
                    Set<String> myset = new HashSet<>();
                    for (StoreDTO currentStore : currentStores) {
                        myset.add(gson.toJson(currentStore));
                    }
                    preferenceManager.setMyStores(myset);

                    EventBus.getDefault().post(body);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        EventBus.getDefault().post(new StoreSentFailed(jObjError.getString("title")));
                    } catch (Exception e) {
                        EventBus.getDefault().post(new StoreSentFailed(getContext().getResources().getString(R.string.error) + " a01: " + response.code()));
                    }


                }
            }

            @Override
            public void onFailure(Call<StoreDTO> call, Throwable t) {
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new StoreSentFailed(getContext().getResources().getString(R.string.internet_needed)));
                } else {
                    EventBus.getDefault().post(new StoreSentFailed(getContext().getResources().getString(R.string.error) + " a02"));
                }
            }
        });
    }

    @Override
    public void editStore(StoreDTO storeDTO, StoreDTO old) {
        RetrofitClient.getInstance(getContext()).getService().editStore(storeDTO).enqueue(new Callback<StoreDTO>() {
            @Override
            public void onResponse(Call<StoreDTO> call, Response<StoreDTO> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    StoreDTO body = response.body();

                    List<StoreDTO> currentStores = new ArrayList<>();
                    for (String s : preferenceManager.getMyStores()) {
                        currentStores.add(gson.fromJson(s, StoreDTO.class));
                    }
                    currentStores.add(body);
                    currentStores.remove(old);

                    //save
                    Set<String> myset = new HashSet<>();
                    for (StoreDTO currentStore : currentStores) {
                        myset.add(gson.toJson(currentStore));
                    }
                    preferenceManager.setMyStores(myset);

                    EventBus.getDefault().post(new StoreEditSuccess());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        EventBus.getDefault().post(new StoreSentFailed(jObjError.getString("title")));
                    } catch (Exception e) {
                        EventBus.getDefault().post(new StoreSentFailed(getContext().getResources().getString(R.string.error) +" a01: " + response.code()));
                    }


                }
            }

            @Override
            public void onFailure(Call<StoreDTO> call, Throwable t) {
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new StoreSentFailed(getContext().getResources().getString(R.string.internet_needed)));
                } else {
                    EventBus.getDefault().post(new StoreSentFailed(getContext().getResources().getString(R.string.error) + " a02"));
                }
            }
        });
    }

    @Override
    public List<StoreDTO> getMyStores() {
        List<StoreDTO> storeDTOS = new ArrayList<>();
        for (String s : preferenceManager.getMyStores()) {
            storeDTOS.add(gson.fromJson(s, StoreDTO.class));
        }
        Collections.sort(storeDTOS);
        return storeDTOS;
    }

    @Override
    public void downloadMyStores() {
        RetrofitClient.getInstance(getContext()).getService().getMyStores(workerDataManager.getWorker().getId())
                .enqueue(new Callback<List<StoreDTO>>() {
                    @Override
                    public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                        Log.d(TAG, "onResponse: " + response.code());
                        if (response.isSuccessful()) {
                            Set<String> myset = new HashSet<>();
                            for (StoreDTO storeDTO : response.body()) {
                                myset.add(gson.toJson(storeDTO));
                            }
                            preferenceManager.setMyStores(myset);
                            EventBus.getDefault().post(new MyStoreDownloadSuccess());
                            preferenceManager.setSychroTimeMyStores();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StoreDTO>> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                        EventBus.getDefault().post(new MyStoreDownloadFailed());
                    }
                });

    }


}
