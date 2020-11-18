package com.kostrzewa.cechini.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ProductDataManager;
import com.kostrzewa.cechini.data.ProductDataManagerImpl;
import com.kostrzewa.cechini.model.OrderItemDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.order.dialog.ProductDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class CreateOrderFragment extends Fragment {
    private static final String TAG = "CreateOrderFragment";
    private StoreDTO storeDTO;
    private ProductDataManager productDataManager;
    private OrderItemAdapter adapter;
    private RecyclerView recyclerView;
    private List<OrderItemDTO> orderItemsList;
    int tmp = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FloatingActionButton btn = getActivity().findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                new ProductDialogFragment(orderItemsList, adapter, productDataManager).show(getFragmentManager(), "sample");

            }
        });
        View view = inflater.inflate(R.layout.fragment_order_create, container, false);
        
        storeDTO = (StoreDTO) getArguments().getSerializable(STORE_DTO);
        productDataManager = new ProductDataManagerImpl(getContext());
        Log.d(TAG, "onCreateView: " + productDataManager.getAllProducts());
        orderItemsList = new ArrayList<>();
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new OrderItemAdapter(orderItemsList);
            recyclerView.setAdapter(adapter);
        }
//        createEmptyOrderItemDTO();
        return view;
    }

    private void createEmptyOrderItemDTO(){
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        tmp++;
        orderItemDTO.setProductName("empty " + tmp);
        orderItemsList.add(orderItemDTO);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }
    
    
}