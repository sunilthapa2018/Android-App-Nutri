package com.example.nutri.requests.responses;

import com.example.nutri.models.Parsed;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class FoodResponses {

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("parsed")
    @Expose
    private ArrayList<Parsed> parsed;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Parsed> getParsed() {
        return parsed;
    }

    public void setParsed(ArrayList<Parsed> parsed) {
        this.parsed = parsed;
    }

    @Override
    public String toString() {
        return "FoodResponses{" +
                "text='" + text + '\'' +
                ", parsed=" + parsed +
                '}';
    }
}
