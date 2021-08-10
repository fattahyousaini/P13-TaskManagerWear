package com.myapplicationdev.android.p13_taskmanagerwear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;

public class ScheduledNotificationReceiver extends BroadcastReceiver {
    int reqCode = 12345;
    ArrayList<Task> tasksList;


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }
        String task = intent.getStringExtra("task");
        String description = intent.getStringExtra("description");
        int id = intent.getIntExtra("id", 0);

        String content = task + "\n" + description;

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity (context, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Launch Task Manager",
                pIntent).build();

        Intent intentReply = new Intent(context,
                ReplyActivity.class);

        intentReply.putExtra("id",id);
        PendingIntent pendingIntentReply = PendingIntent.getActivity
                (context, 0, intentReply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String [] {"Completed"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntentReply)
                .addRemoteInput(ri)
                .build();


        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        builder.setContentTitle("Task");
        builder.setContentText(content);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setStyle(bigText);
        builder.setAutoCancel(true);
        builder.setLights(Color.GREEN, 400,500);
        builder.setVibrate(new long[] {0, 1000, 200, 1000});
        builder.extend(extender);
        Notification n = builder.build();
        // Attach the action for Wear notification created above

        notificationManager.notify(123, n);
    }
}