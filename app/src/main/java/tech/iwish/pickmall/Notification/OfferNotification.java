package tech.iwish.pickmall.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.iid.FirebaseInstanceId;
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



//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"pickmall")
//                .setAutoCancel(true)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setSmallIcon(R.drawable.placeholder_icon);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.placeholder_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setChannelId("OFFER");


        NotificationManagerCompat managerCompat =  NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());







    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }


    //    @Override
//    public void onNewToken(String token) {
//        Log.d( "Refreshed token: " , token);
//
//        OfferNotification.this.sendRegistrationToServer(token);
//    }

//    @Override
//    public void onTokenRefresh() {
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
////        Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
//    }


}
