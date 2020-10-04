package com.example.nutri.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrients {

    @SerializedName("ENERC_KCAL")
    @Expose
    private double energyKcal;

    @SerializedName("PROCNT")
    @Expose
    private double proteinCount;

    @SerializedName("FAT")
    @Expose
    private double fat;

    @SerializedName("CHOCDF")
    @Expose
    private double carbs;

    @SerializedName("FIBTG")
    @Expose
    private double fiber;

    public double getEnergyKcal() {
        return energyKcal;
    }

    public void setEnergyKcal(float energyKcal) {
        this.energyKcal = energyKcal;
    }

    public double getProteinCount() {
        return proteinCount;
    }

    public void setProteinCount(float proteinCount) {
        this.proteinCount = proteinCount;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    @Override
    public String toString() {
        return "Nutrients{" +
                "energyKcal=" + energyKcal +
                ", proteinCount=" + proteinCount +
                ", fat=" + fat +
                ", carbs=" + carbs +
                ", fiber=" + fiber +
                '}';
    }
}
