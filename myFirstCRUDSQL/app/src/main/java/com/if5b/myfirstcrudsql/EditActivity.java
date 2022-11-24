package com.if5b.myfirstcrudsql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.if5b.myfirstcrudsql.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;
    private String getId, getTitle, getAuthor, getYear;
    int getPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent data = getIntent();
        getId = data.getStringExtra("keyId");
        getTitle = data.getStringExtra("keyTitle");
        getAuthor = data.getStringExtra("keyAuthor");
        getYear = data.getStringExtra("keyYear");
        getPosition = data.getExtras().getInt("keyPosition", -1);

        binding.etJudul.setText(getTitle);
        binding.etPenulis.setText(getAuthor);
        binding.etTahun.setText(getYear);


        binding.btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtTitle = binding.etJudul.getText().toString();
                String txtAuthor = binding.etPenulis.getText().toString();
                String txtYear = binding.etTahun.getText().toString();
                if(txtTitle.trim().equals("")) {
                    binding.etJudul.setError("Judul tidak boleh kosong");
                }
                else if(txtAuthor.trim().equals("")) {
                    binding.etJudul.setError("Penulis tidak boleh kosong");
                }
                else if(txtYear.trim().equals("")) {
                    binding.etJudul.setError("Tahun tidak boleh kosong");
                }
                else {
                    MyDatabaseHelper myDb = new MyDatabaseHelper(EditActivity.this);
                    long result = myDb.editBookById(getId, txtTitle, txtAuthor, Integer.valueOf(txtYear));

                    if(result == -1) {
                        Toast.makeText(EditActivity.this, "Gagal Mengubah data", Toast.LENGTH_SHORT).show();
                        binding.etJudul.requestFocus();
                    }
                    else {
                        Toast.makeText(EditActivity.this, "Berhasil mengupdate data", Toast.LENGTH_SHORT).show();
                        MainActivity.dataPosition = getPosition;
                        finish();
                    }
                }
            }
        });
    }
}