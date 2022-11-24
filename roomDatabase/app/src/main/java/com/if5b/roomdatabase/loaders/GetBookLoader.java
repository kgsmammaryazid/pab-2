package com.if5b.roomdatabase.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.if5b.roomdatabase.db.Book;
import com.if5b.roomdatabase.db.BookDatabase;

import java.util.List;

public class GetBookLoader extends AsyncTaskLoader<List<Book>> {
    private BookDatabase bookDatabase;

    public GetBookLoader(@NonNull Context context) {
        super(context);
        bookDatabase = BookDatabase.getInstance(context);
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        return bookDatabase.bookDAO().getAllBooks();
    }
}
