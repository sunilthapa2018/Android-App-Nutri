package com.example.nutri.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrients {

    @SerializedName("ENERC_KCAL")
    @Expose
    private String energyKcal;

    @SerializedName("PROCNT")
    @Expose
    private String proteinCount;

    @SerializedName("FAT")
    @Expose
    private String fat;

    @SerializedName("CHOCDF")
    @Expose
    private String carbs;

    @SerializedName("FIBTG")
    @Expose
    private String fiber;

    public String getEnergyKcal() {
        return energyKcal;
    }

    public void setEnergyKcal(String energyKcal) {
        this.energyKcal = energyKcal;
    }

    public String getProteinCount() {
        return proteinCount;
    }

    public void setProteinCount(String proteinCount) {
        this.proteinCount = proteinCount;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFiber() {
        return fiber;
    }

    public void setFiber(String fiber) {
        this.fiber = fiber;
    }

    @Override
    public String toString() {
        return "Nutrients{" +
                "energyKcal='" + energyKcal + '\'' +
                ", proteinCount='" + proteinCount + '\'' +
                ", fat='" + fat + '\'' +
                ", carbs='" + carbs + '\'' +
                ", fiber='" + fiber + '\'' +
                '}';
    }
}
