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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.programowanieaplikacjiandroid.Activities.Lab4Activity;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private RequestQueue queue;

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



        // for testing
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        stopSelf();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if(queue != null){
            queue.stop();
        }

        Toast.makeText(this, "Serwis zakończył działanie", Toast.LENGTH_SHORT).show();
    }

    private Notification getNotification(int time){
        return null;
    }

    private void updateNotification(int time){

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
