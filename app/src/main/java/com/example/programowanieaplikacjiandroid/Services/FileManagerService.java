package com.example.programowanieaplikacjiandroid.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.programowanieaplikacjiandroid.Activities.Lab4Activity;
import com.example.programowanieaplikacjiandroid.R;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            //notificationManager.notify(0, getNotification(0));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "start downloadFIle");
        // Nie działa
        startForeground(NOTIFICATION_ID, getNotification(0));
        Log.d(TAG, "start foregournd");
        serviceThreadHandler.post(() -> {
            //updateNotification();
            //sendBroadcast();
            try{
                downloadFile(intent.getStringExtra("url"));
            } catch (IOException e){
                e.printStackTrace();
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

    private void downloadFile(String urlString) throws IOException {
        HttpsURLConnection conn = null;
        DataInputStream inputStream = null;
        FileOutputStream outputStream = null;
        int progress = 0;
        int fileSize = 0;
        try {
            Log.i("HttpConnectionService", "Rozpoczynam pobieranie");
            URL url = new URL(urlString);
            String fileName = urlString.substring(urlString.lastIndexOf("/"));
            File outputFile = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "Download" + fileName);
            if(outputFile.exists()) {
                outputFile.delete();
            }
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            inputStream = new DataInputStream(conn.getInputStream());
            outputStream = new FileOutputStream(outputFile.getPath());
            byte buffer[] = new byte[1024];
            int bytesRead;
            fileSize = conn.getContentLength();
            int step = 1;
            while((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                progress += bytesRead;
                if(progress >= (fileSize/10)*step) {
                    updateNotification(step*10);
                    Log.i(TAG, "Postęp pobierania: " + step*10 + "/" + 100);
                    step++;
                    //sendProgressBroadcast(progress, fileSize, ProgressStatus.RUNNING);
                }
                outputStream.write(buffer, 0, bytesRead);
            }
            Log.i("HttpConnectionService", "Pobieranie zakończone");
            //downloadNotificationService.setAsCompleted();
            //sendProgressBroadcast(progress, fileSize, ProgressStatus.COMPLETED);
        } catch (IOException e) {
            //sendProgressBroadcast(progress, fileSize, ProgressStatus.ERROR);
            throw new RuntimeException(e);
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
            if(outputStream != null) {
                outputStream.close();
            }
            if(conn != null) {
                conn.disconnect();
            }
        }
    }

    private void sendBroadcast(int progress, int filesize, String status){
        Intent broadcastIntent = new Intent(ACTION_BROADCAST);
        //broadcastIntent.putExtra(TIME_EXTRA, time);
        sendBroadcast(broadcastIntent);
        Log.d(TAG, "Broadcast");
    }

    private Notification getNotification(int progress){
        Log.d(TAG, "start getNotification");
        Intent intent = new Intent(getApplicationContext(), Lab4Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, NOTIFICATION_CHANNEL_ID
        ).setContentTitle("Pobieranie pliku")
                .setContentText("Trwa pobieranie pliku")
                .setOngoing(true)
                .setProgress(100, progress, false)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setTicker("Notification ticker")
                .setWhen(System.currentTimeMillis())
                .setChannelId(NOTIFICATION_CHANNEL_ID);

        return builder.build();
    }

    private void updateNotification(int progress){
        Log.d(TAG, "start update notifitaioc");
        notificationManager.notify(NOTIFICATION_ID, getNotification(progress));
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
