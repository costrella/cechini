package com.kostrzewa.cechini.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kostrzewa.cechini.MainActivity;
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.data.ReportDataManagerImpl;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.OneReportFailed;
import com.kostrzewa.cechini.data.events.OneReportSuccess;
import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import static com.kostrzewa.cechini.util.Constants.REPORT_DTO;
import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ReportViewPagerFragment extends Fragment {

    ReportPagerAdapter reportPagerAdapter;
    ViewPager viewPager;
    WorkerDataManager workerDataManager;

    ReportDataManager reportDataManager;
    StoreDTO currentStore;
    private View view;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        currentStore = (StoreDTO) getArguments().getSerializable(STORE_DTO);
        ReportDTO reportDTO = (ReportDTO) getArguments().getSerializable(REPORT_DTO);
        workerDataManager = new WorkerDataManagerImpl(getContext());
        reportDataManager = new ReportDataManagerImpl(getContext());
        createBaseReport(reportDTO, currentStore);
        return inflater.inflate(R.layout.fragment_report_viewpager, container, false);
    }

    private void createBaseReport(ReportDTO reportDTO, StoreDTO currentStore) {
        if (reportDTO == null && currentStore != null) {
            ReportData.reportDTO = new ReportDTOWithPhotos();
            ReportData.reportDTO.setReadOnly(false);
            ReportData.reportDTO.setOrderDTO(new OrderDTO());
            ReportData.reportDTO.setStoreId(currentStore.getId());
            ReportData.reportDTO.setWorkerId(workerDataManager.getWorker().getId());
        } else {
            ReportData.reportDTO = (ReportDTOWithPhotos) reportDTO;
            ReportData.reportDTO.setReadOnly(true);
//            if(ReportData.reportDTO.getOrderDTO() == null){
//                ReportData.reportDTO.setOrderDTO(new OrderDTO());
//            }
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Podgląd raportu");
            setReportReadByWorker(ReportData.reportDTO);

            reportDataManager.downloadOneReport(ReportData.reportDTO.getId());
        }

    }
    @Subscribe
    public void oneReportSuccess(OneReportSuccess oneReportSuccess) {
        ReportData.reportDTO = oneReportSuccess.getReportDTOWithPhotos();
        ReportData.reportDTO.setReadOnly(true);
        load();
    }

    @Subscribe
    public void oneReportFailed(OneReportFailed oneReportFailed) {
        Toast.makeText(getActivity(), "Error: " + oneReportFailed.getText(), Toast.LENGTH_SHORT).show();
    }

    private void setReportReadByWorker(ReportDTO reportDTO){
        if(reportDTO.getReadByWorker() != null && !reportDTO.getReadByWorker().booleanValue()){
            reportDataManager = new ReportDataManagerImpl(getContext());
            reportDataManager.setReportReadByWorker(reportDTO.getId());
        }
    }

    private void removeBaseReport() {
        ReportData.reportDTO = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        load();
    }

    private void load(){
        reportPagerAdapter = new ReportPagerAdapter(getChildFragmentManager(), currentStore);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(reportPagerAdapter);
        viewPager.setOffscreenPageLimit(10);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeBaseReport();
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

}