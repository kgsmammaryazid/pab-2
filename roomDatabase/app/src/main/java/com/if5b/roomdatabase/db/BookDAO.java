package com.if5b.roomdatabase.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insertBook(Book book);
    @Query("Select * From book")
    List<Book> getAllBooks();

    @Update
    int updateBook(Book book);

    @Query("DELETE FROM book WHERE id = :bookId")
    int deleteBook(int bookId);
}
