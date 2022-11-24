package com.if5b.contact.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.if5b.contact.databases.UserDatabase;
import com.if5b.contact.entities.User;

public class UpdateLoader extends AsyncTaskLoader<Integer> {
    private UserDatabase userDatabase;
    private User user;

    public UpdateLoader(@NonNull Context context, User user) {
        super(context);
        userDatabase = UserDatabase.getInstance(context);
        this.user = user;
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        return userDatabase.userDAO().updateUser(user);
    }
}
