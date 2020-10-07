package com.example.nutri.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutri.AddJournal;
import com.example.nutri.R;
import com.example.nutri.SearchResults;
import com.example.nutri.models.Parsed;
import java.text.NumberFormat;
import java.util.ArrayList;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder> {
    private ArrayList<Parsed> parsedList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtProtein;
        private TextView txtFat;
        private TextView txtCarb;
        private TextView txtCalories;
        public FoodViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtProtein = itemView.findViewById(R.id.txtProtein);
            txtFat = itemView.findViewById(R.id.txtFat);
            txtCarb = itemView.findViewById(R.id.txtCarb);
            txtCalories = itemView.findViewById(R.id.txtCalories);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public FoodRecyclerAdapter(ArrayList<Parsed> parsedList){
        this.parsedList = parsedList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_food, parent,false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(v, mListener);
        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        String unit = " g.";
        Parsed parsed = parsedList.get(position);
        holder.txtTitle.setText(parsed.getFood().getLabel() + " (100 Gm)");
        holder.txtProtein.setText(precise(parsed.getFood().getNutrients().getProteinCount()) + unit);
        holder.txtFat.setText(precise(parsed.getFood().getNutrients().getFat()) + unit);
        holder.txtCarb.setText(precise(parsed.getFood().getNutrients().getCarbs()) + unit);
        holder.txtCalories.setText(calPrecise(parsed.getFood().getNutrients().getEnergyKcal()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String foodName = parsedList.get(position).getFood().getLabel();
                String protein = precise(parsedList.get(position).getFood().getNutrients().getProteinCount());
                String fats = precise(parsedList.get(position).getFood().getNutrients().getFat());
                String carb = precise(parsedList.get(position).getFood().getNutrients().getCarbs());
                String calories = calPrecise(parsedList.get(position).getFood().getNutrients().getEnergyKcal());

                Intent intent = new Intent(v.getContext(), AddJournal.class);
                intent.putExtra("foodName", foodName);
                intent.putExtra("protein", protein);
                intent.putExtra("fats", fats);
                intent.putExtra("carb", carb);
                intent.putExtra("calories", calories);
                intent.putExtra("type", "add");
                v.getContext().startActivity(intent);

                ((Activity)v.getContext()).finish();

            }
        });
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
        if(parsedList!=null) {
            return parsedList.size();
        }else{
            return 0;
        }
    }
}
