package com.example.efrat.myapplication;

import android.Manifest;
import android.graphics.Camera;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import java.io.File;

public class DirectoryHandler {
private int numOfPics = 0;

    public File[] getImages()
    {
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+ "/Camera");
        if(dcim == null)
        {
            return null;
        }
        File[] pics = dcim.listFiles();
        numOfPics = pics.length;
        return pics;
    }

    public int getNumPics()
    {
        return this.numOfPics;
    }
}
