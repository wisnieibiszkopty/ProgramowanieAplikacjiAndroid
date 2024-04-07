package com.example.programowanieaplikacjiandroid.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

// Volley jednak nie nadaje się do pobierania plików
// Ogarnij odbiorce rozgłoszeń

public class FileManagerService extends Service {
    private static final String TAG = "FileManagerService";
    private RequestQueue queue;

    @Override
    public void onCreate(){
        super.onCreate();
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getFileInfo(intent.getStringExtra("url"));

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

    private void getFileInfo(String url){
        HttpsURLConnection connection = null;

        try{
            URL urlToSend = new URL(url);
            connection = (HttpsURLConnection) urlToSend.openConnection();
            connection.setRequestMethod("GET");
            int size = connection.getContentLength();
            String type = connection.getContentType();

            Log.d("length: ", String.valueOf(size));
            Log.d("type" , type);

            Intent intent = new Intent("com.example.ACTION_SEND_DOWNLOAD_INFO");
            intent.putExtra("type", type);
            intent.putExtra("length", String.valueOf(size));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
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
