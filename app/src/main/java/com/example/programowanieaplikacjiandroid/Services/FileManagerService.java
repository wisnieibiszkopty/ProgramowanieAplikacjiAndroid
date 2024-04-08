package com.example.programowanieaplikacjiandroid.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.programowanieaplikacjiandroid.R;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

// Volley jednak nie nadaje się do pobierania plików
// Ogarnij odbiorce rozgłoszeń

public class FileManagerService extends Service {
    private static final String TAG = "FileManagerService";
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "com.example.service.DOWNLOAD_NOTIFICATION_CHANNEL";
    private static final String NOTIFICATION_CHANNEL_NAME = "com.example.service.DOWNLOAD_NOTIFICATION_CHANNEL";
    public static final String ACTION_BROADCAST = "com.example.service.broadcast";
    public static final String TIME_EXTRA = "com.example.service.broadcast.time";

    private NotificationManager notificationManager;

    private HandlerThread serviceHandlerThread;
    private Handler serviceThreadHandler;
    private Handler mainThreadHandler;


    @Override
    public void onCreate(){
        super.onCreate();
        serviceHandlerThread = new HandlerThread(TAG);
        serviceHandlerThread.start();
        serviceThreadHandler = new Handler(serviceHandlerThread.getLooper());
        mainThreadHandler = new Handler(Looper.getMainLooper());
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "start downloadFIle");
        // Nie działa
        startForeground(NOTIFICATION_ID, getNotification(0));
        Log.d(TAG, "start foregournd");
        serviceThreadHandler.post(() -> {
            for (int time = 0; time <= 30; time++) {
                Log.d(TAG, "startTimer()/lambda - current time: " + time);
                updateNotification(time);
                sendBroadcast(time);
                downloadFile();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "Stoped service");
            mainThreadHandler.post(this::stopSelf);
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Serwis zakończył działanie", Toast.LENGTH_SHORT).show();
    }

    private void downloadFile(){
            
    }

    private void sendBroadcast(int time){
        Intent broadcastIntent = new Intent(ACTION_BROADCAST);
        broadcastIntent.putExtra(TIME_EXTRA, time);
        sendBroadcast(broadcastIntent);
        Log.d(TAG, "Broadcast");
    }

    private Notification getNotification(int time){
        Log.d(TAG, "start getNotification");
        Intent intent = new Intent();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, NOTIFICATION_CHANNEL_ID
        ).setContentTitle("Tytuł")
                .setContentText("tekst")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Notification ticker")
                .setWhen(System.currentTimeMillis());

        return builder.build();
    }

    private void updateNotification(int time){
        Log.d(TAG, "start update notifitaioc");
        notificationManager.notify(NOTIFICATION_ID, getNotification(time));
    }

//    private void getHttpInfo(){
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.HEAD, url, null,
//            new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.d(TAG, "Response: " + response.toString());
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e(TAG, "Error: " + error.toString());
//            }
//        });
//
//        queue.add(jsonObjectRequest);
//    }
}
