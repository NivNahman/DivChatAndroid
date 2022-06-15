package com.example.androidapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Chat.class, Message.class}, version = 2)
public abstract class AppDB extends RoomDatabase{

    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();
    private static AppDB db;

    static AppDB getDb(Context context){
        if (db == null) {
                    db = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "DivDB")
                            .allowMainThreadQueries()
                            .build();
        }
        return db;
    }
}
