package com.metric.skava.rocksupport.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.validator.TextValidator;

/**
 * Created by metricboy on 3/9/14.
 */
public class SpanFragment extends SkavaFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SpanFragment.class.getSimpleName() + " : onCreateView ");
        }
        View rootView = inflater.inflate(
                R.layout.rock_support_span_fragment, container, false);

        final EditText spanEditText = (EditText) rootView.findViewById(R.id.span_edit_text);
        spanEditText.setRawInputType(Configuration.KEYBOARD_12KEY);


//        Float span = getSupportRequirementsContext().getSpan();
        Double span =  getCurrentAssessment().getTunnel().getExcavationFactors().getSpan();
        if (span != null) {
            spanEditText.setText(span.toString());
        }

        spanEditText.addTextChangedListener(new TextValidator(spanEditText) {
            @Override
            public void validate(TextView textView, String text) {
                Double enteredValue;
                try {
                    enteredValue = Double.parseDouble(text);
                    getCurrentAssessment().getTunnel().getExcavationFactors().setSpan(enteredValue);
                } catch (NumberFormatException e) {
                    spanEditText.setError("Span must be a number!");
                }
            }
        });


        return rootView;
    }

}


