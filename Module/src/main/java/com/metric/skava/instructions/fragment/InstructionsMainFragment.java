package com.metric.skava.instructions.fragment;

/**
 * Created by metricboy on 2/27/14.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.validator.TextValidator;
import com.metric.skava.calculator.barton.helper.QToQualityMapper;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.SupportRequirementDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
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

        DAOFactory daoFactory = getDAOFactory();

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

        if (supportRequirement != null) {

            NumberFormat numberFormat = DecimalFormat.getNumberInstance();

            BoltType boltType = supportRequirement.getBoltType();
            if (boltType != null) {
                ((TextView) rootView.findViewById(R.id.instructions_bolt_type_value)).setText(boltType.getName());
            }

            Double boltDiameter = supportRequirement.getDiameter();
            if (boltDiameter != null) {
                ((TextView) rootView.findViewById(R.id.instructions_bolt_diameter_value)).setText(numberFormat.format(boltDiameter));
            }

            Double boltLength = supportRequirement.getLength();
            if (boltLength != null) {
                ((TextView) rootView.findViewById(R.id.instructions_bolt_length_value)).setText(numberFormat.format(boltLength));
            }

            ShotcreteType shotcreteType = supportRequirement.getShotcreteType();
            if (shotcreteType != null) {
                ((TextView) rootView.findViewById(R.id.instructions_shotcrete_type_value)).setText(shotcreteType.getName());
            }

            Double thickness = supportRequirement.getThickness();
            if (thickness != null) {
                ((TextView) rootView.findViewById(R.id.instructions_thickness_value)).setText(numberFormat.format(thickness));
            }

            MeshType meshType = supportRequirement.getMeshType();
            if (meshType != null) {
                ((TextView) rootView.findViewById(R.id.instructions_mesh_type_value)).setText(meshType.getName());
            }

            Coverage coverage = supportRequirement.getCoverage();
            if (coverage != null) {
                ((TextView) rootView.findViewById(R.id.instructions_coverage_spinner)).setText(coverage.getName());
            }

            ArchType archType = supportRequirement.getArchType();
            if (meshType != null) {
                ((TextView) rootView.findViewById(R.id.instructions_arch_type_value)).setText(archType.getName());
            }

            Double separation = supportRequirement.getSeparation();
            if (thickness != null) {
                ((TextView) rootView.findViewById(R.id.instructions_separation_value)).setText(numberFormat.format(separation));
            }



            //TODO salvar esta info asociada al assessment
            EditText observationsEditText = ((EditText) rootView.findViewById(R.id.instructions_observations_value));

            observationsEditText.addTextChangedListener(new TextValidator(observationsEditText) {
                @Override public void validate(TextView textView, String text) {

                }
            });



        }
        return rootView;
    }
}