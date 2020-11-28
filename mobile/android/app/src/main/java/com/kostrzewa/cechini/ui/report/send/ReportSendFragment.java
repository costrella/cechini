package com.kostrzewa.cechini.ui.report.send;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.data.ReportDataManagerImpl;
import com.kostrzewa.cechini.data.events.ReportSentFailed;
import com.kostrzewa.cechini.data.events.ReportSentSuccess;
import com.kostrzewa.cechini.model.WarehouseDTO;
import com.kostrzewa.cechini.ui.order.CreateOrderFragment;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportSendFragment extends Fragment {
    private static final String TAG = "ReportSendFragment";
    private NavController navController;
    private ReportDataManager reportDataManager;

    @BindView(R.id.fragment_report_sendBtn)
    Button sendBtn;

    @OnClick(R.id.fragment_report_sendBtn)
    void sendReport() {
        ReportData.reportDTO.setWorkerId(1L); //todo
        reportDataManager.send(ReportData.reportDTO);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report_send, container, false);
        ButterKnife.bind(this, root);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        reportDataManager = new ReportDataManagerImpl(getContext());
        return root;
    }

    @Subscribe
    public void onReportSentSuccess(ReportSentSuccess r) {
        Toast.makeText(getActivity(), r.getText(), Toast.LENGTH_LONG).show();
        navController.popBackStack();
    }

    @Subscribe
    public void onReportSentFailed(ReportSentFailed r) {
        Toast.makeText(getActivity(), r.getText(), Toast.LENGTH_LONG).show();
        navController.popBackStack();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}