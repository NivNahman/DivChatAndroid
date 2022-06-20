//package com.example.androidapp;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.room.Room;
//
//import com.example.androidapp.api.PostAPI;
//import com.example.androidapp.api.WebServiceAPI;
//import com.example.androidapp.databinding.ActivityContactListBinding;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ContactList extends AppCompatActivity {
//    List<Contact> contacts = new ArrayList<Contact>();
//    private ActivityContactListBinding binding;
//    //private AppDB db;
//    private ContactDao contactDao;
//    private MessageDao messageDao;
//    private ArrayAdapter<Contact> adapter;
//    private String UsernameID;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        binding = ActivityContactListBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        contactDao = AppDB.getDb(getBaseContext()).contactDao();
//        messageDao = AppDB.getDb(getBaseContext()).messageDao();
//        UsernameID = getIntent().getExtras().getString("username");
//        get_contacts(UsernameID);
//        FloatingActionButton openDialog = findViewById(R.id.addContactBtn);
//            openDialog.setOnClickListener(v -> {
//                showCustomDialog();
//            });
//
//        ListView lvContacts = findViewById(R.id.ContactList);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
//        lvContacts.setAdapter(adapter);
//
//
//        lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
//            Contact chat = contacts.remove(i);
//            contactDao.delete(chat);
//            adapter.notifyDataSetChanged();
//            return true;
//        });
//
//        lvContacts.setOnItemClickListener((adapterView, view, i, l) -> {
//            Intent intent = new Intent(this, ChatScreen.class);
//            intent.putExtra("contact_id", contacts.get(i).getId());
//            intent.putExtra("connectedUsername", UsernameID);
//            startActivity(intent);
//        });
//
//    }
//    void showCustomDialog(){
//        final Dialog dialog = new Dialog(ContactList.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.custom_add_contact);
//
//        final EditText contact_username = dialog.findViewById(R.id.contact_username);
//        final EditText contact_name = dialog.findViewById(R.id.contact_nickname);
//        final EditText contact_server = dialog.findViewById(R.id.contact_server);
//        Button add_contact_submitBtn = dialog.findViewById(R.id.add_contact_submitBtn);
//        add_contact_submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = contact_username.getText().toString();
//                String nickname = contact_name.getText().toString();
//                String server = contact_server.getText().toString();
//                if(username.isEmpty() || nickname.isEmpty() || server.isEmpty()){
//                    Toast.makeText(ContactList.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    PostAPI postAPI = new PostAPI();
//                    WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
//                    Call<Void> call = webServiceAPI.invitation(new Invitation(UsernameID,username,server));
//                    call.enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            if(response.raw().code() == 201) {
//                                Call<Void> call2 = webServiceAPI.addcontact(new AddContact(UsernameID,username,nickname,server));
//                                call2.enqueue(new Callback<Void>() {
//                                    @Override
//                                    public void onResponse(Call<Void> call2, Response<Void> response2) {
//                                        if(response2.raw().code() == 200){
//                                            get_contacts(UsernameID);
//                                            dialog.dismiss();
//                                        }
//                                        else {
//                                            Toast.makeText(ContactList.this, "Failed to add the contact", Toast.LENGTH_SHORT).show();
//                                            onResume();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Void> call2, Throwable t) {
//                                        Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                            else {
//                                Toast.makeText(ContactList.this, "Failed to add the contact", Toast.LENGTH_SHORT).show();
//                                onResume();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//
//                        }
//                    });
//                }
//            }
//        });
//
//        dialog.show();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        contacts.clear();
//        contacts.addAll(contactDao.index());
//        adapter.notifyDataSetChanged();
//    }
//
//    public void get_contacts(String username) {
//        PostAPI postAPI = new PostAPI();
//        WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
//        Call<List<Contact>> call = webServiceAPI.getcontacts(username);
//        call.enqueue(new Callback<List<Contact>>() {
//            @Override
//            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
//                List<Contact> contacts = response.body();
//                AppDB.clearRoomDB();
//                for (Contact contact:contacts) {
//                    contactDao.insert(contact);
//                    Call<List<Message>> call2 = webServiceAPI.getmessages(contact.getId(), username);
//                    call2.enqueue(new Callback<List<Message>>() {
//                        @Override
//                        public void onResponse(Call<List<Message>> call2, Response<List<Message>> response2) {
//                            List<Message> messages = response2.body();
//                            for (Message message:messages) {
//                                message.setContactID(contact.getId());
//                                messageDao.insert(message);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Message>> call2, Throwable t) {
//                            Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                onResume();
//            }
//
//            @Override
//            public void onFailure(Call<List<Contact>> call, Throwable t) {
//                Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}


package com.example.androidapp;

import static com.example.androidapp.MyApplication.context;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.androidapp.api.PostAPI;
import com.example.androidapp.api.WebServiceAPI;
import com.example.androidapp.databinding.ActivityContactListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactList extends AppCompatActivity {
    List<Contact> contacts = new ArrayList<Contact>();
    private ActivityContactListBinding binding;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private ContactAdapter adapter = new ContactAdapter(contacts);
    private String UsernameID;
    private RecyclerView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactDao = AppDB.getDb(getBaseContext()).contactDao();
        messageDao = AppDB.getDb(getBaseContext()).messageDao();
        UsernameID = getIntent().getExtras().getString("username");
        get_contacts(UsernameID);
        FloatingActionButton openDialog = findViewById(R.id.addContactBtn);
        openDialog.setOnClickListener(v -> {
            showCustomDialog();
        });

        lvContacts = binding.ContactList;
        lvContacts.setAdapter(adapter);

//        binding.ContactList.setAdapter(adapter);

        lvContacts.addOnItemTouchListener(
                new RecyclerItemClickListener(this, lvContacts ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(context, ChatScreen.class);
                        intent.putExtra("contact_id", contacts.get(position).getId());
                        intent.putExtra("connectedUsername", UsernameID);
                        startActivity(intent);
                    }

                })
        );


    }
    void showCustomDialog(){
        final Dialog dialog = new Dialog(ContactList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_add_contact);

        final EditText contact_username = dialog.findViewById(R.id.contact_username);
        final EditText contact_name = dialog.findViewById(R.id.contact_nickname);
        final EditText contact_server = dialog.findViewById(R.id.contact_server);
        Button add_contact_submitBtn = dialog.findViewById(R.id.add_contact_submitBtn);
        add_contact_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = contact_username.getText().toString();
                String nickname = contact_name.getText().toString();
                String server = contact_server.getText().toString();
                if(username.isEmpty() || nickname.isEmpty() || server.isEmpty()){
                    Toast.makeText(ContactList.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    PostAPI postAPI = new PostAPI();
                    WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
                    Call<Void> call = webServiceAPI.invitation(new Invitation(UsernameID,username,server));
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.raw().code() == 201) {
                                Call<Void> call2 = webServiceAPI.addcontact(new AddContact(UsernameID,username,nickname,server));
                                call2.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call2, Response<Void> response2) {
                                        if(response2.raw().code() == 200){
                                            get_contacts(UsernameID);
                                            dialog.dismiss();
                                        }
                                        else {
                                            Toast.makeText(ContactList.this, "Failed to add the contact", Toast.LENGTH_SHORT).show();
                                            onResume();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call2, Throwable t) {
                                        Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(ContactList.this, "Failed to add the contact", Toast.LENGTH_SHORT).show();
                                onResume();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }
        });

        dialog.show();
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.index());
        adapter.notifyDataSetChanged();
        lvContacts.setVisibility(View.VISIBLE);
    }

    public void get_contacts(String username) {
        PostAPI postAPI = new PostAPI();
        WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
        Call<List<Contact>> call = webServiceAPI.getcontacts(username);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                AppDB.clearRoomDB();
                for (Contact contact:contacts) {
                    contactDao.insert(contact);
                    Call<List<Message>> call2 = webServiceAPI.getmessages(contact.getId(), username);
                    call2.enqueue(new Callback<List<Message>>() {
                        @Override
                        public void onResponse(Call<List<Message>> call2, Response<List<Message>> response2) {
                            List<Message> messages = response2.body();
                            for (Message message:messages) {
                                message.setContactID(contact.getId());
                                messageDao.insert(message);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Message>> call2, Throwable t) {
                            Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                onResume();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}