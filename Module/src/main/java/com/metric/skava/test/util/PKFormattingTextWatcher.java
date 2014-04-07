package com.metric.skava.test.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by metricboy on 3/17/14.
 */
public class PKFormattingTextWatcher implements TextWatcher {

    private EditText target;

    public PKFormattingTextWatcher(EditText targetEdit) {
        this.target = targetEdit;
    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {



    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable text) {
        if (text.length() > 3) {
            String dd = text.toString().substring(text.length() - 2, text.length());
            if (text.length() > 6){
                String ccc = text.toString().substring(text.length() - 5, text.length() - 2);
                String mm = text.toString().substring(0, text.length() - 5);
                text.clear();
                text.append("(PK): " + mm + "+" + ccc + "," + dd);
            }
            text.append("(PK): " + 00 + "+" + 000 + "," + dd);
        }
    }
}
