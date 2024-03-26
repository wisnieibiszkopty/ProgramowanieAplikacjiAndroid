package com.example.programowanieaplikacjiandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.programowanieaplikacjiandroid.Adapters.GradeAdapter;
import com.example.programowanieaplikacjiandroid.Models.Grade;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityGradesBinding;

import java.util.ArrayList;
import java.util.List;

public class GradesActivity extends AppCompatActivity {

    private ActivityGradesBinding binding;
    private List<Grade> grades;
    private int gradeCount = 0;
    private double gradesAverage;

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

        String[] gradesNames = getResources().getStringArray(R.array.grades_list);

        grades = new ArrayList<>();
        for(int i=0; i<gradeCount; i++){
            grades.add(new Grade(gradesNames[i], 2));
        }

        GradeAdapter adapter = new GradeAdapter(this, grades);
        RecyclerView gradesList = binding.gradesList;
        gradesList.setAdapter(adapter);
        gradesList.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Oceny");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.averageButton.setOnClickListener(v -> onFinish());
    }

    private void computeAverage(){
        double sum = 0;
        for(Grade grade : grades){
            sum += grade.getGrade();
        }
        gradesAverage = sum / Double.valueOf(gradeCount);
    }

    private void onFinish(){
        computeAverage();
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