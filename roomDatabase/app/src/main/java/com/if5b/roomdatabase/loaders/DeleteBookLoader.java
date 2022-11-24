package com.if5b.roomdatabase.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.if5b.roomdatabase.db.BookDatabase;

public class DeleteBookLoader extends AsyncTaskLoader<Integer> {
    private int bookId;
    private BookDatabase bookDatabase;

    public DeleteBookLoader(@NonNull Context context, int bookId) {
        super(context);
        this.bookId = bookId;
        this.bookDatabase = BookDatabase.getInstance(context);
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        return bookDatabase.bookDAO().deleteBook(bookId);
    }
}
