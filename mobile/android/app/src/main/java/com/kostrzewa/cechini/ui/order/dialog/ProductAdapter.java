package com.kostrzewa.cechini.ui.order.dialog;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.ProductDTO;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment.OnListFragmentInteractionListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements SpinnerAdapter {
    private static final String TAG = "ProductAdapter";
    private final List<ProductDTO> mValues;

    public List<ProductDTO> getData() {
        return mValues;
    }


    public ProductAdapter(List<ProductDTO> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return generateView(position, convertView, parent);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    private View generateView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product_item, parent, false);
        TextView nameTV = convertView.findViewById(R.id.name);
//        TextView val01TV = convertView.findViewById(R.id.product_item_val01);
//        TextView val02TV = convertView.findViewById(R.id.product_item_val02);
//        TextView val03TV = convertView.findViewById(R.id.product_item_val03);
        ProductDTO p = mValues.get(position);
        nameTV.setText(p.getName() + "\n\t" + p.getCapacity() + " L");
//        val01TV.setText("" + p.getPackCountPalette());
//        val02TV.setText("" + p.getArtCountPalette());
//        val03TV.setText("" + p.getLayerCountPalette());
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return generateView(position, convertView, parent);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

    }
}
