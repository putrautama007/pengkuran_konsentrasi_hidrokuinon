package com.putra.pengkurankonsentrasihidrokuinon.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "scanData")
public class ScanModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;

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

    public ScanModel(long id, String sampleName, int red, int green, int blue, double concentration, double concentrationPercentage, String status) {
        this.id = id;
        this.sampleName = sampleName;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.concentration = concentration;
        this.concentrationPercentage = concentrationPercentage;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getSampleName() {
        return sampleName;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public double getConcentration() {
        return concentration;
    }

    public double getConcentrationPercentage() {
        return concentrationPercentage;
    }

    public String getStatus() {
        return status;
    }
}
