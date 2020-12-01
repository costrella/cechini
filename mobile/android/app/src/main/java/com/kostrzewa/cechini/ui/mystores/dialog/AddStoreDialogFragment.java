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

package com.kostrzewa.cechini.ui.mystores.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.StoreDataManager;
import com.kostrzewa.cechini.data.StoreDataManagerImpl;
import com.kostrzewa.cechini.data.StoreGroupDataManager;
import com.kostrzewa.cechini.data.StoreGroupDataManagerImpl;
import com.kostrzewa.cechini.data.events.StoreSentFailed;
import com.kostrzewa.cechini.model.OrderItemDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.model.StoreGroupDTO;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment;
import com.kostrzewa.cechini.ui.order.OrderItemAdapter;
import com.kostrzewa.cechini.ui.order.dialog.ProductAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class AddStoreDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "AddStoreDialogFragment";
    private View form = null;
    List<StoreGroupDTO> storeGroupDTOList;
    Spinner spinner;
    StoreGroupDTO selectedStoreGroup;
    StoreGroupDataManager storeGroupDataManager;
    StoreDataManager storeDataManager;
    ArrayAdapter<StoreGroupDTO> spinnerArrayAdapter;
    ProgressBar progressBar;
    private MyStoresFragment.OnListFragmentInteractionListener mListener;
    Button okBtn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        form =
                getActivity().getLayoutInflater()
                        .inflate(R.layout.fragment_mystores_dialog_store, null);

        spinner = form.findViewById(R.id.fragment_mystores_dialog_storeGroupSpinner);
        okBtn = form.findViewById(R.id.fragment_mystores_dialog_store_okBtn);
        progressBar = form.findViewById(R.id.fragment_mystores_dialog_store_progressBar);
        okBtn.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        storeDataManager = new StoreDataManagerImpl(getContext());
        storeGroupDataManager = new StoreGroupDataManagerImpl(getContext());
        storeGroupDTOList = storeGroupDataManager.getStoreGroups();
//        StoreGroupDTO[] tab = (StoreGroupDTO[]) storeGroupDTOList.toArray();
//        productAdapter = new ProductAdapter(storeGroupDTOList);
        spinnerArrayAdapter = new ArrayAdapter<StoreGroupDTO>
                (getContext(), android.R.layout.simple_spinner_dropdown_item,
                        storeGroupDTOList);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);

//        spinner.setAdapter(new ArrayAdapter<StoreGroupDTO>());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return (builder.setTitle("Dodaj sklep").setView(form)
//                .setNeutralButton(android.R.string.ok, this)
//                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create());
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {

//        OrderItemDTO orderItemDTO = new OrderItemDTO();
//        orderItemDTO.setId(selectedStoreGroup.getId());
//        orderItemDTO.setProductId(selectedStoreGroup.getId());
//        orderItemDTO.setProductName(selectedStoreGroup.getName());
////        orderItemDTO.setProductCapacity(selectedStoreGroup.getCapacity());
////        orderItemDTO.setProductArtCountPalette(selectedStoreGroup.getArtCountPalette());
////        orderItemDTO.setProductLayerCountPalette(selectedStoreGroup.getLayerCountPalette());
////        orderItemDTO.setProductPackCountPalette(selectedStoreGroup.getPackCountPalette());
//
//        String packCountString = packCountTV.getText().toString();
//        if (packCountString == null || packCountString.equals("")) {
//            Toast.makeText(getActivity(), "Wypełnij ilość zgrzewek !", Toast.LENGTH_LONG).show();
//            return;
//        }
//        orderItemDTO.setPackCount(Integer.valueOf(packCountString));
//
//        orderItemsList.add(orderItemDTO);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyStoresFragment.OnListFragmentInteractionListener) {
            mListener = (MyStoresFragment.OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedStoreGroup = storeGroupDTOList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedStoreGroup = null;
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "onClick: ");
        EditText name = form.findViewById(R.id.fragment_mystores_dialog_store_nameET);
        EditText address = form.findViewById(R.id.fragment_mystores_dialog_store_addressET);

        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName(name.getText().toString());
        storeDTO.setAddress(address.getText().toString());
        storeDTO.setStoregroupId(selectedStoreGroup.getId());
        storeDataManager.addNewStore(storeDTO);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        EventBus.getDefault().unregister(this);
        super.onDismiss(dialog);
    }

    @Subscribe
    public void onStoreAdded(StoreDTO storeDTO) {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "onStoreAdded: ");

    }

    @Subscribe
    public void onStoreSentFailed(StoreSentFailed sentFailed) {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "onStoreSentFailed: ");
    }


}
