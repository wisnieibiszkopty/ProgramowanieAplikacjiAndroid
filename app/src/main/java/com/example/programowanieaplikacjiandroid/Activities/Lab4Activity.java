package com.example.programowanieaplikacjiandroid.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.programowanieaplikacjiandroid.Activities.ViewModels.Lab4ViewModel;
import com.example.programowanieaplikacjiandroid.Data.Dto.DownloadInfo;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab4Binding;

public class Lab4Activity extends AppCompatActivity {

    private ActivityLab4Binding binding;
    private Lab4ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding  = ActivityLab4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Laboratorium 4");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(Lab4ViewModel.class);

        binding.getInfo.setOnClickListener(v -> onDownloadInfo());
    }

    private void onDownloadInfo(){
        DownloadInfo info = viewModel.downloadInfo();
        binding.filsize.setText(info.filesize());
        binding.filetype.setText(info.filetype());
    }
}