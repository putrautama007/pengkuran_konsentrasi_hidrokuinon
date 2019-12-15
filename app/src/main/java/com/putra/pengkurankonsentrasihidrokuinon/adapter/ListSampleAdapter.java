package com.putra.pengkurankonsentrasihidrokuinon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.putra.pengkurankonsentrasihidrokuinon.R;
import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;

import java.util.List;

public class ListSampleAdapter extends RecyclerView.Adapter<ListSampleAdapter.ViewHolder> {
    private Context context;
    private List<ScanModel> scanModelList;

    public ListSampleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_sample,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScanModel scanModel = scanModelList.get(position);
        holder.llColorSample.setBackgroundColor(Color.rgb(scanModel.getRed(), scanModel.getGreen(),scanModel.getBlue()));
        holder.tvSampleName.setText(scanModel.getSampleName());
        holder.tvSampleColorRGB.setText("RGB : " + "(" + scanModel.getRed() +","+ scanModel.getGreen() +","+ scanModel.getBlue() + ")");
        holder.tvSampleStatus.setText("Status : "+scanModel.getStatus());
    }

    @Override
    public int getItemCount() {
        if (scanModelList == null){
            return 0;
        }
        return scanModelList.size();
    }

    public void setScanData(List<ScanModel> scanModels) {
        scanModelList = scanModels;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llColorSample;
        TextView tvSampleName, tvSampleColorRGB, tvSampleStatus;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            llColorSample = itemView.findViewById(R.id.llColorSample);
            tvSampleName = itemView.findViewById(R.id.tvNameSample);
            tvSampleColorRGB = itemView.findViewById(R.id.tvRGB);
            tvSampleStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
