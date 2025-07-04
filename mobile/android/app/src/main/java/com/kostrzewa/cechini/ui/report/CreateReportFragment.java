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
import com.kostrzewa.cechini.ui.mystores.dialog.AddStoreDialogFragment;
import com.kostrzewa.cechini.ui.report.data.ReportData;
import com.kostrzewa.cechini.util.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateReportFragment extends Fragment {
    private static final String TAG = "CreateReportFragment";
    private NavController navController;

    @BindView(R.id.report_desc_et)
    EditText descET;

    @BindView(R.id.reportId_tv)
    TextView reportId_tv;

    @BindView(R.id.report_manager_et)
    EditText reportManagerET;

    @BindView(R.id.report_photoCount_tv)
    TextView photoCountTV;

    @BindView(R.id.btn_addComment)
    Button addCommentBtn;

    @BindView(R.id.btn_report_readyDesc1)
    Button btnReportReadyDesc1;
    @BindView(R.id.btn_report_readyDesc2)
    Button btnReportReadyDesc2;
    @BindView(R.id.btn_report_readyDesc3)
    Button btnReportReadyDesc3;
    @BindView(R.id.btn_report_readyDesc4)
    Button btnReportReadyDesc4;
    @BindView(R.id.btn_report_readyDesc_clean)
    Button btnReportReadyClean;

    @BindView(R.id.report_preview_photo_1)
    ImageView reportPreviewPhoto1;

    @BindView(R.id.report_preview_photo_2)
    ImageView reportPreviewPhoto2;

    @BindView(R.id.report_preview_photo_3)
    ImageView reportPreviewPhoto3;

    @OnClick(R.id.btn_addComment)
    void addComment() {
        new AddCommentDialogFragment(null, null, ReportData.reportDTO)
                .show(getFragmentManager(), "sample");
    }

    @OnClick(R.id.btn_report_readyDesc1)
    void addDesc1() {
        descET.setText(descET.getText().toString() + getResources().getString(R.string.report_ready_desc_zamowienie) + " ");
    }
    @OnClick(R.id.btn_report_readyDesc2)
    void addDesc2() {
        descET.setText(descET.getText().toString() + getResources().getString(R.string.report_ready_desc_rozmowa_handlowa) + " ");
    }
    @OnClick(R.id.btn_report_readyDesc3)
    void addDesc3() {
        descET.setText(descET.getText().toString() + getResources().getString(R.string.report_ready_desc_merchandising) + " ");
    }

    @OnClick(R.id.btn_report_readyDesc4)
    void addDesc4() {
        descET.setText(descET.getText().toString() + getResources().getString(R.string.report_ready_desc_dostawa_towaru) + " ");
    }

    @OnClick(R.id.btn_report_readyDesc_clean)
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

        descET.setText("Mój opis: " + ReportData.reportDTO.getDesc() != null ? ReportData.reportDTO.getDesc() : "");
        descET.setEnabled(false);

        if (ReportData.reportDTO.getNotes() != null && !ReportData.reportDTO.getNotes().isEmpty()) {
            StringBuilder comments = new StringBuilder();
            int i = 0;
            for (NoteDTO note : ReportData.reportDTO.getNotes()) {
                if (i > 0) comments.append("\n---------------\n");
                comments.append(note.getNoteType().equals(NoteType.BY_MANGER) ? "od Managera:\n " : "od Ciebie:\n");
                comments.append(DateUtils.parse(note.getDate()));
                comments.append("\n");
                comments.append(note.getValue());
                i++;
            }
            reportManagerET.setText("Komentarze: \n\n" + comments);
        } else {
            reportManagerET.setText("");
        }
        reportManagerET.setEnabled(false);
        reportManagerET.setVisibility(View.VISIBLE);

        photoCountTV.setText("liczba przesłanych zdjęć: " + ReportData.reportDTO.getPhotosCount());
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
        ButterKnife.bind(this, root);
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