package com.if5b.myapplication.retrofit;

import com.if5b.myapplication.model.ValueNoData;
import com.if5b.myapplication.model.ValueWithData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiEndpoint {
    // Login user
    @POST("loginUser")
    @FormUrlEncoded
    Call<ValueNoData> login(@Field("key") String key,
                            @Field("username") String username,
                            @Field("password") String password);

    // Register user
    @POST("registerUser")
    @FormUrlEncoded
    Call<ValueNoData> register(@Field("key") String key,
                            @Field("username") String username,
                            @Field("password") String password);


    // End Point get all Post
    @POST("getAllPost")
    @FormUrlEncoded
    Call<ValueWithData> getAllPost(@Field("key") String key);

    // End Point insert Post
    @POST("insertPost")
    @FormUrlEncoded
    Call<ValueNoData> insertPost(@Field("key") String key,
                                   @Field("username") String username,
                                   @Field("content") String content);

    // End Point update Post
    @POST("updatePost")
    @FormUrlEncoded
    Call<ValueNoData> updatePost(@Field("key") String key,
                                   @Field("id") int id,
                                   @Field("content") String content);

    // End Point delete Post
    @POST("deletePost")
    @FormUrlEncoded
    Call<ValueNoData> deletePost(@Field("key") String key,
                                   @Field("id") int id);
}
