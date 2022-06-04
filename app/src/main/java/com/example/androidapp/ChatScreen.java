package com.example.androidapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.androidapp.databinding.ActivityChatScreenBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChatScreen extends AppCompatActivity {

    private ActivityChatScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        db= Room.databaseBuilder(getApplicationContext(), AppDB.class, "DivDB").allowMainThreadQueries().build();
//        chatDao = db.chatDao();
//        //contacts = postDao.index().get(0).getChats();
//
//        FloatingActionButton addContactBtn = findViewById(R.id.addContactBtn);
//        addContactBtn.setOnClickListener(view -> {
//            Contact contact = new Contact("a","a","a","a");
//            Chat newChat = new Chat("a");
//            chatDao.insert(newChat);
////            chats.clear();
////            chats.addAll(chatDao.index());
////            adapter.notifyDataSetChanged();
//            onResume();
//        });
//
//        ListView lvContacts = findViewById(R.id.ContactList);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chats);
//        lvContacts.setAdapter(adapter);
//
//
//
//        lvContacts.setOnItemLongClickListener((adapterView,view,i,l)->{
//            Chat chat = chats.remove(i);
//            chatDao.delete(chat);
//            adapter.notifyDataSetChanged();
//            return true;
//        });






    }

//    @Override
//    protected void onResume(){
//
//    }


}