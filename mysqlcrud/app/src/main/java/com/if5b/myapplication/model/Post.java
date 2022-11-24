package com.if5b.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Post {
    private String id, content, username;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("created_date")
    private String createdDate;
    @SerializedName("modified_date")
    private String modifiedDate;

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }
}
