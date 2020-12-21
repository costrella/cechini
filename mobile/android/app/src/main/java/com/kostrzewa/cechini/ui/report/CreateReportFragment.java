package com.kostrzewa.cechini.ui.report;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateReportFragment extends Fragment {
    private static final String TAG = "CreateReportFragment";
    private NavController navController;

    @BindView(R.id.report_desc_et)
    EditText descET;

    private void init(){
        if(ReportData.reportDTO.isReadOnly()) fillData();
    }

    private void fillData(){
        descET.setText(ReportData.reportDTO.getDesc());
        descET.setEnabled(false);
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