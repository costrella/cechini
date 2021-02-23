package com.kostrzewa.cechini.ui.mystores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.StoreDataManager;
import com.kostrzewa.cechini.model.StoreDTO;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link StoreDTO} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStoresRecyclerViewAdapter extends RecyclerView.Adapter<MyStoresRecyclerViewAdapter.ViewHolder>
        implements Filterable {
    private static final String TAG = "MyStoresRecyclerViewAda";
    private final List<StoreDTO> mValues;
    private List<StoreDTO> mValuesFiltered;
    private final OnListFragmentInteractionListener mListener;
    private StoreDataManager storeDataManager;
    private StoreDTO storeToSelect = null;
    Context context;

    public List<StoreDTO> getData() {
        return mValues;
    }

    public MyStoresRecyclerViewAdapter(Context context, StoreDataManager storeDataManager, List<StoreDTO> items, OnListFragmentInteractionListener listener) {
        this.storeDataManager = storeDataManager;
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    public void selectAfterAdded(StoreDTO storeDTO) {
        this.storeToSelect = storeDTO;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mystores_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (storeToSelect != null && holder.mItem.getId().equals(storeToSelect.getId())) {
            holder.mContentView.setTextColor(context.getResources().getColor(R.color.red));
        }
        holder.mIdView.setText("" + mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getName() + " " + mValues.get(position).getAddress());
        if(holder.mItem.isMonthVisited()){
            holder.mContentView.setTextColor(context.getResources().getColor(R.color.green));
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d(TAG, "performFiltering: ");
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mValuesFiltered = storeDataManager.getMyStores();
                } else {
                    List<StoreDTO> filteredList = new ArrayList<>();
                    for (StoreDTO row : storeDataManager.getMyStores()) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

//                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getAddress().toLowerCase().contains(charString.toLowerCase())) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getAddress().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mValuesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mValuesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                Log.d(TAG, "publishResults: ");
                mValuesFiltered = (ArrayList<StoreDTO>) filterResults.values;
                getData().clear();
                getData().addAll(mValuesFiltered);
                notifyDataSetChanged();

            }
        };

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public StoreDTO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
