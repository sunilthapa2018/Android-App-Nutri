package com.example.nutri.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parsed {
    @SerializedName("food")
    @Expose
    private Food food;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "Parsed{" +
                "food=" + food +
                '}';
    }
}
