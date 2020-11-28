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
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class ReportViewPagerFragment extends Fragment {

    ReportPagerAdapter reportPagerAdapter;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StoreDTO currentStore = (StoreDTO) getArguments().getSerializable(STORE_DTO);
        createBaseReport(currentStore);
        return inflater.inflate(R.layout.fragment_report_viewpager, container, false);
    }

    private void createBaseReport(StoreDTO currentStore) {
        ReportData.reportDTO = new ReportDTO();
        ReportData.reportDTO.setOrderDTO(new OrderDTO());
        ReportData.reportDTO.setStoreId(currentStore.getId());
    }

    private void removeBaseReport() {
        ReportData.reportDTO = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        reportPagerAdapter = new ReportPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(reportPagerAdapter);

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