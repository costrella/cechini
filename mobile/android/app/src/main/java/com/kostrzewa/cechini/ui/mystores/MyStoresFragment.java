package com.kostrzewa.cechini.ui.mystores;

import android.os.Bundle;
import android.util.Log;
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
import com.kostrzewa.cechini.rest.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStoresFragment extends Fragment {
    private static final String TAG = "MyStoresFragment";
    private MyStoresViewModel myStoresViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myStoresViewModel =
                ViewModelProviders.of(this).get(MyStoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mystores, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        myStoresViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        RetrofitClient.getInstance().getService().getAllStores().enqueue(new Callback<List<StoreDTO>>() {
            @Override
            public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                Log.d(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<List<StoreDTO>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

//        getContext().getSharedPreferences().get
    }
}