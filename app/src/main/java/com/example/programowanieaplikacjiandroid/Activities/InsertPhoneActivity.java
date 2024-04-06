package com.example.programowanieaplikacjiandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.programowanieaplikacjiandroid.Data.Models.Phone;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityInsertPhoneBinding;

public class InsertPhoneActivity extends AppCompatActivity {
    private ActivityInsertPhoneBinding binding;
    private boolean editing = false;
    private boolean isValid = true;
    Phone editedPhone;

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

        // if intent comes with phone object it means user wants to edit it
        Intent intent  = getIntent();
        if(intent.getExtras() != null && intent.getExtras().getParcelable("phone") != null){
            editing = true;
            editedPhone = intent.getExtras().getParcelable("phone");
            binding.producer.setText(editedPhone.getProducer());
            binding.model.setText(editedPhone.getModel());
            binding.version.setText(editedPhone.getVersion());
            binding.website.setText(editedPhone.getWebsite());
        }

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
        isValid = true;

        // validation
        if(binding.producer.getText().toString().isEmpty()){
            isValid = false;
            binding.producer.setError("Producer cannot be empty!");
        }

        if(binding.model.getText().toString().isEmpty()){
            isValid = false;
            binding.model.setError("Model cannot be empty!");
        }

        if(binding.version.getText().toString().isEmpty()){
            isValid = false;
            binding.version.setError("Version cannot be empty!");
        }

        if(binding.website.getText().toString().isEmpty()){
            isValid = false;
            binding.website.setError("Website cannot be empty!");
        }


        if(isValid){
            Bundle bundle = new Bundle();
            if(editing){
                editedPhone.setProducer(binding.producer.getText().toString());
                editedPhone.setModel(binding.model.getText().toString());
                editedPhone.setVersion(binding.version.getText().toString());
                editedPhone.setWebsite(binding.website.getText().toString());
                bundle.putBoolean("edited", true);
            } else {
                editedPhone = new Phone(
                        binding.producer.getText().toString(),
                        binding.model.getText().toString(),
                        binding.version.getText().toString(),
                        binding.website.getText().toString()
                );
            }

            bundle.putParcelable("phone", editedPhone);
            bundle.putBoolean("hasPhone", true);

            Intent intent = new Intent().putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}