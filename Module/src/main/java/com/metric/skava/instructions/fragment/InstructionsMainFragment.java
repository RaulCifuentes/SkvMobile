package com.metric.skava.instructions.fragment;

/**
 * Created by metricboy on 2/27/14.
 */

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.adapter.SkavaEntityAdapter;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.validator.TextValidator;
import com.metric.skava.data.dao.LocalArchTypeDAO;
import com.metric.skava.data.dao.LocalBoltTypeDAO;
import com.metric.skava.data.dao.LocalCoverageDAO;
import com.metric.skava.data.dao.LocalMeshTypeDAO;
import com.metric.skava.data.dao.LocalShotcreteTypeDAO;
import com.metric.skava.data.dao.LocalSupportPatternTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.logic.RecomendationProvider;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportPatternType;
import com.metric.skava.instructions.model.SupportRecommendation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class InstructionsMainFragment extends SkavaFragment implements AdapterView.OnItemSelectedListener {

    private View mRootView;

    private Spinner boltTypeSpinner;
    private int boltTypeSpinnerLastPosition;
    private BoltType selectedBoltType;
    private SkavaEntityAdapter boltTypeAdapter;

    private Spinner shotcreteTypeSpinner;
    private int shotcreteTypeSpinnerLastPosition;
    private ShotcreteType selectedShotcreteType;
    private SkavaEntityAdapter shotcreteTypeAdapter;

    private Spinner shotcreteCoverageSpinner;
    private int shotcreteCoverageSpinnerLastPosition;
    private Coverage selectedShotcreteCoverage;
    private SkavaEntityAdapter shotcreteCoverageAdapter;

    private Spinner meshTypeSpinner;
    private int meshTypeSpinnerLastPosition;
    private MeshType selectedMeshType;
    private SkavaEntityAdapter meshTypeAdapter;

    private Spinner meshCoverageSpinner;
    private int meshCoverageSpinnerLastPosition;
    private Coverage selectedMeshCoverage;
    private SkavaEntityAdapter meshCoverageAdapter;

    private Spinner archTypeSpinner;
    private int archTypeSpinnerLastPosition;
    private ArchType selectedArchType;
    private SkavaEntityAdapter archTypeAdapter;

    private EditText boltDiameterEditText;
    private EditText boltLengthEditText;
    private EditText separationEditText;
    private EditText thicknessEditText;

    private int roofPatternSpinnerLastPosition;
    private SupportPatternType selectedRoofPatternType;
    private SkavaEntityAdapter roofPatternAdapter;
    private Spinner roofPatternSpinner;
    private EditText roofPatternDx;
    private EditText roofPatternDy;

    private int wallPatternSpinnerLastPosition;
    private SupportPatternType selectedWallPatternType;
    private SkavaEntityAdapter wallPatternAdapter;
    private Spinner wallPatternSpinner;
    private EditText wallPatternDx;
    private EditText wallPatternDy;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.archTypeSpinnerLastPosition = -1;
        this.boltTypeSpinnerLastPosition = -1;
        this.meshCoverageSpinnerLastPosition = -1;
        this.meshTypeSpinnerLastPosition = -1;
        this.roofPatternSpinnerLastPosition = -1;
        this.shotcreteTypeSpinnerLastPosition = -1;
        this.wallPatternSpinnerLastPosition = -1;

        //******BOLT_TYPE ***///
        List<BoltType> boltTypeList;
        LocalBoltTypeDAO localBoltTypeDAO = null;
        try {
            localBoltTypeDAO = getDAOFactory().getLocalBoltTypeDAO();
            boltTypeList = localBoltTypeDAO.getAllBoltTypes();
            boltTypeList.add(new BoltType("HINT", "Select one type ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        boltTypeAdapter = new SkavaEntityAdapter<BoltType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, boltTypeList);
        // Specify the layout to use when the list of choices appears
        boltTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        //******SHOTCRETE_TYPE ***///
        List<ShotcreteType> shotcreteTypeList;
        LocalShotcreteTypeDAO localshotcreteTypeDAO = null;
        try {
            localshotcreteTypeDAO = getDAOFactory().getLocalShotcreteTypeDAO();
            shotcreteTypeList = localshotcreteTypeDAO.getAllShotcreteTypes();
            shotcreteTypeList.add(new ShotcreteType("HINT", "Select one type ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        shotcreteTypeAdapter = new SkavaEntityAdapter<ShotcreteType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, shotcreteTypeList);
        // Specify the layout to use when the list of choices appears
        shotcreteTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        //******SHOTCRETE COVERAGE_TYPE ***///
        List<Coverage> shotcreteCoverageList;
        LocalCoverageDAO localShotcreteCoverageDAO = null;
        try {
            localShotcreteCoverageDAO = getDAOFactory().getLocalCoverageDAO();
            shotcreteCoverageList = localShotcreteCoverageDAO.getAllCoverages();
            shotcreteCoverageList.add(new Coverage("HINT", "Select one type ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        shotcreteCoverageAdapter = new SkavaEntityAdapter<Coverage>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, shotcreteCoverageList);
        // Specify the layout to use when the list of choices appears
        shotcreteCoverageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner


        //******MESH_TYPE ***///
        List<MeshType> meshTypeList;
        LocalMeshTypeDAO localMeshTypeDAO = null;
        try {
            localMeshTypeDAO = getDAOFactory().getLocalMeshTypeDAO();
            meshTypeList = localMeshTypeDAO.getAllMeshTypes();
            meshTypeList.add(new MeshType("HINT", "Select one type ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        meshTypeAdapter = new SkavaEntityAdapter<MeshType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, meshTypeList);
        // Specify the layout to use when the list of choices appears
        meshTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        //******MESH COVERAGE_TYPE ***///
        List<Coverage> meshCoverageList;
        LocalCoverageDAO localMeshCoverageDAO = null;
        try {
            localMeshCoverageDAO = getDAOFactory().getLocalCoverageDAO();
            meshCoverageList = localMeshCoverageDAO.getAllCoverages();
            meshCoverageList.add(new Coverage("HINT", "Select one type ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        meshCoverageAdapter = new SkavaEntityAdapter<Coverage>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, meshCoverageList);
        // Specify the layout to use when the list of choices appears
        meshCoverageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        //******MESH_TYPE ***///
        List<ArchType> archTypeList;
        LocalArchTypeDAO localArchTypeDAO = null;
        try {
            localArchTypeDAO = getDAOFactory().getLocalArchTypeDAO();
            archTypeList = localArchTypeDAO.getAllArchTypes();
            archTypeList.add(new ArchType("HINT", "Select one type ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        archTypeAdapter = new SkavaEntityAdapter<ArchType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, archTypeList);
        // Specify the layout to use when the list of choices appears
        archTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner


        //******PATTERN_TYPE ***///
        List<SupportPatternType> roofPatternTypeList;
        LocalSupportPatternTypeDAO supportPatternTypeDAO = null;
        try {
            supportPatternTypeDAO = getDAOFactory().getLocalSupportPatternTypeDAO();
            roofPatternTypeList = supportPatternTypeDAO.getAllSupportPatternTypes(SupportPatternType.Group.ROOF);
            roofPatternTypeList.add(new SupportPatternType("HINT", "Select one type ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        roofPatternAdapter = new SkavaEntityAdapter<SupportPatternType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, roofPatternTypeList);
        // Specify the layout to use when the list of choices appears
        roofPatternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        List<SupportPatternType> wallPatternTypeList;
        try {
            wallPatternTypeList = supportPatternTypeDAO.getAllSupportPatternTypes(SupportPatternType.Group.WALL);
            wallPatternTypeList.add(new SupportPatternType("HINT", "Select one type ..."));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        wallPatternAdapter = new SkavaEntityAdapter<SupportPatternType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, wallPatternTypeList);
        // Specify the layout to use when the list of choices appears
        wallPatternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.instructions_main_fragment, container, false);

        boltTypeSpinner = (Spinner) mRootView.findViewById(R.id.instructions_bolt_type_spinner);
        boltTypeSpinner.setAdapter(boltTypeAdapter);
        boltTypeSpinner.setOnItemSelectedListener(this);

        boltDiameterEditText = (EditText) mRootView.findViewById(R.id.instructions_bolt_diameter_value);
        boltDiameterEditText.setRawInputType(Configuration.KEYBOARD_12KEY);

        boltLengthEditText = (EditText) mRootView.findViewById(R.id.instructions_bolt_length_value);
        boltLengthEditText.setRawInputType(Configuration.KEYBOARD_12KEY);

        shotcreteTypeSpinner = (Spinner) mRootView.findViewById(R.id.instructions_shotcrete_type_spinner);
        shotcreteTypeSpinner.setAdapter(shotcreteTypeAdapter);
        shotcreteTypeSpinner.setOnItemSelectedListener(this);

        shotcreteCoverageSpinner = (Spinner) mRootView.findViewById(R.id.instructions_shotcrete_coverage_spinner);
        shotcreteCoverageSpinner.setAdapter(shotcreteCoverageAdapter);
        shotcreteCoverageSpinner.setOnItemSelectedListener(this);

        roofPatternSpinner = (Spinner) mRootView.findViewById(R.id.instructions_roof_pattern_spinner);
        roofPatternSpinner.setAdapter(roofPatternAdapter);
        roofPatternSpinner.setOnItemSelectedListener(this);

        roofPatternDx = (EditText) mRootView.findViewById(R.id.instructions_roof_pattern_dx);
        roofPatternDx.setRawInputType(Configuration.KEYBOARD_12KEY);
        roofPatternDy = (EditText) mRootView.findViewById(R.id.instructions_roof_pattern_dy);
        roofPatternDy.setRawInputType(Configuration.KEYBOARD_12KEY);

        wallPatternSpinner = (Spinner) mRootView.findViewById(R.id.instructions_wall_pattern_spinner);
        wallPatternSpinner.setAdapter(wallPatternAdapter);
        wallPatternSpinner.setOnItemSelectedListener(this);

        wallPatternDx = (EditText) mRootView.findViewById(R.id.instructions_wall_pattern_dx);
        wallPatternDx.setRawInputType(Configuration.KEYBOARD_12KEY);
        wallPatternDy = (EditText) mRootView.findViewById(R.id.instructions_wall_pattern_dy);
        wallPatternDy.setRawInputType(Configuration.KEYBOARD_12KEY);

        thicknessEditText = (EditText) mRootView.findViewById(R.id.instructions_thickness_value);
        thicknessEditText.setRawInputType(Configuration.KEYBOARD_12KEY);

        meshTypeSpinner = (Spinner) mRootView.findViewById(R.id.instructions_mesh_type_spinner);
        meshTypeSpinner.setAdapter(meshTypeAdapter);
        meshTypeSpinner.setOnItemSelectedListener(this);

        meshCoverageSpinner = (Spinner) mRootView.findViewById(R.id.instructions_mesh_coverage_spinner);
        meshCoverageSpinner.setAdapter(meshCoverageAdapter);
        meshCoverageSpinner.setOnItemSelectedListener(this);

        archTypeSpinner = (Spinner) mRootView.findViewById(R.id.instructions_arch_type_spinner);
        archTypeSpinner.setAdapter(archTypeAdapter);
        archTypeSpinner.setOnItemSelectedListener(this);

        separationEditText = (EditText) mRootView.findViewById(R.id.instructions_separation_value);
        separationEditText.setRawInputType(Configuration.KEYBOARD_12KEY);


        SupportRecommendation currentRecommendation = getCurrentAssessment().getRecomendation();
        // Use either the current recomendation for the assessment, or use the one mapped
        // from the requirements
        if (currentRecommendation == null) {
            SupportRecommendation supportRecomendation = null;
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


    private void mapSupportRecommendationToView() {

        NumberFormat numberFormatter = DecimalFormat.getNumberInstance();

        final SupportRecommendation supportRecommendation = getCurrentAssessment().getRecomendation();

        if (supportRecommendation.getRequirementBase() != null) {
            String name = supportRecommendation.getRequirementBase().getName();
            TextView label = (TextView) mRootView.findViewById(R.id.instructions_requirement_base_label);
            TextView base = (TextView) mRootView.findViewById(R.id.instructions_requirement_base_value);
            label.setVisibility(View.VISIBLE);
            base.setVisibility(View.VISIBLE);
            base.setText(name);
        }

        BoltType boltType = supportRecommendation.getBoltType();
        if (boltType != null) {
            boltTypeSpinner.setSelection(boltTypeAdapter.getPosition(boltType)); //display hint
        } else {
            boltTypeSpinner.setSelection(boltTypeAdapter.getCount() - 1); //display hint
        }

        Double boltDiameter = supportRecommendation.getBoltDiameter();
        if (boltDiameter != null) {
            boltDiameterEditText.setText(numberFormatter.format(boltDiameter));
        }

        boltDiameterEditText.addTextChangedListener(new TextValidator(boltDiameterEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                try {
                    Double enteredValue = Double.parseDouble(text);
                    supportRecommendation.setBoltDiameter(enteredValue);
                } catch (NumberFormatException e) {
                    boltDiameterEditText.setError("Diameter must be a number!");
                }
            }
        });

        Double boltLength = supportRecommendation.getBoltLength();
        if (boltLength != null) {
            boltLengthEditText.setText(numberFormatter.format(boltLength));
        }

        boltLengthEditText.addTextChangedListener(new TextValidator(boltLengthEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                try {
                    Double enteredValue = Double.parseDouble(text);
                    supportRecommendation.setBoltLength(enteredValue);
                } catch (NumberFormatException e) {
                    boltLengthEditText.setError("Length must be a number!");
                }
            }
        });

        ShotcreteType shotcreteType = supportRecommendation.getShotcreteType();
        if (shotcreteType != null) {
            shotcreteTypeSpinner.setSelection(shotcreteTypeAdapter.getPosition(shotcreteType)); //display hint
        } else {
            shotcreteTypeSpinner.setSelection(shotcreteTypeAdapter.getCount() - 1); //display hint
        }

        Coverage shotcreteCoverage = supportRecommendation.getShotcreteCoverage();
        if (shotcreteCoverage != null) {
            shotcreteCoverageSpinner.setSelection(shotcreteCoverageAdapter.getPosition(shotcreteCoverage)); //display hint
        } else {
            shotcreteCoverageSpinner.setSelection(shotcreteCoverageAdapter.getCount() - 1); //display hint
        }

        SupportPattern roofPattern = supportRecommendation.getRoofPattern();
        roofPatternSpinner.setOnItemSelectedListener(this);
        if (roofPattern != null) {
            roofPatternSpinner.setSelection(roofPatternAdapter.getPosition(roofPattern.getType())); //display hint
            roofPatternDx.setText(numberFormatter.format(roofPattern.getDistanceX()));
            roofPatternDy.setText(numberFormatter.format(roofPattern.getDistanceY()));
        } else {
            roofPatternSpinner.setSelection(roofPatternAdapter.getCount() - 1); //display hint
            roofPatternDx.setEnabled(false);
            roofPatternDy.setEnabled(false);
        }
        roofPatternDx.addTextChangedListener(new TextValidator(roofPatternDx) {
            @Override
            public void validate(TextView textView, String text) {
                try {
                    Double enteredValue = Double.parseDouble(text);
                    if (supportRecommendation.getRoofPattern() != null) {
                        supportRecommendation.getRoofPattern().setDistanceX(enteredValue);
                    } else {
                        supportRecommendation.setRoofPattern(new SupportPattern(null, enteredValue, 0d));
                    }
                } catch (NumberFormatException nfe) {
                    roofPatternDx.setError("Length must be a number!");
                }
            }
        });
        roofPatternDy.addTextChangedListener(new TextValidator(roofPatternDy) {
            @Override
            public void validate(TextView textView, String text) {
                try {
                    Double enteredValue = Double.parseDouble(text);
                    if (supportRecommendation.getRoofPattern() != null) {
                        supportRecommendation.getRoofPattern().setDistanceY(enteredValue);
                    } else {
                        supportRecommendation.setRoofPattern(new SupportPattern(null, 0d, enteredValue));
                    }
                } catch (NumberFormatException nfe) {
                    roofPatternDy.setError("Length must be a number!");
                }
            }
        });


        SupportPattern wallPattern = supportRecommendation.getWallPattern();
        if (wallPattern != null) {
            wallPatternSpinner.setSelection(wallPatternAdapter.getPosition(wallPattern.getType())); //display hint
            wallPatternDx.setText(numberFormatter.format(wallPattern.getDistanceX()));
            wallPatternDy.setText(numberFormatter.format(wallPattern.getDistanceY()));
        } else {
            wallPatternSpinner.setSelection(wallPatternAdapter.getCount() - 1); //display hint
            wallPatternDx.setEnabled(false);
            wallPatternDy.setEnabled(false);
        }
        wallPatternDx.addTextChangedListener(new TextValidator(wallPatternDx) {
            @Override
            public void validate(TextView textView, String text) {
                try {
                    Double enteredValue = Double.parseDouble(text);
                    if (supportRecommendation.getWallPattern() != null) {
                        supportRecommendation.getWallPattern().setDistanceX(enteredValue);
                    } else {
                        supportRecommendation.setWallPattern(new SupportPattern(null, enteredValue, 0d));
                    }
                } catch (NumberFormatException nfe) {
                    wallPatternDx.setError("Length must be a number!");
                }
            }
        });
        wallPatternDy.addTextChangedListener(new TextValidator(wallPatternDy) {
            @Override
            public void validate(TextView textView, String text) {
                try {
                    Double enteredValue = Double.parseDouble(text);
                    if (supportRecommendation.getWallPattern() != null) {
                        supportRecommendation.getWallPattern().setDistanceY(enteredValue);
                    } else {
                        supportRecommendation.setWallPattern(new SupportPattern(null, 0d, enteredValue));
                    }
                } catch (NumberFormatException nfe) {
                    wallPatternDy.setError("Length must be a number!");
                }
            }
        });


        Double thickness = supportRecommendation.getThickness();
        if (thickness != null) {
            thicknessEditText.setText(numberFormatter.format(thickness));
        }

        thicknessEditText.addTextChangedListener(new TextValidator(thicknessEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                try {
                    Double enteredValue = Double.parseDouble(text);
                    supportRecommendation.setThickness(enteredValue);
                } catch (NumberFormatException e) {
                    thicknessEditText.setError("Thickness must be a number!");
                }
            }
        });


        MeshType meshType = supportRecommendation.getMeshType();
        if (meshType != null) {
            meshTypeSpinner.setSelection(meshTypeAdapter.getPosition(meshType)); //display hint
        } else {
            meshTypeSpinner.setSelection(meshTypeAdapter.getCount() - 1); //display hint
        }

        Coverage meshCoverage = supportRecommendation.getMeshCoverage();
        if (meshCoverage != null) {
            meshCoverageSpinner.setSelection(meshCoverageAdapter.getPosition(meshCoverage)); //display hint
        } else {
            meshCoverageSpinner.setSelection(meshCoverageAdapter.getCount() - 1); //display hint
        }

        ArchType archType = supportRecommendation.getArchType();
        if (archType != null) {
            archTypeSpinner.setSelection(archTypeAdapter.getPosition(archType)); //display hint
        } else {
            archTypeSpinner.setSelection(archTypeAdapter.getCount() - 1); //display hint
        }

        Double separation = supportRecommendation.getSeparation();
        if (separation != null) {
            separationEditText.setText(separation.toString());
        }

        separationEditText.addTextChangedListener(new TextValidator(separationEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                try {
                    Double enteredValue = Double.parseDouble(text);
                    supportRecommendation.setSeparation(enteredValue);
                } catch (NumberFormatException e) {
                    separationEditText.setError("Separation must be a number!");
                }
            }
        });

        String observations = supportRecommendation.getObservations();
        if (observations != null) {
            ((TextView) mRootView.findViewById(R.id.instructions_observations_value)).setText(observations);
        }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == boltTypeSpinner) {
            if (position != boltTypeSpinner.getAdapter().getCount() && position != boltTypeSpinnerLastPosition) {
                selectedBoltType = (BoltType) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().getRecomendation().setBoltType(selectedBoltType);
                boltTypeSpinnerLastPosition = position;
            }
        }
        if (parent == shotcreteTypeSpinner) {
            if (position != shotcreteTypeSpinner.getAdapter().getCount() && position != shotcreteTypeSpinnerLastPosition) {
                selectedShotcreteType = (ShotcreteType) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().getRecomendation().setShotcreteType(selectedShotcreteType);
                shotcreteTypeSpinnerLastPosition = position;
            }
        }
        if (parent == shotcreteCoverageSpinner) {
            if (position != shotcreteCoverageSpinner.getAdapter().getCount() && position != shotcreteCoverageSpinnerLastPosition) {
                selectedShotcreteCoverage = (Coverage) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().getRecomendation().setShotcreteCoverage(selectedShotcreteCoverage);
                shotcreteCoverageSpinnerLastPosition = position;
            }
        }
        if (parent == roofPatternSpinner) {
            if (position != roofPatternSpinner.getAdapter().getCount() && position != roofPatternSpinnerLastPosition) {
                SupportRecommendation recommendation = getSkavaContext().getAssessment().getRecomendation();
                if (recommendation != null) {
                    selectedRoofPatternType = (SupportPatternType) parent.getItemAtPosition(position);
                    SupportPattern supportPattern;
                    if (recommendation.getRoofPattern() != null) {
                        supportPattern = recommendation.getRoofPattern();
                        supportPattern.setType(selectedRoofPatternType);
                    } else {
                        supportPattern = new SupportPattern(selectedRoofPatternType, 0d, 0d);
                        recommendation.setRoofPattern(supportPattern);
                    }
                }
                roofPatternDx.setEnabled(true);
                roofPatternDy.setEnabled(true);
                roofPatternSpinnerLastPosition = position;
            }
        }
        if (parent == wallPatternSpinner) {
            if (position != wallPatternSpinner.getAdapter().getCount() && position != wallPatternSpinnerLastPosition) {
                SupportRecommendation recommendation = getSkavaContext().getAssessment().getRecomendation();
                if (recommendation != null) {
                    selectedWallPatternType = (SupportPatternType) parent.getItemAtPosition(position);
                    SupportPattern supportPattern;
                    if (recommendation.getWallPattern() != null) {
                        supportPattern = recommendation.getWallPattern();
                        supportPattern.setType(selectedWallPatternType);
                    } else {
                        supportPattern = new SupportPattern(selectedWallPatternType, 0d, 0d);
                        recommendation.setWallPattern(supportPattern);
                    }
                }
                wallPatternDx.setEnabled(true);
                wallPatternDy.setEnabled(true);
                wallPatternSpinnerLastPosition = position;
            }
        }

        if (parent == meshTypeSpinner) {
            if (position != meshTypeSpinner.getAdapter().getCount() && position != meshTypeSpinnerLastPosition) {
                selectedMeshType = (MeshType) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().getRecomendation().setMeshType(selectedMeshType);
                meshTypeSpinnerLastPosition = position;
            }
        }
        if (parent == meshCoverageSpinner) {
            if (position != meshCoverageSpinner.getAdapter().getCount() && position != meshCoverageSpinnerLastPosition) {
                selectedMeshCoverage = (Coverage) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().getRecomendation().setMeshCoverage(selectedMeshCoverage);
                meshCoverageSpinnerLastPosition = position;
            }
        }
        if (parent == archTypeSpinner) {
            if (position != archTypeSpinner.getAdapter().getCount() && position != archTypeSpinnerLastPosition) {
                selectedArchType = (ArchType) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().getRecomendation().setArchType(selectedArchType);
                archTypeSpinnerLastPosition = position;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}