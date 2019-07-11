package com.eywa_kitchen.Eywa_client.Controllers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.eywa_kitchen.Eywa_client.MainActivity;
import com.eywa_kitchen.Eywa_client.R;

import static android.support.v4.app.NotificationCompat.PRIORITY_HIGH;

public class NotificationController {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    public static void SendNotif_gas(Context context, NotificationManager notificationManager) {
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Смарт-центр EYWA")
                        .setContentText("Обнаружена утечка газа на кухне!")
                        .setPriority(PRIORITY_HIGH)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(1, notificationBuilder.build());
    }

    public static void SendNotif_fire(Context context, NotificationManager notificationManager) {
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Смарт-центр EYWA")
                        .setContentText("Обнаружено возгорание на кухне!")
                        .setPriority(PRIORITY_HIGH)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(2, notificationBuilder.build());
    }

    private static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
