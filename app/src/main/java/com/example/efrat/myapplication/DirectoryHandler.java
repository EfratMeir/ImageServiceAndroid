package com.example.efrat.myapplication;

import android.os.Environment;
import java.io.File;

public class DirectoryHandler {

    public File[] getImages()
    {
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if(dcim == null)
        {
            return null;
        }
        File[] pics = dcim.listFiles();

        return pics;
    }
}
