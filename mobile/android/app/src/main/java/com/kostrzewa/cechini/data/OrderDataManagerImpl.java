package com.kostrzewa.cechini.data;

import android.content.Context;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.events.MyOrdersDownloadFailed;
import com.kostrzewa.cechini.data.events.MyOrdersDownloadSuccess;
import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDataManagerImpl extends AbstractDataManager implements OrderDataManager {

    public OrderDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public void downloadMyOrders(Long workerId) {
        RetrofitClient.getInstance(getContext()).getService().getMyOrders(workerId)
                .enqueue(new Callback<List<OrderDTO>>() {
                    @Override
                    public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                        if (response.isSuccessful()) {
                            EventBus.getDefault().post(new MyOrdersDownloadSuccess(response.body()));
                            preferenceManager.setSychroTimeMyOrders();
                        } else {
                            EventBus.getDefault().post(new MyOrdersDownloadFailed(
                                    getContext().getResources().getString(R.string.error) + " a03!"));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                        if (!isNetworkConnected()) {
                            EventBus.getDefault().post(new MyOrdersDownloadFailed(getContext().getResources().getString(R.string.no_internet)));
                        } else {
                            EventBus.getDefault().post(new MyOrdersDownloadFailed(
                                    getContext().getResources().getString(R.string.error) + " a04!"));
                        }
                    }
                });
    }
}
