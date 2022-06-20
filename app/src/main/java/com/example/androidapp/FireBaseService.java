package com.example.androidapp;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
        if (message.getNotification() != null) {
//            PostAPI postAPI = new PostAPI();
//            WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
//            Call<String> call = webServiceAPI.getconnecteduser();
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    ConnectedUsername = response.body();
//                    Intent intent = new Intent(FireBaseService.this,ContactList.class);
//                    intent.putExtra("username", ConnectedUsername);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(FireBaseService.this,0,intent,FLAG_UPDATE_CURRENT);
//                    createNotificationChannel();
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(FireBaseService.this, "1")
//                            .setSmallIcon(R.drawable.ic_launcher_foreground)
//                            .setContentTitle(message.getNotification().getTitle())
//                            .setContentText(message.getNotification().getBody())
//                            .setContentIntent(pendingIntent)
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                            .setAutoCancel(true);
//                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(FireBaseService.this);
//                    notificationManager.notify(1, builder.build());
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    int x = 100;
//                }
//            });
            Intent intent = new Intent(this,ChatScreen.class);
            intent.putExtra("connectedUsername", "Niv");
            intent.putExtra("contact_id","Dvir");
            intent.putExtra("contact_server", "10.0.2.2:7261");
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,FLAG_UPDATE_CURRENT);
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
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