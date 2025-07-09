package com.kostrzewa.cechini.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.LoginActivity;
import com.kostrzewa.cechini.R;

public class LogoutFragment extends Fragment {

    private Button logout_yesBtn;
    private Button logout_noBtn;

    public void yes(){
        SharedPreferences settings = getContext().getSharedPreferences("cechini", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void no(){
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.popBackStack();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        logout_yesBtn = view.findViewById(R.id.logout_yesBtn);
        logout_yesBtn.setOnClickListener(v -> yes());
        logout_noBtn = view.findViewById(R.id.logout_noBtn);
        logout_noBtn.setOnClickListener(v -> no());

        return view;
    }
}
