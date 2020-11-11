package com.kostrzewa.cechini.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.StoreDTO;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class CreateOrderFragment extends Fragment {

    private StoreDTO storeDTO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_create, container, false);
        storeDTO = (StoreDTO) getArguments().getSerializable(STORE_DTO);

        return root;
    }
}