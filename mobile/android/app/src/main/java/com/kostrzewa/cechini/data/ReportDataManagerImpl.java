package com.kostrzewa.cechini.data;

import android.content.Context;
import android.util.Log;

import com.kostrzewa.cechini.data.events.MyReportsDownloadFailed;
import com.kostrzewa.cechini.data.events.MyReportsDownloadSuccess;
import com.kostrzewa.cechini.data.events.ReportSentFailed;
import com.kostrzewa.cechini.data.events.ReportSentSuccess;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;
import com.kostrzewa.cechini.model.ReportsDTO;
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
        if (reportDTO.getOrderDTO().getOrderItems().isEmpty()) {
            reportDTO.setOrderDTO(null);
        }

        RetrofitClient.getInstance().getService().sendReport(ReportData.reportDTO).enqueue(new Callback<ReportDTO>() {
            @Override
            public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new ReportSentSuccess("Raport wysłany !"));
                } else {
                    saveNotSentReport(reportDTO);
                    EventBus.getDefault().post(new ReportSentFailed("Raport NIE został wysłany! Zapisano w pamięci! Kod błędu: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                saveNotSentReport(reportDTO);
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new ReportSentFailed("Brak dostępu do internetu. Zapisano w pamięci ! "));
                } else {
                    EventBus.getDefault().post(new ReportSentFailed("Wystąpił problem. Zapisano w pamięci ! "));
                }

            }
        });
    }

    @Override
    public void sendReportNotSent() {
        List<ReportDTO> notSendReports = new ArrayList<>();
        preferenceManager.getReportsNotSend().stream().forEach(s -> notSendReports.add(gson.fromJson(s, ReportDTO.class)));
        if (!notSendReports.isEmpty()) {
            ReportsDTO reportsDTO = new ReportsDTO();
            reportsDTO.setReportsDTOS(notSendReports);
            RetrofitClient.getInstance().getService().sendManyReports(reportsDTO).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d(TAG, "onResponse: " + response.code());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                }
            });
        }
    }

    private void saveNotSentReport(ReportDTO reportDTO) {
        List<ReportDTO> notSendReports = new ArrayList<>();
        preferenceManager.getReportsNotSend().stream().forEach(s -> notSendReports.add(gson.fromJson(s, ReportDTO.class)));
        notSendReports.add(reportDTO);

        Set<String> myset = new HashSet<>();
        notSendReports.stream().forEach(v -> myset.add(gson.toJson(v)));
        preferenceManager.setReportsNotSend(myset);
    }

    @Override
    public void downloadMyReports(Long workerId) {
        RetrofitClient.getInstance().getService().getMyReports(workerId).enqueue(new Callback<List<ReportDTOWithPhotos>>() {
            @Override
            public void onResponse(Call<List<ReportDTOWithPhotos>> call, Response<List<ReportDTOWithPhotos>> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new MyReportsDownloadSuccess(response.body()));
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed("Wystąpił problem"));
                }
            }

            @Override
            public void onFailure(Call<List<ReportDTOWithPhotos>> call, Throwable t) {
                EventBus.getDefault().post(new MyReportsDownloadFailed("Wystąpił problem"));
            }
        });
    }
}
