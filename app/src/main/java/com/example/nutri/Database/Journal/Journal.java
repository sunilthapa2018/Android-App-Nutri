package com.example.nutri.Database.Journal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "journal_table")
public class Journal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String foodName;

    private int quantity;

    private String foodType;

    private String date;

    private float protein;

    private float fats;

    private float carb;

    private int calories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getDate() {
        return date;
    }

    public float getProtein() {
        return protein;
    }

    public float getFats() {
        return fats;
    }

    public float getCarb() {
        return carb;
    }

    public int getCalories() {
        return calories;
    }

    public Journal(String foodName, int quantity, String foodType, String date, float protein, float fats, float carb, int calories) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.foodType = foodType;
        this.date = date;
        this.protein = protein;
        this.fats = fats;
        this.carb = carb;
        this.calories = calories;
    }
}
