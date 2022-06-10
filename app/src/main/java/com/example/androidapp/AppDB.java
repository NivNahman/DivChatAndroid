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

//    static Migration migration = new Migration(1,2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("");
//        }
//    };

    static AppDB getDb(Context context){
        if (db == null) {
//            Synchronized (AppDB.class){
                    db = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "DivDB")
                            .allowMainThreadQueries()
//                            .addMigrations(migration)
                            .build();
//        }
        }
        return db;
    }
}
