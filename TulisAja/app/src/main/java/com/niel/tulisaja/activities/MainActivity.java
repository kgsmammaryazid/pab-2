package com.niel.tulisaja.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.niel.tulisaja.adapters.PostViewAdapter;
import com.niel.tulisaja.databinding.ActivityMainBinding;
import com.niel.tulisaja.models.Post;
import com.niel.tulisaja.models.ValueData;
import com.niel.tulisaja.services.APIService;
import com.niel.tulisaja.services.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PostViewAdapter postViewAdapter;
    private List<Post> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        postViewAdapter = new PostViewAdapter();
        binding.rvPost.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPost.setAdapter(postViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllPost();
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void getAllPost() {
        showProgressBar();
        APIService api = Utilities.getRetrofit().create(APIService.class);
        api.getAllPost(Utilities.TULIS_AJA_API_KEY).enqueue(new Callback<ValueData<Post>>() {
            @Override
            public void onResponse(Call<ValueData<Post>> call, Response<ValueData<Post>> response) {
                if (response.code() == 200) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        data = response.body().getData();
                        postViewAdapter.setData(data);
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Response Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<ValueData<Post>> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(MainActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}