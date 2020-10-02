package com.example.nutri.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food{
    @SerializedName("foodId")
    @Expose
    private String foodId;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("nutrients")
    @Expose
    private Nutrients nutrients;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("categoryLabel")
    @Expose
    private String categoryLabel;

    @SerializedName("quantity")
    @Expose
    private float quantity;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId='" + foodId + '\'' +
                ", label='" + label + '\'' +
                ", nutrients=" + nutrients +
                ", category='" + category + '\'' +
                ", categoryLabel='" + categoryLabel + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
