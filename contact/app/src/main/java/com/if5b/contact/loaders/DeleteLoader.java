package com.if5b.contact.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.if5b.contact.databases.UserDatabase;
import com.if5b.contact.entities.User;

public class DeleteLoader extends AsyncTaskLoader<Integer> {
    private UserDatabase userDatabase;
    private int userId;
    public DeleteLoader(@NonNull Context context, int userId) {
        super(context);
        userDatabase = UserDatabase.getInstance(context);
        this.userId = userId;
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        return userDatabase.userDAO().deleteUser(userId);
    }
}
