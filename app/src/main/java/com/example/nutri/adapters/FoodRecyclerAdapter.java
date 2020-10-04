package com.example.nutri.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutri.FoodItems;
import com.example.nutri.R;
import com.example.nutri.models.Food;
import com.example.nutri.models.Parsed;

import java.math.MathContext;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder> {
    private ArrayList<Parsed> parsedList;


    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtProtein;
        private TextView txtFat;
        private TextView txtCarb;
        private TextView txtCalories;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtProtein = itemView.findViewById(R.id.txtProtein);
            txtFat = itemView.findViewById(R.id.txtFat);
            txtCarb = itemView.findViewById(R.id.txtCarb);
            txtCalories = itemView.findViewById(R.id.txtCalories);
        }
    }

    public FoodRecyclerAdapter(ArrayList<Parsed> parsedList){
        this.parsedList = parsedList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_food, parent,false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(v);
        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Parsed parsed = parsedList.get(position);
        holder.txtTitle.setText(parsed.getFood().getLabel() + " (100 Gm)");
        holder.txtProtein.setText(precise(parsed.getFood().getNutrients().getProteinCount()));
        holder.txtFat.setText(precise(parsed.getFood().getNutrients().getFat()));
        holder.txtCarb.setText(precise(parsed.getFood().getNutrients().getCarbs()));
        holder.txtCalories.setText(calPrecise(parsed.getFood().getNutrients().getEnergyKcal()));
    }

    public String precise(double x) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        return nf.format(x);

    }

    public String calPrecise(double x) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        return nf.format(x);

    }

    @Override
    public int getItemCount() {
        return parsedList.size();
    }
}
