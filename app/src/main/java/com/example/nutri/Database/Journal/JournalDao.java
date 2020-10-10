package com.example.nutri.Database.Journal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JournalDao {
    @Insert
    void insert(Journal journal);

    @Update
    void update(Journal journal);

    @Delete
    void delete(Journal journal);

    @Query("DELETE FROM journal_table")
    void deleteAllJournals();

    @Query("SELECT * FROM journal_table")
    LiveData<List<Journal>> getAllJournals();

    @Query("SELECT * FROM journal_table WHERE date= :date")
    LiveData<List<Journal>> getAllJournalsOfDate(String date);
}
