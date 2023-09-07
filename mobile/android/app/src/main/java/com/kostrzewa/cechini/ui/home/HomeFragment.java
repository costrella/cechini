package com.kostrzewa.cechini.ui.home;

import static com.kostrzewa.cechini.util.Constants.IS_UNREAD_REPORTS_FRAGMENT;
import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

import android.content.Context;
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

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.StoreSentFailed;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;
import com.kostrzewa.cechini.ui.mystores.dialog.AddStoreDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private WorkerDataManager workerDataManager;

    @OnClick(R.id.btn_mystores)
    public void onClick1() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_mystores);
    }

    @OnClick(R.id.btn_add_store)
    public void onClick2() {
        new AddStoreDialogFragment(null, null, null)
                .show(getFragmentManager(), "sample");
    }

    @OnClick(R.id.btn_my_reports)
    public void onClick3() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_myreports);
    }

    @OnClick(R.id.btn_synchro)
    public void onClick4() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_synchro);
    }


    @BindView(R.id.btn_unreadReport)
    Button btn_unreadReport;

    @OnClick(R.id.btn_unreadReport)
    public void onClick5() {
        Bundle args = new Bundle();
        args.putBoolean(IS_UNREAD_REPORTS_FRAGMENT, true);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_unread_reports, args);
    }

    String text = "Nieprzeczytane komentarze do raportów";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
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
        RetrofitClient.getInstance().getService()
                .getAllUnreadReportsByWorkerIdCount(workerDataManager.getWorker().getId()).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if(response.isSuccessful()){
                    Long count = response.body();
                    if(count != null && count > 0){
                        btn_unreadReport.setText(text + " ("+ count + ")");
                    } else {
                        btn_unreadReport.setText(text + " (0)");
                    }
                } else {
                    btn_unreadReport.setText("U05");
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                if(!isNetworkConnected()){
                    btn_unreadReport.setText("Brak internetu");
                } else {
                    btn_unreadReport.setText("U04");
                }
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
        Toast.makeText(getActivity(), "Dodano sklep!", Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onStoreSentFailed(StoreSentFailed sentFailed) {
        Toast.makeText(getActivity(), sentFailed.getText(), Toast.LENGTH_LONG).show();
    }

}