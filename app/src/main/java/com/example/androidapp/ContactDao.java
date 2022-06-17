
package com.example.androidapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact_table")
    List<Contact> index();

    @Query("SELECT * FROM Contact_table WHERE id = :id")
    Contact get(int id);

    @Insert
    void insert(Contact... users);

    @Update
    void update(Contact... users);

    @Delete
    void delete(Contact... users);
}



