/***
 Copyright (c) 2012 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 Covered in detail in the book _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package com.kostrzewa.cechini.ui.order.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ProductDataManager;
import com.kostrzewa.cechini.model.OrderItemDTO;
import com.kostrzewa.cechini.model.ProductDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment;
import com.kostrzewa.cechini.ui.order.OrderItemAdapter;

import java.util.List;

public class ProductDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener, AdapterView.OnItemSelectedListener {
    private View form = null;
    List<OrderItemDTO> orderItemsList;
    List<ProductDTO> productsList;
    OrderItemAdapter adapter;
    Spinner spinner;
    ProductDTO selectedProduct;
    final StoreDTO storeDTO;
    private ProductAdapter productAdapter;
    final ProductDataManager productDataManager;
    private MyStoresFragment.OnListFragmentInteractionListener mListener;



    public ProductDialogFragment(StoreDTO storeDTO, List<OrderItemDTO> orderItemsList, OrderItemAdapter adapter, ProductDataManager productDataManager) {
        this.storeDTO = storeDTO;
        this.adapter = adapter;
        this.orderItemsList = orderItemsList;
        this.productDataManager = productDataManager;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form =
                getActivity().getLayoutInflater()
                        .inflate(R.layout.dialog, null);

        spinner = form.findViewById(R.id.order_item_dialog_product_spinner);
        spinner.setOnItemSelectedListener(this);
        productsList = productDataManager.getAllProducts();
        productAdapter = new ProductAdapter(storeDTO, productsList);
        spinner.setAdapter(productAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return (builder.setTitle("Wybierz produkt").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        EditText packCountTV = form.findViewById(R.id.order_packCount);
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(selectedProduct.getId());
        orderItemDTO.setProductId(selectedProduct.getId());
        orderItemDTO.setProductName(selectedProduct.getName());
        orderItemDTO.setProductCapacity(selectedProduct.getCapacity());
//        orderItemDTO.setProductArtCountPalette(selectedProduct.getArtCountPalette());
//        orderItemDTO.setProductLayerCountPalette(selectedProduct.getLayerCountPalette());
//        orderItemDTO.setProductPackCountPalette(selectedProduct.getPackCountPalette());

        String packCountString = packCountTV.getText().toString();
        if (packCountString == null || packCountString.equals("")) {
            Toast.makeText(getActivity(), "Wypełnij ilość zgrzewek !", Toast.LENGTH_LONG).show();
            return;
        }
        orderItemDTO.setPackCount(Integer.valueOf(packCountString));

        orderItemsList.add(orderItemDTO);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyStoresFragment.OnListFragmentInteractionListener) {
            mListener = (MyStoresFragment.OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
        Log.d(getClass().getSimpleName(), "Goodbye!");
    }

    @Override
    public void onCancel(DialogInterface unused) {
        super.onCancel(unused);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedProduct = productsList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedProduct = null;
    }
}
