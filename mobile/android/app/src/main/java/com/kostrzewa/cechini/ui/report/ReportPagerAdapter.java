package com.kostrzewa.cechini.ui.report;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.order.CreateOrderFragment;
import com.kostrzewa.cechini.ui.report.data.ReportData;
import com.kostrzewa.cechini.ui.report.photo.PhotosFragment;
import com.kostrzewa.cechini.ui.report.send.ReportSendFragment;

public class ReportPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "ReportPagerAdapter";
    private final StoreDTO storeDTO;

    private final Context context;

    public ReportPagerAdapter(FragmentManager fm, StoreDTO storeDTO, Context context) {
        super(fm);
        this.storeDTO = storeDTO;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Log.d(TAG, "getItem: " + i);
        if (ReportData.reportDTO.isReadOnly()) {

            if (ReportData.reportDTO.getOrderDTO() == null) {
                return new CreateReportFragment();
            } else {
                switch (i) {
                    case 0:
                        return new CreateReportFragment();
                    case 1:
                        return new CreateOrderFragment();
                }
            }
        } else {
            switch (i) {
                case 0:
                    return new CreateReportFragment();
                case 1:
                    return new PhotosFragment();
                case 2:
                    return new CreateOrderFragment();
                case 3:
                    return new ReportSendFragment(storeDTO);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        if(ReportData.reportDTO.isReadOnly()){
            if (ReportData.reportDTO.getOrderDTO() == null) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 4;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (ReportData.reportDTO.isReadOnly()) {
            switch (position) {
                case 0:
                    return context.getResources().getString(R.string.adapter_report);
                case 1:
                    return context.getResources().getString(R.string.adapter_order);
            }
        } else {
            switch (position) {
                case 0:
                    return context.getResources().getString(R.string.adapter_report);
                case 1:
                    return context.getResources().getString(R.string.adapter_photos);
                case 2:
                    return context.getResources().getString(R.string.adapter_order);
                case 3:
                    return context.getResources().getString(R.string.adapter_realization);
            }
        }

        return null;
    }
}

