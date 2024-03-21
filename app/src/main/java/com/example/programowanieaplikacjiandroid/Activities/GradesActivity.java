package com.example.programowanieaplikacjiandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityGradesBinding;

public class GradesActivity extends AppCompatActivity {

    private ActivityGradesBinding binding;
    private int gradeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        binding  = ActivityGradesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();
        gradeCount = bundle.getInt("gradesCount");
        Log.i("aaa", Integer.toString(gradeCount));
        binding.grades.setText("oceny:" + gradeCount);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Oceny");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}