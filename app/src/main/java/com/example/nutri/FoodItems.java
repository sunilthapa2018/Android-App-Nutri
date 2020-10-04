package com.example.nutri;

import com.example.nutri.models.Parsed;

import java.util.ArrayList;
import java.util.Objects;

public class FoodItems {
    private ArrayList<Parsed> parsedList;

    public FoodItems(ArrayList<Parsed> parsedList) {
        this.parsedList = parsedList;
    }

    public ArrayList<Parsed> getParsedList() {
        return parsedList;
    }

    public void setParsedList(ArrayList<Parsed> parsedList) {
        this.parsedList = parsedList;
    }

    public int getItemCount() {
        return parsedList.size();
    }
}
