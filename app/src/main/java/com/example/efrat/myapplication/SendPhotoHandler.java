package com.example.efrat.myapplication;

import android.content.Context;

public class SendPhotoHandler {
    private ProgressBarHandler progressBarHandler;

    public void Start(int numOfPics, Context context)
    {
        final Context c = context;
        this.progressBarHandler = new ProgressBarHandler((new ProgressBar(numOfPics)));
        //tryout -  progress bar:
        new Thread() {

            @Override
            public void run() {

                progressBarHandler.DisplayProgressBar(c);
            }
        }.start();
        //end tryout
    }

    public void oneSent()
    {
        progressBarHandler.OneSent();
    }
}
