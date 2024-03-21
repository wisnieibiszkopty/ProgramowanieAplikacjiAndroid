package com.example.programowanieaplikacjiandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programowanieaplikacjiandroid.EditTextOnFocusChange;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityGradesBinding;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab1Binding;

public class Lab1Activity extends AppCompatActivity {
    private ActivityLab1Binding binding;
    private boolean[] validationSuccess = {false, false, false};
    private int gradeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);

        binding  = ActivityLab1Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Zadanie 1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.gradesButton.setOnClickListener(v -> onFormSubmit());

        EditTextOnFocusChange nameFoo = text -> {
            if(text.getText().toString().isEmpty()){
                throw new Exception();
            }
        };

        EditTextOnFocusChange gradesFoo = text -> {
            try{
                int number = Integer.parseInt(text.getText().toString());
                if(number < 5 || number > 15){
                    throw new NumberFormatException();
                }
                gradeCount = number;
            } catch(Exception e){
                throw new Exception();
            }
        };

        addFocusChangeListener(nameFoo, binding.name, "Musisz wpisać imię!", 0);
        addFocusChangeListener(nameFoo, binding.surname, "Musisz wpisać nazwisko!", 1);
        addFocusChangeListener(gradesFoo, binding.grades, "Liczba musi być z przedziału [5, 15]!",2);

    }

    private void addFocusChangeListener(EditTextOnFocusChange foo, EditText text, String errorMessage, int n){
        text.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                try {
                    foo.handleFocus(text);
                    validationSuccess[n] = true;
                } catch(Exception e){
                    handleError(text, errorMessage);
                    validationSuccess[n] = false;
                } finally {
                    onValidationChange();
                }
            }
        });
    }

    void handleError(TextView text, String errorMessage){
        text.setError(errorMessage);
        Toast.makeText(getApplicationContext(),
                        errorMessage, Toast.LENGTH_LONG)
                        .show();
    }

    private void onValidationChange(){
        Button button = binding.gradesButton;

        if(validationSuccess[0] && validationSuccess[1] && validationSuccess[2]){
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.INVISIBLE);
        }
    }

    private void onFormSubmit(){
        Bundle bundle = new Bundle();
        bundle.putInt("gradesCount", gradeCount);
        bundle.putString("name", binding.name.getText().toString());
        bundle.putString("surname", binding.surname.getText().toString());
        Intent intent = new Intent(this, GradesActivity.class);
        intent.putExtras(bundle);
        //setResult(RESULT_OK, intent);
        //finish();
        startActivity(intent);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        TextView name = findViewById(R.id.name);
//        TextView surname = findViewById(R.id.surname);
//        TextView grades = findViewById(R.id.grades);
//
//        outState.putString("name", name.getText().toString());
//        outState.putString("nameError", name.getError().toString());
//        outState.putString("surname", surname.getText().toString());
//        outState.putString("surnameError", surname.getError().toString());
//        outState.putString("grades", grades.getText().toString());
//        outState.putString("gradesError", grades.getError().toString());
//        outState.putBooleanArray("validation", this.validationSuccess);
//
//        super.onSaveInstanceState(outState);
//    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        TextView name = binding.name;
        TextView surname = binding.surname;
        TextView grades = binding.grades;

        name.setText(savedInstanceState.getString("name"));
        name.setError(savedInstanceState.getString("nameError"));
        surname.setText(savedInstanceState.getString("surname"));
        surname.setError(savedInstanceState.getString("surnameError"));
        grades.setText(savedInstanceState.getString("grades"));
        grades.setError(savedInstanceState.getString("gradesError"));

        this.validationSuccess = savedInstanceState.getBooleanArray("validation");

        super.onRestoreInstanceState(savedInstanceState);
    }
}