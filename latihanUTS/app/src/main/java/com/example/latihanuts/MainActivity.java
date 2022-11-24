package com.example.latihanuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.latihanuts.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BookAdapter bookAdapter;

    DatabaseHelper databaseHelper;
    ArrayList<Book> arrayBook;

    public static int dataPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(MainActivity.this);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });

    }

    private void getAllData() {
        Cursor cursor = databaseHelper.getAllBook();

        if(cursor.getCount() == 0) {
            Toast.makeText(this, "Data buku tidak ada", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()) {
                Book book = new Book(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5));
                arrayBook.add(book);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayBook = new ArrayList<>();
        getAllData();
        bookAdapter = new BookAdapter(MainActivity.this, arrayBook);
        binding.rvBook.setAdapter(bookAdapter);
        binding.rvBook.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.rvBook.smoothScrollToPosition(dataPosition);


    }
}