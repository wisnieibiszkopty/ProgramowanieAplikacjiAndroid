package com.example.programowanieaplikacjiandroid.Interfaces.Validation.Base;

import com.example.programowanieaplikacjiandroid.Data.Models.ValidationResult;

public abstract class BaseValidator implements IValidator {

    public static ValidationResult validate(IValidator... validators) {
        for (IValidator validator : validators) {
            ValidationResult result = validator.validate();
            if (!result.success()) {
                return result;
            }
        }
        return new ValidationResult(true, "Success");
    }

}
