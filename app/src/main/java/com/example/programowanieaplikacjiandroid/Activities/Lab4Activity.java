package com.example.programowanieaplikacjiandroid.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.programowanieaplikacjiandroid.Data.Dto.DownloadInfo;
import com.example.programowanieaplikacjiandroid.Data.Dto.ProgressInfo;
import com.example.programowanieaplikacjiandroid.Services.FileManagerService;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab4Binding;

import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;


// nie wiem czemu nie przeniosłem logiki do viewModel
// ale tu tyle nowości jest że nie wiedziałbym pewnie jak to połączyć

// TODO ask for external storage

public class Lab4Activity extends AppCompatActivity {

    private ActivityLab4Binding binding;
    private final FileProgressReceiver fileProgressReceiver = new FileProgressReceiver();

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(fileProgressReceiver, new IntentFilter(FileManagerService.ACTION_BROADCAST));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLab4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Laboratorium 4");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.getInfo.setOnClickListener(v -> onDownloadInfo());
        binding.downloadFile.setOnClickListener(v -> downloadFile());
    }

    @Override
    protected void onStop() {
        unregisterReceiver(fileProgressReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void onDownloadInfo() {
        String url = binding.url.getText().toString();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture.supplyAsync(() -> {
            HttpsURLConnection connection = null;
            try {
                URL urlToSend = new URL(url);
                connection = (HttpsURLConnection) urlToSend.openConnection();
                connection.setRequestMethod("GET");
                int size = connection.getContentLength();
                String type = connection.getContentType();

                return new DownloadInfo(type, String.valueOf(size));
            } catch (Exception e) {
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

    private void downloadFile() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_LONG).show();
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }

        Intent serviceIntent = new Intent(this, FileManagerService.class);
        Log.d("URL", binding.url.getText().toString());
        serviceIntent.putExtra("url", binding.url.getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(serviceIntent);
        }

    }

    // asking user for permissions
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult
        (new ActivityResultContracts.RequestPermission(), isGranted -> {
            if(isGranted){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        });

    class FileProgressReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ProgressInfo progressInfo = intent.getParcelableExtra("progress");
            Log.i("PROGRESS INFO", progressInfo.toString());

            binding.downloadedBytes.setText(String.valueOf(progressInfo.getDownloadedBytes()));

            if(progressInfo.getStatus().equals("Running")){
                binding.downloadFile.setText("Pobieranie...");
            } else {
                binding.downloadFile.setText("Pobierz plik");
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("filesize", binding.filsize.getText().toString());
        outState.putString("filetype", binding.filetype.getText().toString());
        outState.putString("url", binding.url.getText().toString());
        outState.putString("downloadedBytes", binding.downloadedBytes.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle state) {
        binding.filsize.setText(state.getString("filesize"));
        binding.filetype.setText(state.getString("filetype"));
        binding.url.setText(state.getString("url"));
        binding.downloadedBytes.setText(state.getString("downloadedBytes"));

        super.onRestoreInstanceState(state);
    }
}