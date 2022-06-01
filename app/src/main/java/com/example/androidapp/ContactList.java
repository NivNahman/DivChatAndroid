package com.example.androidapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.androidapp.databinding.ActivityContactListBinding;

public class ContactList extends AppCompatActivity {
    //List<String> contacts = new ArrayList<String>();
    private ActivityContactListBinding binding;
    private AppDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        contacts.add("Niv");
//        contacts.add("Dvir");
//        contacts.add("Omer");
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db= Room.databaseBuilder(getApplicationContext(), AppDB.class, "DivDB").allowMainThreadQueries().build();
       // Button addContact = findViewById(R.id.addContactBtn);
//        addContact.setOnClickListener(view -> {
//
//        });
    }


}