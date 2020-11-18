package com.kostrzewa.cechini.data;

import android.content.Context;
import android.util.Log;

import com.kostrzewa.cechini.model.ProductDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDataManagerImpl extends AbstractDataManager implements ProductDataManager {
    private static final String TAG = "ProductDataManagerImpl";

    public ProductDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        preferenceManager.getAllProducts().stream().forEach(s -> productDTOS.add(gson.fromJson(s, ProductDTO.class)));
        return productDTOS;
    }

    @Override
    public void downloadProducts() {
        RetrofitClient.getInstance().getService().getAllProducts().enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Set<String> myset = new HashSet<>();
                    response.body().stream().forEach(v -> myset.add(gson.toJson(v)));
                    preferenceManager.setAllProducts(myset);
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }
}
