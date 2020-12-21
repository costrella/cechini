package com.kostrzewa.cechini.ui.mystores.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.MainActivity;
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.StoreDTO;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class MyStoreDetailFragment extends Fragment {
    private static final String TAG = "MyStoreDetailFragment";
    private StoreDTO storeDTO;
    private NavController navController;

    @OnClick(R.id.btn_addReport)
    void addReport() {
        Bundle args = new Bundle();
        args.putSerializable(STORE_DTO, storeDTO);
        navController.navigate(R.id.nav_report_create, args);
    }

    @OnClick(R.id.btn_storeReports)
    void storeReports() {
        Bundle args = new Bundle();
        args.putSerializable(STORE_DTO, storeDTO);
        navController.navigate(R.id.nav_myreportsOfStore, args);
    }

    public static MyStoreDetailFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        return new MyStoreDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mystores_detail, container, false);
        ButterKnife.bind(this, view);
        storeDTO = (StoreDTO) getArguments().getSerializable(STORE_DTO);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Sklep:" + storeDTO.getName() + " " + storeDTO.getAddress());
        return view;
    }

}
