package com.putra.pengkurankonsentrasihidrokuinon;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.putra.pengkurankonsentrasihidrokuinon.adapter.ListSampleAdapter;
import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;
import com.putra.pengkurankonsentrasihidrokuinon.room.AppExecutors;
import com.putra.pengkurankonsentrasihidrokuinon.room.ScanDataDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvListSample;
    private ListSampleAdapter listSampleAdapter;
    ScanDataDatabase scanDataDatabase;
    ConstraintLayout clNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clNoData = findViewById(R.id.clNoData);

        rvListSample = findViewById(R.id.rvListSample);
        rvListSample.setLayoutManager(new LinearLayoutManager(this));
        listSampleAdapter = new ListSampleAdapter(this);
        scanDataDatabase = ScanDataDatabase.getInstance(getApplicationContext());
        rvListSample.setAdapter(listSampleAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectImageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveScanData();
    }

    private void retrieveScanData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<ScanModel> scanModels = scanDataDatabase.calculationDao().getScanData();
                if (scanModels.size() == 0){
                    clNoData.setVisibility(View.VISIBLE);
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listSampleAdapter.setScanData(scanModels);
                        }
                    });
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
