package com.kostrzewa.cechini.ui.report.myreports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.model.ReportDTO;

import java.util.List;

public class MyReportsRecyclerViewAdapter extends RecyclerView.Adapter<MyReportsRecyclerViewAdapter.ViewHolder> {
    private final List<ReportDTO> mValues;
    private ReportDataManager reportDataManager;
    Context context;

    public List<ReportDTO> getData() {
        return mValues;
    }

    public MyReportsRecyclerViewAdapter(Context context, ReportDataManager reportDataManager, List<ReportDTO> items) {
        this.reportDataManager = reportDataManager;
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_myreports_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("" + mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getDesc());//todo dataaa
        if (holder.mItem.getManagerNote() != null) {
            holder.managerNoteTV.setVisibility(View.VISIBLE);
            holder.managerNoteTV.setText(holder.mItem.getManagerNote());
        } else {
            holder.managerNoteTV.setVisibility(View.GONE);
        }
        holder.mContentView.setText(mValues.get(position).getDesc());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView managerNoteTV;

        public ReportDTO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            managerNoteTV = (TextView) view.findViewById(R.id.item_managerNote);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
