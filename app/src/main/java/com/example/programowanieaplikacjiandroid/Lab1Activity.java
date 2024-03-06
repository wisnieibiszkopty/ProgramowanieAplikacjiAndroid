package com.example.programowanieaplikacjiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lab1Activity extends AppCompatActivity {

    boolean[] validationSuccess = {false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> finish());

        EditText nameText = findViewById(R.id.name);
        nameText.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                if(nameText.getText().toString().isEmpty()){
                    nameText.setError("Musisz wpisać imię!");
                    Toast.makeText(getApplicationContext(),
                                    "Niepoprawne dane!",
                                    Toast.LENGTH_LONG)
                                    .show();
                    validationSuccess[0] = false;
                } else {
                    validationSuccess[0] = true;
                }

                onValidationChange();
            }
        });

        EditText surnameText = findViewById(R.id.surname);
        surnameText.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                if(surnameText.getText().toString().isEmpty()){
                    surnameText.setError("Musisz wpisać nazwisko!");
                    Toast.makeText(getApplicationContext(),
                                    "Niepoprawne dane!",
                                    Toast.LENGTH_LONG)
                                    .show();
                    validationSuccess[1] = false;
                } else {
                    validationSuccess[1] = true;
                }

                onValidationChange();
            }
        });

        EditText gradesText = findViewById(R.id.grades);
        gradesText.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                try{
                    int number = Integer.parseInt(gradesText.getText().toString());
                    if(gradesText.getText().toString().isEmpty() || number <= 5 || number >= 15){
                        gradesText.setError("Liczba musi być z przedziału [5, 15]!");
                        Toast.makeText(getApplicationContext(),
                                        "Niepoprawne dane!",
                                        Toast.LENGTH_LONG)
                                        .show();
                        validationSuccess[2] = false;
                    } else {
                        validationSuccess[2] = true;
                    }

                    onValidationChange();
                }
                catch(NumberFormatException e){
                    validationSuccess[2] = false;
                    gradesText.setError("Liczba musi być z przedziału [5, 15]!");
                    Toast.makeText(getApplicationContext(),
                                    "To nie jest liczba!",
                                    Toast.LENGTH_LONG)
                                    .show();
                }
            }
        });
    }

    private void onValidationChange(){
        Button button = findViewById(R.id.gradesButton);

        if(validationSuccess[0] && validationSuccess[1] && validationSuccess[2]){
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.INVISIBLE);
        }
    }
}