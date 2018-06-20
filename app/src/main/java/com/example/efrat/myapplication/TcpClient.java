package com.example.efrat.myapplication;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import static android.util.Base64.encodeToString;

public class TcpClient {

    private DirectoryHandler directoryHandler;

    public TcpClient(){
        this.directoryHandler = new DirectoryHandler();
    }
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
        //Log.d("myTag", "This is my message");

        try
        {
            FileInputStream fis = new FileInputStream(pic);
            Bitmap bm = BitmapFactory.decodeStream(fis);
            final byte[] imgbyte = ConvertPicToByte(bm); // if its not final it doesnt work

            //now send the pic to the server!!!!
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                   try{
                        InetAddress serverAddr = InetAddress.getByName("10.0.2.2");
                        Socket socket = new Socket(serverAddr,8000 ); // check port number


                        try {
                            //send msg to server
                            OutputStream output = socket.getOutputStream();
                            // FileInputStream fis = new FileInputStream(pic);
//                            int flags = Base64.NO_WRAP | Base64.URL_SAFE;
//                            String imgString = encodeToString(imgbyte, flags);
//                            String[] args = new String[2];
//                           args[0] = imgString;
//                            Command cmd = new Command(3, args, null);
//                            byte[] msg = cmd.toString().getBytes();

                            output.write(imgbyte);
                            output.flush();
                        }
                        catch (Exception e){
                            Log.e("TCP", "S: Error", e);
                        }finally {
                            socket.close();
                        }
                    }catch (Exception e){
                        Log.e("TCP", "S: Error", e);
                    }
                }
            });
            thread.start();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public byte[] ConvertPicToByte(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        return stream .toByteArray();

    }


}
