package com.kostrzewa.cechini.ui.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ProductDataManager;
import com.kostrzewa.cechini.data.ProductDataManagerImpl;
import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.model.OrderItemDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment;
import com.kostrzewa.cechini.ui.order.dialog.ProductDialogFragment;
import com.kostrzewa.cechini.ui.report.CreateReportFragment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class CreateOrderFragment extends Fragment {
    private static final String TAG = "CreateOrderFragment";
    private StoreDTO storeDTO;
    private ProductDataManager productDataManager;
    private OrderItemAdapter adapter;
    private FloatingActionButton sendBtn;
    NavController navController;
    private MyStoresFragment.OnListFragmentInteractionListener mListener;


    @OnClick(R.id.fragment_order_addProductBtn)
    void addProduct() {
        new ProductDialogFragment(storeDTO, CreateReportFragment.orderDTO.getOrderItems(), adapter, productDataManager).show(getFragmentManager(), "sample");
    }

    @BindView(R.id.fragment_order_recyclerview_emptyTV)
    TextView emptyTV;
    @BindView(R.id.fragment_order_recyclerview)
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        sendBtn = getActivity().findViewById(R.id.orderItem_add);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setOrderItems(CreateReportFragment.orderDTO.getOrderItems());
//                orderDTO.setDeliveryDate(Instant.now());
//                orderDTO.setOrderDate(Instant.now());
                orderDTO.toString();
                RetrofitClient.getInstance().getService().sendOrder(orderDTO).enqueue(new Callback<OrderDTO>() {
                    @Override
                    public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                        Log.d(TAG, "onResponse: " + response.code());
                        Toast.makeText(getActivity(), "Wysłano poprawnie", Toast.LENGTH_SHORT).show();
//                        navController.navigate(R.id.nav_mystores_detail);
                        mListener.onListFragmentInteraction(storeDTO);
                    }

                    @Override
                    public void onFailure(Call<OrderDTO> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
            }
        });
        View view = inflater.inflate(R.layout.fragment_order_create, container, false);
        ButterKnife.bind(this, view);

//        orderItemsList = (List<OrderItemDTO>) getArguments().getSerializable("test");

//        storeDTO = (StoreDTO) getArguments().getSerializable(STORE_DTO); todo
        productDataManager = new ProductDataManagerImpl(getContext());
        Log.d(TAG, "onCreateView: " + productDataManager.getAllProducts());
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderItemAdapter(CreateReportFragment.orderDTO.getOrderItems());
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
//        createEmptyOrderItemDTO();
        return view;
    }

    private void refresh() {
        if (CreateReportFragment.orderDTO.getOrderItems().isEmpty()) {
            emptyTV.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyTV.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyStoresFragment.OnListFragmentInteractionListener) {
            mListener = (MyStoresFragment.OnListFragmentInteractionListener) context;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        sendBtn.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        sendBtn.setVisibility(View.GONE);
    }


}