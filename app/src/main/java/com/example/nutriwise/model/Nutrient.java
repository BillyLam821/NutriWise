package com.example.nutriwise.model;

import com.google.gson.annotations.SerializedName;

public class Nutrient {
    @SerializedName("label")
    private String label;

    @SerializedName("quantity")
    private double quantity;

    @SerializedName("unit")
    private String unit;

    public String getLabel() {
        return label;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
