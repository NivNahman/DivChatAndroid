package com.example.androidapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message_table")
    List<Message> index();

    @Query("SELECT * FROM message_table WHERE contactID = :id")
    List<Message> get(String id);

    @Insert
    void insert(Message... messages);

    @Update
    void update(Message...messages);

    @Delete
    void delete(Message... messages);
}
