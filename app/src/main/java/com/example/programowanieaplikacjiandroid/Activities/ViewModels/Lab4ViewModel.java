package com.example.programowanieaplikacjiandroid.Activities.ViewModels;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.programowanieaplikacjiandroid.Data.Dto.DownloadInfo;
import com.example.programowanieaplikacjiandroid.Services.FileManagerService;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

public class Lab4ViewModel extends AndroidViewModel {


    public Lab4ViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public void downloadInfo(Context context, String url){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<DownloadInfo> callableTask = () -> {
            HttpsURLConnection connection = null;
            try{
                URL urlToSend = new URL(url);
                connection = (HttpsURLConnection) urlToSend.openConnection();
                connection.setRequestMethod("GET");
                int size = connection.getContentLength();
                String type = connection.getContentType();

                Log.d("length: ", String.valueOf(size));
                Log.d("type" , type);

                return new DownloadInfo(type, String.valueOf(size));

                //Intent intent = new Intent("com.example.ACTION_SEND_DOWNLOAD_INFO");
                //intent.putExtra("type", type);
                //intent.putExtra("length", String.valueOf(size));
                //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        };

        Future<DownloadInfo> info = executor.submit(callableTask);

//        executor.execute(() -> {
////            Intent serviceIntent = new Intent(context, FileManagerService.class);
////            serviceIntent.putExtra("url", url);
////            context.startService(serviceIntent);
//        });
        executor.shutdown();
    }

}
