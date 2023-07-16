package com.example.nutriwise.model;

public class LogEntry {
    private String ingredient;
    private int quantity;
    private String unit;
    private double calories;
    private double carbs;
    private double proteins;
    private double fats;
    private int year;
    private int month;
    private int day;

    public LogEntry(String ingredient, int quantity, String unit, double calories, double carbs, double proteins, double fats) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.calories = calories;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
    }

    public LogEntry(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public LogEntry(String ingredient, int quantity, String unit, double calories, double carbs, double proteins, double fats, int year, int month, int day) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.calories = calories;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
