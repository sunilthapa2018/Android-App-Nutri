package com.example.nutri.ui.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nutri.Database.Journal.Journal;
import com.example.nutri.Database.Journal.JournalDao;
import com.example.nutri.R;
import com.example.nutri.ui.search.SearchResults;
import com.example.nutri.adapters.JournalRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JournalFragment extends Fragment{

    private JournalViewModel journalViewModel;
    private RecyclerView mRecyclerView;
    private JournalRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private LiveData<List<Journal>> journal;

    private JournalDao journalDao;

    private TextView txtDate;
    private TextView txtError;
    private TextView txtTotalKCal;
    private int totalKCal = 0;

    private Button btnLeft;
    private Button btnRight;

    private String fDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_journal, container, false);
        txtDate = root.findViewById(R.id.txtDate);
        txtError = root.findViewById(R.id.txtError);

        String dateString = txtDate.getText().toString();

        if(dateString.isEmpty() || dateString.toLowerCase().equals("date")){
            Date cal = Calendar.getInstance().getTime();
            SimpleDateFormat datef = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            fDate = datef.format(cal);
            txtDate.setText(changeDate2(fDate));
        }else{
            fDate = changeDate(dateString);
        }

        mRecyclerView = root.findViewById(R.id.recyclerViewJournal);
        loadViewModel();

        txtTotalKCal = root.findViewById(R.id.txtTotalkCal);
        btnLeft = root.findViewById(R.id.btnLeft);
        btnRight = root.findViewById(R.id.btnRight);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchResults.class);
                startActivity(i);
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtOnDate = txtDate.getText().toString();
                String newDate = subtractDate(txtOnDate);
                fDate = changeDate(newDate);
                txtDate.setText(newDate);
                String str = txtDate.getText().toString();
                fDate = changeDate(str);
                loadViewModel();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtOnDate = txtDate.getText().toString();
                String newDate = addDate(txtOnDate);
                fDate = changeDate(newDate);
                txtDate.setText(newDate);
                String str = txtDate.getText().toString();
                fDate = changeDate(str);
                loadViewModel();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                journalViewModel.delete(mAdapter.getJournalAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(),"Journal Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        return root;
    }

    public void loadViewModel(){
        journalViewModel.getAllJournalsOfDate(fDate).observe(getActivity(), new Observer<List<Journal>>() {
            @Override
            public void onChanged(List<Journal> journals) {
                totalKCal=0;
                if (journals.size()>0) {
                    txtError.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    Journal currentData = journals.get(0);
                    if(fDate.isEmpty()){
                        txtDate.setText(changeDate2(currentData.getDate()));
                    }else{
                        txtDate.setText(changeDate2(fDate));
                    }


                    for(int i=0; i < journals.size(); i++){
                        totalKCal += journals.get(i).getCalories();
                    }

                    txtTotalKCal.setText(String.valueOf(totalKCal) + " KCal");

                    mAdapter = new JournalRecyclerAdapter(journals);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);

                }else{
                    txtTotalKCal.setText(String.valueOf(totalKCal) + " KCal");
                    mRecyclerView.setVisibility(View.GONE);
                    txtError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public String changeDate(String date){
        String newDate = "";
        try {
            Date tempDate=new SimpleDateFormat("MMM d, yyyy").parse(date);
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            String formattedDate = df.format(tempDate);
            newDate = formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public String changeDate2(String date){
        String newDate = "";
        try {
            Date tempDate=new SimpleDateFormat("MM/dd/yyyy").parse(date);
            SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String formattedDate = df.format(tempDate);
            newDate = formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public String subtractDate(String date){
        String newDate = "";
        try {
            Date tempDate=new SimpleDateFormat("MMM d, yyyy").parse(date);
            tempDate.setDate(tempDate.getDate() - 1);
            SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String formattedDate = df.format(tempDate);
            newDate = formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public String addDate(String date){
        String newDate = "";
        try {
            Date tempDate=new SimpleDateFormat("MMM d, yyyy").parse(date);
            tempDate.setDate(tempDate.getDate() + 1);
            SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String formattedDate = df.format(tempDate);
            newDate = formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
}