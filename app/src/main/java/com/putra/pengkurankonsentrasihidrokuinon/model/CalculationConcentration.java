package com.putra.pengkurankonsentrasihidrokuinon.model;

public class CalculationConcentration {
    private double rgb;
    private static final String STATUS_GOOD = "Layak Pakai";
    private static final String STATUS_BAD = "Tidak Layak Pakai";
    public CalculationConcentration(double rgb) {
        this.rgb = rgb;
    }

    private double absorbency(){
        return -Math.log10((rgb/255));
    }

    public double concentrationCalculation(){
        double a = 0.07;
        double b = 0.1538;
        return (absorbency() - b)/ a;
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
