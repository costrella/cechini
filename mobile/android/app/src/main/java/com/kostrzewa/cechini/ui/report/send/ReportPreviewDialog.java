package com.kostrzewa.cechini.ui.report.send;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.data.ReportDataManagerImpl;
import com.kostrzewa.cechini.data.events.ReportSentFailed;
import com.kostrzewa.cechini.data.events.ReportSentSuccess;
import com.kostrzewa.cechini.model.OrderItemDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class ReportPreviewDialog extends DialogFragment {
    Handler handler = new Handler();
    private View form;
    private final StoreDTO storeDTO;
    private ReportDataManager reportDataManager;

    @BindView(R.id.fragment_report_preview_desc)
    TextView descTV;

    @BindView(R.id.fragment_report_preview_photoCount)
    TextView photoCountTV;

    @BindView(R.id.fragment_report_preview_order)
    TextView orderTV;

    @BindView(R.id.fragment_report_preview_mailInfoTV)
    TextView mailInfoTV;

    @BindView(R.id.fragment_report_preview_sendBtn)
    Button sendBtn;

    @BindView(R.id.fragment_report_previewProgressBar)
    ProgressBar progressBar;

    @OnClick(R.id.fragment_report_preview_sendBtn)
    void send() {
        progressBar.setVisibility(View.VISIBLE);
        sendBtn.setVisibility(View.GONE);
        handler.postDelayed(() -> reportDataManager.send(ReportData.reportDTO), 1500);
    }

    public ReportPreviewDialog(StoreDTO storeDTO) {
        this.storeDTO = storeDTO;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        form =
                getActivity().getLayoutInflater()
                        .inflate(R.layout.fragment_report_preview, null);
        ButterKnife.bind(this, form);
        reportDataManager = new ReportDataManagerImpl(getContext());
        init();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return (builder.setTitle("Podgląd").setView(form)
//                .setNeutralButton(getContext().getResources().getText(R.string.close), null)
                .create());
    }


    private void init() {
        String desc = ReportData.reportDTO.getDesc() != null ? ReportData.reportDTO.getDesc() : "brak";
        descTV.setText("" + desc);
        photoCountTV.setText("" + ReportData.reportDTO.getPhotosList().size());
        orderTV.setText("" + genarateOrderPreview());
    }

    private String genarateOrderPreview() {
        if (ReportData.reportDTO.getOrderDTO().getOrderItems().isEmpty()) {
            mailInfoTV.setVisibility(View.GONE);
            return "brak";
        } else {
            mailInfoTV.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(storeDTO.getNip())) {
                mailInfoTV.setText("Z powodu braku NIP, system nie wyśle e-mail z zamówieniem.");
                mailInfoTV.setTextColor(getResources().getColor(R.color.red));
            } else {
                mailInfoTV.setText("System wyśle również e-mail z zamówieniem na odpowiedni adres");
                mailInfoTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            }
            String value = "";
            int i = 1;
            for (OrderItemDTO item : ReportData.reportDTO.getOrderDTO().getOrderItems()) {
                value += "\n" + i + ") " + item.getProductName() + " " + item.getProductCapacity() + "L" + ", zgrzewek:" + item.getPackCount();
                i++;
            }
            value += "\n ";
            value += "\n Sklep: " + storeDTO.getName() + ", adres: " + storeDTO.getAddress();
            value += "\n Sklep NIP: " + storeDTO.getNip();
            value += "\n ";
            value += "\n Hurtownia: " + ReportData.reportDTO.getOrderDTO().getWarehouseName();
            return value;
        }

        @Subscribe
        public void onReportSentSuccess (ReportSentSuccess r){
            progressBar.setVisibility(View.GONE);
            sendBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), r.getText(), Toast.LENGTH_LONG).show();
            end();

        }

        @Subscribe
        public void onReportSentFailed (ReportSentFailed r){
            progressBar.setVisibility(View.GONE);
            sendBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), r.getText(), Toast.LENGTH_LONG).show();
            end();
        }

        private void end () {
            getDialog().dismiss();

            Bundle args = new Bundle();
            args.putSerializable(STORE_DTO, storeDTO);
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.popBackStack();
            navController.navigate(R.id.nav_mystores_detail, args);
        }

        @Override
        public void onDismiss (DialogInterface dialog){
            EventBus.getDefault().unregister(this);
            super.onDismiss(dialog);

        }
    }
