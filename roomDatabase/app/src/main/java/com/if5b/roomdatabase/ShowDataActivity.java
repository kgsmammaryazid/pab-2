package com.if5b.roomdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.if5b.roomdatabase.databinding.ActivityShowDataBinding;
import com.if5b.roomdatabase.db.Book;
import com.if5b.roomdatabase.loaders.DeleteBookLoader;
import com.if5b.roomdatabase.loaders.GetBookLoader;

import java.util.List;

public class ShowDataActivity extends AppCompatActivity {
    public static final int DATA_LOADER_CODE = 122;
    private static final int EDIT_CODE = 123;
    private static final int DELETE_LOADER_CODE = 124;
    ActivityShowDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getBook();
    }

    private void getBook() {
        showProgressBar();
        LoaderManager.getInstance(this).restartLoader(DATA_LOADER_CODE, null, new LoaderManager.LoaderCallbacks<List<Book>>() {

            @NonNull
            @Override
            public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
                return new GetBookLoader(ShowDataActivity.this);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
                hideProgressBar();
                initAdapter(data);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<Book>> loader) {

            }
        }).forceLoad();
        hideProgressBar();
    }

    private void initAdapter(List<Book> data) {
        BookAdapter bookAdapter = new BookAdapter(this, data);
        binding.rvBook.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBook.setAdapter(bookAdapter);
        bookAdapter.setOnClickListener(new BookAdapter.OnClickListener() {
            @Override
            public void onEditClicked(Book book) {
                gotoupdateBookActivity(book);
            }

            @Override
            public void onDeleteClicked(int bookId) {
                deleteBook(bookId);
            }
        });
    }

    private void deleteBook(int bookId) {
        showProgressBar();
        Bundle args = new Bundle();
        args.putInt("id", bookId);
        LoaderManager.getInstance(this).restartLoader(DELETE_LOADER_CODE, args, new LoaderManager.LoaderCallbacks<Integer>() {
            @NonNull
            @Override
            public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
                return new DeleteBookLoader(ShowDataActivity.this, args.getInt("id"));
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
                hideProgressBar();
                if(data!=-1){
                    itemDeleted();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Integer> loader) {

            }
        }).forceLoad();
    }

    private void itemDeleted() {
        Toast.makeText(this, "Book Deleted", Toast.LENGTH_SHORT).show();
        getBook();
    }

    private void gotoupdateBookActivity(Book book) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("edit", true);
        intent.putExtra("book", book);
        startActivityForResult(intent, EDIT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==EDIT_CODE){
            if(resultCode==RESULT_OK){
                bookUpdated();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void bookUpdated() {
        Toast.makeText(this, "Book updated Successfully !", Toast.LENGTH_SHORT).show();
        getBook();
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}