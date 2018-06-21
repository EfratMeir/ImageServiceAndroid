package com.example.efrat.myapplication;

import android.Manifest;
import android.graphics.Camera;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class DirectoryHandler {
private int numOfPics = 0;
//    List<File> pics;
//
//    public DirectoryHandler(){
//        pics = new ArrayList<File>();
//
//    }

    public File[] getImages()
    {
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+ "/Camera");
        if(dcim == null)
        {
            return null;
        }
        final List<File> pics = new ArrayList<File>();

        getAllDirs(dcim, pics);

        numOfPics = pics.size();
        return pics.toArray(new File[numOfPics]);

//        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+ "/Camera");
//        if(dcim == null)
//        {
//            return null;
//        }
//        File[] pics = dcim.listFiles();
//        numOfPics = pics.length;
//        return pics;
    }
    public void getAllDirs(File path, List<File> files){
        // Get all the files from a directory.
        File[] fList = path.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                getAllDirs(file.getAbsoluteFile(), files);
            }
        }

    }
    public int getNumPics()
    {
        return this.numOfPics;
    }
}
