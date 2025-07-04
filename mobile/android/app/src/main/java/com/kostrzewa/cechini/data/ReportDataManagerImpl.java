package com.kostrzewa.cechini.data;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.events.CommentAddedFailed;
import com.kostrzewa.cechini.data.events.CommentAddedSuccess;
import com.kostrzewa.cechini.data.events.MyReportsDownloadFailed;
import com.kostrzewa.cechini.data.events.MyReportsDownloadSuccess;
import com.kostrzewa.cechini.data.events.OneReportFailed;
import com.kostrzewa.cechini.data.events.OneReportSuccess;
import com.kostrzewa.cechini.data.events.ReportSentFailed;
import com.kostrzewa.cechini.data.events.ReportSentSuccess;
import com.kostrzewa.cechini.data.events.UnreadReportsDownloadFailed;
import com.kostrzewa.cechini.data.events.UnreadReportsDownloadSuccess;
import com.kostrzewa.cechini.model.NoteDTO;
import com.kostrzewa.cechini.model.NotesDTO;
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
    public void addNewComment(NoteDTO noteDTO) {

        RetrofitClient.getInstance(getContext()).getService().addCommentToReportMobile(noteDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new CommentAddedSuccess(getContext().getResources().getString(R.string.comment_added)));
                } else {
                    saveNotSentComment(noteDTO);
                    EventBus.getDefault().post(new CommentAddedFailed(getContext().getResources().getString(R.string.comment_not_added)
                            + " " + getContext().getResources().getString(R.string.error) + response.code()));

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                saveNotSentComment(noteDTO);
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new CommentAddedFailed(getContext().getResources().getString(R.string.internet_needed)));
                } else {
                    EventBus.getDefault().post(new CommentAddedFailed(getContext().getResources().getString(R.string.error) + " a11 "
                            + getContext().getResources().getString(R.string.memory_saved)));
                }
            }
        });
    }

    @Override
    public void send(ReportDTO reportDTO) {
        if (reportDTO.getOrderDTO().getOrderItems().isEmpty()) {
            reportDTO.setOrderDTO(null);
        }

        RetrofitClient.getInstance(getContext()).getService().sendReport(ReportData.reportDTO).enqueue(new Callback<ReportDTO>() {
            @Override
            public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                if (response.isSuccessful()) {
//                    String mailInfo = "";
//                    if(response.body().isSentMail() ){
//                        mailInfo= "E-mail z również !";
//                    }else {
//                        mailInfo= "E-mail nie dotarł !";
//                    }
                    EventBus.getDefault().post(new ReportSentSuccess(getContext().getResources().getString(R.string.report_sent_ok)));
                } else {
                    saveNotSentReport(reportDTO);
                    EventBus.getDefault().post(new ReportSentFailed(
                            getContext().getResources().getString(R.string.report_not_added) + " " +
                                    getContext().getResources().getString(R.string.error) + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                saveNotSentReport(reportDTO);
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new ReportSentFailed(getContext().getResources().getString(R.string.no_internet)
                            + " " + getContext().getResources().getString(R.string.memory_saved)));
                } else {
                    EventBus.getDefault().post(new ReportSentFailed(getContext().getResources().getString(R.string.error)
                            + " a06. " + getContext().getResources().getString(R.string.memory_saved)));
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
            RetrofitClient.getInstance(getContext()).getService().sendManyReports(reportsDTO).enqueue(new Callback<Void>() {
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
            RetrofitClient.getInstance(getContext()).getService().addCommentToReportMobileMany(notesDTO).enqueue(new Callback<Void>() {
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
    public void downloadMyUnreadReportsByWorkerId(Long workerId) {
        RetrofitClient.getInstance(getContext()).getService().getAllUnreadReportsByWorkerId(workerId).enqueue(new Callback<List<ReportDTOWithPhotos>>() {
            @Override
            public void onResponse(Call<List<ReportDTOWithPhotos>> call, Response<List<ReportDTOWithPhotos>> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new UnreadReportsDownloadSuccess(response.body()));
                } else {
                    EventBus.getDefault().post(new UnreadReportsDownloadFailed(getContext().getResources().getString(R.string.error) + " U01"));
                }
            }

            @Override
            public void onFailure(Call<List<ReportDTOWithPhotos>> call, Throwable t) {
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new UnreadReportsDownloadFailed(getContext().getResources().getString(R.string.no_internet) + " U03"));
                } else {
                    EventBus.getDefault().post(new UnreadReportsDownloadFailed(getContext().getResources().getString(R.string.error) + " U02"));
                }
            }
        });
    }


    @Override
    public void downloadMyReports(Long workerId) {
        RetrofitClient.getInstance(getContext()).getService().getMyReports(workerId).enqueue(new Callback<List<ReportDTOWithPhotos>>() {
            @Override
            public void onResponse(Call<List<ReportDTOWithPhotos>> call, Response<List<ReportDTOWithPhotos>> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new MyReportsDownloadSuccess(response.body()));
                    preferenceManager.setSychroTimeMyReports();

                    Set<String> myset = new HashSet<>();
                    for (ReportDTO v : response.body()) {
                        myset.add(gson.toJson(v));
                    }
                    preferenceManager.setMyReports(myset);
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed(getContext().getResources().getString(R.string.error) + " a07"));
                }
            }

            @Override
            public void onFailure(Call<List<ReportDTOWithPhotos>> call, Throwable t) {
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new MyReportsDownloadFailed(getContext().getResources().getString(R.string.load_from_memory)));
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed(getContext().getResources().getString(R.string.error) + " a08"));
                }
            }
        });
    }

    @Override
    public void downloadMyReportsByStoreId(Long workerId, Long storeId) {
        RetrofitClient.getInstance(getContext()).getService().getMyReportsByStoreId(workerId, storeId).enqueue(new Callback<List<ReportDTOWithPhotos>>() {
            @Override
            public void onResponse(Call<List<ReportDTOWithPhotos>> call, Response<List<ReportDTOWithPhotos>> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new MyReportsDownloadSuccess(response.body()));
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed(getContext().getResources().getString(R.string.error) + " a09"));
                }
            }

            @Override
            public void onFailure(Call<List<ReportDTOWithPhotos>> call, Throwable t) {
                if (!isNetworkConnected()) {
                    EventBus.getDefault().post(new MyReportsDownloadFailed(getContext().getResources().getString(R.string.internet_needed)));
                } else {
                    EventBus.getDefault().post(new MyReportsDownloadFailed(getContext().getResources().getString(R.string.error) + " a10"));
                }
            }
        });
    }

    @Override
    public void setReportReadByWorker(Long reportID) {
        if (!isNetworkConnected()) {
            return;
        }
        RetrofitClient.getInstance(getContext()).getService().setReportReadByWorker(reportID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println();
            }
        });

    }

    @Override
    public void downloadOneReport(Long id) {
        if (!isNetworkConnected()) {
            return;
        }
        RetrofitClient.getInstance(getContext()).getService().getReport(id).enqueue(new Callback<ReportDTOWithPhotos>() {
            @Override
            public void onResponse(Call<ReportDTOWithPhotos> call, Response<ReportDTOWithPhotos> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new OneReportSuccess(response.body()));
                } else {
                    EventBus.getDefault().post(new OneReportFailed(getContext().getResources().getString(R.string.error) + " O1"));
                }
            }

            @Override
            public void onFailure(Call<ReportDTOWithPhotos> call, Throwable t) {
                EventBus.getDefault().post(new OneReportFailed(getContext().getResources().getString(R.string.error) + " O2"));
            }
        });
    }
}
