package com.example.programowanieaplikacjiandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.programowanieaplikacjiandroid.Adapters.GradeAdapter;
import com.example.programowanieaplikacjiandroid.Data.Models.Grade;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityGradesBinding;

import java.util.ArrayList;

// todo jest problem zaznaczone wartości nie zapisują się po obrocie


public class GradesActivity extends AppCompatActivity {

    private ActivityGradesBinding binding;
    private ArrayList<Grade> grades;
    private int gradeCount = 0;
    private String name;
    private String surname;
    private double gradesAverage;
    private boolean endedByBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        binding  = ActivityGradesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();
        gradeCount = bundle.getInt("gradesCount");
        name = bundle.getString("name");
        surname = bundle.getString("surname");
        String[] gradesNames = getResources().getStringArray(R.array.grades_list);

        grades = new ArrayList<>();
        for(int i=0; i<gradeCount; i++){
            grades.add(new Grade(gradesNames[i], 2));
        }

        setUpState();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Oceny");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.averageButton.setOnClickListener(v -> onFinish());

    }

    // wrap this code into separated method, so
    // it can be called in onCreate and onRestoreInstanceState
    private void setUpState(){
        binding.student.setText("Student " + name + " " + surname);

        Log.v("setup", "Setup method is being called");

        for(Grade grade: grades){
            Log.i("grade: ", grade.toString());
        }

        GradeAdapter adapter = new GradeAdapter(this, grades);
        RecyclerView gradesList = binding.gradesList;
        gradesList.setAdapter(adapter);
        gradesList.setLayoutManager(new LinearLayoutManager(this));
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
        endedByBackButton = false;
        boolean passed = gradesAverage >= 3;
        Bundle bundleOut = new Bundle();
        bundleOut.putString("name", name);
        bundleOut.putString("surname", surname);
        bundleOut.putBoolean("passed", passed);
        bundleOut.putBoolean("endedByBackButton", endedByBackButton);
        bundleOut.putInt("gradeCount", gradeCount);
        bundleOut.putDouble("average", gradesAverage);
        Intent intent = new Intent();
        intent.putExtras(bundleOut);
        int result = passed ? RESULT_OK : RESULT_CANCELED;
        setResult(result, intent);
        finish();
    }

    // after clicking back button app won't invoke ActivityResultCallback
    // and input fields will be cleared. This method is preventing this from happen
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            endedByBackButton = true;
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("surname", surname);
            bundle.putInt("gradeCount", gradeCount);
            bundle.putBoolean("endedByBackButton", endedByBackButton);

            setResult(RESULT_OK, new Intent().putExtras(bundle));
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("name", name);
        outState.putString("surname", surname);
        outState.putDouble("average", gradesAverage);
        outState.putParcelableArrayList("grades", grades);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        name = savedInstanceState.getString("name");
        surname = savedInstanceState.getString("surname");
        gradesAverage = savedInstanceState.getDouble("average");
        grades = savedInstanceState.getParcelableArrayList("grades");

        setUpState();

        super.onRestoreInstanceState(savedInstanceState);
    }
}