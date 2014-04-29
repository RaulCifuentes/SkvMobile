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
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.helper.QToQualityMapper;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.SupportRequirementDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportRecomendation;
import com.metric.skava.rockmass.model.RockMass;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructionsMainFragment extends SkavaFragment {

    public InstructionsMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.instructions_main_fragment, container, false);

        Assessment assessment = getCurrentAssessment();
        SupportRecomendation assessmentRecomendation = assessment.getRecomendation();

        SupportRequirement supportRequirement = getSupportRequirement();
        SupportRecomendation supportRecomendation = mapSupportRequirementToSupportRecommendation(supportRequirement);
        if (supportRecomendation == null) {
            supportRecomendation = new SupportRecomendation();
        }

        // Use either the current recomendation for the assessment, or use the one mapped
        // from the requirements
        if (assessmentRecomendation == null) {
            assessment.setRecomendation(supportRecomendation);
        } else {
            supportRecomendation = assessmentRecomendation;
        }

        mapSupportRecommendationToView(supportRecomendation, rootView);

        wireEventsForObservations(rootView);

        return rootView;
    }

    private void wireEventsForObservations(View rootView) {
        EditText observationsEditText = ((EditText) rootView.findViewById(R.id.instructions_observations_value));

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

    private SupportRecomendation mapSupportRequirementToSupportRecommendation(SupportRequirement supportRequirement) {
        if (supportRequirement == null) {
            return null;
        }

        SupportRecomendation sr = new SupportRecomendation();

        BoltType boltType = supportRequirement.getBoltType();
        if (boltType != null) {
            sr.setBoltType(boltType);
        }

        Double boltDiameter = supportRequirement.getDiameter();
        if (boltDiameter != null) {
            sr.setBoltDiameter(boltDiameter);
        }

        Double boltLength = supportRequirement.getLength();
        if (boltLength != null) {
            sr.setBoltLength(boltLength);
        }

        ShotcreteType shotcreteType = supportRequirement.getShotcreteType();
        if (shotcreteType != null) {
            sr.setShotcreteType(shotcreteType);
        }

        Double thickness = supportRequirement.getThickness();
        if (thickness != null) {
            sr.setThickness(thickness);
        }

        MeshType meshType = supportRequirement.getMeshType();
        if (meshType != null) {
            sr.setMeshType(meshType);
        }

        Coverage coverage = supportRequirement.getCoverage();
        if (coverage != null) {
            sr.setCoverage(coverage);
        }

        ArchType archType = supportRequirement.getArchType();
        if (meshType != null) {
            sr.setArchType(archType);
        }

        Double separation = supportRequirement.getSeparation();
        if (thickness != null) {
            sr.setSeparation(separation);
        }

        return sr;
    }

    private void mapSupportRecommendationToView(SupportRecomendation supportRecommendation, View rootView) {
        NumberFormat numberFormat = DecimalFormat.getNumberInstance();

        BoltType boltType = supportRecommendation.getBoltType();
        if (boltType != null) {
            ((TextView) rootView.findViewById(R.id.instructions_bolt_type_value)).setText(boltType.getName());
        }

        Double boltDiameter = supportRecommendation.getBoltDiameter();
        if (boltDiameter != null) {
            ((TextView) rootView.findViewById(R.id.instructions_bolt_diameter_value)).setText(numberFormat.format(boltDiameter));
        }

        Double boltLength = supportRecommendation.getBoltLength();
        if (boltLength != null) {
            ((TextView) rootView.findViewById(R.id.instructions_bolt_length_value)).setText(numberFormat.format(boltLength));
        }

        ShotcreteType shotcreteType = supportRecommendation.getShotcreteType();
        if (shotcreteType != null) {
            ((TextView) rootView.findViewById(R.id.instructions_shotcrete_type_value)).setText(shotcreteType.getName());
        }

        Double thickness = supportRecommendation.getThickness();
        if (thickness != null) {
            ((TextView) rootView.findViewById(R.id.instructions_thickness_value)).setText(numberFormat.format(thickness));
        }

        MeshType meshType = supportRecommendation.getMeshType();
        if (meshType != null) {
            ((TextView) rootView.findViewById(R.id.instructions_mesh_type_value)).setText(meshType.getName());
        }

        Coverage coverage = supportRecommendation.getCoverage();
        if (coverage != null) {
            ((TextView) rootView.findViewById(R.id.instructions_coverage_spinner)).setText(coverage.getName());
        }

        ArchType archType = supportRecommendation.getArchType();
        if (meshType != null) {
            ((TextView) rootView.findViewById(R.id.instructions_arch_type_value)).setText(archType.getName());
        }

        Double separation = supportRecommendation.getSeparation();
        if (thickness != null) {
            ((TextView) rootView.findViewById(R.id.instructions_separation_value)).setText(numberFormat.format(separation));
        }

        String observations = supportRecommendation.getObservations();
        if (observations != null) {
            ((TextView) rootView.findViewById(R.id.instructions_observations_value)).setText(observations);
        }
    }

    private SupportRequirement getSupportRequirement() {
        DAOFactory daoFactory = DAOFactory.getInstance(getActivity());

        SupportRequirementDAO supportRequirementDAO = daoFactory.getSupportRequirementDAO();

        Tunnel tunnel = getCurrentAssessment().getTunnel();
        Double qValue = null;
        if (getQCalculationContext()!= null){
            if (getQCalculationContext().getQResult() != null )
            qValue = getQCalculationContext().getQResult().getQBarton();
        } else if (getRMRCalculationContext() != null) {
            if (getRMRCalculationContext().getRMRResult() != null )
            qValue = getRMRCalculationContext().getRMRResult().getRMR();
        }

        QToQualityMapper qMapper = QToQualityMapper.getInstance();
        RockMass.RockMassQualityType quality =  qMapper.mapQToRockMassQuality(qValue);

        SupportRequirement supportRequirement = null;
        if (tunnel != null) {
            try {
                supportRequirement = supportRequirementDAO.getSupportRequirementByTunnel(tunnel, quality);
            } catch (DAOException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            }
        } else {
            Toast.makeText(getActivity(), "Support recomendations requires a face tunnel selection.", Toast.LENGTH_LONG);
        }
        return supportRequirement;
    }
}