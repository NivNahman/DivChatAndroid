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
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.androidapp.api.PostAPI;
import com.example.androidapp.api.WebServiceAPI;
import com.example.androidapp.databinding.ActivityContactListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactList extends AppCompatActivity {
    List<Contact> contacts = new ArrayList<Contact>();
    //private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    private ActivityContactListBinding binding;
    //private AppDB db;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private ArrayAdapter<Contact> adapter;
    private String UsernameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Button addContact = findViewById(R.id.addContactBtn);
//        addContact.setOnClickListener(view -> {
//
//        });

//        db= Room.databaseBuilder(getApplicationContext(), AppDB.class, "DivDB").allowMainThreadQueries().build();
        contactDao = AppDB.getDb(getBaseContext()).contactDao();
        messageDao = AppDB.getDb(getBaseContext()).messageDao();
        //contacts = postDao.index().get(0).getChats();
        UsernameID = getIntent().getExtras().getString("username");
        //String trying = "?connecteduser=" + UsernameID;
        //String urlEncoded = Uri.encode(trying, ALLOWED_URI_CHARS);
        get_contacts(UsernameID);
//        AppDB.updateRoomDB(UsernameID);
//        System.out.println("========================================");
//        for (Contact contact : contactDao.index()) {
//            System.out.println(contact);
//        }
//        System.out.println("========================================");
        FloatingActionButton addContactBtn = findViewById(R.id.addContactBtn);
        addContactBtn.setOnClickListener(view -> {
            //Contact contact = new Contact("a","a","a","a");
            //Chat newChat = new Chat("a");
            //chatDao.insert(newChat);
//            chats.clear();
//            chats.addAll(chatDao.index());
//            adapter.notifyDataSetChanged();
            onResume();
        });

        ListView lvContacts = findViewById(R.id.ContactList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        lvContacts.setAdapter(adapter);


        lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Contact chat = contacts.remove(i);
            contactDao.delete(chat);
            adapter.notifyDataSetChanged();
            return true;
        });

        lvContacts.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, ChatScreen.class);
            intent.putExtra("contact_id", contacts.get(i).getId());
            intent.putExtra("connectedUsername", UsernameID);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.index());
        adapter.notifyDataSetChanged();
    }

    public void get_contacts(String username) {
        PostAPI postAPI = new PostAPI();
        WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
        Call<List<Contact>> call = webServiceAPI.getcontacts(username);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                //7261
                //Toast.makeText(LoginPage.this, "SUCCESS !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                List<Contact> contacts = response.body();
                AppDB.clearRoomDB();
                for (Contact contact:contacts) {
                    contactDao.insert(contact);
                    Call<List<Message>> call2 = webServiceAPI.getmessages(contact.getId(), username);
                    call2.enqueue(new Callback<List<Message>>() {
                        @Override
                        public void onResponse(Call<List<Message>> call2, Response<List<Message>> response2) {
                            //7261
                            //Toast.makeText(LoginPage.this, "SUCCESS !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                            List<Message> messages = response2.body();
                            for (Message message:messages) {
                                message.setContactID(contact.getId());
                                messageDao.insert(message);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Message>> call2, Throwable t) {
                            Toast.makeText(ContactList.this, "FAILED !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                onResume();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Toast.makeText(ContactList.this, "FAILED !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}