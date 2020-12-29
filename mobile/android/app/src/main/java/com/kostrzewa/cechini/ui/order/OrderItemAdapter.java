package com.kostrzewa.cechini.ui.order;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.OrderItemDTO;
import com.kostrzewa.cechini.ui.mystores.MyStoresFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link OrderItemDTO} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> implements android.widget.Checkable {
    private static final String TAG = "MyStoresRecyclerViewAda";
    private final List<OrderItemDTO> mValues;
    private final boolean isReadOnly;

    public List<OrderItemDTO> getData() {
        return mValues;
    }


    public OrderItemAdapter(List<OrderItemDTO> items, boolean isReadOnly) {
        mValues = items;
        this.isReadOnly = isReadOnly;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        OrderItemDTO o = mValues.get(position);
        holder.mItem = o;
        holder.nameTV.setText(o.getProductName() + " " + o.getProductCapacity() + "L");
        int lp = position + 1;
        holder.mIdView.setText("" + lp);
        holder.mPackCount.setText("" + o.getPackCount());
//        holder.val1.setText("" + o.getProductArtCountPalette());//todo upewnic sie
//        holder.val2.setText("" + o.getProductLayerCountPalette());//todo upewnic sie
//        holder.val3.setText("" + o.getProductPackCountPalette());//todo upewnic sie

//        holder.mContentView.setText(mValues.get(position).getProductName());

        holder.removeBtn.setVisibility(isReadOnly ? View.GONE : View.VISIBLE);

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData().remove(position);
                notifyDataSetChanged();
            }
        });
//        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.d(TAG, "onLongClick: " + v.getId());
//                getData().remove(position);
//                notifyDataSetChanged();
////                notifyItemRemoved(position);
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mPackCount;
        public final TextView nameTV;
        public final Button removeBtn;
//        public final TextView val1;
//        public final TextView val2;
//        public final TextView val3;

        public OrderItemDTO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mPackCount = (TextView) view.findViewById(R.id.orderItem_packCount);
            removeBtn = (Button) view.findViewById(R.id.orderItem_remove);
//            val1 = (TextView) view.findViewById(R.id.product_item_val01);
//            val2 = (TextView) view.findViewById(R.id.product_item_val02);
//            val3 = (TextView) view.findViewById(R.id.product_item_val03);

            nameTV = view.findViewById(R.id.name);
        }
    }
}
