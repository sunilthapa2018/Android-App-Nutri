package com.example.nutri.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.nutri.R;
import com.example.nutri.SearchResults;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);


        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), SearchResults.class);
//                i.putExtra("id", "Chicken Breast");
//                startActivity(i);

                Intent i = new Intent(getActivity(), SearchResults.class);
                startActivity(i);

            }
        });

        return root;
    }
    public void openNewPage(View view) {
        displayToast("Good Job");
    }
    public void displayToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}