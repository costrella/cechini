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
import com.kostrzewa.cechini.model.StoreDTO;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class ReportViewPagerFragment extends Fragment {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    ReportPagerAdapter reportPagerAdapter;
    ViewPager viewPager;
    public static StoreDTO storeDTO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ReportViewPagerFragment.storeDTO = (StoreDTO) getArguments().getSerializable(STORE_DTO);
        return inflater.inflate(R.layout.fragment_report_viewpager, container, false);
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
        ReportViewPagerFragment.storeDTO = null;
    }
}