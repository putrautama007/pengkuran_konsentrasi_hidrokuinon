package com.putra.pengkurankonsentrasihidrokuinon.model;

public class PerhitunganKonsentrasi {
    private double rgb;
    private static final String STATUS_GOOD = "Layak Pakai";
    private static final String STATUS_BAD = "Tidak Layak Pakai";
    public PerhitunganKonsentrasi(double rgb) {
        this.rgb = rgb;
    }

    public double concentrationCalculation(){
        double xAxis = 16.971;
        double yAxis = 138.76;
        return (yAxis -rgb)/ xAxis;
    }

    public double concentrationPercentage(){
        double p = 100/0.01*concentrationCalculation();
        double q = p/1000;
        return q/100*100;
    }

    public String checkStatus(){
        if (concentrationCalculation() < 0.02){
            return STATUS_GOOD;
        }else {
            return STATUS_BAD;
        }
    }
}
