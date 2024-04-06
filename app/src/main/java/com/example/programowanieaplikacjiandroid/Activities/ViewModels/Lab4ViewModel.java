package com.example.programowanieaplikacjiandroid.Activities.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.programowanieaplikacjiandroid.Data.Dto.DownloadInfo;

public class Lab4ViewModel extends AndroidViewModel {


    public Lab4ViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public DownloadInfo downloadInfo(){


        return null;
    }
}
