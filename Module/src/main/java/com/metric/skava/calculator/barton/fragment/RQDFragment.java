package com.metric.skava.calculator.barton.fragment;

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
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.validator.TextValidator;
import com.metric.skava.calculator.barton.model.RQD;


public class RQDFragment extends QBartonCalculatorBaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (BuildConfig.DEBUG) {
			Log.d(SkavaConstants.LOG, "Entering "
					+ RQDFragment.class.getSimpleName() + " : onCreateView ");
		}		
		View rootView = inflater.inflate(
				R.layout.calculator_qbarton_rqd_fragment, container, false);

//        final EditText jvEditText = (EditText) rootView.findViewById(R.id.rqd_edit_text);
//        jvEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        final EditText rqdEditText = (EditText) rootView.findViewById(R.id.rqd_edit_text);
        rqdEditText.setRawInputType(Configuration.KEYBOARD_12KEY);


        RQD rQD = getQCalculationContext().getRqd();
		if (rQD != null) {
			if (rQD.getValue() != null) {
                rqdEditText.setText(rQD.getValue().toString());
			}
		}
        rqdEditText.addTextChangedListener(new TextValidator(rqdEditText) {
            @Override public void validate(TextView textView, String text) {
                int enteredValue = 0;
                try {
                    enteredValue = Integer.parseInt(text);
                    if (enteredValue >= 0 && enteredValue <= 100 ) {
                        RQD plainRqd = new RQD(enteredValue);
//                        RQD mapeado = RQDMapper.getInstance().mapJvToRQD(enteredValue);
						getQCalculationContext().setRqd(plainRqd);
                    } else {
                        rqdEditText.setError( "RQD value must be between 0 and 100!" );
                    }
                } catch (NumberFormatException e) {
                    rqdEditText.setError( "RQD value must be between 0 and 100!" );
                }

            }
        });

		return rootView;
	}



}
