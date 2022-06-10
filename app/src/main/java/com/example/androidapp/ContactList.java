//package com.example.androidapp;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.room.Room;
//
//import com.example.androidapp.databinding.ActivityContactListBinding;
//
//public class ContactList extends AppCompatActivity {
//    //List<String> contacts = new ArrayList<String>();
//    private ActivityContactListBinding binding;
//    private AppDB db;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
////        contacts.add("Niv");
////        contacts.add("Dvir");
////        contacts.add("Omer");
//        super.onCreate(savedInstanceState);
//        binding = ActivityContactListBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        db= Room.databaseBuilder(getApplicationContext(), AppDB.class, "DivDB").allowMainThreadQueries().build();
//       // Button addContact = findViewById(R.id.addContactBtn);
////        addContact.setOnClickListener(view -> {
////
////        });
//    }
//
//
//}


package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.androidapp.databinding.ActivityContactListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {
    List<Chat> chats = new ArrayList<Chat>();

    private ActivityContactListBinding binding;
    private AppDB db;
    private ChatDao chatDao;
    private ArrayAdapter<Chat> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



//        chats.add(new Chat(new Contact("a","a","a","a","a")));
//        chats.add(new Chat(new Contact("b","b","b","b","b")));
//        chats.add(new Chat(new Contact("c","c","c","c","c")));



        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Button addContact = findViewById(R.id.addContactBtn);
//        addContact.setOnClickListener(view -> {
//
//        });

//        db= Room.databaseBuilder(getApplicationContext(), AppDB.class, "DivDB").allowMainThreadQueries().build();
        chatDao = AppDB.getDb(getBaseContext()).chatDao();
        //contacts = postDao.index().get(0).getChats();

        FloatingActionButton addContactBtn = findViewById(R.id.addContactBtn);
        addContactBtn.setOnClickListener(view -> {
            Contact contact = new Contact("a","a","a","a");
            Chat newChat = new Chat("a");
            chatDao.insert(newChat);
//            chats.clear();
//            chats.addAll(chatDao.index());
//            adapter.notifyDataSetChanged();
            onResume();
        });

        ListView lvContacts = findViewById(R.id.ContactList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chats);
        lvContacts.setAdapter(adapter);



        lvContacts.setOnItemLongClickListener((adapterView,view,i,l)->{
            Chat chat = chats.remove(i);
            chatDao.delete(chat);
            adapter.notifyDataSetChanged();
            return true;
        });

        lvContacts.setOnItemClickListener((adapterView,view,i,l)->{
            Intent intent = new Intent(this, ChatScreen.class);
            intent.putExtra("id",chats.get(i).getId());
            startActivity(intent);
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        chats.clear();
        chats.addAll(chatDao.index());
        adapter.notifyDataSetChanged();
    }


}