package com.example.nutriwise.model;

import com.google.gson.annotations.SerializedName;

public class NutritionData {
    @SerializedName("ENERC_KCAL")
    private Nutrient energy;

    @SerializedName("FAT")
    private Nutrient fat;

    @SerializedName("CHOCDF")
    private Nutrient carbs;

    @SerializedName("PROCNT")
    private Nutrient protein;

    public NutritionData(Nutrient energy, Nutrient fat, Nutrient carbs, Nutrient protein) {
        this.energy = energy;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    public Nutrient getEnergy() {
        return energy;
    }

    public Nutrient getFat() {
        return fat;
    }

    public Nutrient getCarbs() {
        return carbs;
    }

    public Nutrient getProtein() {
        return protein;
    }

    public void setEnergy(Nutrient energy) {
        this.energy = energy;
    }

    public void setFat(Nutrient fat) {
        this.fat = fat;
    }

    public void setCarbs(Nutrient carbs) {
        this.carbs = carbs;
    }

    public void setProtein(Nutrient protein) {
        this.protein = protein;
    }
}
