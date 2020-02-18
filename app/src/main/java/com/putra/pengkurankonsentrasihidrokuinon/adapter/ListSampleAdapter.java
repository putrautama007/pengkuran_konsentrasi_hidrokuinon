package com.putra.pengkurankonsentrasihidrokuinon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.putra.pengkurankonsentrasihidrokuinon.DetailSampleDataActivity;
import com.putra.pengkurankonsentrasihidrokuinon.R;
import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;

import java.util.List;

//Class digunakan untuk membuat list secara dinamik
public class ListSampleAdapter extends RecyclerView.Adapter<ListSampleAdapter.ViewHolder> {
    private Context context;
    private List<ScanModel> scanModelList;

    public ListSampleAdapter(Context context) {
        this.context = context;
    }


    //fungsi untuk menampilkan tampilan untuk list
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_sample,parent,false));
    }


    //fungsi untuk memasukan data kedalam tampilan pada list
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ScanModel scanModel = scanModelList.get(position);
        holder.llColorSample.setBackgroundColor(Color.rgb(scanModel.getRed(), scanModel.getGreen(),scanModel.getBlue()));
        holder.tvSampleName.setText(scanModel.getSampleName());
        holder.tvSampleColorRGB.setText("RGB : " + "(" + scanModel.getRed() +","+ scanModel.getGreen() +","+ scanModel.getBlue() + ")");
        holder.tvSampleStatus.setText(context.getResources().getString(R.string.status)+scanModel.getStatus());
        holder.cvScanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailSampleDataActivity.class);
                intent.putExtra("sampleId",scanModel.getId());
                context.startActivity(intent);
            }
        });
    }

    //fungsi untuk mendapatkan jumlah banyak data yang akan ditampilkan pada list
    @Override
    public int getItemCount() {
        if (scanModelList == null){
            return 0;
        }
        return scanModelList.size();
    }

    //fungsi untuk memperbarui data pada list
    public void setScanData(List<ScanModel> scanModels) {
        scanModelList = scanModels;
        notifyDataSetChanged();
    }


    //class untuk menyambungkan class logic dengan dengan desain (XML)
    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llColorSample;
        TextView tvSampleName, tvSampleColorRGB, tvSampleStatus;
        CardView cvScanData;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            llColorSample = itemView.findViewById(R.id.llColorSample);
            tvSampleName = itemView.findViewById(R.id.tvNameSample);
            tvSampleColorRGB = itemView.findViewById(R.id.tvRGB);
            tvSampleStatus = itemView.findViewById(R.id.tvStatus);
            cvScanData = itemView.findViewById(R.id.cvScanData);
        }
    }
}
