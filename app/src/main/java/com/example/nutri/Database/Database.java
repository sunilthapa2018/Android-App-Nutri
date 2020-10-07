package com.example.nutri.Database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Profile.class, Journal.class}, version = 3)
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public abstract ProfileDao profileDao();

    public abstract JournalDao journalDao();

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                Database.class, "Database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
