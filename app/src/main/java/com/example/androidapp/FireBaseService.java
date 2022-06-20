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
            String [] arr = message.getNotification().getTitle().split(";");
            Intent intent;
            if (arr[0].equals("transfer")) {
                intent = new Intent(this,ChatScreen.class);
                intent.putExtra("connectedUsername", arr[1]);
                intent.putExtra("contact_id",arr[2]);
                arr[3] = "10.0.2.2:" + arr[3].split(":")[1];
                intent.putExtra("contact_server", arr[3]);
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,FLAG_UPDATE_CURRENT);
                createNotificationChannel();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                        .setSmallIcon(R.drawable.ic_send)
                        .setContentTitle("Message from: " + arr[2])
                        .setContentText(message.getNotification().getBody())
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(1, builder.build());
            }
            else{
                intent = new Intent(this,ContactList.class);
                intent.putExtra("username", arr[1]);
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,FLAG_UPDATE_CURRENT);
                createNotificationChannel();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                        .setSmallIcon(R.drawable.ic_add_friend)
                        .setContentTitle("You have a new friend")
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(1, builder.build());
            }
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