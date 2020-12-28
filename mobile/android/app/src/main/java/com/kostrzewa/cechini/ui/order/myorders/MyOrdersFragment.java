package com.kostrzewa.cechini.ui.order.myorders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.OrderDataManager;
import com.kostrzewa.cechini.data.OrderDataManagerImpl;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.MyOrdersDownloadFailed;
import com.kostrzewa.cechini.data.events.MyOrdersDownloadSuccess;
import com.kostrzewa.cechini.model.OrderDTO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrdersFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private OrderDataManager orderDataManager;
    private WorkerDataManager workerDataManager;
    private MyOrdersRecyclerViewAdapter adapter;
    private List<OrderDTO> orderDTOList;

    @BindView(R.id.fragment_myorders_recyclerView)
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyOrdersFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyOrdersFragment newInstance(int columnCount) {
        MyOrdersFragment fragment = new MyOrdersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderDataManager = new OrderDataManagerImpl(getContext());
        workerDataManager = new WorkerDataManagerImpl(getContext());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_list, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        orderDataManager.downloadMyOrders(workerDataManager.getWorker().getId());

        // Set the adapter
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    @Subscribe
    public void onSuccess(MyOrdersDownloadSuccess s) {
        orderDTOList = s.getList();
        adapter = new MyOrdersRecyclerViewAdapter(getContext(), orderDataManager, orderDTOList);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe
    public void onFailed(MyOrdersDownloadFailed f) {
        Toast.makeText(getActivity(), f.getText(), Toast.LENGTH_LONG).show();
    }

}
