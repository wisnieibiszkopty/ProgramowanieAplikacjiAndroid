package com.example.programowanieaplikacjiandroid.Interfaces.Validation;

import android.widget.EditText;

import com.example.programowanieaplikacjiandroid.Data.Dto.ValidationResult;
import com.example.programowanieaplikacjiandroid.Interfaces.Validation.Base.BaseValidator;

public class EmptyValidatorRule extends BaseValidator {
    public EditText text;

    public EmptyValidatorRule(EditText editText) {
        text = editText;
    }


    @Override
    public ValidationResult validate() {
        boolean isValid = text.getText().toString().isEmpty();
        return new ValidationResult(isValid, "Field is empty!");
    }

}
