package com.example.nutri.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutri.FoodMenuItems;
import com.example.nutri.R;
import com.example.nutri.models.Parsed;

import java.text.NumberFormat;
import java.util.ArrayList;

public class DefaultSearchRecyclerAdapter extends RecyclerView.Adapter<DefaultSearchRecyclerAdapter.FoodViewHolder> {
    private ArrayList<FoodMenuItems> parsedList;
    private OnItemClickListener mListener;
    private int counter = 0;
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView txtTitleName;

        public FoodViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtTitleName = itemView.findViewById(R.id.txtTitleName);
            imageView = itemView.findViewById(R.id.imageView);

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

    public DefaultSearchRecyclerAdapter(ArrayList<FoodMenuItems> parsedList){
        this.parsedList = parsedList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_foods, parent,false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view, mListener);
        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodMenuItems parsed = parsedList.get(position);
        holder.txtTitleName.setText(parsed.getTxtTitleText());
        holder.imageView.setImageResource(parsed.getmImageResource());

    }

    @Override
    public int getItemCount() {
        return parsedList.size();
    }
}
