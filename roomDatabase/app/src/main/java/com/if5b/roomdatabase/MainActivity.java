package com.if5b.roomdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.if5b.roomdatabase.databinding.ActivityMainBinding;
import com.if5b.roomdatabase.db.Book;
import com.if5b.roomdatabase.loaders.InsertLoader;
import com.if5b.roomdatabase.loaders.UpdateBookLoader;


public class MainActivity extends AppCompatActivity {
    private static final int INSERT_LOADER = 121;
    private static final int UPDATE_LOADER = 122;
    ActivityMainBinding binding;
    private boolean editMode;
    private int bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editMode = getIntent().getBooleanExtra("edit",false);
        if(editMode){
            Book book = getIntent().getParcelableExtra("book");
            bookId = book.getId();
            setDetails(book);
        }

    binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validateFields()) {
                if(editMode){
                    updateBook();
                }
                else{
                    insertBook();
                }
            }
        }
    });
    }

    private void updateBook() {
        Book book = new Book();
        book.setId(bookId);
        book.setTitle(binding.etJudul.getText().toString());
        book.setAuthor(binding.etPenulis.getText().toString());
        book.setYear(Integer.parseInt(binding.etTahun.getText().toString()));
        showProgressBar();
        Bundle args = new Bundle();
        args.putParcelable("book", book);
        LoaderManager.getInstance(this).restartLoader(UPDATE_LOADER, args, new LoaderManager.LoaderCallbacks<Integer>() {

            @NonNull
            @Override
            public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
                return new UpdateBookLoader(MainActivity.this, args.getParcelable("book"));
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
                hideProgressBar();
                if(data!=-1){
                    bookUpdated();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Integer> loader) {

            }
        }).forceLoad();
    }

    private void bookUpdated() {
        setResult(RESULT_OK);
        finish();
    }

    private void setDetails(Book book) {
        binding.etJudul.setText(book.getTitle());
        binding.etPenulis.setText(book.getAuthor());
        binding.etTahun.setText(String.valueOf(book.getYear()));
        binding.btnSimpan.setText("Update Book");
    }

    private void insertBook() {
        Book book = new Book();
        book.setTitle(binding.etJudul.getText().toString());
        book.setAuthor(binding.etPenulis.getText().toString());
        book.setYear(Integer.parseInt(binding.etTahun.getText().toString()));
        showProgressBar();
        Bundle args = new Bundle();
        args.putParcelable("book", book);
        LoaderManager.getInstance(this)
                .restartLoader(INSERT_LOADER, args, new LoaderManager.LoaderCallbacks<Boolean>() {
                    @NonNull
                    @Override
                    public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle args) {
                        return new InsertLoader(MainActivity.this, args.getParcelable("book"));
                    }

                    @Override
                    public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean data) {
                        hideProgressBar();
                        if(data){
                            bookInserted();
                        }
                    }

                    @Override
                    public void onLoaderReset(@NonNull Loader<Boolean> loader) {
                    }
                }).forceLoad();
    }

    private void bookInserted() {
        binding.etJudul.setText("");
        binding.etPenulis.setText("");
        binding.etTahun.setText("");
        Toast.makeText(this, "Book added to database", Toast.LENGTH_SHORT).show();
    }

    private void showProgressBar() {
        binding.pb.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.pb.setVisibility(View.GONE);
    }

    private boolean validateFields() {
        if(binding.etJudul.getText().toString().equals("")||binding.etPenulis.getText().toString().equals("")
        ||binding.etTahun.getText().toString().equals("")){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_show_all) {
            gotoShowDataActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoShowDataActivity() {
        startActivity(new Intent(this, ShowDataActivity.class));
    }
}