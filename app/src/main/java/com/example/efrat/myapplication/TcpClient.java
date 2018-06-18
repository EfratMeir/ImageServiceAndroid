package com.example.efrat.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class TcpClient {

    private DirectoryHandler directoryHandler;

    public void SendAllPicsToServer()
    {
        File[] pics = directoryHandler.getImages();
        if (pics != null)
        {
            for (File pic : pics)
            {
                sendOnePicToserver(pic);
            }
        }
    }

    public void sendOnePicToserver(File pic)
    {
        try
        {
            FileInputStream fis = new FileInputStream(pic);
            Bitmap bm = BitmapFactory.decodeStream(fis);
            byte[] imgbyte = ConvertPicToByte(bm);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //now send the pic to the server!!!!

    }

    public byte[] ConvertPicToByte(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        return stream .toByteArray();

    }


}
