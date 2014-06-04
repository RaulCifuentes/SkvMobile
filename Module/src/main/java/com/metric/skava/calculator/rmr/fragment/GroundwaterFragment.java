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
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

public class GroundwaterFragment extends RMRCalculatorBaseFragment implements RadioGroup.OnCheckedChangeListener {

    private View headerView;
    private MultiColumnMappedIndexArrayAdapter<Groundwater> groudwaterAdapter;
    private Groundwater selectedGroundwater;
    private ListView inflowListView, jointPressListView, generalListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);

        selectedGroundwater = getRMRCalculationContext().getGroundwater();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_radios_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.title_section_groundwater));

        RadioButton inflowRadio = (RadioButton) view.findViewById(R.id.radioButtonFirst);
        inflowRadio.setText(Groundwater.Group.INFLOW_LENGHT.toString());
        RadioButton jointPressRadio = (RadioButton) view.findViewById(R.id.radioButtonSecond);
        jointPressRadio.setText(Groundwater.Group.JOINT_PRESS_PRINCIPAL.toString());
        RadioButton generalRadio = (RadioButton) view.findViewById(R.id.radioButtonThird);
        generalRadio.setText(Groundwater.Group.GENERAL_CONDITIONS.toString());
        RadioButton fourthRadio = (RadioButton) view.findViewById(R.id.radioButtonFourth);
        fourthRadio.setVisibility(View.GONE);

        inflowListView = (ListView) getView().findViewById(R.id.listview_a);
        jointPressListView = (ListView) getView().findViewById(R.id.listview_b);
        generalListView = (ListView) getView().findViewById(R.id.listview_c);

        inflowListView.setVisibility(View.GONE);
        jointPressListView.setVisibility(View.GONE);
        generalListView.setVisibility(View.GONE);

        if (selectedGroundwater != null) {
            switch (selectedGroundwater.getGroup()) {
                case INFLOW_LENGHT:
                    inflowRadio.setChecked(true);
                    jointPressRadio.setChecked(false);
                    generalRadio.setChecked(false);
                    inflowListView.setVisibility(View.VISIBLE);
                    jointPressListView.setVisibility(View.GONE);
                    generalListView.setVisibility(View.GONE);
                case JOINT_PRESS_PRINCIPAL:
                    inflowRadio.setChecked(false);
                    jointPressRadio.setChecked(true);
                    generalRadio.setChecked(false);
                    inflowListView.setVisibility(View.GONE);
                    jointPressListView.setVisibility(View.VISIBLE);
                    generalListView.setVisibility(View.GONE);
                case GENERAL_CONDITIONS:
                    inflowRadio.setChecked(false);
                    jointPressRadio.setChecked(false);
                    generalRadio.setChecked(true);
                    inflowListView.setVisibility(View.GONE);
                    jointPressListView.setVisibility(View.GONE);
                    generalListView.setVisibility(View.VISIBLE);
            }
        }

        setupInflow();
        setupJointPress();
        setupGeneral();
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

    }

    private void setupInflow() {
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(StrengthOfRock.DESCRIPTION);

        inflowListView.addHeaderView(headerView, null, false);
        final int numberOfHeaders = inflowListView.getHeaderViewsCount();
        List<Groundwater> listGroundwater;
        try {
            listGroundwater = getDAOFactory().getLocalGroundwaterDAO().getAllGroundwaters(Groundwater.Group.INFLOW_LENGHT);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            throw new SkavaSystemException(e);
        }

        groudwaterAdapter = new MultiColumnMappedIndexArrayAdapter<Groundwater>(getActivity(), R.layout.calculator_two_column_list_view_row_checked_radio, listGroundwater);
        inflowListView.setAdapter(groudwaterAdapter);

        if (selectedGroundwater != null) {
            int posIndex = groudwaterAdapter.getPosition(selectedGroundwater);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                inflowListView.setItemChecked(posIndex, true);
                inflowListView.setSelection(posIndex);
            }
        }

        inflowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedGroundwater = (Groundwater) parent.getItemAtPosition(position);
                getRMRCalculationContext().setGroundwater(selectedGroundwater);
            }
        });
    }

    private void setupJointPress() {
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(StrengthOfRock.DESCRIPTION);

        jointPressListView.addHeaderView(headerView, null, false);
        final int numberOfHeaders = jointPressListView.getHeaderViewsCount();
        List<Groundwater> listGroundwater;
        try {
            listGroundwater = getDAOFactory().getLocalGroundwaterDAO().getAllGroundwaters(Groundwater.Group.JOINT_PRESS_PRINCIPAL);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            throw new SkavaSystemException(e);
        }

        groudwaterAdapter = new MultiColumnMappedIndexArrayAdapter<Groundwater>(getActivity(), R.layout.calculator_two_column_list_view_row_checked_radio, listGroundwater);
        jointPressListView.setAdapter(groudwaterAdapter);

        if (selectedGroundwater != null) {
            int posIndex = groudwaterAdapter.getPosition(selectedGroundwater);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                jointPressListView.setItemChecked(posIndex, true);
                jointPressListView.setSelection(posIndex);
            }
        }

        jointPressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedGroundwater = (Groundwater) parent.getItemAtPosition(position);
                getRMRCalculationContext().setGroundwater(selectedGroundwater);
            }
        });
    }

    private void setupGeneral() {
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(StrengthOfRock.DESCRIPTION);

        generalListView.addHeaderView(headerView, null, false);
        final int numberOfHeaders = generalListView.getHeaderViewsCount();
        List<Groundwater> listGroundwater;
        try {
            listGroundwater = getDAOFactory().getLocalGroundwaterDAO().getAllGroundwaters(Groundwater.Group.GENERAL_CONDITIONS);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            throw new SkavaSystemException(e);
        }

        groudwaterAdapter = new MultiColumnMappedIndexArrayAdapter<Groundwater>(getActivity(), R.layout.calculator_two_column_list_view_row_checked_radio, listGroundwater);
        generalListView.setAdapter(groudwaterAdapter);

        if (selectedGroundwater != null) {
            int posIndex = groudwaterAdapter.getPosition(selectedGroundwater);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                generalListView.setItemChecked(posIndex, true);
                generalListView.setSelection(posIndex);
            }
        }

        generalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedGroundwater = (Groundwater) parent.getItemAtPosition(position);
                getRMRCalculationContext().setGroundwater(selectedGroundwater);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonFirst:
                inflowListView.setVisibility(View.VISIBLE);
                inflowListView.setSelection(1);
                inflowListView.setItemChecked(1, true);
                selectedGroundwater = (Groundwater) inflowListView.getItemAtPosition(1);
                getRMRCalculationContext().setGroundwater(selectedGroundwater);
                jointPressListView.setVisibility(View.GONE);
                jointPressListView.clearChoices();
                generalListView.setVisibility(View.GONE);
                generalListView.clearChoices();
                break;
            case R.id.radioButtonSecond:
                inflowListView.setVisibility(View.GONE);
                inflowListView.clearChoices();
                jointPressListView.setVisibility(View.VISIBLE);
                jointPressListView.setSelection(1);
                jointPressListView.setItemChecked(1, true);
                selectedGroundwater = (Groundwater) inflowListView.getItemAtPosition(1);
                getRMRCalculationContext().setGroundwater(selectedGroundwater);
                generalListView.setVisibility(View.GONE);
                generalListView.clearChoices();
                break;
            case R.id.radioButtonThird:
                inflowListView.setVisibility(View.GONE);
                inflowListView.clearChoices();
                jointPressListView.setVisibility(View.GONE);
                jointPressListView.clearChoices();
                generalListView.setVisibility(View.VISIBLE);
                generalListView.setSelection(1);
                generalListView.setItemChecked(1, true);
                selectedGroundwater = (Groundwater) inflowListView.getItemAtPosition(1);
                getRMRCalculationContext().setGroundwater(selectedGroundwater);
                break;
        }
    }
}
