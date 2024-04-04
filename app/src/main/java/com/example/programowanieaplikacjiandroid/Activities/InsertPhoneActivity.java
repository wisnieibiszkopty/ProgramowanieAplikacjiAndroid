package com.example.programowanieaplikacjiandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityInsertPhoneBinding;

public class InsertPhoneActivity extends AppCompatActivity {
    private ActivityInsertPhoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_phone);

        binding  = ActivityInsertPhoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Laboratorium 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.websiteButton.setOnClickListener(v -> onWebsite());
        binding.cancelButton.setOnClickListener(v -> onCancel());
        binding.saveButton.setOnClickListener(v -> onSave());
    }

    private void onWebsite(){
        startActivity(
            new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(binding.website.getText().toString())
            )
        );
    }

    private void onCancel(){
        finish();
    }

    private void onSave(){
        Bundle bundle = new Bundle();
        bundle.putString("producer", binding.producer.getText().toString());
        bundle.putString("model", binding.model.getText().toString());
        bundle.putString("version", binding.version.getText().toString());
        bundle.putString("website", binding.website.getText().toString());
        bundle.putBoolean("hasPhone", true);

        Intent intent = new Intent().putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

}