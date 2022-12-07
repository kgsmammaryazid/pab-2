package com.niel.tulisaja.services;

import com.niel.tulisaja.models.Post;
import com.niel.tulisaja.models.ValueData;
import com.niel.tulisaja.models.ValueNoData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("loginUser")
    Call<ValueNoData> loginUser(@Field("key") String key,
                                @Field("username") String username,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("registerUser")
    Call<ValueNoData> registerUser(@Field("key") String key,
                     @Field("username") String username,
                     @Field("password") String password);

    @FormUrlEncoded
    @POST("getAllPost")
    Call<ValueData<Post>> getAllPost(@Field("key") String key);

    @FormUrlEncoded
    @POST("insertPost")
    Call<ValueNoData> insertPost(@Field("key") String key,
                      @Field("username") String username,
                      @Field("content") String content);

    @FormUrlEncoded
    @POST("updatePost")
    Call<ValueNoData> updatePost(@Field("key") String key,
                      @Field("id") int id,
                      @Field("content") String content);

    @FormUrlEncoded
    @POST("deletePost")
    Call<ValueNoData> deletePost(@Field("key") String key,
                      @Field("id") int id);

}
