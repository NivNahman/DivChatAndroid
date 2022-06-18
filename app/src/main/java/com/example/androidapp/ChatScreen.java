package com.example.androidapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.androidapp.api.PostAPI;
import com.example.androidapp.api.WebServiceAPI;
import com.example.androidapp.databinding.ActivityChatScreenBinding;
import com.example.androidapp.databinding.ItemContainerSentMessageBinding;
import com.example.androidapp.databinding.ItemContainerRecivedMessageBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatScreen extends AppCompatActivity {

    static public int id = 9;
    private List<Message> messages = new ArrayList<Message>();
    private ActivityChatScreenBinding binding;
    private MessageDao messageDao;
    private TextView name;
    private View back;
    private View send;
    private EditText input;
    private String contact_id;
    private ChatAdapter chatAdapter = new ChatAdapter(messages);
    private String ConnectedUsername;
    private String content;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contact_id = getIntent().getExtras().getString("contact_id");
        ConnectedUsername = getIntent().getExtras().getString("connectedUsername");
        name = findViewById(R.id.textName);
        back = findViewById(R.id.imageBack);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactList.class);
            startActivity(intent);
        });
        name.setText(""+contact_id+"");
        messageDao = AppDB.getDb(getBaseContext()).messageDao();
        binding.chatRecyclerView.setAdapter(chatAdapter);
        input = findViewById(R.id.inputMessage);
        send = findViewById(R.id.layoutSend);
        send.setOnClickListener(v -> {
//            input = findViewById(R.id.inputMessage);
//            Message message = new Message(id, input.getText().toString(),java.time.LocalDateTime.now().toString(),true);
//            message.setContactID(contact_id);
//            messageDao.insert(message);
            content = input.getText().toString();
            new_message(contact_id,ConnectedUsername, content);
            // messages.add(message);
            //onResume();
            input.setText("");

        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume(){
        super.onResume();
        //messages.clear();
        messages.addAll(messageDao.get(contact_id));
        chatAdapter.notifyDataSetChanged();
        binding.chatRecyclerView.setVisibility(View.VISIBLE);
    }

    public void new_message(String contact_id,String ConnectedUsername, String content){
        PostAPI postAPI = new PostAPI();
        WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
        Call<Void> call = webServiceAPI.transfer(new transfer(ConnectedUsername, contact_id, content));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.raw().code() == 201) {
                    Call<Void> call2 = webServiceAPI.newmessage(contact_id,new Addmsg(ConnectedUsername,contact_id,content));
                    call2.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call2, Response<Void> response2) {
                            for (Message m : messages) {
                                messageDao.delete(m);
                            }
                            messages.clear();
                            Call<List<Message>> call3 = webServiceAPI.getmessages(contact_id, ConnectedUsername);
                            call3.enqueue(new retrofit2.Callback<List<Message>>() {
                                @Override
                                public void onResponse(Call<List<Message>> call3, Response<List<Message>> response3) {
                                    List<Message> messages2 = response3.body();
                                    for (Message message : messages2) {
                                        message.setContactID(contact_id);
                                        messageDao.insert(message);
                                    }

                                    onResume();
                                }

                                @Override
                                public void onFailure(Call<List<Message>> call3, Throwable t) {
                                    System.out.println("connection failed");
                                }
                            });
                            //onResume();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(ChatScreen.this, "FAILED !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                onResume();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}