package com.kostrzewa.cechini.data;

import android.content.Context;

import com.kostrzewa.cechini.data.events.ReportSentFailed;
import com.kostrzewa.cechini.data.events.ReportSentSuccess;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDataManagerImpl extends AbstractDataManager implements ReportDataManager {
    private static final String TAG = "ReportDataManagerImpl";

    public ReportDataManagerImpl(Context context) {
        super(context);
    }

    @Override
    public void send(ReportDTO reportDTO) {
        RetrofitClient.getInstance().getService().sendReport(ReportData.reportDTO).enqueue(new Callback<ReportDTO>() {
            @Override
            public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new ReportSentSuccess("Raport wysłany ! Możesz oglądać swoje raporty i zamówienia w zakładce 'Moje raporty' / 'Moje zamówienia'"));
                } else {
                    saveNotSentReport(reportDTO);
                    EventBus.getDefault().post(new ReportSentFailed("Raport nie został wysłany. Zapisano raport w pamięci telefonu! Kod błędu: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                saveNotSentReport(reportDTO);
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new ReportSentFailed("Brak dostępu do internetu. Zapisano raport w pamięci telefonu! "));
                } else {
                    EventBus.getDefault().post(new ReportSentFailed("Wystąpił problem. Zapisano raport w pamięci telefonu! "));
                }

            }
        });
    }

    private void saveNotSentReport(ReportDTO reportDTO) {
        List<ReportDTO> notSendReports = new ArrayList<>();
        preferenceManager.getReportsNotSend().stream().forEach(s -> notSendReports.add(gson.fromJson(s, ReportDTO.class)));
        notSendReports.add(reportDTO);

        Set<String> myset = new HashSet<>();
        notSendReports.stream().forEach(v -> myset.add(gson.toJson(v)));
        preferenceManager.setReportsNotSend(myset);
    }

//    @Override
//    public List<WarehouseDTO> getAllWarehouses() {
//        List<WarehouseDTO> warehouseDTOS = new ArrayList<>();
//        preferenceManager.getAllWarehouses().stream().forEach(s -> warehouseDTOS.add(gson.fromJson(s, WarehouseDTO.class)));
//        return warehouseDTOS;
//    }
//
//    @Override
//    public void downloadWarehouse() {
//        RetrofitClient.getInstance().getService().getAllWarehouses().enqueue(new Callback<List<WarehouseDTO>>() {
//            @Override
//            public void onResponse(Call<List<WarehouseDTO>> call, Response<List<WarehouseDTO>> response) {
//                Log.d(TAG, "onResponse: " + response.code());
//                if (response.isSuccessful()) {
//                    Set<String> myset = new HashSet<>();
//                    response.body().stream().forEach(v -> myset.add(gson.toJson(v)));
//                    preferenceManager.setAllWarehouses(myset);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<WarehouseDTO>> call, Throwable t) {
//                Log.d(TAG, "onFailure: ");
//            }
//        });
//    }


}
