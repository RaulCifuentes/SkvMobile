package com.metric.skava.discontinuities.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
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
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.adapter.MappedIndexSpinnerArrayAdapter;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 2/27/14.
 */
public class DiscontinuitySystemBaseFragment extends SkavaFragment implements AdapterView.OnItemSelectedListener {

    private Spinner typeSpinner;
    private SkavaEntityAdapter discTypeAdapter;

    private Spinner discRelevanceSpinner;
    private SkavaEntityAdapter discRelevanceAdapter;

    private Spinner discSpacingSpinner;
    private MappedIndexSpinnerArrayAdapter<Spacing> discSpacingsAdapter;

    private Spinner discPersistenceSpinner;
    private MappedIndexSpinnerArrayAdapter<Persistence> discPersistenceAdapter;

    private Spinner discApertureSpinner;
    private MappedIndexSpinnerArrayAdapter<Aperture> discApertureAdapter;

    private Spinner discShapeSpinner;
    private SkavaEntityAdapter discShapeAdapter;

    private Spinner discRoughnessSpinner;
    private MappedIndexSpinnerArrayAdapter<Roughness> discRoughnessAdapter;

    private Spinner discWeatheringSpinner;
    private MappedIndexSpinnerArrayAdapter<Weathering> discWeatheringAdapter;

    private Spinner discWaterSpinner;
    private SkavaEntityAdapter discWaterAdapter;

    private Spinner discJaSpinner;
    private MappedIndexSpinnerArrayAdapter<Ja> discJaAdapter;

    private Spinner discJrSpinner;
    private MappedIndexSpinnerArrayAdapter<Jr> discJrAdapter;

    private Spinner discInfillingSpinner;
    private MappedIndexSpinnerArrayAdapter<Infilling> discInfillingAdapter;


    private DiscontinuityFamily mDiscontinuityFamilyInstance;

    private int mDiscontinuityFamilyNumber;

    private DAOFactory daoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoFactory = DAOFactory.getInstance(getActivity());
        if (savedInstanceState != null) {
            mDiscontinuityFamilyNumber = savedInstanceState.getInt(ARG_BASKET_ID, 0);
        }
        if (getArguments() != null) {
            mDiscontinuityFamilyNumber = getArguments().getInt(ARG_BASKET_ID, 0);
        }
        if (getCurrentAssessment().getDiscontinuitySystem().isEmpty()) {
            mDiscontinuityFamilyInstance = new DiscontinuityFamily();
            getCurrentAssessment().getDiscontinuitySystem().add(mDiscontinuityFamilyInstance);
        } else {
            if (getCurrentAssessment().getDiscontinuitySystem().size() < mDiscontinuityFamilyNumber) {
                mDiscontinuityFamilyInstance = new DiscontinuityFamily();
                //add as many as necessary
                for (int i = getCurrentAssessment().getDiscontinuitySystem().size(); i < mDiscontinuityFamilyNumber; i++) {
                    getCurrentAssessment().getDiscontinuitySystem().add(null);
                }
                getCurrentAssessment().getDiscontinuitySystem().add(mDiscontinuityFamilyNumber - 1, mDiscontinuityFamilyInstance);
            } else {
                mDiscontinuityFamilyInstance = getCurrentAssessment().getDiscontinuitySystem().get(mDiscontinuityFamilyNumber - 1);
                if (mDiscontinuityFamilyInstance == null) {
                    mDiscontinuityFamilyInstance = new DiscontinuityFamily();
                } else {
                    System.out.println("mDiscontinuityFamilyNumber:: " + mDiscontinuityFamilyNumber + " tiene el mDiscontinuityFamilyInstance :: " + mDiscontinuityFamilyInstance);
                }
            }
        }

//        getSkavaActivity().getSkavaDataProvider().getAllDiscontinuitiesTypes();
        List<DiscontinuityType> discTypeList = null;
        try {
            discTypeList = daoFactory.getDiscontinuityTypeDAO().getAllDiscontinuityTypes();
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
        }
        discTypeList.add(new DiscontinuityType("HINT", "Select type ..."));
        // Create an ArrayAdapter using the string array and a default spinner layout
        discTypeAdapter = new SkavaEntityAdapter<DiscontinuityType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discTypeList);
        // Specify the layout to use when the list of choices appears
        discTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //getSkavaActivity().getSkavaDataProvider().getAllDiscontinuityRelevances();
        List<DiscontinuityRelevance> discRelevanceList = new ArrayList<DiscontinuityRelevance>();
        try {
            discRelevanceList = daoFactory.getDiscontinuityRelevanceDAO().getAllDiscontinuityRelevances();
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
        }
        discRelevanceList.add(new DiscontinuityRelevance("HINT", "Select relevance ..."));
        // Create an ArrayAdapter using the string array and a default spinner layout
        discRelevanceAdapter = new SkavaEntityAdapter<DiscontinuityRelevance>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discRelevanceList);
        // Specify the layout to use when the list of choices appears
        discRelevanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        List<Spacing> discSpacingsList = getSkavaActivity().getMappedIndexDataProvider().getAllSpacings();
//        List<Spacing> discSpacingsList = null;
//        try {
//            discSpacingsList = daoFactory.getSpacingDAO().getAllSpacings();
//        } catch (DAOException e) {
//            Log.e(SkavaConstants.LOG, e.getMessage());
//            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
//        }
        discSpacingsList.add(new Spacing("HINT", "Select spacing ...", 0d));
        discSpacingsAdapter = new MappedIndexSpinnerArrayAdapter<Spacing>(
                getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1, discSpacingsList);
        // Specify the layout to use when the list of choices appears
        discSpacingsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        List<Persistence> discPersistencesList = getSkavaActivity().getMappedIndexDataProvider().getAllPersistences();
        discPersistencesList.add(new Persistence("HINT", "Select persistence ...", 0d));
        discPersistenceAdapter = new MappedIndexSpinnerArrayAdapter<Persistence>(
                getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discPersistencesList);
        // Specify the layout to use when the list of choices appears
        discPersistenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<Aperture> discAperturesList = getSkavaActivity().getMappedIndexDataProvider().getAllApertures();
        discAperturesList.add(new Aperture("HINT", "Select aperture ...", 0d));
        discApertureAdapter = new MappedIndexSpinnerArrayAdapter<Aperture>(
                getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discAperturesList);
        // Specify the layout to use when the list of choices appears
        discApertureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<DiscontinuityShape> discShapeList = getSkavaActivity().getSkavaDataProvider().getAllDiscontinuityShapes();
        discShapeList.add(new DiscontinuityShape("HINT", "Select shape ..."));
        discShapeAdapter = new SkavaEntityAdapter<DiscontinuityShape>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discShapeList);
        // Specify the layout to use when the list of choices appears
        discShapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        List<Roughness> discRoughnessList = getSkavaActivity().getMappedIndexDataProvider().getAllRoughness();
        discRoughnessList.add(new Roughness("HINT", "Select roughness ...", 0d));
        discRoughnessAdapter = new MappedIndexSpinnerArrayAdapter<Roughness>(
                getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1, discRoughnessList);
        // Specify the layout to use when the list of choices appears
        discRoughnessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        List<Infilling> discInfillingList = getSkavaActivity().getMappedIndexDataProvider().getAllInfillings();
        discInfillingList.add(new Infilling("HINT", "Select an infilling ...", 0d));
        // Create an ArrayAdapter using the string array and a default spinner layout
        discInfillingAdapter = new MappedIndexSpinnerArrayAdapter<Infilling>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discInfillingList);
        // Specify the layout to use when the list of choices appears
        discInfillingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        List<Jr> discJrList = getSkavaActivity().getMappedIndexDataProvider().getAllJr(Jr.a);
        discJrList.add(new Jr("HINT", "Select Jr ...", 1d));
        // Create an ArrayAdapter using the string array and a default spinner layout
        discJrAdapter = new MappedIndexSpinnerArrayAdapter<Jr>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discJrList);
        // Specify the layout to use when the list of choices appears
        discJrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<Ja> discJaList = getSkavaActivity().getMappedIndexDataProvider().getAllJa(Ja.a);
        discJaList.add(new Ja("HINT", "Select Ja ...", 10d));
        // Create an ArrayAdapter using the string array and a default spinner layout
        discJaAdapter = new MappedIndexSpinnerArrayAdapter<Ja>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discJaList);
        // Specify the layout to use when the list of choices appears
        discJaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<Weathering> discWeatheringList = getSkavaActivity().getMappedIndexDataProvider().getAllWeatherings();
        discWeatheringList.add(new Weathering("HINT", "Select weathering ...", 0d));
        // Create an ArrayAdapter using the string array and a default spinner layout
        discWeatheringAdapter = new MappedIndexSpinnerArrayAdapter<Weathering>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discWeatheringList);


        List<DiscontinuityWater> discWaterList = getSkavaActivity().getSkavaDataProvider().getAllDiscontinuityWaters();
        discWaterList.add(new DiscontinuityWater("HINT", "Select water ..."));
        // Create an ArrayAdapter using the string array and a default spinner layout
        discWaterAdapter = new SkavaEntityAdapter<DiscontinuityWater>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, discWaterList);
        // Specify the layout to use when the list of choices appears
        discWaterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.discontinuity_system_base_fragment, container, false);

        typeSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_type_spinner);
        typeSpinner.setAdapter(discTypeAdapter);
        typeSpinner.setOnItemSelectedListener(this);
        DiscontinuityType type = mDiscontinuityFamilyInstance.getType();
        if (type != null) {
            typeSpinner.setSelection(discTypeAdapter.getPosition(type));
        } else {
            typeSpinner.setSelection(discTypeAdapter.getCount() - 1); //display hint
        }

        discRelevanceSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_relevance_spinner);
        discRelevanceSpinner.setAdapter(discRelevanceAdapter);
        discRelevanceSpinner.setOnItemSelectedListener(this);
        DiscontinuityRelevance relevance = mDiscontinuityFamilyInstance.getRelevance();
        if (relevance != null) {
            discRelevanceSpinner.setSelection(discRelevanceAdapter.getPosition(relevance)); //display hint
        } else {
            discRelevanceSpinner.setSelection(discRelevanceAdapter.getCount() - 1); //display hint
        }


        final EditText dipDirEditText = (EditText) rootView.findViewById(R.id.discontinuity_system_dip_dir_value);
        dipDirEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        dipDirEditText.addTextChangedListener(new TextValidator(dipDirEditText) {
            @Override
            public void validate(TextView textView, String text) {
                /* Validation code here */
                Short enteredValue = 0;
                try {
                    enteredValue = Short.parseShort(text);
                    if (enteredValue > 0 && enteredValue < 360) {
                        mDiscontinuityFamilyInstance.setDipDirDegrees(enteredValue);
                    } else {
                        dipDirEditText.setError("DipDir must be between 0 and 360!");
                    }
                } catch (NumberFormatException e) {
                    dipDirEditText.setError("DipDir must be between 0 and 360!");
                }
            }
        });

        final EditText dipEditText = (EditText) rootView.findViewById(R.id.discontinuity_system_dip_value);
        dipEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        dipEditText.addTextChangedListener(new TextValidator(dipEditText) {
            @Override
            public void validate(TextView textView, String text) {
                /* Validation code here */
                Short enteredValue = 0;
                try {
                    enteredValue = Short.parseShort(text);
                    if (enteredValue > 0 && enteredValue < 90) {
                        mDiscontinuityFamilyInstance.setDipDegrees(enteredValue);
                    } else {
                        dipEditText.setError("Dip must be between 0 and 90!");
                    }
                } catch (NumberFormatException e) {
                    dipEditText.setError("Dip must be between 0 and 90!");
                }
            }
        });


        discSpacingSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_spacing_spinner);
        discSpacingSpinner.setAdapter(discSpacingsAdapter);
        discSpacingSpinner.setOnItemSelectedListener(this);
        Spacing spacing = mDiscontinuityFamilyInstance.getSpacing();
        if (spacing != null) {
//            discSpacingSpinner.setSelection(discSpacingsAdapter.getPosition(spacing));
            discSpacingSpinner.setSelection(discSpacingsAdapter.getPosition(spacing));
        } else {
            discSpacingSpinner.setSelection(discSpacingsAdapter.getCount()-1);
        }


        discPersistenceSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_persistence_spinner);
        discPersistenceSpinner.setAdapter(discPersistenceAdapter);
        discPersistenceSpinner.setOnItemSelectedListener(this);
        Persistence persistence = mDiscontinuityFamilyInstance.getPersistence();
        if (persistence != null) {
            discPersistenceSpinner.setSelection(discPersistenceAdapter.getPosition(persistence));
        } else {
            discPersistenceSpinner.setSelection(discPersistenceAdapter.getCount()-1);
        }


        discApertureSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_aperture_spinner);
        discApertureSpinner.setAdapter(discApertureAdapter);
        discApertureSpinner.setOnItemSelectedListener(this);
        Aperture aperture = mDiscontinuityFamilyInstance.getAperture();
        if (aperture != null) {
            discApertureSpinner.setSelection(discApertureAdapter.getPosition(aperture));
        } else {
            discApertureSpinner.setSelection(discApertureAdapter.getCount()-1);
        }


        discShapeSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_shape_spinner);
        discShapeSpinner.setAdapter(discShapeAdapter);
        discShapeSpinner.setOnItemSelectedListener(this);
        DiscontinuityShape shape = mDiscontinuityFamilyInstance.getShape();
        if (shape != null) {
            discShapeSpinner.setSelection(discShapeAdapter.getPosition(shape));
        } else {
            discShapeSpinner.setSelection(discShapeAdapter.getCount()-1);
        }


        discRoughnessSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_roughness_spinner);
        discRoughnessSpinner.setAdapter(discRoughnessAdapter);
        discRoughnessSpinner.setOnItemSelectedListener(this);
        Roughness roughness = mDiscontinuityFamilyInstance.getRoughness();
        if (roughness != null) {
            discRoughnessSpinner.setSelection(discRoughnessAdapter.getPosition(roughness));
        } else {
            discRoughnessSpinner.setSelection(discRoughnessAdapter.getCount()-1);
        }


        discInfillingSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_infilling_spinner);
        discInfillingSpinner.setAdapter(discInfillingAdapter);
        discInfillingSpinner.setOnItemSelectedListener(this);
        Infilling infilling = mDiscontinuityFamilyInstance.getInfilling();
        if (infilling != null) {
            discInfillingSpinner.setSelection(discInfillingAdapter.getPosition(infilling));
        } else {
            discInfillingSpinner.setSelection(discInfillingAdapter.getCount()-1);
        }


        // Specify the layout to use when the list of choices appears
        discWeatheringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        discWeatheringSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_weathering_spinner);
        discWeatheringSpinner.setAdapter(discWeatheringAdapter);
        Weathering weathering = mDiscontinuityFamilyInstance.getWeathering();
        if (weathering != null) {
            discWeatheringSpinner.setSelection(discWeatheringAdapter.getPosition(weathering));
        } else {
            discWeatheringSpinner.setSelection(discWeatheringAdapter.getCount()-1);
        }
        discWeatheringSpinner.setOnItemSelectedListener(this);


        discJrSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_jr_spinner);
        discJrSpinner.setOnItemSelectedListener(this);
        discJrSpinner.setAdapter(discJrAdapter);
        Jr jr = mDiscontinuityFamilyInstance.getJr();
        if (jr != null) {
            discJrSpinner.setSelection(discJrAdapter.getPosition(jr));
        } else {
            discJrSpinner.setSelection(discJrAdapter.getCount()-1);
        }


        discJaSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_ja_spinner);
        discJaSpinner.setAdapter(discJaAdapter);
        Ja ja = mDiscontinuityFamilyInstance.getJa();
        if (ja != null) {
            discJaSpinner.setSelection(discJaAdapter.getPosition(ja));
        } else {
            discJaSpinner.setSelection(discJaAdapter.getCount()-1);
        }
        discJaSpinner.setOnItemSelectedListener(this);


        discWaterSpinner = (Spinner) rootView.findViewById(R.id.discontinuity_system_water_spinner);
        discWaterSpinner.setAdapter(discWaterAdapter);
        DiscontinuityWater water = mDiscontinuityFamilyInstance.getWater();
        if (water != null) {
            discWaterSpinner.setSelection(discWaterAdapter.getPosition(water));
        } else {
            discWaterSpinner.setSelection(discWaterAdapter.getCount()-1); //display hint
        }
        discWaterSpinner.setOnItemSelectedListener(this);


        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == typeSpinner) {
            if (position != typeSpinner.getAdapter().getCount()) {
                DiscontinuityType selectedType = (DiscontinuityType) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setType(selectedType);
            }
        }
        if (parent == discRelevanceSpinner) {
            if (position != discRelevanceSpinner.getAdapter().getCount()) {
                DiscontinuityRelevance selectedRelevance = (DiscontinuityRelevance) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setRelevance(selectedRelevance);
            }
        }
        if (parent == discSpacingSpinner) {
            if (position != discSpacingSpinner.getAdapter().getCount()) {
                Spacing selectedSpace = (Spacing) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setSpacing(selectedSpace);
            }
        }
        if (parent == discPersistenceSpinner) {
            if (position != discPersistenceSpinner.getAdapter().getCount()) {
                Persistence selectedPersistence = (Persistence) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setPersistence(selectedPersistence);
            }
        }
        if (parent == discShapeSpinner) {
            if (position != discShapeSpinner.getAdapter().getCount()) {
                DiscontinuityShape selectedShape = (DiscontinuityShape) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setShape(selectedShape);
            }
        }
        if (parent == discRoughnessSpinner) {
            if (position != discRoughnessSpinner.getAdapter().getCount()) {
                Roughness selectedRoughness = (Roughness) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setRoughness(selectedRoughness);
            }
        }
        if (parent == discInfillingSpinner) {
            if (position != discInfillingSpinner.getAdapter().getCount()) {
                Infilling selectedInfilling = (Infilling) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setInfilling(selectedInfilling);
            }
        }
        if (parent == discWeatheringSpinner) {
            if (position != discWeatheringSpinner.getAdapter().getCount()) {
                Weathering selectedWeathering = (Weathering) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setWeathering(selectedWeathering);
            }
        }
        if (parent == discWaterSpinner) {
            if (position != discWaterSpinner.getAdapter().getCount()) {
                DiscontinuityWater selectedWater = (DiscontinuityWater) parent.getItemAtPosition(position);
                mDiscontinuityFamilyInstance.setWater(selectedWater);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    public void saveDiscontinuityFamily() {
//        List<DiscontinuityFamily> discFamilySystem = getSkavaContext().getAssessment().getDiscontinuitySystem();
//        if (mDiscontinuityFamilyNumber - 1 < discFamilySystem.size()) {
//            discFamilySystem.set(mDiscontinuityFamilyNumber - 1, mDiscontinuityFamilyInstance);
//        } else {
////            discFamilySystem.add(mDiscontinuityFamilyNumber - 1, mDiscontinuityFamilyInstance);
//        }
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
////        saveDiscontinuityFamily();
//    }
}