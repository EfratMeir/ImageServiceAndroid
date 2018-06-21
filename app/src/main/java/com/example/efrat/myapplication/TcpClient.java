package com.example.efrat.myapplication;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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
    //private SendPhotoHandler sendHandler;
    private InetAddress serverAddr;
    private Socket socket;
    private OutputStream output;
    private Context context;

    public TcpClient(Context context){
        this.directoryHandler = new DirectoryHandler();
        this.context = context;
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

                    SendPhotoHandler sendHandler = new SendPhotoHandler();
                    if(pics.length != 0)
                    {
                        sendHandler.Start(directoryHandler.getNumPics(), context); //initializes the prograss bar with numOfPics and runs it
                    }
                    else //pics list size is 0
                    {
                        Toast.makeText(context, "There are no images to transfer",
                                Toast.LENGTH_LONG).show();
                    }
                    for (File pic : pics)
                    {
                        sendOnePicToserver(pic);
                        sendHandler.oneSent();  //updates the prograss bar...

                    }
                    sendEndMsg();
                }
           else //pics is null
           {
               Toast.makeText(context, "There are no images to transfer",
                       Toast.LENGTH_LONG).show();
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
            //send the image name
            byte[] name = pic.getName().getBytes();
            output.write(name,0, name.length );
            output.flush();
            Thread.sleep(100);

            //send the image size
            byte[] size = (imgbyte.length + " ").getBytes();
            output.write(size,0, size.length );
            output.flush();
            Thread.sleep(100);
;

            //send the image
            output.write(imgbyte,0, imgbyte.length );
            output.flush();
            Thread.sleep(100);

;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void closeSocket(){
        try{
            this.socket.close();
        }
        catch (Exception e) {
            Log.e("TCP", "S: Error", e);
        }
    }
    public byte[] ConvertPicToByte(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        return stream.toByteArray();

    }

    public void closeSocket(){
        try{
            this.socket.close();
        }
        catch (Exception e) {
            Log.e("TCP", "S: Error", e);
        }
    }


}
