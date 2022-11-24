package com.if5b.myfirstcrudsql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.if5b.myfirstcrudsql.databinding.ActivityInsertBinding;

public class InsertActivity extends AppCompatActivity {

    private ActivityInsertBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getTitle = binding.etJudul.getText().toString();
                String getAuthor = binding.etPenulis.getText().toString();
                String getYear = binding.etTahun.getText().toString();
                if(getTitle.trim().equals("")) {
                    binding.etJudul.setError("Judul tidak boleh kosong");
                }
                else if(getAuthor.trim().equals("")) {
                    binding.etJudul.setError("Penulis tidak boleh kosong");
                }
                else if(getYear.trim().equals("")) {
                    binding.etJudul.setError("Tahun tidak boleh kosong");
                }
                else {
                    MyDatabaseHelper dB = new MyDatabaseHelper(InsertActivity.this);
                    long result = dB.addBook(getTitle, getAuthor, Integer.valueOf(getYear));

                    if(result == -1) {
                        Toast.makeText(InsertActivity.this, "Gagal Menambah Data", Toast.LENGTH_SHORT).show();
                        binding.etJudul.requestFocus();
                    }
                    else {
                        Toast.makeText(InsertActivity.this, "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }
            }
        });
    }
}