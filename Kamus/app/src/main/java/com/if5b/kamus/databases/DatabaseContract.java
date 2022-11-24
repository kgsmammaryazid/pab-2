package com.if5b.kamus.databases;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME = "google_translate";
    static final class googleTranslateColumns implements BaseColumns {
        static String WORD_COLUMN = "kata";
        static String DESC_COLUMN = "deskripsi";
    }

}
