package com.example.programowanieaplikacjiandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.example.programowanieaplikacjiandroid.R;

public class MainActivity extends AppCompatActivity {

    private final Class[] acitvities = {
            Lab1Activity.class,
            Lab3Activity.class,
            Lab4Activity.class,
            Lab5Activity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lab1Button = findViewById(R.id.lab1);
        lab1Button.setOnClickListener(v -> startLab(0));
        Button lab3Button = findViewById(R.id.lab3);
        lab3Button.setOnClickListener(v -> startLab(1));
        Button lab4Button = findViewById(R.id.lab4);
        lab4Button.setOnClickListener(v -> startLab(2));
        Button lab5Button = findViewById(R.id.lab5);
        lab5Button.setOnClickListener(v -> startLab(3));
    }

    private void startLab(int n){
        Intent intent = new Intent(this, acitvities[n]);
        startActivity(intent);
    }

}