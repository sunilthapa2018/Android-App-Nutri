package com.example.nutri.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutri.Database.Journal.Journal;
import com.example.nutri.ui.journal.AddJournal;

import com.example.nutri.R;
import java.text.NumberFormat;
import java.util.List;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.JournalViewHolder> {
    private List<Journal> journals;

    private OnItemClickListener mListener;

    private int kTotalCal;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class JournalViewHolder extends RecyclerView.ViewHolder{
        private TextView txtFoodType;
        private TextView txtFoodQuantity;
        private TextView txtFoodName;
        private TextView txtCalories;
        private TextView txtProtein;        
        private TextView txtFat;
        private TextView txtCarb;
        public JournalViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtFoodType = itemView.findViewById(R.id.txtFoodType);
            txtFoodQuantity = itemView.findViewById(R.id.txtFoodQuantity);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtCalories = itemView.findViewById(R.id.txtCalories);
            txtProtein = itemView.findViewById(R.id.txtProtein);
            txtFat = itemView.findViewById(R.id.txtFat);
            txtCarb = itemView.findViewById(R.id.txtCarb);

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

    public JournalRecyclerAdapter(List<Journal> journals){
        this.journals = journals;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_journal_list, parent,false);
        JournalViewHolder journalViewHolder = new JournalViewHolder(v, mListener);
        return journalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        Journal currentJournal = journals.get(position);
        String unit = "g";
        //Journal data = journals.get(position);
        kTotalCal += currentJournal.getCalories();
        holder.txtFoodType.setText(currentJournal.getFoodType());
        holder.txtFoodQuantity.setText(String.valueOf(currentJournal.getQuantity()) + unit);
        holder.txtFoodName.setText(currentJournal.getFoodName());
        holder.txtCalories.setText(String.valueOf(currentJournal.getCalories()) );
        holder.txtProtein.setText(String.valueOf(currentJournal.getProtein()) + unit );
        holder.txtFat.setText(String.valueOf(currentJournal.getFats()) + unit );
        holder.txtCarb.setText(String.valueOf(currentJournal.getCarb()) + unit );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int foodId = journals.get(position).getId();
                int foodQuantity = journals.get(position).getQuantity();
                String date = journals.get(position).getDate();
                String foodType = journals.get(position).getFoodType();
                String foodName = journals.get(position).getFoodName();
                String protein = precise(journals.get(position).getProtein());
                String fats = precise(journals.get(position).getFats());
                String carb = precise(journals.get(position).getCarb());
                String calories = calPrecise(journals.get(position).getCalories());

                Intent intent = new Intent(v.getContext(), AddJournal.class);
                intent.putExtra("foodId", foodId);
                intent.putExtra("foodQuantity", foodQuantity);
                intent.putExtra("date", date);
                intent.putExtra("foodType", foodType);
                intent.putExtra("foodName", foodName);
                intent.putExtra("protein", protein);
                intent.putExtra("fats", fats);
                intent.putExtra("carb", carb);
                intent.putExtra("calories", calories);
                intent.putExtra("type", "edit");
                v.getContext().startActivity(intent);

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
        if(journals!=null) {
            return journals.size();
        }else{
            return 0;
        }
    }

    public void setJournals(List<Journal> journals){
        this.journals = journals;
        notifyDataSetChanged();
    }

    public Journal getJournalAt(int position){
        return journals.get(position);
    }


    public void onRefreshAdapter(List<Journal> journals) {
        this.journals = journals;
        notifyDataSetChanged();
    }


}
