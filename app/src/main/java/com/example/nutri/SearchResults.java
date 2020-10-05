package com.example.nutri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nutri.adapters.FoodRecyclerAdapter;
import com.example.nutri.models.Parsed;
import com.example.nutri.requests.FoodApi;
import com.example.nutri.requests.ServiceGenerator;
import com.example.nutri.requests.responses.FoodResponses;
import com.example.nutri.util.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResults extends AppCompatActivity {
    public ProgressBar mProgressBar;
    private static final String TAG = "MYTAG";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String query = "";
    private TextView txtNotFound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        query = getIntent().getStringExtra("query");
        Menu menu = findViewById(R.id.menu_search);
        txtNotFound = findViewById(R.id.txtNotFound);
        mProgressBar = findViewById(R.id.progress_bar);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search food here");
        searchView.onActionViewExpanded();
        if(query!=null){
            if(!query.isEmpty()){

                searchView.setQuery(query, true);
                searchView.clearFocus();
                mProgressBar.setVisibility(View.VISIBLE);
                retrofitRequest(query);
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query!="") {
                    mProgressBar.setVisibility(View.VISIBLE);
                    retrofitRequest(query);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
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
                    if(parsedList.size()==0){
                        txtNotFound.setText("No Data found in the remote server");
                        txtNotFound.setVisibility(View.VISIBLE);
                    }else {
                        txtNotFound.setVisibility(View.GONE);
                        mAdapter = new FoodRecyclerAdapter(parsedList);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                    mProgressBar.setVisibility(View.GONE);
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