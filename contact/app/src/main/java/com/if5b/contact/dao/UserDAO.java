package com.if5b.contact.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.if5b.contact.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void addUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Update
    int updateUser(User user);

    @Query("DELETE FROM users WHERE id = :userId")
    int deleteUser(int userId);

}
