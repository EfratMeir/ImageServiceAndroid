package com.example.efrat.myapplication;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ImageService extends Service {



    public ImageService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        //put here code for the service
    }

    public int onStartCommand(Intent intent, int flag, int startId)
    {
        Toast.makeText(this, "Service starting...",
                Toast.LENGTH_SHORT).show();

        //tryout -  progress bar:
        final Context context = this;
        new Thread() {

            @Override
            public void run() {
                ProgressBarHandler pbHandler = new ProgressBarHandler(new ProgressBar(50));
                pbHandler.DisplayProgressBar(context);
            }
        }.start();
        //end tryout

        return START_STICKY;


    }

    public void onDestroy()
    {
        Toast.makeText(this, "Service ending...",
                Toast.LENGTH_SHORT).show();
    }
}
