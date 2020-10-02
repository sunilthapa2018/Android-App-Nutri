package com.example.nutri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nutri.models.Food;
import com.example.nutri.models.Nutrients;
import com.example.nutri.requests.FoodApi;
import com.example.nutri.requests.ServiceGenerator;
import com.example.nutri.requests.responses.FoodResponses;
import com.example.nutri.util.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public ProgressBar mProgressBar;
    private static final String TAG = "MYTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mProgressBar = findViewById(R.id.progress_bar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search, R.id.navigation_journal, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        testRetrofitRequest();
        //mProgressBar.setVisibility(View.VISIBLE);

    }
    private void testRetrofitRequest(){
        FoodApi foodApi = ServiceGenerator.getFoodApi();
        Call<FoodResponses> responsesCall = foodApi
                .searchFood(
                        "chicken breast",
                        Constants.APPID,
                        Constants.APIKEY
                );

        responsesCall.enqueue(new Callback<FoodResponses>() {
            @Override
            public void onResponse(Call<FoodResponses> call, Response<FoodResponses> response) {
                Log.d(TAG, "onResponse : Server Response: " + response.toString());
                if(response.code() == 200){
                    Log.d(TAG, "onResponse: " + response.body());


                    //String nutrients = response.body().getParsed().ge
//                    for (Food food: foods){
//                        Log.d(TAG, "onResponse: " + food.getFoodId());
//                    }
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