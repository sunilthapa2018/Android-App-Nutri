package com.example.nutri;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutri.adapters.FoodRecyclerAdapter;
import com.example.nutri.models.Food;
import com.example.nutri.models.Parsed;
import com.example.nutri.requests.FoodApi;
import com.example.nutri.requests.ServiceGenerator;
import com.example.nutri.requests.responses.FoodResponses;
import com.example.nutri.util.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewResults extends AppCompatActivity {
    private ArrayList<Parsed> newParsedList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String query;
    private static final String TAG = "MYTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        Intent intent = getIntent();
        query = intent.getStringExtra("query");


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        retrofitRequest(query);
        if(newParsedList!=null) {
            mAdapter = new FoodRecyclerAdapter(newParsedList);
        }

    }

    private void retrofitRequest(String searchText){
        FoodApi foodApi = ServiceGenerator.getFoodApi();
        Call<FoodResponses> responsesCall = foodApi
                .searchFood(
                        searchText,
                        Constants.APPID,
                        Constants.APIKEY
                );

        responsesCall.enqueue(new Callback<FoodResponses>() {
            @Override
            public void onResponse(Call<FoodResponses> call, Response<FoodResponses> response) {
                Log.d(TAG, "onResponse : Server Response: " + response.toString());
                if(response.code() == 200){
                    Log.d(TAG, "onResponse: " + response.body());
                    ArrayList<Parsed> parsedList = response.body().getParsed();
                    mAdapter = new FoodRecyclerAdapter(parsedList);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    //newParsedList = parsedList;
                    //Collections.copy(newParsedList, parsedList);
                }else{
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodResponses> call, Throwable t) {
                StringWriter errors = new StringWriter();
                t.printStackTrace(new PrintWriter(errors));
                Log.d(TAG, "onFailure: Failed to get response \n" + errors);
                t.printStackTrace();
            }
        });
    }
}
