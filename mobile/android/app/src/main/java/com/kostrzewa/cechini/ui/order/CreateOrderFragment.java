package com.kostrzewa.cechini.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ProductDataManager;
import com.kostrzewa.cechini.data.ProductDataManagerImpl;
import com.kostrzewa.cechini.data.WarehouseDataManager;
import com.kostrzewa.cechini.data.WarehouseDataManagerImpl;
import com.kostrzewa.cechini.model.WarehouseDTO;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment;
import com.kostrzewa.cechini.ui.order.dialog.ProductDialogFragment;
import com.kostrzewa.cechini.ui.order.warehouse.WarehouseAdapter;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateOrderFragment extends Fragment {
    private static final String TAG = "CreateOrderFragment";
    private ProductDataManager productDataManager;
    private WarehouseDataManager warehouseDataManager;
    private OrderItemAdapter adapter;
    private WarehouseAdapter warehouseAdapter;
    NavController navController;
    List<WarehouseDTO> warehouseDTOS;
    private MyStoresFragment.OnListFragmentInteractionListener mListener;

    private void init() {
        if (ReportData.reportDTO.isReadOnly()) fillData();
    }

    private void fillData() {
        if (ReportData.reportDTO.getOrderDTO() != null && !ReportData.reportDTO.getOrderDTO().getOrderItems().isEmpty()) {
            for (int i = 0; i < warehouseDTOS.size(); i++) {
                if (ReportData.reportDTO.getOrderDTO().getWarehouseId().equals(
                        warehouseDTOS.get(i).getId()
                )
                ) {
                    warehouseSpinner.setSelection(i);
                }
            }
        }
        warehouseSpinner.setEnabled(false);
        warehouseTV.setText("Hurtownia");
        recyclerView.setLayoutFrozen(true);
        recyclerView.setEnabled(false);
        recyclerView.setActivated(false);
        addProductBtn.setEnabled(false);
    }

    @OnClick(R.id.fragment_order_addProductBtn)
    void addProduct() {
        new ProductDialogFragment(ReportData.reportDTO.getOrderDTO().getOrderItems(), adapter, productDataManager)
                .show(getFragmentManager(), "sample");
    }

    @BindView(R.id.fragment_order_addProductBtn)
    Button addProductBtn;
    @BindView(R.id.fragment_order_recyclerview_emptyTV)
    TextView emptyTV;
    @BindView(R.id.fragment_order_warehouseTV)
    TextView warehouseTV;
    @BindView(R.id.fragment_order_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_order_warehouseSpinner)
    public Spinner warehouseSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_create, container, false);
        ButterKnife.bind(this, view);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        warehouseDataManager = new WarehouseDataManagerImpl(getContext());
        warehouseDTOS = warehouseDataManager.getAllWarehouses();
        warehouseAdapter = new WarehouseAdapter(warehouseDTOS);
        warehouseSpinner.setAdapter(warehouseAdapter);
        init();
        warehouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WarehouseDTO warehouseDTO = warehouseDTOS.get(position);
                ReportData.reportDTO.getOrderDTO().setWarehouseId(warehouseDTO.getId());
                ReportData.reportDTO.getOrderDTO().setWarehouseName(warehouseDTO.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        productDataManager = new ProductDataManagerImpl(getContext());
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderItemAdapter(ReportData.reportDTO.getOrderDTO().getOrderItems(), ReportData.reportDTO.isReadOnly());
        refresh();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                refresh();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                refresh();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                refresh();
            }

        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void refresh() {
        if (ReportData.reportDTO.getOrderDTO().getOrderItems().isEmpty()) {
            emptyTV.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            warehouseSpinner.setVisibility(View.GONE);
            warehouseTV.setVisibility(View.GONE);
        } else {
            emptyTV.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            warehouseSpinner.setVisibility(View.VISIBLE);
            warehouseTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyStoresFragment.OnListFragmentInteractionListener) {
            mListener = (MyStoresFragment.OnListFragmentInteractionListener) context;
        }
    }

}