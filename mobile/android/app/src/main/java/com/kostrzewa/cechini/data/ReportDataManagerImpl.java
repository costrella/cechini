package com.kostrzewa.cechini.data;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.kostrzewa.cechini.data.events.CommentAddedFailed;
import com.kostrzewa.cechini.data.events.CommentAddedSuccess;
import com.kostrzewa.cechini.data.events.MyReportsDownloadFailed;
import com.kostrzewa.cechini.data.events.MyReportsDownloadSuccess;
import com.kostrzewa.cechini.data.events.ReportSentFailed;
import com.kostrzewa.cechini.data.events.ReportSentSuccess;
import com.kostrzewa.cechini.model.NoteDTO;
import com.kostrzewa.cechini.model.NotesDTO;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;
import com.kostrzewa.cechini.model.ReportsDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Comparator;
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
    public void addNewComment(NoteDTO noteDTO) {

        RetrofitClient.getInstance().getService().addCommentToReportMobile(noteDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new CommentAddedSuccess("Komentarz dodany!"));
                } else {
                    saveNotSentComment(noteDTO);
                    EventBus.getDefault().post(new CommentAddedFailed("Komentarz NIE został dodany! Zapisano w pamięci! Kod błędu: " + response.code()));

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                saveNotSentComment(noteDTO);
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new CommentAddedFailed("Ta operacja wymaga internetu! Zapisano w pamięci."));
                } else {
                    EventBus.getDefault().post(new CommentAddedFailed("Wystąpił problem a11, Zapisano w pamięci"));
                }
            }
        });
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
//                    String mailInfo = "";
//                    if(response.body().isSentMail() ){
//                        mailInfo= "E-mail z również !";
//                    }else {
//                        mailInfo= "E-mail nie dotarł !";
//                    }
                    EventBus.getDefault().post(new ReportSentSuccess("Raport wysłany!"));
                } else {
                    saveNotSentReport(reportDTO);
                    EventBus.getDefault().post(new ReportSentFailed("Raport NIE został wysłany! Zapisano w pamięci! Kod błędu: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                saveNotSentReport(reportDTO);
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new ReportSentFailed("Brak internetu. Zapisano w pamięci ! "));
                } else {
                    EventBus.getDefault().post(new ReportSentFailed("Wystąpił problem a06. Zapisano w pamięci ! "));
                }

            }
        });
    }

    @Override
    public void sendReportNotSent() {
        List<ReportDTOWithPhotos> notSendReports = new ArrayList<>();
        for (String s : preferenceManager.getReportsNotSend()) {
            notSendReports.add(gson.fromJson(s, ReportDTOWithPhotos.class));
        }
        if (!notSendReports.isEmpty()) {
            ReportsDTO reportsDTO = new ReportsDTO();
            reportsDTO.setReportsDTOS(notSendReports);
            RetrofitClient.getInstance().getService().sendManyReports(reportsDTO).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: " + response.code());
                        preferenceManager.setReportsNotSend(new HashSet<>());
                        EventBus.getDefault().post(new ReportSentSuccess("ok"));
                    } else {
                        EventBus.getDefault().post(new ReportSentFailed("synchro ReportSentFailed, code:" + response.code()));
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    EventBus.getDefault().post(new ReportSentFailed("error"));
                    Log.d(TAG, "onFailure: ");
                }
            });
        }
    }

    @Override
    public void sendCommentsNotSent() {
        List<NoteDTO> notSendComments = new ArrayList<>();
        for (String s : preferenceManager.getCommentsNotSend()) {
            notSendComments.add(gson.fromJson(s, NoteDTO.class));
        }

        if (!notSendComments.isEmpty()) {
            NotesDTO notesDTO = new NotesDTO();
            notesDTO.setNoteDTOS(notSendComments);
            RetrofitClient.getInstance().getService().addCommentToReportMobileMany(notesDTO).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: " + response.code());
                        preferenceManager.setCommentsNotSend(new HashSet<>());
                        EventBus.getDefault().post(new CommentAddedSuccess("ok"));
                    } else {
                        EventBus.getDefault().post(new CommentAddedFailed("synchro CommentAddedFailed, code:" + response.code()));
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    EventBus.getDefault().post(new CommentAddedFailed("synchro CommentAddedFailed"));
                    Log.d(TAG, "onFailure: ");
                }
            });
        }
    }

    private void saveNotSentReport(ReportDTO reportDTO) {
        List<ReportDTO> notSendReports = new ArrayList<>();
        for (String s : preferenceManager.getReportsNotSend()) {
            notSendReports.add(gson.fromJson(s, ReportDTO.class));
        }
        notSendReports.add(reportDTO);

        Set<String> myset = new HashSet<>();
        for (ReportDTO v : notSendReports) {
            myset.add(gson.toJson(v));
        }
        preferenceManager.setReportsNotSend(myset);
    }

    private void saveNotSentComment(NoteDTO noteDTO) {
        List<NoteDTO> notSendComments = new ArrayList<>();
        for (String s : preferenceManager.getCommentsNotSend()) {
            notSendComments.add(gson.fromJson(s, NoteDTO.class));
        }
        notSendComments.add(noteDTO);

        Set<String> myset = new HashSet<>();
        for (NoteDTO v : notSendComments) {
            myset.add(gson.toJson(v));
        }
        preferenceManager.setCommentsNotSend(myset);
    }

    @Override
    public List<ReportDTOWithPhotos> getMyReports() {
        List<ReportDTOWithPhotos> reportDTOS = new ArrayList<>();
        for (String s : preferenceManager.getMyReports()) {
            reportDTOS.add(gson.fromJson(s, ReportDTOWithPhotos.class));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            reportDTOS.sort((o1, o2) -> o2.getReportDate().compareTo(o1.getReportDate()));
        }
        return reportDTOS;
    }

    @Override
    public void downloadMyReports(Long workerId) {
        RetrofitClient.getInstance().getService().getMyReports(workerId).enqueue(new Callback<List<ReportDTOWithPhotos>>() {
            @Override
            public void onResponse(Call<List<ReportDTOWithPhotos>> call, Response<List<ReportDTOWithPhotos>> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new MyReportsDownloadSuccess(response.body()));
                    preferenceManager.setSychroTimeMyReports();

                    Set<String> myset = new HashSet<>();
                    for (ReportDTOWithPhotos v : response.body()) {
                        myset.add(gson.toJson(v));
                    }
                    preferenceManager.setMyReports(myset);
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed("Wystąpił problem a07"));
                }
            }

            @Override
            public void onFailure(Call<List<ReportDTOWithPhotos>> call, Throwable t) {
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new MyReportsDownloadFailed("Brak internetu. Wczytuję raporty z pamięci."));
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed("Wystąpił problem a08"));
                }
            }
        });
    }

    @Override
    public void downloadMyReportsByStoreId(Long workerId, Long storeId) {
        RetrofitClient.getInstance().getService().getMyReportsByStoreId(workerId, storeId).enqueue(new Callback<List<ReportDTOWithPhotos>>() {
            @Override
            public void onResponse(Call<List<ReportDTOWithPhotos>> call, Response<List<ReportDTOWithPhotos>> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new MyReportsDownloadSuccess(response.body()));
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed("Wystąpił problem a09"));
                }
            }

            @Override
            public void onFailure(Call<List<ReportDTOWithPhotos>> call, Throwable t) {
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new MyReportsDownloadFailed("Ta operacja wymaga internetu!"));
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed("Wystąpił problem a10"));
                }
            }
        });
    }

    @Override
    public void setReportReadByWorker(Long reportID) {
        if (!isNetworkConnected()) {
            return;
        }
        RetrofitClient.getInstance().getService().setReportReadByWorker(reportID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("ok");
                }else  {
                    System.out.println("nie ok");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println();
            }
        });

    }
}
