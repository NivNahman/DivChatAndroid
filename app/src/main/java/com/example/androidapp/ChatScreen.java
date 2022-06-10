package com.example.androidapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.androidapp.databinding.ActivityChatScreenBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChatScreen extends AppCompatActivity {

    List<Message> messages = new ArrayList<Message>();
    private ActivityChatScreenBinding binding;
    private AppDB db;
    private ChatDao chatDao;
    private MessageDao messageDao;
    private TextView name;
    private View back;
    private View send;
    private ArrayAdapter<Message> adapter;
    private EditText input;
    private int id;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        db= Room.databaseBuilder(getApplicationContext(), AppDB.class, "DivDB").allowMainThreadQueries().build();
//        chatDao = db.chatDao();
        chatDao = AppDB.getDb(getBaseContext()).chatDao();
        id = getIntent().getExtras().getInt("id");
        name = findViewById(R.id.textName);
        back = findViewById(R.id.imageBack);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactList.class);
            startActivity(intent);
        });
        name.setText(""+id+"");
       messageDao = AppDB.getDb(getBaseContext()).messageDao();

        send = findViewById(R.id.layoutSend);
        send.setOnClickListener(v -> {
            input = findViewById(R.id.inputMessage);
            Message message = new Message(input.getText().toString(),java.time.LocalDateTime.now().toString(),true);
            message.setId(id);
            messageDao.insert(message);
            // messages.add(message);
            onResume();
            input.setText("");
        });

//
        ListView rvMessages = findViewById(R.id.chatRecyclerView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        rvMessages.setAdapter(adapter);

//        for (Message message : messages){
//            if (message.getSent()){
//
//            }
//        }

//        send = findViewById(R.id.layoutSend);
//        send.setOnClickListener(v -> {
//            input = findViewById(R.id.inputMessage);
//            Message message = new Message(input.getText().toString(),java.time.LocalDateTime.now().toString(),true);
//            message.setId(id);
//            messageDao.insert(message);
//            // messages.add(message);
//            onResume();
//            input.setText("");
//        });








    }

    @Override
    protected void onResume(){
        super.onResume();
        messages.clear();
        messages.addAll(messageDao.get(id));
        adapter.notifyDataSetChanged();
    }


}