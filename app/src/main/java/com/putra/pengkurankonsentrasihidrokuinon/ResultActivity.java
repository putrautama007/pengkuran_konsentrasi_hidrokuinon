package com.putra.pengkurankonsentrasihidrokuinon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {

    EditText etSampleName;
    LinearLayout llColor;
    TextView tvRBGColor;
    TextView tvHSLColor;
    Button btnSaveSample;

    private Palette.Swatch vibrantSwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etSampleName = findViewById(R.id.etSampleName);
        llColor = findViewById(R.id.llColor);
        tvHSLColor = findViewById(R.id.textHsl);
        tvRBGColor = findViewById(R.id.txtRGB);
        btnSaveSample = findViewById(R.id.btnSave);

        Bitmap bitmap =  getIntent().getParcelableExtra("result");

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                vibrantSwatch = palette.getDominantSwatch();
                int vibrantRGB = vibrantSwatch.getRgb();
                float[] vibrantHSL = vibrantSwatch.getHsl();

                int color = (int)Long.parseLong(String.valueOf(vibrantRGB), 16);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color) & 0xFF;


                tvRBGColor.setText("red : "+r + " Green: " + g+ " Blue: " + b);
                tvHSLColor.setText("Hue : "+vibrantHSL[0] + " saturation: " + vibrantHSL[1]+ " lightness: " + vibrantHSL[2]);
                llColor.setBackgroundColor(vibrantRGB);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
