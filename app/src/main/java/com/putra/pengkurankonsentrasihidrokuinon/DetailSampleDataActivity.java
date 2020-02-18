package com.putra.pengkurankonsentrasihidrokuinon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;
import com.putra.pengkurankonsentrasihidrokuinon.room.AppExecutors;
import com.putra.pengkurankonsentrasihidrokuinon.room.ScanDataDatabase;

import java.text.DecimalFormat;
import java.util.Objects;

public class DetailSampleDataActivity extends AppCompatActivity {

    LinearLayout llColor;
    TextView tvRGB, tvConcentration, tvHqLevel, tvStatus, tvSampleName;
    private ScanDataDatabase scanDataDatabase;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");


    // fungsi yang dijalankan saar pertama kali membuka halaman
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sample_data);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvSampleName = findViewById(R.id.tvSampleNameDetail);
        llColor = findViewById(R.id.llColorDetail);
        tvRGB = findViewById(R.id.txtRGBDetail);
        tvConcentration = findViewById(R.id.tvConcentrationDetail);
        tvHqLevel = findViewById(R.id.tvHQLevelDetail);
        tvStatus = findViewById(R.id.tvStatusDetail);

        scanDataDatabase = ScanDataDatabase.getInstance(DetailSampleDataActivity.this);
        long id = getIntent().getLongExtra("sampleId",0);
        retrieveScanDataById(id);
    }

    // fungsi yang dijalankan saat menekan tombol back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // fungsi yang dijalankan untuk mendapatkan data yang sudah di simpan pada RoomDB
    private void retrieveScanDataById(final long id) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final ScanModel scanModel = scanDataDatabase.calculationDao().getScanDataById(id);
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        tvSampleName.setText(scanModel.getSampleName());
                        llColor.setBackgroundColor(Color.rgb(scanModel.getRed(), scanModel.getGreen(), scanModel.getBlue()));
                        tvRGB.setText(getResources().getString(R.string.RGB) + "(" + scanModel.getRed() +","+ scanModel.getGreen() +","+ scanModel.getBlue() + ")");
                        tvConcentration.setText(getResources().getString(R.string.konsentrasi) + decimalFormat.format(scanModel.getConcentration()) + getResources().getString(R.string.satuan_konsentrasi));
                        tvHqLevel.setText(getResources().getString(R.string.tingkat_hq) + decimalFormat.format(scanModel.getConcentrationPercentage()) + getResources().getString(R.string.percent));
                        tvStatus.setText(getResources().getString(R.string.status) + scanModel.getStatus());
                    }
                });
            }
        });
    }
}
