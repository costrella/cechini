package com.kostrzewa.cechini.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.events.StoreSentFailed;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.mystores.dialog.AddStoreDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @OnClick(R.id.btn_mystores)
    public void onClick1(){
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_mystores);
    }

    @OnClick(R.id.btn_add_store)
    public void onClick2(){
        new AddStoreDialogFragment(null, null)
                .show(getFragmentManager(), "sample");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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