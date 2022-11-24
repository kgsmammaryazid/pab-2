package com.if5b.contact.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.if5b.contact.R;
import com.if5b.contact.databinding.ActivityInputBinding;
import com.if5b.contact.entities.User;
import com.if5b.contact.loaders.InsertLoader;
import com.if5b.contact.loaders.UpdateLoader;

public class InputActivity extends AppCompatActivity {
    private static final int INSERT_LOADER = 666;
    private static final int UPDATE_LOADER = 667;
    private ActivityInputBinding binding;
    // untuk edit / insert mode
    private boolean editMode;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editMode = getIntent().getBooleanExtra("edit", false);
        if (editMode) {
            User user = getIntent().getParcelableExtra("user");
            userId = user.getId();
            // method untuk set detail user ke dalam edit text
            setDetails(user);
        }

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    if (editMode) {
                        updateUser();
                    }
                    else {
                        insertUser();
                    }
                }
            }
        });
    }

    private boolean validateFields() {
        if(binding.etName.getText().toString().equals("")
            || binding.etEmail.getText().toString().equals("")
            || binding.etPhone.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateUser() {
        User user = new User();
        user.setId(userId);
        user.setName(binding.etName.getText().toString());
        user.setEmail(binding.etEmail.getText().toString());
        user.setPhone(binding.etPhone.getText().toString());
        showProgressBar();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        LoaderManager.getInstance(this).restartLoader(UPDATE_LOADER, args, new LoaderManager.LoaderCallbacks<Integer>() {
            @NonNull
            @Override
            public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
                return new UpdateLoader(InputActivity.this, args.getParcelable("user"));
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
                hideProgressBar();
                if (data != -1) {
                    userUpdated();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Integer> loader) {

            }
        }).forceLoad();
    }

    private void userUpdated() {
        setResult(RESULT_OK);
        finish();
    }

    private void insertUser() {
        User user = new User();
        user.setName(binding.etName.getText().toString());
        user.setEmail(binding.etEmail.getText().toString());
        user.setPhone(binding.etPhone.getText().toString());
        showProgressBar();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        LoaderManager.getInstance(this).restartLoader(INSERT_LOADER, args, new LoaderManager.LoaderCallbacks<Boolean>() {
            @NonNull
            @Override
            public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle args) {
                return new InsertLoader(InputActivity.this, args.getParcelable("user"));
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean data) {
                hideProgressBar();
                if (data) {
                    userAdded();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Boolean> loader) {

            }
        }).forceLoad();
    }

    private void userAdded() {
        binding.etName.setText("");
        binding.etEmail.setText("");
        binding.etPhone.setText("");
        Toast.makeText(this, "User added to database !", Toast.LENGTH_SHORT).show();

    }

    private void setDetails(User user) {
        binding.etName.setText(user.getName());
        binding.etEmail.setText(user.getEmail());
        binding.etPhone.setText(user.getPhone());
        binding.btnAdd.setText("Update user");
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }
    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

}