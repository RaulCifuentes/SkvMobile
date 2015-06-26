package com.metric.skava.calculator.rmr.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.adapter.MultiColumnMappedIndexArrayAdapter;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

public class StrengthFragment extends RMRCalculatorBaseFragment  implements RadioGroup.OnCheckedChangeListener {

    private View headerView;
    private MultiColumnMappedIndexArrayAdapter<StrengthOfRock> strengthAdapter;
    private StrengthOfRock selectedStrength;
    private ListView mPointLoadListView;
    private ListView mUniaxialListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);

        selectedStrength = getRMRCalculationContext().getStrengthOfRock();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_radios_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.strength_title_label));

        RadioButton pointLoadRadio = (RadioButton) view.findViewById(R.id.radioButtonFirst);
//        pointLoadRadio.setText(StrengthOfRock.Group.POINT_LOAD_KEY.toString());
        String strengthPointLoadGroup = getResources().getString(R.string.strength_point_load_group);
        pointLoadRadio.setText(strengthPointLoadGroup);

        RadioButton uniaxialRadio = (RadioButton) view.findViewById(R.id.radioButtonSecond);
//        uniaxialRadio.setText(StrengthOfRock.Group.UNIAXIAL_KEY.toString());
        String strengthUniaxialGroup = getResources().getString(R.string.strength_uniaxial_group);
        uniaxialRadio.setText(strengthUniaxialGroup);

        RadioButton thirdRadio = (RadioButton) view.findViewById(R.id.radioButtonThird);
        thirdRadio.setVisibility(View.GONE);

        RadioButton fourthRadio = (RadioButton) view.findViewById(R.id.radioButtonFourth);
        fourthRadio.setVisibility(View.GONE);

        mPointLoadListView = (ListView) getView().findViewById(R.id.listview_a);
        mPointLoadListView.setVisibility(View.GONE);
        mUniaxialListView = (ListView) getView().findViewById(R.id.listview_b);
        mUniaxialListView.setVisibility(View.GONE);

        if (selectedStrength != null){
            switch (selectedStrength.getGroup()){
                case POINT_LOAD_KEY:
                    pointLoadRadio.setChecked(true);
                    uniaxialRadio.setChecked(false);
                    mPointLoadListView.setVisibility(View.VISIBLE);
                    mUniaxialListView.setVisibility(View.GONE);
                    break;
                case UNIAXIAL_KEY:
                    pointLoadRadio.setChecked(false);
                    uniaxialRadio.setChecked(true);
                    mPointLoadListView.setVisibility(View.GONE);
                    mUniaxialListView.setVisibility(View.VISIBLE);
                    break;
            }
        }

        setupPointLoad();
        setupUniaxial();
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
    }

    public void setupPointLoad(){
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(StrengthOfRock.DESCRIPTION);

        mPointLoadListView.addHeaderView(headerView, null, false);
        final int numberOfHeaders = mPointLoadListView.getHeaderViewsCount();

        List<StrengthOfRock> listStrenght;
        try {
            listStrenght = getDAOFactory().getLocalStrengthDAO().getAllStrengths(StrengthOfRock.Group.POINT_LOAD_KEY);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        strengthAdapter = new MultiColumnMappedIndexArrayAdapter<StrengthOfRock>(getActivity(), R.layout.calculator_two_column_list_view_row_checked_radio, listStrenght);
        mPointLoadListView.setAdapter(strengthAdapter);

        if (selectedStrength != null) {
            int posIndex = strengthAdapter.getPosition(selectedStrength);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                mPointLoadListView.setItemChecked(posIndex, true);
                mPointLoadListView.setSelection(posIndex);
            }

        }

        mPointLoadListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedStrength = (StrengthOfRock) parent.getItemAtPosition(position);
                getRMRCalculationContext().setStrengthOfRock(selectedStrength);
            }
        });

    }

    public void setupUniaxial(){
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(StrengthOfRock.DESCRIPTION);

        mUniaxialListView.addHeaderView(headerView, null, false);
        final int numberOfHeaders = mUniaxialListView.getHeaderViewsCount();

        List<StrengthOfRock> listStrenght;
        try {
            listStrenght = getDAOFactory().getLocalStrengthDAO().getAllStrengths(StrengthOfRock.Group.UNIAXIAL_KEY);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        strengthAdapter = new MultiColumnMappedIndexArrayAdapter<StrengthOfRock>(getActivity(), R.layout.calculator_two_column_list_view_row_checked_radio, listStrenght);
        mUniaxialListView.setAdapter(strengthAdapter);

        if (selectedStrength != null) {
            int posIndex = strengthAdapter.getPosition(selectedStrength);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                mUniaxialListView.setItemChecked(posIndex, true);
                mUniaxialListView.setSelection(posIndex);
            }

        }

        mUniaxialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedStrength = (StrengthOfRock) parent.getItemAtPosition(position);
                getRMRCalculationContext().setStrengthOfRock(selectedStrength);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonFirst:
                mPointLoadListView.setVisibility(View.VISIBLE);
                mPointLoadListView.setSelection(1);
                mPointLoadListView.setItemChecked(1,true);
                selectedStrength = (StrengthOfRock) mPointLoadListView.getItemAtPosition(1);
                getRMRCalculationContext().setStrengthOfRock(selectedStrength);
                mUniaxialListView.setVisibility(View.GONE);
                mUniaxialListView.clearChoices();
                break;
            case R.id.radioButtonSecond:
                mPointLoadListView.setVisibility(View.GONE);
                mPointLoadListView.clearChoices();
                mUniaxialListView.setVisibility(View.VISIBLE);
                mUniaxialListView.setSelection(1);
                mUniaxialListView.setItemChecked(1,true);
                selectedStrength = (StrengthOfRock) mUniaxialListView.getItemAtPosition(1);
                getRMRCalculationContext().setStrengthOfRock(selectedStrength);
                break;
        }
    }


}
