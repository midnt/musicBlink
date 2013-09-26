package com.midnight.musicblink.data;


import android.content.Context;

public class DatabaseManager {


    private static final DatabaseManager instance = new DatabaseManager();

    private DatabaseHelper databaseHelper;

    public static DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager() {
    }

    public void init(final Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

}