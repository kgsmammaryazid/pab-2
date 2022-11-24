package com.example.latihanuts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Inisialiasi context, database name & version
    private Context ctx;
    private static final String DATABASE_NAME = "myFirstDB";
    private static final int DATABASE_VERSION = 1;
    // Create nama table dan nama column
    private static final String TABLE_NAME = "tbBuku";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ISBN = "isbn";
    private static final String COLUMN_JUDUL = "judul";
    private static final String COLUMN_KATEGORI = "kategori";
    private static final String COLUMN_DESKRIPSI = "deskripsi";
    private static final String COLUMN_HARGA = "harga";

    // Constructor Super
    public DatabaseHelper(@Nullable Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = ctx;
    }

    //Query Create TABLE
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ISBN + " TEXT, "
                + COLUMN_JUDUL + " TEXT, "
                + COLUMN_KATEGORI + " TEXT, "
                + COLUMN_DESKRIPSI + " TEXT, "
                + COLUMN_HARGA + " TEXT ); ";
        sqLiteDatabase.execSQL(query);
    }
    //Query check apakah table sudah ada pada database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // Fungsi Helper untuk Insert Data
    public long insertBook(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ISBN, book.getIsbn());
        contentValues.put(COLUMN_JUDUL, book.getJudul());
        contentValues.put(COLUMN_KATEGORI, book.getKategori());
        contentValues.put(COLUMN_DESKRIPSI, book.getDeskripsi());
        contentValues.put(COLUMN_HARGA, book.getHarga());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return result;
    }
    // Fungsi Helper untuk getAllData

    public Cursor getAllBook() {
        String query = "SELECT*FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    // Fungsi Helper untuk editBuku

    public long editBookById(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ISBN, book.getIsbn());
        contentValues.put(COLUMN_JUDUL, book.getJudul());
        contentValues.put(COLUMN_KATEGORI, book.getKategori());
        contentValues.put(COLUMN_DESKRIPSI, book.getDeskripsi());
        contentValues.put(COLUMN_HARGA, book.getHarga());

        long result = sqLiteDatabase.update(TABLE_NAME, contentValues, "id = ?", new String[]{book.getId()});
        return result;
    }

    public long deleteBookById(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_NAME, "id = ?", new String[]{book.getId()});
        return  result;

    }
}
