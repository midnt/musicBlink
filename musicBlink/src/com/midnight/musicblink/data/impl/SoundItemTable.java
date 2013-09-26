package com.midnight.musicblink.data.impl;

import android.provider.BaseColumns;


public class SoundItemTable implements BaseColumns {
    public static final String TABLE_NAME = "soundItems";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URI = "uri";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_CONTENT =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_URI + TEXT_TYPE +
                    " )";


    private SoundItemTable() {
    }


}