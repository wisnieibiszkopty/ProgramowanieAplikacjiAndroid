package com.example.programowanieaplikacjiandroid.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

// Volley jednak nie nadaje się do pobierania plików

public class FileManagerService extends Service {
    private static final String TAG = "FileManagerService";
    private String url = "";
    private RequestQueue queue;

    @Override
    public void onCreate(){
        super.onCreate();
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("url");
        Log.d("url", url);

        getFileInfo();

        stopSelf();
        return START_NOT_STICKY;
    }

    private void getFileInfo(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.HEAD, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "Response: " + response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error.toString());
            }
        });

        queue.add(jsonObjectRequest);
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
}
