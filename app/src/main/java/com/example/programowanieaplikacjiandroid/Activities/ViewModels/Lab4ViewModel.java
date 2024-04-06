package com.example.programowanieaplikacjiandroid.Activities.ViewModels;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.programowanieaplikacjiandroid.Data.Dto.DownloadInfo;
import com.example.programowanieaplikacjiandroid.Services.FileManagerService;

public class Lab4ViewModel extends AndroidViewModel {


    public Lab4ViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public DownloadInfo downloadInfo(Context context, String url){
        Intent serviceIntent = new Intent(context, FileManagerService.class);
        serviceIntent.putExtra("url", url);
        context.startService(serviceIntent);

        return new DownloadInfo(".txt", "123456");
    }
}
