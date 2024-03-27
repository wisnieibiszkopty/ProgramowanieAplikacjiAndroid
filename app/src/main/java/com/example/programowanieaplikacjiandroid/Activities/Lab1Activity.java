package com.example.programowanieaplikacjiandroid.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programowanieaplikacjiandroid.Interfaces.EditTextOnFocusChange;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab1Binding;

public class Lab1Activity extends AppCompatActivity {
    private ActivityLab1Binding binding;
    private boolean[] validationSuccess = {false, false, false};
    private int gradeCount;
    private boolean passed = false;
    private double gradePointAverage = 0.0;
    private boolean isFinished = false;

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
        binding.endButton.setOnClickListener(v -> onAppEnd());

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

        activityResultLauncher.launch(intent);
    }

    // 💀💀💀💀💀
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    handleActivityResult(data);
                }
            }
    );

    private void handleActivityResult(Intent data){
        binding.name.setText(data.getExtras().getString("name"));
        binding.surname.setText(data.getExtras().getString("surname"));

        passed = data.getExtras().getBoolean("passed");
        gradePointAverage = data.getExtras().getDouble("average");

        isFinished = true;

        changeEndButtonState();
    }

    private void changeEndButtonState(){
        if(isFinished){
            Button endButton = binding.endButton;

            if(passed){
                endButton.setText("Super :')");
            } else {
                endButton.setText("Tym razem mi nie poszło :<");
            }
            endButton.setVisibility(View.VISIBLE);
        }
    }

    private void onAppEnd(){

        String averageString = String.format("Twoja średnia to %.2f ", gradePointAverage);
        String message = averageString + (passed ? "Gratulacje, otrzymujesz zaliecznie!"
                : "Wysyłam podanie o zaliczenie warunkowe 💀💀💀");

        AlertDialog alert = new AlertDialog.Builder(Lab1Activity.this)
                .setMessage(message)
                .setTitle("Opuszczasz aplikacje!")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .create();

        alert.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBooleanArray("validation", this.validationSuccess);
        outState.putString("name", String.valueOf(binding.name.getText()));
        outState.putString("surname", String.valueOf(binding.surname.getText()));
        outState.putInt("gradeCount", gradeCount);
        outState.putBoolean("passed", passed);
        outState.putDouble("gradePointAverage", gradePointAverage);
        outState.putBoolean("isFinished", isFinished);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        binding.name.setText(savedInstanceState.getString("name"));
        binding.surname.setText(savedInstanceState.getString("surname"));

        this.validationSuccess = savedInstanceState.getBooleanArray("validation");
        onValidationChange();

        passed = savedInstanceState.getBoolean("passed");
        gradeCount = savedInstanceState.getInt("gradeCount");
        gradePointAverage = savedInstanceState.getDouble("gradePointAverage");
        isFinished = savedInstanceState.getBoolean("isFinished");

        changeEndButtonState();

        super.onRestoreInstanceState(savedInstanceState);
    }
}