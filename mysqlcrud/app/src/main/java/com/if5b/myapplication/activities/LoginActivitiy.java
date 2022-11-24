package com.if5b.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.if5b.myapplication.databinding.ActivityLoginBinding;
import com.if5b.myapplication.model.ValueNoData;
import com.if5b.myapplication.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivitiy extends AppCompatActivity {
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivitiy.this, RegisterActivity.class));
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isLogin = true;
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                if(TextUtils.isEmpty(username)) {
                    isLogin = false;
                    binding.etUsername.setError("Silahkan isi username");
                }
                if(TextUtils.isEmpty(password)) {
                    isLogin = false;
                    binding.etPassword.setError("Silahkan isi password");
                }

                if(isLogin) {
                    sendLoginRequest(username, password);
                }
            }
        });
    }

    private void sendLoginRequest(String username, String password) {
        ApiService.endpoint().login("dirumahaja", username, password)
                .enqueue(new Callback<ValueNoData>() {
                    @Override
                    public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                        if (response.code() == 200) {
                            if(response.body().getSuccess() == 1) {
                                Toast.makeText(LoginActivitiy.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivitiy.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivitiy.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivitiy.this, "Response : " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ValueNoData> call, Throwable t) {
                        System.out.println("Retrofit Error : " + t.getMessage());
                    }
                });
    }
}