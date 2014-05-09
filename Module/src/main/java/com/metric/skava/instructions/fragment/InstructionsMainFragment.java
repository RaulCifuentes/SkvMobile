package com.metric.skava.instructions.fragment;

/**
 * Created by metricboy on 2/27/14.
 */

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.logic.RecomendationProvider;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportRecomendation;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructionsMainFragment extends SkavaFragment {

    private View mRootView;
    
    public InstructionsMainFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.instructions_main_fragment, container, false);

        SupportRecomendation assessmentRecomendation = getCurrentAssessment().getRecomendation();
        SupportRecomendation supportRecomendation = null;
        // Use either the current recomendation for the assessment, or use the one mapped
        // from the requirements
        if (assessmentRecomendation == null) {
            RecomendationProvider provider = new RecomendationProvider(getSkavaContext());
            try {
                supportRecomendation = provider.recomend(getCurrentAssessment());
            } catch (DAOException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getSkavaActivity(), e.getMessage(), Toast.LENGTH_LONG);
            }
            getCurrentAssessment().setRecomendation(supportRecomendation);
        }
        mapSupportRecommendationToView();
        wireEventsForObservations();
        return mRootView;
    }

    private void wireEventsForObservations() {
        EditText observationsEditText = ((EditText) mRootView.findViewById(R.id.instructions_observations_value));

        observationsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editableValue = editable.toString();
                getCurrentAssessment().getRecomendation().setObservations(editableValue);
            }
        });
    }


    private void mapSupportRecommendationToView() {

        SupportRecomendation supportRecommendation = getCurrentAssessment().getRecomendation();

        NumberFormat numberFormat = DecimalFormat.getNumberInstance();

        BoltType boltType = supportRecommendation.getBoltType();
        if (boltType != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_bolt_type_value)).setText(boltType.getName());
        }

        Double boltDiameter = supportRecommendation.getBoltDiameter();
        if (boltDiameter != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_bolt_diameter_value)).setText(numberFormat.format(boltDiameter));
        }

        Double boltLength = supportRecommendation.getBoltLength();
        if (boltLength != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_bolt_length_value)).setText(numberFormat.format(boltLength));
        }

        ShotcreteType shotcreteType = supportRecommendation.getShotcreteType();
        if (shotcreteType != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_shotcrete_type_value)).setText(shotcreteType.getName());
        }

        SupportPattern roofPattern = supportRecommendation.getRoofPattern();
        if (roofPattern != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_roof_pattern_value)).setText(roofPattern.getType().getName() + " " + roofPattern.getDistanceX() + " x " + roofPattern.getDistanceY()) ;
        }

        SupportPattern wallPattern = supportRecommendation.getWallPattern();
        if (wallPattern != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_wall_pattern_value)).setText(wallPattern.getType().getName() + " " + wallPattern.getDistanceX() + " x " + wallPattern.getDistanceY()) ;
        }

        Double thickness = supportRecommendation.getThickness();
        if (thickness != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_thickness_value)).setText(numberFormat.format(thickness));
        }

        MeshType meshType = supportRecommendation.getMeshType();
        if (meshType != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_mesh_type_value)).setText(meshType.getName());
        }

        Coverage coverage = supportRecommendation.getCoverage();
        if (coverage != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_coverage_spinner)).setText(coverage.getName());
        }

        ArchType archType = supportRecommendation.getArchType();
        if (meshType != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_arch_type_value)).setText(archType.getName());
        }

        Double separation = supportRecommendation.getSeparation();
        if (thickness != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_separation_value)).setText(numberFormat.format(separation));
        }

        String observations = supportRecommendation.getObservations();
        if (observations != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_observations_value)).setText(observations);
        }
    }


}