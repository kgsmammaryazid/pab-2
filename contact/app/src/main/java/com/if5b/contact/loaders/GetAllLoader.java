package com.if5b.contact.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.if5b.contact.databases.UserDatabase;
import com.if5b.contact.entities.User;

import java.util.List;

public class GetAllLoader extends AsyncTaskLoader<List<User>> {
    private UserDatabase userDatabase;
    public GetAllLoader(@NonNull Context context) {
        super(context);
        userDatabase = UserDatabase.getInstance(context);
    }

    @Nullable
    @Override
    public List<User> loadInBackground() {
        return userDatabase.userDAO().getAllUsers();
    }
}
