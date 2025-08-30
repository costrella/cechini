package com.kostrzewa.cechini.ui.home;

import static com.kostrzewa.cechini.util.Constants.IS_UNREAD_REPORTS_FRAGMENT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.LoginActivity;
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.LoginFailed;
import com.kostrzewa.cechini.data.events.LoginSuccess;
import com.kostrzewa.cechini.data.events.StoreSentFailed;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.model.WorkerDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;
import com.kostrzewa.cechini.ui.mystores.dialog.AddStoreDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private WorkerDataManager workerDataManager;
    private Button btn_add_store;
    private Button btn_my_reports;
    private Button btn_synchro;

    public void onClick1() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_mystores);
    }

    public void onClick2() {
        new AddStoreDialogFragment(null, null, null)
                .show(getFragmentManager(), "sample");
    }

    public void onClick3() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_myreports);
    }

    public void onClick4() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_synchro);
    }


    Button btn_unreadReport;

    public void onClick5() {
        Bundle args = new Bundle();
        args.putBoolean(IS_UNREAD_REPORTS_FRAGMENT, true);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_unread_reports, args);
    }

    Button btn_mystores;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btn_mystores = root.findViewById(R.id.btn_mystores);
        btn_mystores.setOnClickListener(v -> onClick1());
        btn_add_store = root.findViewById(R.id.btn_add_store);
        btn_add_store.setOnClickListener(v -> onClick2());

        btn_my_reports = root.findViewById(R.id.btn_my_reports);
        btn_my_reports.setOnClickListener(v -> onClick3());

        btn_synchro = root.findViewById(R.id.btn_synchro);
        btn_synchro.setOnClickListener(v -> onClick4());

        btn_unreadReport = root.findViewById(R.id.btn_unreadReport);
        btn_unreadReport.setOnClickListener(v -> onClick5());

        workerDataManager = new WorkerDataManagerImpl(getContext());
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllUnreadReportsByWorkerIdCount();
    }

    private void getAllUnreadReportsByWorkerIdCount() {
        String text = getContext().getResources().getString(R.string.unread_comments);
        RetrofitClient.getInstance(getContext()).getService()
                .getAllUnreadReportsByWorkerIdCount(workerDataManager.getWorker().getId()).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.code() == 401) {
                    WorkerDTO worker = workerDataManager.getWorker();
                    if (worker != null) {
                        workerDataManager.loginAsync(worker);
                    } else {
                        SharedPreferences settings = getContext().getSharedPreferences("cechini", Context.MODE_PRIVATE);
                        settings.edit().clear().commit();

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getContext().startActivity(intent);
                    }
                    return;
                }

                if(response.isSuccessful()){
                    Long count = response.body();
                    if(count != null && count > 0){

                        btn_unreadReport
                                .setBackgroundColor(getContext().
                                getResources()
                                        .getColor(R.color.red));

                        btn_unreadReport.setText(text + " ("+ count + ")");
                    } else {
                        btn_unreadReport
                                .setBackgroundColor(getContext()
                                        .getResources().getColor(R.color.colorPrimaryDark));
                        btn_unreadReport.setText(text + " (0)");
                    }
                } else {
                    btn_unreadReport.setText("U05");
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
//                if(!isNetworkConnected()){
//                    btn_unreadReport.setText("Brak internetu");
//                } else {
//                }
                btn_unreadReport.setText("U04");
            }
        });
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onStoreAdded(StoreDTO storeDTO) {
        Toast.makeText(getActivity(), getContext().getResources().getString(R.string.store_added), Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onStoreSentFailed(StoreSentFailed sentFailed) {
        Toast.makeText(getActivity(), sentFailed.getText(), Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onLoginSuccess(LoginSuccess loginSuccess) {
    }


    @Subscribe
    public void onLoginFailed(LoginFailed loginFailed) {
    }

}