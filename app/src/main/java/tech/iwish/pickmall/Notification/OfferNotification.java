package tech.iwish.pickmall.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.SplashActivity;

public class OfferNotification extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        notification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notification(String title , String body){

        Intent intent = new Intent(this , SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"pickmall")
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.placeholder_icon);



        NotificationManagerCompat managerCompat =  NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());




        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


    }



}
