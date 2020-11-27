package com.kostrzewa.cechini.ui.report;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kostrzewa.cechini.ui.order.CreateOrderFragment;
import com.kostrzewa.cechini.ui.tools.DemoObjectFragment;
import com.kostrzewa.cechini.ui.tools.DemoObjectFragment2;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class ReportPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "DemoCollectionPagerAdap";

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
                return new CreateOrderFragment();
        }
        return null;
//        Fragment fragment = DemoObjectFragment.getInstance();
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
//        fragment.setArguments(args);
//        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Raport";
            case 1:
                return "Zamówienie";
        }
        return null;
    }
}

