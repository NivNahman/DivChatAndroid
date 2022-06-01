package com.example.androidapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Chat.class}, version = 1)
public abstract class AppDB extends RoomDatabase{

    public abstract ChatDao chatDao();
}
