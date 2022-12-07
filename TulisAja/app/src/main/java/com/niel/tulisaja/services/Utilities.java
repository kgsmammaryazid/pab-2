package com.niel.tulisaja.services;

import java.net.URI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utilities {
    public static final String TULIS_AJA_BASE_URL = "https://restapi.mdp.ac.id/tulisaja/";
    public static final String TULIS_AJA_API_KEY = "dirumahaja";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TULIS_AJA_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
