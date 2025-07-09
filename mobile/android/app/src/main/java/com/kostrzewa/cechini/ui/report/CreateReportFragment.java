package com.kostrzewa.cechini.ui.report;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.NoteDTO;
import com.kostrzewa.cechini.model.NoteType;
import com.kostrzewa.cechini.ui.mystores.dialog.AddCommentDialogFragment;
import com.kostrzewa.cechini.ui.report.data.ReportData;
import com.kostrzewa.cechini.util.DateUtils;

public class CreateReportFragment extends Fragment {
    private static final String TAG = "CreateReportFragment";
    private NavController navController;

    EditText descET;

    TextView reportId_tv;

    EditText reportManagerET;

    TextView photoCountTV;

    Button addCommentBtn;

    Button btnReportReadyDesc1;
    Button btnReportReadyDesc2;
    Button btnReportReadyDesc3;
    Button btnReportReadyDesc4;
    Button btnReportReadyClean;

    ImageView reportPreviewPhoto1;

    ImageView reportPreviewPhoto2;

    ImageView reportPreviewPhoto3;

    void addComment() {
        new AddCommentDialogFragment(null, null, ReportData.reportDTO)
                .show(getFragmentManager(), "sample");
    }

    void addDesc1() {
        descET.setText(descET.getText().toString() + getContext().getResources().getString(R.string.report_ready_desc_zamowienie) + " ");
    }
    void addDesc2() {
        descET.setText(descET.getText().toString() + getContext().getResources().getString(R.string.report_ready_desc_rozmowa_handlowa) + " ");
    }
    void addDesc3() {
        descET.setText(descET.getText().toString() + getContext().getResources().getString(R.string.report_ready_desc_merchandising) + " ");
    }

    void addDesc4() {
        descET.setText(descET.getText().toString() + getContext().getResources().getString(R.string.report_ready_desc_dostawa_towaru) + " ");
    }

    void addDesc5() {
        descET.setText("");
    }


    private void init() {
        if (ReportData.reportDTO.isReadOnly()){
            fillDataReadOnly();
        } else {
            addCommentBtn.setVisibility(View.GONE);
            reportId_tv.setVisibility(View.GONE);
            btnReportReadyDesc1.setVisibility(View.VISIBLE);
            btnReportReadyDesc2.setVisibility(View.VISIBLE);
            btnReportReadyDesc3.setVisibility(View.VISIBLE);
            btnReportReadyDesc4.setVisibility(View.VISIBLE);
            btnReportReadyClean.setVisibility(View.VISIBLE);
        }
    }

    private void fillDataReadOnly() {
        btnReportReadyDesc1.setVisibility(View.GONE);
        btnReportReadyDesc2.setVisibility(View.GONE);
        btnReportReadyDesc3.setVisibility(View.GONE);
        btnReportReadyDesc4.setVisibility(View.GONE);
        btnReportReadyClean.setVisibility(View.GONE);
        if (ReportData.reportDTO.getId() != null) {
            reportId_tv.setText("ID: " + ReportData.reportDTO.getId());
            reportId_tv.setVisibility(View.VISIBLE);
        }else{
            reportId_tv.setVisibility(View.GONE);
        }

        descET.setText(getContext().getResources().getString(R.string.report_my_desc) + " " + ReportData.reportDTO.getDesc() != null ? ReportData.reportDTO.getDesc() : "");
        descET.setEnabled(false);

        if (ReportData.reportDTO.getNotes() != null && !ReportData.reportDTO.getNotes().isEmpty()) {
            StringBuilder comments = new StringBuilder();
            int i = 0;
            for (NoteDTO note : ReportData.reportDTO.getNotes()) {
                if (i > 0) comments.append("\n---------------\n");
                comments.append(note.getNoteType().equals(NoteType.BY_MANGER)
                        ? getContext().getResources().getString(R.string.comment_from_manager)+"\n "
                        : getContext().getResources().getString(R.string.comment_from_you)+ "\n");
                comments.append(DateUtils.parse(note.getDate()));
                comments.append("\n");
                comments.append(note.getValue());
                i++;
            }
            reportManagerET.setText(getContext().getResources().getString(R.string.report_comments) + "\n\n" + comments);
        } else {
            reportManagerET.setText("");
        }
        reportManagerET.setEnabled(false);
        reportManagerET.setVisibility(View.VISIBLE);

        photoCountTV.setText(getContext().getResources().getString(R.string.report_photos_sent) + " " + ReportData.reportDTO.getPhotosCount());
        photoCountTV.setEnabled(false);

        if (ReportData.reportDTO.getPhotos() != null
                && !ReportData.reportDTO.getPhotos().isEmpty()) {

            if (ReportData.reportDTO.getPhotos().size() >= 1 && ReportData.reportDTO.getPhotos().get(0) != null) {
                Bitmap bitmap = byteToBitMap(ReportData.reportDTO.getPhotos().get(0).getValue());
                if (bitmap != null) {
                    reportPreviewPhoto1.setImageBitmap(bitmap);
                }
            }

            if (ReportData.reportDTO.getPhotos().size() >= 2 && ReportData.reportDTO.getPhotos().get(1) != null) {
                Bitmap bitmap = byteToBitMap(ReportData.reportDTO.getPhotos().get(1).getValue());
                if (bitmap != null) {
                    reportPreviewPhoto2.setImageBitmap(bitmap);
                }
            }

            if (ReportData.reportDTO.getPhotos().size() >= 3 && ReportData.reportDTO.getPhotos().get(2) != null) {
                Bitmap bitmap = byteToBitMap(ReportData.reportDTO.getPhotos().get(2).getValue());
                if (bitmap != null) {
                    reportPreviewPhoto3.setImageBitmap(bitmap);
                }
            }

        }
    }

    public Bitmap byteToBitMap(byte[] bytes){
        try{
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public Bitmap stringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report_create, container, false);
        descET= root.findViewById(R.id.report_desc_et);
        reportId_tv= root.findViewById(R.id.reportId_tv);
        reportManagerET= root.findViewById(R.id.report_manager_et);
        photoCountTV= root.findViewById(R.id.report_photoCount_tv);
        addCommentBtn= root.findViewById(R.id.btn_addComment);
        btnReportReadyDesc1= root.findViewById(R.id.btn_report_readyDesc1);
        btnReportReadyDesc2= root.findViewById(R.id.btn_report_readyDesc2);
        btnReportReadyDesc3= root.findViewById(R.id.btn_report_readyDesc3);
        btnReportReadyDesc4= root.findViewById(R.id.btn_report_readyDesc4);
        btnReportReadyClean= root.findViewById(R.id.btn_report_readyDesc_clean);
        reportPreviewPhoto1= root.findViewById(R.id.report_preview_photo_1);
        reportPreviewPhoto2= root.findViewById(R.id.report_preview_photo_2);
        reportPreviewPhoto3= root.findViewById(R.id.report_preview_photo_3);
        addCommentBtn.setOnClickListener(v -> addComment());
        btnReportReadyDesc1.setOnClickListener(v -> addDesc1());
        btnReportReadyDesc2.setOnClickListener(v -> addDesc2());
        btnReportReadyDesc3.setOnClickListener(v -> addDesc3());
        btnReportReadyDesc4.setOnClickListener(v -> addDesc4());
        btnReportReadyClean.setOnClickListener(v -> addDesc5());


        init();
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        descET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ReportData.reportDTO.setDesc(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }

}