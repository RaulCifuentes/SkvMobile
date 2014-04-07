package com.metric.skava.rockmass.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.adapter.SkavaEntityAdapter;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rockmass.model.FractureType;

import java.util.List;

/**
 * Created by metricboy on 2/22/14.
 */
public class RockMassDescriptionMainFragment extends SkavaFragment implements AdapterView.OnItemSelectedListener {


    private Spinner fractureTypeSpinner;
    private DAOFactory daoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoFactory = DAOFactory.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rock_mass_description_main_fragment, container, false);

        fractureTypeSpinner = (Spinner) rootView.findViewById(R.id.rockmass_desc_fracture_type_spinner);

//        SkavaDataProvider dataProvider = SkavaDataProvider.getInstance(getActivity());
        List<FractureType> fractureTypeList = null;
        try {
            fractureTypeList = daoFactory.getFractureTypeDAO().getAllFractureTypes();
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
        }
        SkavaEntityAdapter fractureTypeAdapter = new SkavaEntityAdapter<FractureType>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, fractureTypeList);
        // Specify the layout to use when the list of choices appears
        fractureTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        fractureTypeSpinner.setAdapter(fractureTypeAdapter);

        FractureType fractureType = getCurrentAssessment().getFractureType();
        if (fractureType != null) {
            fractureTypeSpinner.setSelection(fractureTypeAdapter.getPosition(fractureType));
        } else {
            fractureTypeSpinner.setSelection(fractureTypeAdapter.getCount()-1); //display hint
        }
        fractureTypeSpinner.setOnItemSelectedListener(this);

        return rootView;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == fractureTypeSpinner){
            FractureType selectedFractureType = (FractureType)parent.getItemAtPosition(position);
            getSkavaContext().getAssessment().setFractureType(selectedFractureType);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
