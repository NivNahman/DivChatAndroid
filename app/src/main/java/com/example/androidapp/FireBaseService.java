package com.example.androidapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.androidapp.api.PostAPI;
import com.example.androidapp.api.WebServiceAPI;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FireBaseService extends FirebaseMessagingService {
    private ContactDao contactDao;
    private MessageDao messageDao;
    private String contact_id;
    private String ConnectedUsername;
    private String content;
    private List<Message> messages = new ArrayList<Message>();
    public FireBaseService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if(message.getNotification()!=null){
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            if(message.getNotification().getBody().toString().equals("Added you as a friend")){

            }
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
            PostAPI postAPI = new PostAPI();
            WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
            contact_id = message.getNotification().getTitle().toString();
            ConnectedUsername = message.getNotification().getImageUrl().toString();
            content = message.getNotification().getBody().toString();
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
                    Toast.makeText(FireBaseService.this, "FAILED !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1","divchat",importance);
            channel.setDescription("demo channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}