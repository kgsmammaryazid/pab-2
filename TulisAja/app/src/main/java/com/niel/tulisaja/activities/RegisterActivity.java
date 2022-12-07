package com.niel.tulisaja.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.niel.tulisaja.databinding.ActivityRegisterBinding;
import com.niel.tulisaja.models.ValueNoData;
import com.niel.tulisaja.services.APIService;
import com.niel.tulisaja.services.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                String konfirmasipassword = binding.etKonfirmasiPassword.getText().toString();

                boolean bolehRegister = true;
                if(TextUtils.isEmpty(username)) {
                    bolehRegister = false;
                    binding.etUsername.setError("Username tidak boleh kosong!");
                }
                if(TextUtils.isEmpty(password)) {
                    bolehRegister = false;
                    binding.etPassword.setError("Password tidak boleh kosong!");
                }
                if(TextUtils.isEmpty(konfirmasipassword)) {
                    bolehRegister = false;
                    binding.etKonfirmasiPassword.setError("Konfirmasi password tidak boleh kosong!");
                }
                if(!password.equals(konfirmasipassword)) {
                    bolehRegister = false;
                    binding.etKonfirmasiPassword.setError("Konfirmasi Password tidak sama dengan password!");
                }
                if(password.length() < 6) {
                    bolehRegister = false;
                    binding.etPassword.setError("Password minimal 6 karakter");
                }
                if(bolehRegister) {
                    register(username, password);
                }
            }
        });
    }

    private void register(String username, String password) {
        showProgressBar();
        APIService api = Utilities.getRetrofit().create(APIService.class);
        api.registerUser(Utilities.TULIS_AJA_API_KEY, username, password).enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                if(response.code() == 200) {
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    if(success == 1) {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Response Code : " + response.code(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(RegisterActivity.this,
                        "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }
}