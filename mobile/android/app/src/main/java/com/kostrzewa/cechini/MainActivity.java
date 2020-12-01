package com.kostrzewa.cechini;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kostrzewa.cechini.data.ProductDataManager;
import com.kostrzewa.cechini.data.ProductDataManagerImpl;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.data.ReportDataManagerImpl;
import com.kostrzewa.cechini.data.StoreDataManager;
import com.kostrzewa.cechini.data.StoreDataManagerImpl;
import com.kostrzewa.cechini.data.StoreGroupDataManager;
import com.kostrzewa.cechini.data.StoreGroupDataManagerImpl;
import com.kostrzewa.cechini.data.WarehouseDataManager;
import com.kostrzewa.cechini.data.WarehouseDataManagerImpl;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class MainActivity extends AppCompatActivity implements MyStoresFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private StoreDataManager storeDataManager;
    private ProductDataManager productDataManager;
    private WarehouseDataManager warehouseDataManager;
    private ReportDataManager reportDataManager;
    private StoreGroupDataManager storeGroupDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_mystores, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        storeDataManager = new StoreDataManagerImpl(getApplicationContext());
        productDataManager = new ProductDataManagerImpl(getApplicationContext());
        warehouseDataManager = new WarehouseDataManagerImpl(getApplicationContext());
        reportDataManager = new ReportDataManagerImpl(getApplicationContext());
        storeGroupDataManager = new StoreGroupDataManagerImpl(getApplicationContext());
        storeDataManager.downloadMyStores();
        productDataManager.downloadProducts();
        warehouseDataManager.downloadWarehouse();
        reportDataManager.sendReportNotSent();
        storeGroupDataManager.downloadStoreGroups();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onListFragmentInteraction(StoreDTO item) {
        Log.d(TAG, "onListFragmentInteraction: ");

        Bundle args = new Bundle();
        args.putSerializable(STORE_DTO, item);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_mystores_detail, args);

    }
}
