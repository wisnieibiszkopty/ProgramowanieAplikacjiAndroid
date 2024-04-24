package com.example.programowanieaplikacjiandroid.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.programowanieaplikacjiandroid.Fragments.PaintingAdapter;
import com.example.programowanieaplikacjiandroid.Fragments.PaintingFragmentDetails;
import com.example.programowanieaplikacjiandroid.Fragments.PaintingFragmentList;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.Activities.PaintSurfaceView;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab5Binding;;

import java.util.ArrayList;

public class Lab5Activity extends AppCompatActivity {

    ActivityLab5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLab5Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Rysowanie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PaintSurfaceView paintSurfaceView = binding.paintSurfaceView;

        binding.buttonRED.setOnClickListener( v -> {
            paintSurfaceView.setStrokeColor(R.color.red);
        });
        binding.buttonYELLOW.setOnClickListener( v -> {
            paintSurfaceView.setStrokeColor(R.color.yellow);
        });
        binding.buttonBLUE.setOnClickListener( v -> {
            paintSurfaceView.setStrokeColor(R.color.blue);
        });
        binding.buttonGREEN.setOnClickListener( v -> {
            paintSurfaceView.setStrokeColor(R.color.green);
        });
        binding.clearPaintButton.setOnClickListener( v-> {
            paintSurfaceView.clearCanva();
        });
        binding.savePaintButton.setOnClickListener( v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23);
            }
            if (paintSurfaceView.saveCanva("rysunek")) {
                Toast.makeText(this, "Zapisano rysunek", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Nie udało sie zapisac", Toast.LENGTH_SHORT).show();
            }

        });
        binding.fragmentListButton.setOnClickListener(v -> {
            paintSurfaceView.clearCanva();
            binding.lab5submain.setVisibility(View.INVISIBLE);
            PaintingFragmentList fragmentList = new PaintingFragmentList();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, fragmentList)
                    .addToBackStack("gallery")
                    .commit();
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount()==1 ){
            binding.lab5submain.setVisibility(View.VISIBLE);
            binding.paintSurfaceView.clearCanva();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
//        zablokuj cofanie aby nie rozwaLILO backStack() dla toolbara
        super.onBackPressed();
    }
}
