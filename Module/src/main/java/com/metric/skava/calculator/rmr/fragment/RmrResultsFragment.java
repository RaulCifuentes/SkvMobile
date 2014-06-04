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

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by metricboy on 3/9/14.
 */
public class RmrResultsFragment extends RMRCalculatorBaseFragment {

    boolean showResultsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewRoot = null;
        showResultsView = true;

        RMR_Calculation rmrCalculation = getRMRCalculationContext();

        RMRInput input = new RMRInput();
        if (rmrCalculation.getStrengthOfRock() != null) {
            input.setStrength(rmrCalculation.getStrengthOfRock().getValue());
        }
//        if (rmrCalculation.getRqd() == null) {
//            //Pull RQD from QBartonCalculation
//            RQD rqd = (RQD) getQCalculationContext().getRqd();
//            RQD_RMR rqdRMR = RQD_RMR.findWrapper(rqd);
//            rmrCalculation.setRqd(rqdRMR);
//        }
        if (rmrCalculation.getRqd() != null) {
            input.setRqd(Double.valueOf(rmrCalculation.getRqd().getValue()));
        }
        if (rmrCalculation.getSpacing() != null) {
            input.setSpacing(rmrCalculation.getSpacing().getValue());
        }
        if (rmrCalculation.getRoughness() != null) {
            input.setRoughness(rmrCalculation.getRoughness().getValue());
        }
        if (rmrCalculation.getAperture() != null) {
            input.setAperture(rmrCalculation.getAperture().getValue());
        }
        if (rmrCalculation.getInfilling() != null) {
            input.setInfilling(rmrCalculation.getInfilling().getValue());
        }
        if (rmrCalculation.getWeathering() != null) {
            input.setWeathering(rmrCalculation.getWeathering().getValue());
        }
        if (rmrCalculation.getPersistence() != null) {
            input.setPersistence(rmrCalculation.getPersistence().getValue());
        }
        if (rmrCalculation.getGroundwater() != null) {
            input.setGroundwater(rmrCalculation.getGroundwater().getValue());
        }
        if (rmrCalculation.getOrientationDiscontinuities() != null) {
            input.setOrientation(rmrCalculation.getOrientationDiscontinuities().getValue());
        }
        if (input.isComplete()) {
            // Inflate the layout for this fragment
            viewRoot = inflater.inflate(R.layout.calculator_rmr_results_fragment, container, false);

            TextView rmrValueTextView = (TextView) viewRoot.findViewById(R.id.rmr_results_rmr);
            RMROutput output;
            try {
                output = RMRCalculator.calculate(input);
                getRMRCalculationContext().setRMRResult(output);
                rmrValueTextView.setText(String.format("%.2f", output.getRMR()));
            } catch (Exception e) {
                Log.e(SkavaConstants.LOG, e.getMessage(), e);
                e.printStackTrace();
            }
        } else {
            showResultsView = false;

            viewRoot = inflater.inflate(R.layout.calculator_rmr_missing_input_fragment, container,
                    false);

            NumberFormat nf = DecimalFormat.getInstance();

            TextView strengthTextView = (TextView) viewRoot.findViewById(R.id.strength_results_strength);
            strengthTextView.setText(rmrCalculation.getStrengthOfRock() != null ? nf.format(input.getStrength()) : "Not yet selected");

            TextView rqdTextView = (TextView) viewRoot.findViewById(R.id.rqd_results_rqd);
            rqdTextView.setText(rmrCalculation.getRqd() != null ? nf.format(input.getRqd()) : "Not yet selected");

            TextView spacingTextView = (TextView) viewRoot.findViewById(R.id.spacing_results_spacing);
            spacingTextView.setText(rmrCalculation.getSpacing() != null ? nf.format(input.getSpacing()) : "Not yet selected");

            TextView persistenceTextView = (TextView) viewRoot.findViewById(R.id.condition_results_persistence_value);
            persistenceTextView.setText(rmrCalculation.getPersistence() != null ? nf.format(input.getPersistence()) : "Not yet selected");

            TextView apertureTextView = (TextView) viewRoot.findViewById(R.id.condition_results_aperture_value);
            apertureTextView.setText(rmrCalculation.getAperture() != null ? nf.format(input.getAperture()) : "Not yet selected");

            TextView roughnessTextView = (TextView) viewRoot.findViewById(R.id.condition_results_roughness_value);
            roughnessTextView.setText(rmrCalculation.getRoughness() != null ? nf.format(input.getRoughness()) : "Not yet selected");

            TextView infillingTextView = (TextView) viewRoot.findViewById(R.id.condition_results_infilling_value);
            infillingTextView.setText(rmrCalculation.getInfilling() != null ? nf.format(input.getInfilling()) : "Not yet selected");

            TextView weatheringTextView = (TextView) viewRoot.findViewById(R.id.condition_results_weathering_value);
            weatheringTextView.setText(rmrCalculation.getWeathering() != null ? nf.format(input.getWeathering()) : "Not yet selected");

            TextView groundwaterTextView = (TextView) viewRoot.findViewById(R.id.groundwater_results_groundwater);
            groundwaterTextView.setText(rmrCalculation.getGroundwater() != null ? nf.format(input.getGroundwater()) : "Not yet selected");

            TextView orientationTextView = (TextView) viewRoot.findViewById(R.id.orientation_results_orientation);
            orientationTextView.setText(rmrCalculation.getOrientationDiscontinuities() != null ? nf.format(input.getOrientation()) : "Not yet selected");

        }

        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface iconTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Android-Dev-Icons-1.ttf");
        if (showResultsView) {
            TextView icon = (TextView) view.findViewById(R.id.rmr_results_rmr_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        } else {
            TextView icon = (TextView) view.findViewById(R.id.strength_results_strength_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            icon = (TextView) view.findViewById(R.id.rqd_results_rqd_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            icon = (TextView) view.findViewById(R.id.spacing_results_spacing_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            icon = (TextView) view.findViewById(R.id.condition_results_condition_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            icon = (TextView) view.findViewById(R.id.groundwater_results_groundwater_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            icon = (TextView) view.findViewById(R.id.orientation_results_orientation_label_icon);
            icon.setTypeface(iconTypeFace);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        }

    }
}
