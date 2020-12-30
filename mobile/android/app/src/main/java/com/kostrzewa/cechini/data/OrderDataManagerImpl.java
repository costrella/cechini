package com.kostrzewa.cechini.data;

import android.content.Context;
import android.util.Log;

import com.kostrzewa.cechini.data.events.MyOrdersDownloadFailed;
import com.kostrzewa.cechini.data.events.MyOrdersDownloadSuccess;
import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.model.ProductDTO;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDataManagerImpl extends AbstractDataManager implements OrderDataManager {

    public OrderDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public void downloadMyOrders(Long workerId) {
        RetrofitClient.getInstance().getService().getMyOrders(workerId)
                .enqueue(new Callback<List<OrderDTO>>() {
                    @Override
                    public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                        if (response.isSuccessful()) {
                            EventBus.getDefault().post(new MyOrdersDownloadSuccess(response.body()));
                            preferenceManager.setSychroTimeMyOrders();
                        } else {
                            EventBus.getDefault().post(new MyOrdersDownloadFailed("Wystpił błąd!"));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                        EventBus.getDefault().post(new MyOrdersDownloadFailed("Wystpił błąd!"));
                    }
                });
    }
}
