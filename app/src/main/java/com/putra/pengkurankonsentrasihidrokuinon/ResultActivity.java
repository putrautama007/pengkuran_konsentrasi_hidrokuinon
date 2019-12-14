package com.putra.pengkurankonsentrasihidrokuinon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putra.pengkurankonsentrasihidrokuinon.model.BitMapConverter;
import com.putra.pengkurankonsentrasihidrokuinon.model.CalculationConcentration;
import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;
import com.putra.pengkurankonsentrasihidrokuinon.room.ScanDataDatabase;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    EditText etSampleName;
    LinearLayout llColor;
    TextView tvRed, tvGreen, tvBlue, tvConcentration, tvHqLevel, tvStatus;
    Button btnSaveSample;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etSampleName = findViewById(R.id.etSampleName);
        llColor = findViewById(R.id.llColor);
        tvRed = findViewById(R.id.txtRed);
        tvGreen = findViewById(R.id.txtGreen);
        tvBlue = findViewById(R.id.txtBlue);
        tvConcentration = findViewById(R.id.tvConcentration);
        tvHqLevel = findViewById(R.id.tvHQLevel);
        tvStatus = findViewById(R.id.tvStatus);
        btnSaveSample = findViewById(R.id.btnSave);

        Bitmap bitmap = getIntent().getParcelableExtra("result");

        assert bitmap != null;
        BitMapConverter bitMapConverter = new BitMapConverter(bitmap);
        final int[] rgb = bitMapConverter.getAverageColorRGB();

        tvRed.setText(getResources().getString(R.string.red) + rgb[0]);
        tvGreen.setText(getResources().getString(R.string.green) + rgb[1]);
        tvBlue.setText(getResources().getString(R.string.blue) + rgb[2]);

        llColor.setBackgroundColor(Color.rgb(rgb[0], rgb[1], rgb[2]));

        final CalculationConcentration calculationConcentration = new CalculationConcentration(rgb[2]);
        tvConcentration.setText(getResources().getString(R.string.konsentrasi) + calculationConcentration.concentrationCalculation() + getResources().getString(R.string.satuan_konsentrasi));
        tvHqLevel.setText(getResources().getString(R.string.tingkat_hq) + calculationConcentration.concentrationPercentage());
        tvStatus.setText(getResources().getString(R.string.status) + calculationConcentration.checkStatus());

        btnSaveSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanDataDatabase scanDataDatabase = ScanDataDatabase.getInstance(ResultActivity.this);
                ScanModel scanModel = new ScanModel(
                        System.currentTimeMillis(),
                        etSampleName.getText().toString(),
                        rgb[0],
                        rgb[1],
                        rgb[2],
                        calculationConcentration.concentrationCalculation(),
                        calculationConcentration.concentrationPercentage(),
                        calculationConcentration.checkStatus());
                scanDataDatabase.calculationDao().insertScanData(scanModel);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
