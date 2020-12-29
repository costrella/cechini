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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.StoreDataManager;
import com.kostrzewa.cechini.data.StoreDataManagerImpl;
import com.kostrzewa.cechini.data.StoreGroupDataManager;
import com.kostrzewa.cechini.data.StoreGroupDataManagerImpl;
import com.kostrzewa.cechini.data.WorkerDataManager;
import com.kostrzewa.cechini.data.WorkerDataManagerImpl;
import com.kostrzewa.cechini.data.events.StoreSentFailed;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.model.StoreGroupDTO;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment;
import com.kostrzewa.cechini.ui.mystores.MyStoresRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.kostrzewa.cechini.util.Constants.STORE_DTO;

public class AddStoreDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "AddStoreDialogFragment";
    private View form = null;
    List<StoreGroupDTO> storeGroupDTOList = new ArrayList<>();
    Spinner spinner;
    StoreGroupDTO selectedStoreGroup;
    StoreGroupDataManager storeGroupDataManager;
    StoreDataManager storeDataManager;
    WorkerDataManager workerDataManager;
    ArrayAdapter<StoreGroupDTO> spinnerArrayAdapter;
    ProgressBar progressBar;
    private MyStoresFragment.OnListFragmentInteractionListener mListener;
    Button okBtn;
    List<StoreDTO> storeDTOList;
    MyStoresRecyclerViewAdapter adapter;

    public AddStoreDialogFragment(List<StoreDTO> storeDTOList, MyStoresRecyclerViewAdapter adapter) {
        this.storeDTOList = storeDTOList;
        this.adapter = adapter;
    }

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
        workerDataManager = new WorkerDataManagerImpl(getContext());
        storeGroupDTOList = storeGroupDataManager.getStoreGroups();

        spinnerArrayAdapter = new ArrayAdapter<StoreGroupDTO>
                (getContext(), android.R.layout.simple_spinner_dropdown_item,
                        storeGroupDTOList);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return (builder.setTitle("Dodaj sklep").setView(form)
//                .setNeutralButton(android.R.string.ok, this)
//                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create());
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

    EditText nameET;
    EditText addressET;
    EditText nipET;
    TextView storeGroupTV;

    @Override
    public void onClick(View v) {
        nameET = form.findViewById(R.id.fragment_mystores_dialog_store_nameET);
        addressET = form.findViewById(R.id.fragment_mystores_dialog_store_addressET);
        storeGroupTV = form.findViewById(R.id.fragment_mystores_dialog_storeGroupSpinner_label);
        nipET = form.findViewById(R.id.fragment_mystores_dialog_store_nipET);

        if (isValid()) {
            progressBar.setVisibility(View.VISIBLE);
            StoreDTO storeDTO = new StoreDTO();
            storeDTO.setName(nameET.getText().toString());
            storeDTO.setAddress(addressET.getText().toString());
            storeDTO.setNip(nipET.getText().toString());
//            storeDTO.setStoregroupId(selectedStoreGroup.getId());
            storeDTO.setWorkerId(workerDataManager.getWorker().getId());
            storeDataManager.addNewStore(storeDTO);
        }
    }

    private boolean isValid() {

        View focusView = null;

        String name = nameET.getText().toString();
        String address = addressET.getText().toString();
        if (selectedStoreGroup == null) {
            storeGroupTV.setError("Wybierz grupę sklepu");
            focusView = storeGroupTV;
            focusView.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            nameET.setError("Wypełnij pole");
            focusView = nameET;
            focusView.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            addressET.setError("Wypełnij pole");
            focusView = addressET;
            focusView.requestFocus();
            return false;
        }
        return true;
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
        if (storeDTOList != null) {
            storeDTOList.add(storeDTO);
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        getDialog().dismiss();

        Bundle args = new Bundle();
        args.putSerializable(STORE_DTO, storeDTO);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_mystores_detail, args);
    }

    @Subscribe
    public void onStoreSentFailed(StoreSentFailed sentFailed) {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "onStoreSentFailed: ");
        Toast.makeText(getActivity(), sentFailed.getText(), Toast.LENGTH_LONG).show();
    }


}
