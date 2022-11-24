package com.if5b.roomdatabase.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.if5b.roomdatabase.db.Book;
import com.if5b.roomdatabase.db.BookDatabase;

public class InsertLoader extends AsyncTaskLoader<Boolean> {
    private Book book;
    private BookDatabase bookDatabase;

    public InsertLoader(@NonNull Context context,Book book) {
        super(context);
        this.book = book;
        this.bookDatabase = BookDatabase.getInstance(context);
    }


    @Nullable
    @Override
    public Boolean loadInBackground() {
        bookDatabase.bookDAO().insertBook(book);
        return true;
    }
}
