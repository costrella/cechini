package com.kostrzewa.cechini.ui.report.send;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportSendFragment extends Fragment {
    private static final String TAG = "ReportSendFragment";
    private final StoreDTO storeDTO;

    @BindView(R.id.fragment_report_sendBtn)
    Button sendBtn;

    @BindView(R.id.fragment_report_send_validError)
    TextView validTV;

    @BindView(R.id.fragment_report_sendProgressBar)
    ProgressBar progressBar;

    public ReportSendFragment(StoreDTO storeDTO) {
        this.storeDTO = storeDTO;
    }

    @OnClick(R.id.fragment_report_sendBtn)
    public void sendReport() {
        if (valid()) {
            new ReportPreviewDialog(storeDTO)
                    .show(getFragmentManager(), ReportPreviewDialog.class.getSimpleName());
        }
    }

    private boolean valid() {
        View focusView = null;
        validTV.setText("");
        if (ReportData.reportDTO.getOrderDTO() == null ||
                ReportData.reportDTO.getOrderDTO().getOrderItems() == null
                || ReportData.reportDTO.getOrderDTO().getOrderItems().isEmpty()) {
            //raport bez zamowienia:
            if (TextUtils.isEmpty(ReportData.reportDTO.getDesc())) {
                validTV.setText("Opis raportu jest pusty :( \n Wymagamy go tylko wtedy, kiedy nie dodajesz zamówienia");
                sendBtn.setError("");
                focusView = sendBtn;
                focusView.requestFocus();
                return false;
            }
        }
        return true;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report_send, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

}