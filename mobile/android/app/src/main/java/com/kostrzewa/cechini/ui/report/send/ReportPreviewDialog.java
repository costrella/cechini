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
import com.kostrzewa.cechini.model.PhotoDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ReportPreviewDialog extends DialogFragment {
    Handler handler = new Handler();
    private View form;
    private final StoreDTO storeDTO;
    private ReportDataManager reportDataManager;

    TextView descTV;

    TextView photoCountTV;

    TextView orderTV;

    TextView mailInfoTV;

    Button sendBtn;

    ProgressBar progressBar;

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
        descTV = form.findViewById(R.id.fragment_report_preview_desc);
        photoCountTV = form.findViewById(R.id.fragment_report_preview_photoCount);
        orderTV = form.findViewById(R.id.fragment_report_preview_order);
        mailInfoTV = form.findViewById(R.id.fragment_report_preview_mailInfoTV);
        sendBtn = form.findViewById(R.id.fragment_report_preview_sendBtn);
        progressBar = form.findViewById(R.id.fragment_report_previewProgressBar);
        sendBtn.setOnClickListener(v -> send());


        reportDataManager = new ReportDataManagerImpl(getContext());
        init();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return (builder.setTitle(getContext().getResources().getString(R.string.report_preview)).setView(form)
//                .setNeutralButton(getContext().getResources().getText(R.string.close), null)
                .create());
    }


    private void init() {
        String desc = ReportData.reportDTO.getDesc() != null ? ReportData.reportDTO.getDesc() : getContext().getResources().getString(R.string.empty);
        descTV.setText("" + desc);
        orderTV.setText("" + genarateOrderPreview());
        int count = 0;
        for(PhotoDTO photoDTO : ReportData.reportDTO.getPhotosList()){
            if(photoDTO.getPhotoFileDTO() != null){
                count++;
            }
        }
        photoCountTV.setText("" + count);

    }

    private String genarateOrderPreview() {
        if (ReportData.reportDTO.getOrderDTO().getOrderItems().isEmpty()) {
            mailInfoTV.setVisibility(View.GONE);
            return getContext().getResources().getString(R.string.empty);
        } else {
            mailInfoTV.setVisibility(View.VISIBLE);
            String warehouseMail = ReportData.reportDTO.getOrderDTO().getWarehouseMail();
            if (!TextUtils.isEmpty(warehouseMail)) {
                if (TextUtils.isEmpty(storeDTO.getNip())) {
                    mailInfoTV.setText(getContext().getResources().getString(R.string.report_nip_not_mail) + " " + warehouseMail);
                    mailInfoTV.setTextColor(getResources().getColor(R.color.red));
                } else {
                    mailInfoTV.setText(getContext().getResources().getString(R.string.report_nip_mail) + " " + warehouseMail);
                    mailInfoTV.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } else {
                mailInfoTV.setText(getContext().getResources().getString(R.string.report_not_mail_warehouse));
                mailInfoTV.setTextColor(getContext().getResources().getColor(R.color.recycler_view_empty_text));
            }

        }
        String value = "";
        int i = 1;
        for (OrderItemDTO item : ReportData.reportDTO.getOrderDTO().getOrderItems()) {
            value += ""+i + ") " + item.getProductName() + "\n\t" + item.getProductCapacity() + "L" + ", zgrzewek: " + item.getPackCount() + "\n";
            i++;
        }
        value += "\n" + getContext().getResources().getString(R.string.store_name) + " " + storeDTO.getName() + "\n\t" + getContext().getResources().getString(R.string.store_address) + " " + storeDTO.getAddress();
        value += "\n" + getContext().getResources().getString(R.string.store_nip) + " " + (storeDTO.getNip() != null ? storeDTO.getNip() : "");
        value += "\n";
        value += "\n" + getContext().getResources().getString(R.string.warehouse)   + " " + ReportData.reportDTO.getOrderDTO().getWarehouseName();
        return value;
    }

    @Subscribe
    public void onReportSentSuccess(ReportSentSuccess r) {
        progressBar.setVisibility(View.GONE);
        sendBtn.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), r.getText(), Toast.LENGTH_LONG).show();
        end();

    }

    @Subscribe
    public void onReportSentFailed(ReportSentFailed r) {
        progressBar.setVisibility(View.GONE);
        sendBtn.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), r.getText(), Toast.LENGTH_LONG).show();
        end();
    }

    private void end() {
        getDialog().dismiss();

        Bundle args = new Bundle();
        args.putSerializable("doRefresh", true);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.popBackStack();
        navController.navigate(R.id.nav_mystores, args);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        EventBus.getDefault().unregister(this);
        super.onDismiss(dialog);

    }
}
