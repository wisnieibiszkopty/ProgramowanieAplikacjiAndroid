package com.example.programowanieaplikacjiandroid.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.programowanieaplikacjiandroid.Activities.ViewModels.Lab4ViewModel;
import com.example.programowanieaplikacjiandroid.Data.Dto.DownloadInfo;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab4Binding;

import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class Lab4Activity extends AppCompatActivity {

    private ActivityLab4Binding binding;
    private Lab4ViewModel viewModel;

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            binding.filetype.setText(intent.getStringExtra("type"));
            binding.filsize.setText(intent.getStringExtra("length"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding  = ActivityLab4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Laboratorium 4");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                messageReceiver, new IntentFilter("com.example.ACTION_SEND_DOWNLOAD_INFO")
        );

        viewModel = new ViewModelProvider(this).get(Lab4ViewModel.class);

        binding.getInfo.setOnClickListener(v -> onDownloadInfo());
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onDestroy();
    }

    private void onDownloadInfo(){
        String url = binding.url.getText().toString();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture.supplyAsync(() -> {
            HttpsURLConnection connection = null;
            try{
                URL urlToSend = new URL(url);
                connection = (HttpsURLConnection) urlToSend.openConnection();
                connection.setRequestMethod("GET");
                int size = connection.getContentLength();
                String type = connection.getContentType();

                return new DownloadInfo(type, String.valueOf(size));
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }, executor).thenAccept(result ->
            runOnUiThread(() -> {
                binding.filsize.setText(result.filesize());
                binding.filetype.setText(result.filetype());
        }));
    }

}