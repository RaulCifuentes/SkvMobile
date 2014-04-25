package com.metric.skava.rockmass.fragment;

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
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rockmass.model.FractureType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by metricboy on 2/22/14.
 */
public class RockMassDescriptionMainFragment extends SkavaFragment implements AdapterView.OnItemSelectedListener {


    private DAOFactory daoFactory;

    private Spinner fractureTypeSpinner;
    private SkavaEntityAdapter<FractureType> fractureTypeAdapter;

    private EditText blocksSizeEditText;
    private EditText numOfJointsEditText;
    private EditText outcropEditText;

    private int fractureTypeSpinnerLastPosition;
    private FractureType selectedFractureType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoFactory = DAOFactory.getInstance(getActivity());

        fractureTypeSpinnerLastPosition = -1;

        List<FractureType> fractureTypeList = null;
        try {
            fractureTypeList = daoFactory.getFractureTypeDAO().getAllFractureTypes();
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
        }
        fractureTypeList.add(new FractureType("HINT", "Select a type ..."));
        fractureTypeAdapter = new SkavaEntityAdapter<FractureType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, fractureTypeList);
        // Specify the layout to use when the list of choices appears
        fractureTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectedFractureType = getCurrentAssessment().getFractureType();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.rock_mass_description_main_fragment, container, false);


        fractureTypeSpinner = (Spinner) rootView.findViewById(R.id.rockmass_desc_fracture_type_spinner);
        fractureTypeSpinner.setAdapter(fractureTypeAdapter);
        fractureTypeSpinner.setOnItemSelectedListener(this);

        FractureType fractureType = getCurrentAssessment().getFractureType();
        if (fractureType != null) {
            fractureTypeSpinner.setSelection(fractureTypeAdapter.getPosition(fractureType));
        } else {
            fractureTypeSpinner.setSelection(fractureTypeAdapter.getCount() - 1); //display hint
        }

        NumberFormat numberFormatter = DecimalFormat.getNumberInstance();

        blocksSizeEditText = (EditText) rootView.findViewById(R.id.rockmass_desc_block_size_value);
        blocksSizeEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        Double blocksSize = getCurrentAssessment().getBlockSize();
        if (blocksSize != null) {
            blocksSizeEditText.setText(numberFormatter.format(blocksSize));
        }

        blocksSizeEditText.addTextChangedListener(new TextValidator(blocksSizeEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                try {
                    Double enteredValue = Double.parseDouble(text);
                    getSkavaContext().getAssessment().setBlockSize(enteredValue);
                } catch (NumberFormatException e) {
                    blocksSizeEditText.setError("Block size must be a number!");
                }
            }
        });

        numOfJointsEditText = (EditText) rootView.findViewById(R.id.rockmass_desc_junctures_value);
        numOfJointsEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
        Short numberOfJoints = getCurrentAssessment().getNumberOfJoints();
        if (numberOfJoints != null) {
            numOfJointsEditText.setText(numberFormatter.format(numberOfJoints));
        }

        numOfJointsEditText.addTextChangedListener(new TextValidator(numOfJointsEditText) {
            @Override
            public void validate(TextView textView, java.lang.String text) {
                /* Validation code here */
                Short enteredValue = 0;
                try {
                    enteredValue = Short.parseShort(text);
                    getSkavaContext().getAssessment().setNumberOfJoints(enteredValue);
                } catch (NumberFormatException e) {
                    numOfJointsEditText.setError("Joints per m3 must be a number!");
                }
            }
        });

        outcropEditText = (EditText) rootView.findViewById(R.id.rockmass_desc_details_value);
        String outcropDescription = getCurrentAssessment().getOutcropDescription();
        if (outcropDescription != null) {
            outcropEditText.setText(outcropDescription);
        }

        outcropEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                // Implementation left blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                // Implementation left blank
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getCurrentAssessment().setOutcropDescription(editable.toString());
            }
        });

        return rootView;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == fractureTypeSpinner){
            if (position != fractureTypeSpinner.getAdapter().getCount() && position != fractureTypeSpinnerLastPosition) {
                selectedFractureType = (FractureType) parent.getItemAtPosition(position);
                getSkavaContext().getAssessment().setFractureType(selectedFractureType);
                fractureTypeSpinnerLastPosition = position;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing
    }
}
