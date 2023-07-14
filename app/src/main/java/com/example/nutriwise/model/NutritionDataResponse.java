package com.example.nutriwise.model;

import com.google.gson.annotations.SerializedName;

public class NutritionDataResponse {
    @SerializedName("calories")
    private double calories;

    @SerializedName("totalWeight")
    private double totalWeight;

    @SerializedName("totalNutrients")
    private NutritionData nutritionData;
    private String ingredient;

    public NutritionDataResponse(double calories, double totalWeight, NutritionData nutritionData) {
        this.calories = calories;
        this.totalWeight = totalWeight;
        this.nutritionData = nutritionData;
    }

    public double getCalories() {
        return calories;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public NutritionData getNutritionData() {
        return nutritionData;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setNutritionData(NutritionData nutritionData) {
        this.nutritionData = nutritionData;
    }
}
