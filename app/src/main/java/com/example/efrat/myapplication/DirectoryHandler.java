package com.example.efrat.myapplication;

import android.Manifest;
import android.graphics.Camera;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import java.io.File;

public class DirectoryHandler {

    public File[] getImages()
    {
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+ "/Camera");
        if(dcim == null)
        {
            return null;
        }
        File[] pics = dcim.listFiles();

        return pics;
    }
}
