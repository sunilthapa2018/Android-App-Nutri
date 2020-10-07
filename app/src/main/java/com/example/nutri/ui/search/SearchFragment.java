package com.example.nutri.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutri.adapters.DefaultSearchRecyclerAdapter;
import com.example.nutri.FoodMenuItems;
import com.example.nutri.R;
import com.example.nutri.SearchResults;
import com.example.nutri.adapters.FoodRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView mRecyclerView;
    private DefaultSearchRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<FoodMenuItems> foodMenuItem = new ArrayList<>();
        foodMenuItem.add(new FoodMenuItems(R.drawable.egg, "Dairy and Egg Products"));
        foodMenuItem.add(new FoodMenuItems(R.drawable.spices, "Spices and Herbs"));
        foodMenuItem.add(new FoodMenuItems(R.drawable.babyfoods, "Baby Foods"));
        foodMenuItem.add(new FoodMenuItems(R.drawable.fats, "Fats and Oils"));
        foodMenuItem.add(new FoodMenuItems(R.drawable.poultry, "Poultry Products"));
        foodMenuItem.add(new FoodMenuItems(R.drawable.sweets, "Sweets"));

        mAdapter = new DefaultSearchRecyclerAdapter(foodMenuItem);
        mRecyclerView = root.findViewById(R.id.recyclerViewSearch);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DefaultSearchRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                String query = foodMenuItem.get(position).getTxtTitleText();
                Intent intent = new Intent(root.getContext(), SearchResults.class);
                intent.putExtra("query", query);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchResults.class);
                startActivity(i);
            }
        });

        return root;
    }

    public void displayToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}