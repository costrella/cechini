package com.kostrzewa.cechini.ui.report.send;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.events.ReportSendResult;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportSendFragment extends Fragment {
    private static final String TAG = "ReportSendFragment";
    private NavController navController;

    @OnClick(R.id.fragment_report_sendBtn)
    void sendReport() {

        ReportData.reportDTO.setWorkerId(1L); //todo
        RetrofitClient.getInstance().getService().sendReport(ReportData.reportDTO).enqueue(new Callback<ReportDTO>() {
            @Override
            public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                Log.d(TAG, "onResponse: " + response.code());
                Toast.makeText(getActivity(), "Wysłano raport !", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new ReportSendResult());
                navController.popBackStack();
            }

            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                //todo save memory etc
                Toast.makeText(getActivity(), "Ups. Coś poszło nie tak. Raport zapisany w pamięci !", Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new ReportSendResult());
                navController.popBackStack();
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report_send, container, false);
        ButterKnife.bind(this, root);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        return root;
    }

}