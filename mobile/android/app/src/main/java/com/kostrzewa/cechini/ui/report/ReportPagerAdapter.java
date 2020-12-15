package com.kostrzewa.cechini.ui.report;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kostrzewa.cechini.ui.order.CreateOrderFragment;
import com.kostrzewa.cechini.ui.report.photo.PhotosFragment;
import com.kostrzewa.cechini.ui.report.send.ReportSendFragment;

public class ReportPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "ReportPagerAdapter";

    public ReportPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Log.d(TAG, "getItem: " + i);
        switch (i) {
            case 0:
                return new CreateReportFragment();
            case 1:
                return new PhotosFragment();
            case 2:
                return new CreateOrderFragment();
            case 3:
                return new ReportSendFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
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
        return null;
    }
}

