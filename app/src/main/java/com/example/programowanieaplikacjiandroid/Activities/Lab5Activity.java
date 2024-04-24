package com.example.programowanieaplikacjiandroid.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.programowanieaplikacjiandroid.Fragments.PaintingFragmentList;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab5Binding;;

public class Lab5Activity extends AppCompatActivity {

    ActivityLab5Binding binding;
    PaintSurfaceView paintSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLab5Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Laboratorium 5 - Grafika");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paintSurfaceView = binding.paintSurfaceView;

        binding.buttonRed.setOnClickListener( v -> paintSurfaceView.setStrokeColor(R.color.red));
        binding.buttonYellow.setOnClickListener( v -> paintSurfaceView.setStrokeColor(R.color.yellow));
        binding.buttonBlue.setOnClickListener( v -> paintSurfaceView.setStrokeColor(R.color.blue));
        binding.buttonGreen.setOnClickListener( v -> paintSurfaceView.setStrokeColor(R.color.green));
        binding.clearPaintButton.setOnClickListener( v-> paintSurfaceView.clearCanva());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_image) {
            saveImage();
            return true;
        } else if (item.getItemId() == R.id.show_images){
            showImages();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void saveImage(){
        if (ContextCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions
                    (this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            23);
        }

        if (paintSurfaceView.saveCanva("rysunek")) {
            Toast.makeText(this, "Zapisano rysunek", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nie udało sie zapisac", Toast.LENGTH_SHORT).show();
        }
    }

    private void showImages(){
        paintSurfaceView.clearCanva();
        binding.lab5submain.setVisibility(View.INVISIBLE);
        PaintingFragmentList fragmentList = new PaintingFragmentList();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, fragmentList)
                .addToBackStack("gallery")
                .commit();
    }

}
