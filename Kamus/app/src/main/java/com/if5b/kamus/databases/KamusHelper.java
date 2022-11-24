package com.if5b.kamus.databases;

import static android.provider.BaseColumns._ID;
import static com.if5b.kamus.databases.DatabaseContract.TABLE_NAME;
import static com.if5b.kamus.databases.DatabaseContract.googleTranslateColumns.DESC_COLUMN;
import static com.if5b.kamus.databases.DatabaseContract.googleTranslateColumns.WORD_COLUMN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.if5b.kamus.models.Kamus;

import java.util.ArrayList;

public class KamusHelper {
    private Context ctx;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context ctx) {
        this.ctx = ctx;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(ctx);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Kamus> getAllData() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null,
                _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Kamus> arrayList = new ArrayList<>();
        Kamus kamus;
        if (cursor.getCount() > 0) {
            do {
                kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(WORD_COLUMN)));
                kamus.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESC_COLUMN)));

                arrayList.add(kamus);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return  arrayList;
    }

    public ArrayList<Kamus> getDataByTitle(String title) {
        Cursor cursor = database.query(TABLE_NAME, null,
                WORD_COLUMN + " LIKE ?", new String[]{"%" + title + "%"}, null, null,
                _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Kamus> arrayList = new ArrayList<>();
        Kamus kamus;
        if (cursor.getCount() > 0) {
            do {
                kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(WORD_COLUMN)));
                kamus.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESC_COLUMN)));

                arrayList.add(kamus);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return  arrayList;
    }

    //insert bulk
    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }
    
    public void insertTransactionData(Kamus kamus) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + WORD_COLUMN + ", " + DESC_COLUMN +
                ") VALUES (?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, kamus.getTitle());
        statement.bindString(2, kamus.getDescription());
        statement.execute();
        statement.clearBindings();
    }

    public long insertData(Kamus kamus) {
        ContentValues cv = new ContentValues();
        cv.put(WORD_COLUMN, kamus.getTitle());
        cv.put(DESC_COLUMN, kamus.getDescription());
        return database.insert(TABLE_NAME, null, cv);
    }

    public int updateData(Kamus kamus) {
        ContentValues cv = new ContentValues();
        cv.put(WORD_COLUMN, kamus.getTitle());
        cv.put(DESC_COLUMN, kamus.getDescription());
        return database.update(TABLE_NAME, cv, _ID + "= '" + kamus.getId() + "'", null);
    }

    public int deleteData(int id) {
        return database.delete(TABLE_NAME, _ID + "= '" + id + "'", null);
    }
}
