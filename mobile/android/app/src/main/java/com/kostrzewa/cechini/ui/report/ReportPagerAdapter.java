package com.kostrzewa.cechini.ui.report;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.order.CreateOrderFragment;
import com.kostrzewa.cechini.ui.report.data.ReportData;
import com.kostrzewa.cechini.ui.report.photo.PhotosFragment;
import com.kostrzewa.cechini.ui.report.send.ReportSendFragment;

public class ReportPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "ReportPagerAdapter";
    private final StoreDTO storeDTO;

    public ReportPagerAdapter(FragmentManager fm, StoreDTO storeDTO) {
        super(fm);
        this.storeDTO = storeDTO;
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
                    return "Raport";
                case 1:
                    return "Zamówienie";
            }
        } else {
            switch (position) {
                case 0:
                    return "Raport";
                case 1:
                    return "Zdjęcia";
                case 2:
                    return "Zamówienie";
                case 3:
                    return "Realizacja";
            }
        }

        return null;
    }
}

