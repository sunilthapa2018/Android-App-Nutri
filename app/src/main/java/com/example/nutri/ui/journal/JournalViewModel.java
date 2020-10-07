package com.example.nutri.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutri.Database.Journal;
import com.example.nutri.Database.JournalRepository;
import com.example.nutri.Database.Journal;
import com.example.nutri.Database.JournalRepository;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {

    private JournalRepository repository;

    private LiveData<List<Journal>> allDataOfDate;

    private String date;

    public JournalViewModel(@NonNull Application application) {
        super(application);
        repository = new JournalRepository(application);
        //allData = repository.getAllJournals();
        allDataOfDate = repository.getAllJournalsOfDate(date);
    }

    public void insert(Journal journal){
        repository.insert(journal);
    }

    public void update(Journal journal){
        repository.update(journal);
    }

    public void delete(Journal journal){
        repository.delete(journal);
    }

    public void deleteAllNotes(){
        repository.deleteAllJournals();
    }


    public LiveData<List<Journal>> getAllJournalsOfDate(String date) {
        allDataOfDate = repository.getAllJournalsOfDate(date);
        return allDataOfDate;
    }

    
}