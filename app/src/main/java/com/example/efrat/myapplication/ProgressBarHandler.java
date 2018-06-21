package com.example.efrat.myapplication;


import android.app.NotificationManager;
import android.content.Context;
//import android.support.v4.app.NotificationCompat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.app.NotificationChannel;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class ProgressBarHandler {
    private ProgressBar pb;

    /**
     * constructor
     *
     * @param pb - progress bar
     */
    public ProgressBarHandler(ProgressBar pb) {
        this.pb = pb;
    }


    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    public void DisplayProgressBar(Context context) {

        String channelId = "ImageServiceApp";
        CharSequence channelName = "ImageServiceAndroid";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ImageServiceApp");
        builder.setContentTitle("Picture Transfer")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Transfer in progress...")
                .setPriority(NotificationCompat.PRIORITY_LOW);

        while (!pb.isFinished()) {
            builder.setProgress(pb.getLimit(), pb.getTransferd(), false);
            builder.setContentText(pb.getTransferd() + "/" + pb.getLimit() + " images were transferred.");
            notificationManager.notify(0, builder.build());
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        builder.setProgress(0, 0, false);
        builder.setContentText("Transfer Completed. " + pb.getTransferd() + " Images Transferred.");
        notificationManager.notify(0, builder.build());

    }

    public void OneSent()
    {
        this.pb.Inc();
    }
}