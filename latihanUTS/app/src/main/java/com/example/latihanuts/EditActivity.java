package com.example.latihanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.latihanuts.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {
    private String intentId, intentIsbn, intentJudul, intentKategori, intentDeskripsi, intentHarga;
    int intentPosition;
    ActivityEditBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intentId = getIntent().getStringExtra("keyId");
        intentIsbn = getIntent().getStringExtra("keyIsbn");
        intentJudul = getIntent().getStringExtra("keyJudul");
        intentDeskripsi = getIntent().getStringExtra("keyDeskripsi");
        intentKategori = getIntent().getStringExtra("keyKategori");
        intentHarga = getIntent().getStringExtra("keyHarga");
        intentPosition = getIntent().getExtras().getInt("keyPosition", -1);

        binding.etISBN.setText(intentIsbn);
        binding.etJudul.setText(intentJudul);
        binding.etDeskripsi.setText(intentDeskripsi);
        binding.etKategori.setText(intentKategori);
        binding.etHarga.setText(intentHarga);

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
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
                    Book book = new Book(intentId, getIsbn, getJudul, getKategori, getDeskripsi, getHarga);
                    DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);
                    long result = databaseHelper.editBookById(book);

                    if(result == -1) {
                        Toast.makeText(EditActivity.this, "Gagal Menambah Data", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditActivity.this, "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
                        MainActivity.dataPosition = intentPosition;
                        finish();
                    }
                }
            }
        });
    }
}