package com.putra.pengkurankonsentrasihidrokuinon.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ScanModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "sampleName")
    private String sampleName;

    @ColumnInfo(name = "red")
    private int red;

    @ColumnInfo(name = "green")
    private int green;

    @ColumnInfo(name = "blue")
    private int blue;

    @ColumnInfo(name = "concentration")
    private double concentration;

    @ColumnInfo(name = "concentrationPercentage")
    private double concentrationPercentage;

    @ColumnInfo(name = "status")
    private String status;
}
