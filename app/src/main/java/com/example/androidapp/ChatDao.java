
package com.example.androidapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM Chat")
    List<Chat> index();

    @Query("SELECT * FROM Chat WHERE id = :id")
    Contact get(int id);

    @Insert
    void insert(Chat... users);

    @Update
    void update(Chat... users);

    @Delete
    void delete(Chat... users);
}



