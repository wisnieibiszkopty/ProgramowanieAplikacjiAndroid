package com.example.programowanieaplikacjiandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityGradesBinding;

public class GradesActivity extends AppCompatActivity {

    private ActivityGradesBinding binding;
    private int gradeCount = 0;
    private double gradesAverage = 3.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        binding  = ActivityGradesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();
        gradeCount = bundle.getInt("gradesCount");
        binding.grades.setText("oceny:" + gradeCount);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Oceny");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.averageButton.setOnClickListener(v -> onFinish());
    }

    private void onFinish(){
        boolean passed = gradesAverage >= 3;
        Bundle bundleOut = new Bundle();
        bundleOut.putBoolean("passed", passed);
        Intent intent = new Intent();
        intent.putExtras(bundleOut);
        int result = passed ? RESULT_OK : RESULT_CANCELED;
        setResult(result, intent);
        finish();
    }
}