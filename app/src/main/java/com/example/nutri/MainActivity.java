package com.example.nutri;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nutri.models.Food;
import com.example.nutri.models.Parsed;
import com.example.nutri.requests.FoodApi;
import com.example.nutri.requests.ServiceGenerator;
import com.example.nutri.requests.responses.FoodResponses;
import com.example.nutri.util.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public ProgressBar mProgressBar;
    private static final String TAG = "MYTAG";
    public SearchView searchView;
    public BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        mProgressBar = findViewById(R.id.progress_bar);

        // Passing each options_menu ID as a set of Ids because each
        // options_menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search, R.id.navigation_journal, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        //retrofitRequest("Chicken");

        //bottomNavigationView = (BottomNavigationView)findViewById(R.id.nav_view);
        //bottomNavigationView.setSelectedItemId(R.id.navigation_journal);

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                //navigationView = (NavigationView) findViewById(R.id.nav_host_fragment);
//                switch (item.getItemId()) {
//                    case R.id.navigation_search:
//                        bottomNavigationView.setSelectedItemId(R.id.navigation_search);
//                        return true;
//                    case R.id.navigation_journal:
//                        bottomNavigationView.setSelectedItemId(R.id.navigation_journal);
//                        return true;
//                    case R.id.navigation_profile:
//                        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
//                        return true;
//                }
//
//                return false;
//            }
//        });

        //retrofitRequest("Dairy Products");
        //mProgressBar.setVisibility(View.VISIBLE);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.options_menu, menu);
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                String searchQuery = searchView.getQuery().toString();
//                Toast.makeText(getApplicationContext(),searchQuery,Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(searchView.hasFocus() == false){
//                    //searchView.setIconified(true);
//                }
//            }
//        });

        return true;
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }*/

/*    @Override
    public boolean onCreateOptionsMenu(Menu options_menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.options_menu.options_menu, options_menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        };
        options_menu.findItem(R.id.menuSearch).setOnActionExpandListener(onActionExpandListener);

        SearchView searchView = (SearchView) options_menu.findItem(R.id.menuSearch).getActionView();
        searchView.setQueryHint("Search food here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }*/

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
                    Food foodList;
                    ArrayList<Parsed> parsedList = response.body().getParsed();
                    for(int i=0; i < parsedList.size();i++){
                        foodList = parsedList.get(i).getFood();
                        Log.d(TAG, "onResponse:  \n" +
                            "foodId = " + foodList.getFoodId() + "\n" +
                            "label = " + foodList.getLabel() + "\n" +
                            "energyKcal = " + foodList.getNutrients().getEnergyKcal() + "\n" +
                            "proteinCount = " + foodList.getNutrients().getProteinCount() + "\n" +
                            "FAT = " + foodList.getNutrients().getFat() + "\n" +
                            "carbs = " + foodList.getNutrients().getCarbs() + "\n" +
                            "fiber = " + foodList.getNutrients().getFiber() + "\n" +
                            "category = " + foodList.getCategory() + "\n" +
                            "category-label = " + foodList.getCategoryLabel() + "\n" +
                            "quantity = " + foodList.getQuantity() + "\n\n\n\n"
                        );

                    }
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