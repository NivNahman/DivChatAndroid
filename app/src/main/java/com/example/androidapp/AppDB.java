package com.example.androidapp;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.androidapp.api.PostAPI;
import com.example.androidapp.api.WebServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Database(entities = {Contact.class, Message.class}, version = 2)
public abstract class AppDB extends RoomDatabase{

    public abstract ContactDao contactDao();
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

    static void clearContactDao(){
        for (Contact chat:db.contactDao().index()) {
            db.contactDao().delete(chat);
        }
    }

    static void clearMessageDao(){
        for (Message message:db.messageDao().index()) {
            db.messageDao().delete(message);
        }
    }

    static void clearRoomDB(){
        clearContactDao();
        clearMessageDao();
    }

    static void updateContactDao(String username){
        clearContactDao();
            PostAPI postAPI = new PostAPI();
            WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
            Call<List<Contact>> call = webServiceAPI.getcontacts(username);
            call.enqueue(new retrofit2.Callback<List<Contact>>() {
                @Override
                public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                    List<Contact> contacts = response.body();
                    for (Contact contact : contacts) {
                        db.contactDao().insert(contact);
                        System.out.println(contact + "added");
                    }
                    //updateMessageDao(username);
                }

                @Override
                public void onFailure(Call<List<Contact>> call, Throwable t) {
                    System.out.println("connection failed");
                }
            });
    }

    static void updateMessageDao(String username){
        clearMessageDao();
        PostAPI postAPI = new PostAPI();
        WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
        for (Contact contact:db.contactDao().index()) {
            Call<List<Message>> call = webServiceAPI.getmessages(contact.getId(),username);
            call.enqueue(new retrofit2.Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    List<Message> messages = response.body();
                    for (Message message : messages) {
                        message.setContactID(contact.getId());
                        db.messageDao().insert(message);
                    }
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    System.out.println("connection failed");
                }
            });
        }
    }

    static void updateRoomDB(String username){
        updateContactDao(username);
        updateMessageDao(username);
    }



}
