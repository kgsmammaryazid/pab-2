package com.if5b.roomdatabase.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Book.class, exportSchema = false, version = 1)
public abstract class BookDatabase extends RoomDatabase {
    private static final String DB_NAME = "bookDatabase";
    private static BookDatabase instance;

    public static synchronized BookDatabase getInstance(Context context) {
        if(instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BookDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract BookDAO bookDAO();
}
