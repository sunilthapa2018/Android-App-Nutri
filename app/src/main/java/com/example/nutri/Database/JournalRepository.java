package com.example.nutri.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class JournalRepository {
    private JournalDao journalDao;
    private LiveData<List<Journal>> allJournals;
    private LiveData<List<Journal>> allJournalsOfDate;
    private String date;

    public JournalRepository(Application application){
        Database database = Database.getInstance(application);
        journalDao = database.journalDao();
        allJournals = journalDao.getAllJournals();

    }

    public void insert(Journal journal){
        new InsertJournalAsyncTask(journalDao).execute(journal);

    }
    public void update(Journal journal){
        new UpdateJournalAsyncTask(journalDao).execute(journal);

    }
    public void delete(Journal journal){
        new DeleteJournalAsyncTask(journalDao).execute(journal);

    }
    public void deleteAllJournals(){
        new DeleteAllJournalAsyncTask(journalDao).execute();

    }

    public LiveData<List<Journal>> getAllJournals(){
        return allJournals;
    }

    public LiveData<List<Journal>> getAllJournalsOfDate(String date){
        allJournalsOfDate = journalDao.getAllJournalsOfDate(date);
        return allJournalsOfDate;
    }

    private static class InsertJournalAsyncTask extends AsyncTask<Journal,Void,Void> {

        private JournalDao journalDao;

        private InsertJournalAsyncTask(JournalDao journalDao){
            this.journalDao = journalDao;
        }

        @Override
        protected Void doInBackground(Journal... journals) {
            journalDao.insert(journals[0]);
            return null;
        }
    }

    private static class UpdateJournalAsyncTask extends AsyncTask<Journal,Void,Void> {

        private JournalDao journalDao;

        private UpdateJournalAsyncTask(JournalDao journalDao){
            this.journalDao = journalDao;
        }

        @Override
        protected Void doInBackground(Journal... journals) {
            journalDao.update(journals[0]);
            return null;
        }
    }

    private static class DeleteJournalAsyncTask extends AsyncTask<Journal,Void,Void> {

        private JournalDao journalDao;

        private DeleteJournalAsyncTask(JournalDao journalDao){
            this.journalDao = journalDao;
        }

        @Override
        protected Void doInBackground(Journal... journals) {
            journalDao.delete(journals[0]);
            return null;
        }
    }

    private static class DeleteAllJournalAsyncTask extends AsyncTask<Void,Void,Void> {

        private JournalDao journalDao;

        private DeleteAllJournalAsyncTask(JournalDao journalDao){
            this.journalDao = journalDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            journalDao.deleteAllJournals();
            return null;
        }
    }

}
