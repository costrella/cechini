package com.kostrzewa.cechini.ui.report.myreports;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.data.ReportDataManager;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;
import com.kostrzewa.cechini.util.DateUtils;

import java.util.List;

import static com.kostrzewa.cechini.util.Constants.REPORT_DTO;

public class MyReportsRecyclerViewAdapter extends RecyclerView.Adapter<MyReportsRecyclerViewAdapter.ViewHolder> {
    private final List<ReportDTOWithPhotos> mValues;
    private ReportDataManager reportDataManager;
    private Activity activity;
    Context context;

    public List<ReportDTOWithPhotos> getData() {
        return mValues;
    }

    public MyReportsRecyclerViewAdapter(Context context, Activity activity, ReportDataManager reportDataManager, List<ReportDTOWithPhotos> items) {
        this.reportDataManager = reportDataManager;
        mValues = items;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_myreports_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ReportDTO reportDTO = mValues.get(position);
        holder.mItem = reportDTO;
        holder.orderExistTV.setVisibility(reportDTO.getOrderId() == null ? View.GONE : View.VISIBLE);
        holder.date.setText(DateUtils.parse(reportDTO.getReportDate()));
        String recordText = reportDTO.getStoreName();
        if(reportDTO.getDesc() != null){
            recordText +=" / \"" + reportDTO.getDesc() + "\"";
        }
        holder.mContentView.setText(recordText);
        if (reportDTO.getReadByWorker() != null && !reportDTO.getReadByWorker().booleanValue()) {
            holder.reportUnreadTV.setVisibility(View.VISIBLE);
        } else {
            holder.reportUnreadTV.setVisibility(View.GONE);
        }

        holder.managerNoteTV.setVisibility(reportDTO.isNotesExist() ? View.VISIBLE : View.GONE);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable(REPORT_DTO, holder.mItem);
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_report_create, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView date;
        public final TextView mContentView;
        public final TextView managerNoteTV;
        public final TextView orderExistTV;
        public final TextView reportUnreadTV;

        public ReportDTO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            date = (TextView) view.findViewById(R.id.item_date);
            mContentView = (TextView) view.findViewById(R.id.content);
            managerNoteTV = (TextView) view.findViewById(R.id.item_managerNote);
            orderExistTV = (TextView) view.findViewById(R.id.item_orderExist);
            reportUnreadTV = (TextView) view.findViewById(R.id.item_unread);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
