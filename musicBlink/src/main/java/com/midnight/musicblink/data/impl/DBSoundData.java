package com.midnight.musicblink.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.midnight.musicblink.data.DatabaseManager;
import com.midnight.musicblink.data.SoundData;

import java.util.ArrayList;
import java.util.List;

public class DBSoundData implements SoundData {


    @Override
    public List<SoundItem> getAll() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] field = new String[]{SoundItemTable.COLUMN_NAME, SoundItemTable.COLUMN_URI};
        Cursor cursor = sqLiteDatabase.query(SoundItemTable.TABLE_NAME, field, null, null, null, null, null);

        ArrayList<SoundItem> arrayList = new ArrayList<SoundItem>();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(new SoundItem(cursor.getString(0), cursor.getString(1)));
            }
            while (cursor.moveToNext());
        }
        release(sqLiteDatabase);
        return arrayList;
    }

    @Override
    public void addItem(final SoundItem item) {
        Log.i("musicB", "add: " + item.getName() + " " + item.getFileUri());
        SQLiteDatabase sqLiteDatabase = getWriteDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SoundItemTable.COLUMN_NAME, item.getName());
        contentValues.put(SoundItemTable.COLUMN_URI, item.getFileUri());
        sqLiteDatabase.insert(SoundItemTable.TABLE_NAME, null, contentValues);
        release(sqLiteDatabase);
    }

    @Override
    public SoundItem getItem(final int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] field = new String[]{SoundItemTable.COLUMN_NAME, SoundItemTable.COLUMN_URI};
        Cursor cursor = sqLiteDatabase.query(SoundItemTable.TABLE_NAME, field, null, null, null, null, null);
//        cursor.moveToFirst();
        if (cursor.moveToPosition(id)) {
            SoundItem soundItem = new SoundItem(cursor.getString(0), cursor.getString(1));

            release(sqLiteDatabase);
            return soundItem;

        } else {
            release(sqLiteDatabase);
            return null;
        }

    }

    private SQLiteDatabase getWriteDatabase() {
        return DatabaseManager.getInstance().getDatabaseHelper().getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        return DatabaseManager.getInstance().getDatabaseHelper().getReadableDatabase();
    }

    private void release(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.close();
    }

}
