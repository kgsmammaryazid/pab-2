package com.example.latihanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.latihanuts.databinding.ActivityInsertBinding;

public class InsertActivity extends AppCompatActivity {

    ActivityInsertBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getIsbn = binding.etISBN.getText().toString();
                String getJudul = binding.etJudul.getText().toString();
                String getDeskripsi = binding.etDeskripsi.getText().toString();
                String getKategori = binding.etKategori.getText().toString();
                String getHarga = binding.etHarga.getText().toString();
                if(getIsbn.trim().equals("")) {
                    binding.etISBN.setError("ISBN tidak boleh kosong");
                }
                else if(getJudul.trim().equals("")) {
                    binding.etJudul.setError("Judul tidak boleh kosong");
                }
                else if(getDeskripsi.trim().equals("")) {
                    binding.etDeskripsi.setError("Deskripsi tidak boleh kosong");
                }
                else if(getKategori.trim().equals("")) {
                    binding.etKategori.setError("Kategori tidak boleh kosong");
                }
                else if(getHarga.trim().equals("")) {
                    binding.etHarga.setError("Harga tidak boleh kosong");
                }
                else {
                    Book book = new Book(getIsbn, getJudul, getKategori, getDeskripsi, getHarga);
                    DatabaseHelper databaseHelper = new DatabaseHelper(InsertActivity.this);
                    long result = databaseHelper.insertBook(book);

                    if(result == -1) {
                        Toast.makeText(InsertActivity.this, "Gagal Menambah Data", Toast.LENGTH_SHORT).show();
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