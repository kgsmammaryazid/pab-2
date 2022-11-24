package com.if5b.kamus.databases;


import static android.provider.BaseColumns._ID;
import static com.if5b.kamus.databases.DatabaseContract.TABLE_NAME;
import static com.if5b.kamus.databases.DatabaseContract.googleTranslateColumns.DESC_COLUMN;
import static com.if5b.kamus.databases.DatabaseContract.googleTranslateColumns.WORD_COLUMN;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "db_kamus";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_QUERY = "CREATE TABLE "+ TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            WORD_COLUMN + " TEXT NOT NULL, " + DESC_COLUMN + " TEXT NOT NULL);";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
