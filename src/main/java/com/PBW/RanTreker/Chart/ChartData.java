package com.PBW.RanTreker.Chart;

public class ChartData {
    private String label; // Label untuk kategori (minggu/bulan/tahun)
    private double value; // Nilai total distance

    // Konstruktor
    public ChartData(String label, double value) {
        this.label = label;
        this.value = value;
    }

    // Getter
    public String getLabel() {
        return label;
    }

    public double getValue() {
        return value;
    }

    // Setter (opsional, jika diperlukan)
    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
