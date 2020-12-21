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

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.data.ReportDataManagerImpl;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.MyReportsDownloadFailed;
import com.kostrzewa.cechini.data.events.MyReportsDownloadSuccess;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyReportsFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private ReportDataManager reportDataManager;
    private WorkerDataManager workerDataManager;
    private MyReportsRecyclerViewAdapter adapter;
    private List<ReportDTOWithPhotos> reportDTOList;

    @BindView(R.id.fragment_myreports_recyclerView)
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyReportsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyReportsFragment newInstance(int columnCount) {
        MyReportsFragment fragment = new MyReportsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
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
        reportDataManager.downloadMyReports(workerDataManager.getWorker().getId());

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
