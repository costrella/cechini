package com.kostrzewa.cechini.ui.report;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.rest.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class CreateReportFragment extends Fragment {
    private static final String TAG = "CreateReportFragment";
    private NavController navController;
    private StoreDTO storeDTO;
    public static OrderDTO orderDTO;
//    public static List<OrderItemDTO> orderItemsList;

    @BindView(R.id.report_desc_et)
    EditText descET;

    @OnClick(R.id.report_addOrder_btn)
    void addOrder() {
        navController.navigate(R.id.nav_order_create);
    }

    @OnClick(R.id.report_sendReport_btn)
    void sendReport() {
        ReportDTO reportDTO = new ReportDTO();
        if (!CreateReportFragment.orderDTO.getOrderItems().isEmpty()) {
            reportDTO.setOrderDTO(CreateReportFragment.orderDTO);
        }

        reportDTO.setDesc(descET.getText().toString());
        reportDTO.setStoreId(storeDTO.getId());
        reportDTO.setWorkerId(1L); //todo
//        reportDTO.setReportDate();
        sendReport(reportDTO);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report_create, container, false);
        ButterKnife.bind(this, root);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//        storeDTO = (StoreDTO) getArguments().getSerializable(STORE_DTO); //todo

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        CreateReportFragment.orderItemsList = new ArrayList<>();
        CreateReportFragment.orderDTO = new OrderDTO();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        CreateReportFragment.orderItemsList.clear();
        CreateReportFragment.orderDTO = null;
    }

    private void sendReport(ReportDTO reportDTO) {
        RetrofitClient.getInstance().getService().sendReport(reportDTO).enqueue(new Callback<ReportDTO>() {
            @Override
            public void onResponse(Call<ReportDTO> call, Response<ReportDTO> response) {
                Log.d(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<ReportDTO> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }
}