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

import static com.example.androidapp.MyApplication.context;

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

import com.example.androidapp.api.ContactAPI;
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
    private String server;
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

        lvContacts.addOnItemTouchListener(
                new RecyclerItemClickListener(this, lvContacts ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(context, ChatScreen.class);
                        intent.putExtra("contact_id", contacts.get(position).getId());
                        intent.putExtra("connectedUsername", UsernameID);
                        intent.putExtra("contact_server",contacts.get(position).getServer());
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
                server = contact_server.getText().toString();
                if(username.isEmpty() || nickname.isEmpty() || server.isEmpty()){
                    Toast.makeText(ContactList.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    String [] arr = server.split(":",2);
                    if (arr[0].equals("localhost")){
                        server = "10.0.2.2:" + arr[1];
                    }
                    ContactAPI contactAPI = new ContactAPI(server);
                    WebServiceAPI contactwebservice = contactAPI.getWebServiceAPI();
                    PostAPI postAPI = new PostAPI();
                    WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
                    Call<Void> call = contactwebservice.invitation(new Invitation(UsernameID,username,"10.0.2.2:7261"));
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
                                            dialog.dismiss();
                                            onResume();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call2, Throwable t) {
                                        Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(ContactList.this, "Failed to add the contact", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                //onResume();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        dialog.show();
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
                List<Contact> contacts = response.body();
                AppDB.clearRoomDB();
                for (Contact contact:contacts) {
                    String [] server = contact.getServer().split(":");
                    contact.setServer("10.0.2.2:" + server[1]);
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