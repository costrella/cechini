package com.kostrzewa.cechini.ui.mystores.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.StoreDTO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyStoreDetailFragment extends Fragment {
    private static final String TAG = "MyStoreDetailFragment";
    private MyStoreDetailViewModel mViewModel;

    @BindView(R.id.mystores_detail_text1)
    TextView textView;

    public static MyStoreDetailFragment newInstance() {
        return new MyStoreDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mystores_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyStoreDetailViewModel.class);
        StoreDTO storeDTO = (StoreDTO) getArguments().getSerializable("storeDTO");
        mViewModel.getmText().setValue("" + storeDTO.getId());
        mViewModel.getmText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        // TODO: Use the ViewModel

    }

}
