package com.kostrzewa.cechini.ui.report.myreports;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kostrzewa.cechini.MainActivity;
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.data.ReportDataManagerImpl;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.MyReportsDownloadFailed;
import com.kostrzewa.cechini.data.events.MyReportsDownloadSuccess;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;
import com.kostrzewa.cechini.model.StoreDTO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class MyReportsFragment extends Fragment {
    private ReportDataManager reportDataManager;
    private WorkerDataManager workerDataManager;
    private MyReportsRecyclerViewAdapter adapter;
    private List<ReportDTOWithPhotos> reportDTOList;

    @BindView(R.id.fragment_myreports_recyclerView)
    RecyclerView recyclerView;

    public MyReportsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportDataManager = new ReportDataManagerImpl(getContext());
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
        View view = inflater.inflate(R.layout.fragment_myreports_list, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        if (getArguments() != null && (StoreDTO) getArguments().getSerializable(STORE_DTO) != null) {
            StoreDTO storeDTO = (StoreDTO) getArguments().getSerializable(STORE_DTO);
            reportDataManager.downloadMyReportsByStoreId(workerDataManager.getWorker().getId(), storeDTO.getId());
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Raporty sklepu: " + storeDTO.getName() + " " + storeDTO.getAddress());

        } else {
            reportDataManager.downloadMyReports(workerDataManager.getWorker().getId());
        }
        // Set the adapter
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    @Subscribe
    public void onSuccess(MyReportsDownloadSuccess s) {
        reportDTOList = s.getList();
        adapter = new MyReportsRecyclerViewAdapter(getContext(), getActivity(), reportDataManager, reportDTOList);
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onFailed(MyReportsDownloadFailed f) {
        Toast.makeText(getActivity(), f.getText(), Toast.LENGTH_LONG).show();
    }

}
