package com.if5b.contact.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.if5b.contact.databases.UserDatabase;
import com.if5b.contact.entities.User;

public class InsertLoader extends AsyncTaskLoader<Boolean> {
    private UserDatabase userDatabase;
    private User user;
    public InsertLoader(@NonNull Context context, User user) {
        super(context);
        userDatabase = UserDatabase.getInstance(context);
        this.user = user;
    }

    @Nullable
    @Override
    public Boolean loadInBackground() {
        userDatabase.userDAO().addUser(user);
        return true;
    }
}
