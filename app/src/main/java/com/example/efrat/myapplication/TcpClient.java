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
    private InetAddress serverAddr;
    private Socket socket;
    private OutputStream output;

    public TcpClient(){
        this.directoryHandler = new DirectoryHandler();
        connetToServer();
    }
    public void connetToServer(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    serverAddr = InetAddress.getByName("10.0.2.2");
                    socket = new Socket(serverAddr,8100 ); // check port number
                    try {
                        //send msg to server
                        output = socket.getOutputStream();
                    }

                    catch (Exception e) {
                        Log.e("TCP", "S: Error", e);
                    }
                }catch (Exception e){
                    Log.e("TCP", "S: Error", e);
                }
            }
        });
        thread.start();
    }
    public void SendAllPicsToServer()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
        File[] pics = directoryHandler.getImages();
        if (pics != null)
        {
            for (File pic : pics)
            {
                try{
                    String lala = new String("lalala ");
                    byte[] lal = lala.getBytes();
                    output.write(lal,0, lal.length );
                    output.flush();
                    Thread.sleep(100);
                }
                catch (Exception e) {
                    Log.e("TCP", "S: Error", e);
                }
                sendOnePicToserver(pic);
            }
            sendEndMsg();
        }
            }
        });
        thread.start();
    }
    public void  sendEndMsg(){
        try{
            try{
                String done = new String("done ");
                byte[] doneBytes = done.getBytes();
                output.write(doneBytes,0, doneBytes.length );
                output.flush();
            }
            catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            }
        }catch (Exception e){
            Log.e("TCP", "S: Error", e);
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

                            String lala = new String("lalala ");
                            byte[] lal = lala.getBytes();
                            output.write(lal,0, lal.length );
                            output.flush();
                            Thread.sleep(100);

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
