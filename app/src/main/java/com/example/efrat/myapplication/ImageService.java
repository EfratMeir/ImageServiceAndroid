package com.example.efrat.myapplication;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.widget.Toast;

public class ImageService extends Service {
    private BroadcastReceiver broadcastReceiver;
    private TcpClient client;
    public ImageService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        final Context context = this;

        this.client = new TcpClient(context);
        super.onCreate();
        //put here code for the service
    }

    public int onStartCommand(Intent intent, int flag, int startId)
    {
        Toast.makeText(this, "Service starting...",
                Toast.LENGTH_SHORT).show();

        connectBroadcast();
////tryout -  progress bar:
//        final Context context = this;
//        new Thread() {
//
//            @Override
//            public void run() {
//                ProgressBarHandler pbHandler = new ProgressBarHandler(new ProgressBar());
//                pbHandler.DisplayProgressBar(context);
//            }
//        }.start();
//        //end tryout
        return START_STICKY;
    }

    public void onDestroy()
    {
        Toast.makeText(this, "Service ending...",
                Toast.LENGTH_SHORT).show();
    }
    public void connectBroadcast(){
        final IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if(networkInfo != null){
                    if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                        //get the different network states
                        if(networkInfo.getState() == NetworkInfo.State.CONNECTED){
                           //start the transfer from DCIM!!!
                           client.SendAllPicsToServer();
                       }
                    }
                }
            }
        };
        // Registers the receiver so that your service will listen for broadcasts

        this.registerReceiver(this.broadcastReceiver, filter);
    }

}
