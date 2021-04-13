package com.kostrzewa.cechini.ui.share;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareFragment extends Fragment {
    PreferenceManager preferenceManager;
    private StoreDataManager storeDataManager;
    private ProductDataManager productDataManager;
    private WarehouseDataManager warehouseDataManager;
    private ReportDataManager reportDataManager;
    private OrderDataManager orderDataManager;
    private WorkerDataManager workerDataManager;
    Handler handler = new Handler();

    String LAST_SYNCHRO = "dane z: ";
    String SYNCHRO_ERROR = "błąd";

    @BindView(R.id.synchroStoreTime)
    TextView synchroStoreTime;
    @BindView(R.id.synchroStoreProgress)
    ProgressBar synchroStoreProgress;

    @BindView(R.id.synchroProductTime)
    TextView synchroProductTime;
    @BindView(R.id.synchroProductProgress)
    ProgressBar synchroProductProgress;

    @BindView(R.id.synchroReportTime)
    TextView synchroReportTime;
    @BindView(R.id.synchroReportProgress)
    ProgressBar synchroReportProgress;

    @BindView(R.id.synchroOrdersTime)
    TextView synchroOrdersTime;
    @BindView(R.id.synchroOrdersProgress)
    ProgressBar synchroOrdersProgress;

    @BindView(R.id.synchroWarehouseTime)
    TextView synchroWarehouseTime;
    @BindView(R.id.synchroWarehouseProgress)
    ProgressBar synchroWarehouseProgress;

    @BindView(R.id.synchroReportsNotSentText)
    TextView synchroReportsNotSent;
    @BindView(R.id.synchroReportsNotSentProgress)
    ProgressBar synchroReportsNotSentProgress;

    @BindView(R.id.version)
    TextView version;

    @OnClick(R.id.synchroAllBtn)
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
        workerDataManager.updateFwVersion(workerDataManager.getWorker().getId(), getVersionApp());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_synchro, container, false);
        ButterKnife.bind(this, root);
        preferenceManager = new PreferenceManagerImpl(getContext());
        init();
        version.setText("Wersja aplikacji: " + getVersionApp());
        synchroStoreTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeMyStores());
        synchroProductTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeProducts());
        synchroReportTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeMyReports());
        synchroWarehouseTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeWarehouses());
        synchroOrdersTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeOrders());
        synchroReportsNotSent.setText("" + preferenceManager.getReportsNotSend().size());
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
            synchroStoreTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeMyStores());
        }, 1500);
    }

    @Subscribe
    public void sub(MyStoreDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroStoreProgress.setVisibility(View.GONE);
            synchroStoreTime.setVisibility(View.VISIBLE);
            synchroStoreTime.setText(SYNCHRO_ERROR);
        }, 1500);
    }


    @Subscribe
    public void sub(MyOrdersDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroOrdersProgress.setVisibility(View.GONE);
            synchroOrdersTime.setVisibility(View.VISIBLE);
            synchroOrdersTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeOrders());
        }, 1500);
    }

    @Subscribe
    public void sub(MyOrdersDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroOrdersProgress.setVisibility(View.GONE);
            synchroOrdersTime.setVisibility(View.VISIBLE);
            synchroOrdersTime.setText(SYNCHRO_ERROR);
        }, 1500);
    }


    @Subscribe
    public void sub(MyReportsDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroReportProgress.setVisibility(View.GONE);
            synchroReportTime.setVisibility(View.VISIBLE);
            synchroReportTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeMyReports());
        }, 1500);
    }

    @Subscribe
    public void sub(MyReportsDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroReportProgress.setVisibility(View.GONE);
            synchroReportTime.setVisibility(View.VISIBLE);
            synchroReportTime.setText(SYNCHRO_ERROR);
        }, 1500);
    }


    @Subscribe
    public void sub(WarehouseDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroWarehouseProgress.setVisibility(View.GONE);
            synchroWarehouseTime.setVisibility(View.VISIBLE);
            synchroWarehouseTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeWarehouses());
        }, 1500);
    }

    @Subscribe
    public void sub(WarehouseDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroWarehouseProgress.setVisibility(View.GONE);
            synchroWarehouseTime.setVisibility(View.VISIBLE);
            synchroWarehouseTime.setText(SYNCHRO_ERROR);
        }, 1500);
    }

    @Subscribe
    public void sub(ProductsDownloadSuccess s) {
        handler.postDelayed(() -> {
            synchroProductProgress.setVisibility(View.GONE);
            synchroProductTime.setVisibility(View.VISIBLE);
            synchroProductTime.setText(LAST_SYNCHRO + preferenceManager.getSychroTimeProducts());
        }, 1500);
    }

    @Subscribe
    public void sub(ProductsDownloadFailed s) {
        handler.postDelayed(() -> {
            synchroProductProgress.setVisibility(View.GONE);
            synchroProductTime.setVisibility(View.VISIBLE);
            synchroProductTime.setText(SYNCHRO_ERROR);
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
            synchroReportsNotSent.setText(SYNCHRO_ERROR);
        }, 1500);
    }
}