package com.putra.pengkurankonsentrasihidrokuinon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.putra.pengkurankonsentrasihidrokuinon.model.BitMapConverter;
import com.putra.pengkurankonsentrasihidrokuinon.model.CalculationConcentration;
import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;
import com.putra.pengkurankonsentrasihidrokuinon.room.AppExecutors;
import com.putra.pengkurankonsentrasihidrokuinon.room.ScanDataDatabase;

import java.text.DecimalFormat;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    EditText etSampleName;
    LinearLayout llColor;
    TextView tvRed, tvGreen, tvBlue, tvConcentration, tvHqLevel, tvStatus;
    Button btnSaveSample;
    private ScanDataDatabase scanDataDatabase;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

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

        scanDataDatabase = ScanDataDatabase.getInstance(ResultActivity.this);

        Bitmap bitmap = getIntent().getParcelableExtra("result");

        assert bitmap != null;
        BitMapConverter bitMapConverter = new BitMapConverter(bitmap);
        final int[] rgb = bitMapConverter.getAverageColorRGB();

        tvRed.setText(getResources().getString(R.string.red) + rgb[0]);
        tvGreen.setText(getResources().getString(R.string.green) + rgb[1]);
        tvBlue.setText(getResources().getString(R.string.blue) + rgb[2]);

        llColor.setBackgroundColor(Color.rgb(rgb[0], rgb[1], rgb[2]));

        final CalculationConcentration calculationConcentration = new CalculationConcentration(rgb[2]);
        tvConcentration.setText(getResources().getString(R.string.konsentrasi) + decimalFormat.format(calculationConcentration.concentrationCalculation()) + getResources().getString(R.string.satuan_konsentrasi));
        tvHqLevel.setText(getResources().getString(R.string.tingkat_hq) + decimalFormat.format(calculationConcentration.concentrationPercentage()) + getResources().getString(R.string.percent));
        tvStatus.setText(getResources().getString(R.string.status) + calculationConcentration.checkStatus());

        btnSaveSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (!etSampleName.getText().toString().equals("")){
                   final ScanModel scanModel = new ScanModel(
                           System.currentTimeMillis(),
                           etSampleName.getText().toString(),
                           rgb[0],
                           rgb[1],
                           rgb[2],
                           calculationConcentration.concentrationCalculation(),
                           calculationConcentration.concentrationPercentage(),
                           calculationConcentration.checkStatus());
                   AppExecutors.getInstance().diskIO().execute(new Runnable() {
                       @Override
                       public void run() {
                           scanDataDatabase.calculationDao().insertScanData(scanModel);
                           Intent intentToMain = new Intent(ResultActivity.this,MainActivity.class);
                           intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           startActivity(intentToMain);
                       }
                   });
               }else {
                   Toast.makeText(ResultActivity.this, "Isi nama sample", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
