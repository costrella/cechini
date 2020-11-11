package com.kostrzewa.cechini.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.share.ShareViewModel;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class CreateReportFragment extends Fragment {

    private StoreDTO storeDTO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report_create, container, false);
        storeDTO = (StoreDTO) getArguments().getSerializable(STORE_DTO);

        return root;
    }
}