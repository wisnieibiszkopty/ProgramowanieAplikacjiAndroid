package com.example.programowanieaplikacjiandroid.Interfaces;

import android.widget.EditText;

@FunctionalInterface
public interface EditTextOnFocusChange {
    void handleFocus(EditText text) throws Exception;
}
