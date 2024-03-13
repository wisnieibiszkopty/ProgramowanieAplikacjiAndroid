package com.example.programowanieaplikacjiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lab1Button = findViewById(R.id.lab1);
        lab1Button.setOnClickListener(v -> startLab1());

        Button wwwButton = findViewById(R.id.webButton);
        wwwButton.setOnClickListener(v -> startWWW());
    }

    private void startLab1(){
        Intent intent = new Intent(this, Lab1Activity.class);
        startActivity(intent);
    }

    // nie używać 💀💀💀💀💀
    private void startWWW(){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://jbzd.com.pl/"));
        startActivity(intent);
    }
}