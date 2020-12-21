package com.kostrzewa.cechini.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kostrzewa.cechini.MainActivity;
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import static com.kostrzewa.cechini.util.Constants.REPORT_DTO;
import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class ReportViewPagerFragment extends Fragment {

    ReportPagerAdapter reportPagerAdapter;
    ViewPager viewPager;
    WorkerDataManager workerDataManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StoreDTO currentStore = (StoreDTO) getArguments().getSerializable(STORE_DTO);
        ReportDTO reportDTO = (ReportDTO) getArguments().getSerializable(REPORT_DTO);
        workerDataManager = new WorkerDataManagerImpl(getContext());
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
            if(ReportData.reportDTO.getOrderDTO() == null){
                ReportData.reportDTO.setOrderDTO(new OrderDTO());
            }
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Podgląd raportu");
        }

    }

    private void removeBaseReport() {
        ReportData.reportDTO = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        reportPagerAdapter = new ReportPagerAdapter(getChildFragmentManager());
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

}