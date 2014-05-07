package com.metric.skava.calculator.rmr.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.rmr.logic.RMRCalculator;
import com.metric.skava.calculator.rmr.logic.RMRInput;
import com.metric.skava.calculator.rmr.logic.RMROutput;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;

/**
 * Created by metricboy on 3/9/14.
 */
public class RmrResultsFragment extends RMRCalculatorBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot =
                inflater.inflate(
                        R.layout.calculator_rmr_results_fragment, container,
                        false);

        Typeface iconTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Android-Dev-Icons-1.ttf");

        TextView icon = (TextView) viewRoot.findViewById(R.id.rmr_results_rmr_label_icon);
        icon.setTypeface(iconTypeFace);
        icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View resultView = view;

        TextView rmrValueTextView = (TextView) resultView.findViewById(R.id.rmr_results_rmr);

        RMR_Calculation RMRCalculation = getRMRCalculationContext();
        RMRInput input = new RMRInput();
        input.setStrength(RMRCalculation.getStrengthOfRock().getValue());
        input.setRqd(Double.valueOf(RMRCalculation.getRqd().getValue()));
        input.setSpacing(RMRCalculation.getSpacing().getValue());
        input.setRoughness(RMRCalculation.getRoughness().getValue());
        input.setAperture(RMRCalculation.getAperture().getValue());
        input.setInfilling(RMRCalculation.getInfilling().getValue());
        input.setWeathering(RMRCalculation.getWeathering().getValue());
        input.setPersistence(RMRCalculation.getPersistence().getValue());
        input.setGroundwater(RMRCalculation.getGroundwater().getValue());
        input.setOrientation(RMRCalculation.getOrientationDiscontinuities().getValue());
        if (input.isComplete()) {
            RMROutput output;
            try {
                output = RMRCalculator.calculate(input);
                getRMRCalculationContext().setRMRResult(output);
                rmrValueTextView.setText(String.format("%.2f", output.getRMR()));
            } catch (Exception e) {
                Log.e(SkavaConstants.LOG, e.getMessage(), e);
                e.printStackTrace();
            }
        }


    }
}
