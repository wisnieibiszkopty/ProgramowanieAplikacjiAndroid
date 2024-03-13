package com.example.programowanieaplikacjiandroid;

import android.widget.EditText;

@FunctionalInterface
public interface EditTextOnFocusChange {
    void handleFocus(EditText text) throws Exception;
}
