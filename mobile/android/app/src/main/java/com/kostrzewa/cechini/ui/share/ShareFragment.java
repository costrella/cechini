package com.kostrzewa.cechini.ui.share;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.OrderDataManager;
import com.kostrzewa.cechini.data.OrderDataManagerImpl;
import com.kostrzewa.cechini.data.ProductDataManager;
import com.kostrzewa.cechini.data.ProductDataManagerImpl;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.data.ReportDataManagerImpl;
import com.kostrzewa.cechini.data.StoreDataManager;
import com.kostrzewa.cechini.data.StoreDataManagerImpl;
import com.kostrzewa.cechini.data.WarehouseDataManager;
import com.kostrzewa.cechini.data.WarehouseDataManagerImpl;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.CommentAddedFailed;
import com.kostrzewa.cechini.data.events.CommentAddedSuccess;
import com.kostrzewa.cechini.data.events.MyOrdersDownloadFailed;
import com.kostrzewa.cechini.data.events.MyOrdersDownloadSuccess;
import com.kostrzewa.cechini.data.events.MyReportsDownloadFailed;
import com.kostrzewa.cechini.data.events.MyReportsDownloadSuccess;
import com.kostrzewa.cechini.data.events.MyStoreDownloadFailed;
import com.kostrzewa.cechini.data.events.MyStoreDownloadSuccess;
import com.kostrzewa.cechini.data.events.ProductsDownloadFailed;
import com.kostrzewa.cechini.data.events.ProductsDownloadSuccess;
import com.kostrzewa.cechini.data.events.ReportSentFailed;
import com.kostrzewa.cechini.data.events.ReportSentSuccess;
import com.kostrzewa.cechini.data.events.WarehouseDownloadFailed;
import com.kostrzewa.cechini.data.events.WarehouseDownloadSuccess;
import com.kostrzewa.cechini.data.prefs.PreferenceManager;
import com.kostrzewa.cechini.data.prefs.PreferenceManagerImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ShareFragment extends Fragment {
    PreferenceManager preferenceManager;
    private StoreDataManager storeDataManager;
    private ProductDataManager productDataManager;
    private WarehouseDataManager warehouseDataManager;
    private ReportDataManager reportDataManager;
    private OrderDataManager orderDataManager;
    private WorkerDataManager workerDataManager;
    Handler handler = new Handler();

    TextView synchroStoreTime;
    ProgressBar synchroStoreProgress;

    TextView synchroProductTime;
    ProgressBar synchroProductProgress;

    TextView synchroReportTime;
    ProgressBar synchroReportProgress;

    TextView synchroOrdersTime;
    ProgressBar synchroOrdersProgress;

    TextView synchroWarehouseTime;
    ProgressBar synchroWarehouseProgress;

    TextView synchroReportsNotSent;
    ProgressBar synchroReportsNotSentProgress;

    TextView synchroCommentsNotSent;
    ProgressBar synchroCommentsNotSentProgress;

    TextView version;
    private Button synchroAllBtn;

    public void synchroAllBtn() {
        synchroStoreProgress.setVisibility(View.VISIBLE);
        synchroProductProgress.setVisibility(View.VISIBLE);
        synchroReportProgress.setVisibility(View.VISIBLE);
        synchroOrdersProgress.setVisibility(View.VISIBLE);
        synchroWarehouseProgress.setVisibility(View.VISIBLE);
        storeDataManager.downloadMyStores();
        productDataManager.downloadProducts();
        warehouseDataManager.downloadWarehouse();
        reportDataManager.downloadMyReports(workerDataManager.getWorker().getId());
        orderDataManager.downloadMyOrders(workerDataManager.getWorker().getId());
        if (!preferenceManager.getReportsNotSend().isEmpty()) {
            synchroReportsNotSentProgress.setVisibility(View.VISIBLE);
            reportDataManager.sendReportNotSent();
        }
        if (!preferenceManager.getCommentsNotSend().isEmpty()) {
            synchroCommentsNotSentProgress.setVisibility(View.VISIBLE);
            reportDataManager.sendCommentsNotSent();
        }
        workerDataManager.updateFwVersion(workerDataManager.getWorker().getId(), getVersionApp());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_synchro, container, false);
        synchroStoreTime = root.findViewById(R.id.synchroStoreTime);
        synchroStoreProgress = root.findViewById(R.id.synchroStoreProgress);
        synchroProductTime = root.findViewById(R.id.synchroProductTime);
        synchroProductProgress = root.findViewById(R.id.synchroProductProgress);
        synchroReportTime = root.findViewById(R.id.synchroReportTime);
        synchroReportProgress = root.findViewById(R.id.synchroReportProgress);
        synchroOrdersTime = root.findViewById(R.id.synchroOrdersTime);
        synchroOrdersProgress = root.findViewById(R.id.synchroOrdersProgress);
        synchroWarehouseTime = root.findViewById(R.id.synchroWarehouseTime);
        synchroWarehouseProgress = root.findViewById(R.id.synchroWarehouseProgress);
        synchroReportsNotSent = root.findViewById(R.id.synchroReportsNotSentText);
        synchroReportsNotSentProgress = root.findViewById(R.id.synchroReportsNotSentProgress);
        synchroCommentsNotSent = root.findViewById(R.id.synchroCommentsNotSentText);
        synchroCommentsNotSentProgress = root.findViewById(R.id.synchroCommentsNotSentProgress);
        version = root.findViewById(R.id.version);
        synchroAllBtn = root.findViewById(R.id.synchroAllBtn);
        synchroAllBtn.setOnClickListener(v -> synchroAllBtn());


        preferenceManager = new PreferenceManagerImpl(getContext());
        init();
        String LAST_SYNCHRO = getContext().getResources().getString(R.string.date_since);
        version.setText(getContext().getResources().getString(R.string.app_version_text) + " " + getVersionApp());
        synchroStoreTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeMyStores());
        synchroProductTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeProducts());
        synchroReportTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeMyReports());
        synchroWarehouseTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeWarehouses());
        synchroOrdersTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeOrders());
        synchroReportsNotSent.setText("" + preferenceManager.getReportsNotSend().size());
        synchroCommentsNotSent.setText("" + preferenceManager.getCommentsNotSend().size());

        return root;
    }

    private String getVersionApp() {
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "error";
        }
    }

    private void init() {
        storeDataManager = new StoreDataManagerImpl(getContext());
        productDataManager = new ProductDataManagerImpl(getContext());
        warehouseDataManager = new WarehouseDataManagerImpl(getContext());
        reportDataManager = new ReportDataManagerImpl(getContext());
        orderDataManager = new OrderDataManagerImpl(getContext());
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

    @Subscribe
    public void sub(MyStoreDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroStoreProgress.setVisibility(View.GONE);
            synchroStoreTime.setVisibility(View.VISIBLE);
            synchroStoreTime.setText(getContext().getResources().getString(R.string.date_since) + preferenceManager.getSychroTimeMyStores());
        }, 1500);
    }

    @Subscribe
    public void sub(MyStoreDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroStoreProgress.setVisibility(View.GONE);
            synchroStoreTime.setVisibility(View.VISIBLE);
            synchroStoreTime.setText(getContext().getResources().getString(R.string.error));
        }, 1500);
    }


    @Subscribe
    public void sub(MyOrdersDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroOrdersProgress.setVisibility(View.GONE);
            synchroOrdersTime.setVisibility(View.VISIBLE);
            synchroOrdersTime.setText(getContext().getResources().getString(R.string.date_since) + preferenceManager.getSychroTimeOrders());
        }, 1500);
    }

    @Subscribe
    public void sub(MyOrdersDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroOrdersProgress.setVisibility(View.GONE);
            synchroOrdersTime.setVisibility(View.VISIBLE);
            synchroOrdersTime.setText(getContext().getResources().getString(R.string.error));
        }, 1500);
    }


    @Subscribe
    public void sub(MyReportsDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroReportProgress.setVisibility(View.GONE);
            synchroReportTime.setVisibility(View.VISIBLE);
            synchroReportTime.setText(getContext().getResources().getString(R.string.date_since) + preferenceManager.getSychroTimeMyReports());
        }, 1500);
    }

    @Subscribe
    public void sub(MyReportsDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroReportProgress.setVisibility(View.GONE);
            synchroReportTime.setVisibility(View.VISIBLE);
            synchroReportTime.setText(getContext().getResources().getString(R.string.error));
        }, 1500);
    }


    @Subscribe
    public void sub(WarehouseDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroWarehouseProgress.setVisibility(View.GONE);
            synchroWarehouseTime.setVisibility(View.VISIBLE);
            synchroWarehouseTime.setText(getContext().getResources().getString(R.string.date_since) + preferenceManager.getSychroTimeWarehouses());
        }, 1500);
    }

    @Subscribe
    public void sub(WarehouseDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroWarehouseProgress.setVisibility(View.GONE);
            synchroWarehouseTime.setVisibility(View.VISIBLE);
            synchroWarehouseTime.setText(getContext().getResources().getString(R.string.error));
        }, 1500);
    }

    @Subscribe
    public void sub(ProductsDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroProductProgress.setVisibility(View.GONE);
            synchroProductTime.setVisibility(View.VISIBLE);
            synchroProductTime.setText(getContext().getResources().getString(R.string.date_since) + preferenceManager.getSychroTimeProducts());
        }, 1500);
    }

    @Subscribe
    public void sub(ProductsDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroProductProgress.setVisibility(View.GONE);
            synchroProductTime.setVisibility(View.VISIBLE);
            synchroProductTime.setText(getContext().getResources().getString(R.string.error));
        }, 1500);
    }

    @Subscribe
    public void sub(ReportSentSuccess s) {
        handler.postDelayed(() -> {
            synchroReportsNotSentProgress.setVisibility(View.GONE);
            synchroReportsNotSent.setVisibility(View.VISIBLE);
            synchroReportsNotSent.setText("" + preferenceManager.getReportsNotSend().size());
        }, 1500);
    }

    @Subscribe
    public void sub(ReportSentFailed s) {
        handler.postDelayed(() -> {
            synchroReportsNotSentProgress.setVisibility(View.GONE);
            synchroReportsNotSent.setVisibility(View.VISIBLE);
            synchroReportsNotSent.setText(getContext().getResources().getString(R.string.error));
        }, 1500);
    }

    @Subscribe
    public void sub(CommentAddedSuccess s) {
        handler.postDelayed(() -> {
            synchroCommentsNotSentProgress.setVisibility(View.GONE);
            synchroCommentsNotSent.setVisibility(View.VISIBLE);
            synchroCommentsNotSent.setText("" + preferenceManager.getCommentsNotSend().size());
        }, 1500);
    }

    @Subscribe
    public void sub(CommentAddedFailed s) {
        handler.postDelayed(() -> {
            synchroCommentsNotSentProgress.setVisibility(View.GONE);
            synchroCommentsNotSent.setVisibility(View.VISIBLE);
            synchroCommentsNotSent.setText(getContext().getResources().getString(R.string.error));
        }, 1500);
    }
}