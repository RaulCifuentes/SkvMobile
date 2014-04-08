package com.metric.skava.calculator.rmr.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.calculator.adapter.MultiColumnMappedIndexArrayAdapter;
import com.metric.skava.calculator.rmr.model.ConditionDiscontinuities;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;

import java.util.List;

public class OrientationFragment extends RMRCalculatorBaseFragment implements RadioGroup.OnCheckedChangeListener {

    private View headerView;
    private ListView mListTunnels;
    private ListView mListSlopes;
    private ListView mListFoundations;
    private OrientationDiscontinuities selectedOrientation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);

        selectedOrientation = getRMRCalculationContext().getOrientationDiscontinuities();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_rmr_orientation_discontinuities_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.title_section_orientation));

        RadioButton tunnelsRadio = (RadioButton) view.findViewById(R.id.radioButtonMines);
        RadioButton foundationsRadio = (RadioButton) view.findViewById(R.id.radioButtonFoundations);
        RadioButton slopesRadio = (RadioButton) view.findViewById(R.id.radioButtonSlopes);

        mListTunnels = (ListView) view.findViewById(R.id.listview_tunnels);
        mListTunnels.setVisibility(View.GONE);
        mListSlopes = (ListView) view.findViewById(R.id.listview_slopes);
        mListSlopes.setVisibility(View.GONE);
        mListFoundations = (ListView) view.findViewById(R.id.listview_foundations);
        mListFoundations.setVisibility(View.GONE);


        if (selectedOrientation != null){
            switch (selectedOrientation.getGroupType()){
                case OrientationDiscontinuities.TUNNEL_MINES:
                    tunnelsRadio.setChecked(true);
                    foundationsRadio.setChecked(false);
                    slopesRadio.setChecked(false);
                    mListTunnels.setVisibility(View.VISIBLE);
                    mListFoundations.setVisibility(View.GONE);
                    mListSlopes.setVisibility(View.GONE);
                    break;
                case OrientationDiscontinuities.FOUNDATIONS:
                    tunnelsRadio.setChecked(false);
                    foundationsRadio.setChecked(true);
                    slopesRadio.setChecked(false);
                    mListTunnels.setVisibility(View.GONE);
                    mListFoundations.setVisibility(View.VISIBLE);
                    mListSlopes.setVisibility(View.GONE);
                    break;
                case OrientationDiscontinuities.SLOPES:
                    tunnelsRadio.setChecked(false);
                    foundationsRadio.setChecked(false);
                    slopesRadio.setChecked(true);
                    mListTunnels.setVisibility(View.GONE);
                    mListFoundations.setVisibility(View.GONE);
                    mListSlopes.setVisibility(View.VISIBLE);
                    break;
            }
        }
        //selects the tunnels as default if theres no previous info
//        radioGroup.check(R.id.radioButtonMines);

        setupTunnelsOrientations();
        setupFoundationsOrientations();
        setupSlopesOrientations();
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);


    }


    public void setupTunnelsOrientations() {

        final ListView listview = (ListView) getView().findViewById(R.id.listview_tunnels);
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(ConditionDiscontinuities.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        final List<OrientationDiscontinuities> listOrientations = getMappedIndexDataProvider().getAllOrientationDiscontinuities(OrientationDiscontinuities.TUNNEL_MINES);
        final MultiColumnMappedIndexArrayAdapter<OrientationDiscontinuities> adapter = new MultiColumnMappedIndexArrayAdapter<OrientationDiscontinuities>(getActivity(),
                R.layout.calculator_two_column_list_view_row_checked_radio, listOrientations);
        listview.setAdapter(adapter);


        if (selectedOrientation != null) {
            int posIndex = adapter.getPosition(selectedOrientation);
            if (posIndex!=-1){
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
                listview.setSelection(posIndex);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedOrientation = (OrientationDiscontinuities) parent.getItemAtPosition(position);
                getRMRCalculationContext().setOrientationDiscontinuities(selectedOrientation);
            }
        });
    }

    public void setupSlopesOrientations() {

        final ListView listview = (ListView) getView().findViewById(R.id.listview_slopes);
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(ConditionDiscontinuities.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        final List<OrientationDiscontinuities> listOrientations = getMappedIndexDataProvider().getAllOrientationDiscontinuities(OrientationDiscontinuities.SLOPES);
        final MultiColumnMappedIndexArrayAdapter<OrientationDiscontinuities> adapter = new MultiColumnMappedIndexArrayAdapter<OrientationDiscontinuities>(getActivity(),
                R.layout.calculator_two_column_list_view_row_checked_radio, listOrientations);
        listview.setAdapter(adapter);

        selectedOrientation = getRMRCalculationContext().getOrientationDiscontinuities();
        if (selectedOrientation != null) {
            int posIndex = adapter.getPosition(selectedOrientation);
            if (posIndex!=-1){
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
                listview.setSelection(posIndex);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedOrientation = (OrientationDiscontinuities) parent.getItemAtPosition(position);
                getRMRCalculationContext().setOrientationDiscontinuities(selectedOrientation);
            }
        });
    }

    public void setupFoundationsOrientations() {

        final ListView listview = (ListView) getView().findViewById(R.id.listview_foundations);
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(ConditionDiscontinuities.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        final List<OrientationDiscontinuities> listOrientations = getMappedIndexDataProvider().getAllOrientationDiscontinuities(OrientationDiscontinuities.FOUNDATIONS);
        final MultiColumnMappedIndexArrayAdapter<OrientationDiscontinuities> adapter = new MultiColumnMappedIndexArrayAdapter<OrientationDiscontinuities>(getActivity(),
                R.layout.calculator_two_column_list_view_row_checked_radio, listOrientations);
        listview.setAdapter(adapter);

        selectedOrientation = getRMRCalculationContext().getOrientationDiscontinuities();
        if (selectedOrientation != null) {
            int posIndex = adapter.getPosition(selectedOrientation);
            if (posIndex!=-1){
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
                listview.setSelection(posIndex);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedOrientation = (OrientationDiscontinuities) parent.getItemAtPosition(position);
                getRMRCalculationContext().setOrientationDiscontinuities(selectedOrientation);
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonMines:
                mListTunnels.setVisibility(View.VISIBLE);
                mListTunnels.setSelection(1);
                mListTunnels.setItemChecked(1,true);
                selectedOrientation = (OrientationDiscontinuities) mListTunnels.getItemAtPosition(1);
                getRMRCalculationContext().setOrientationDiscontinuities(selectedOrientation);
                mListFoundations.setVisibility(View.GONE);
                mListFoundations.clearChoices();
                mListSlopes.setVisibility(View.GONE);
                mListSlopes.clearChoices();
                break;
            case R.id.radioButtonFoundations:
                mListTunnels.setVisibility(View.GONE);
                mListTunnels.clearChoices();
                mListFoundations.setVisibility(View.VISIBLE);
                mListFoundations.setSelection(1);
                mListFoundations.setItemChecked(1,true);
                selectedOrientation = (OrientationDiscontinuities) mListFoundations.getItemAtPosition(1);
                getRMRCalculationContext().setOrientationDiscontinuities(selectedOrientation);
                mListSlopes.setVisibility(View.GONE);
                mListSlopes.clearChoices();
                break;
            case R.id.radioButtonSlopes:
                mListTunnels.setVisibility(View.GONE);
                mListTunnels.clearChoices();
                mListFoundations.setVisibility(View.GONE);
                mListFoundations.clearChoices();
                mListSlopes.setVisibility(View.VISIBLE);
                mListSlopes.setSelection(1);
                mListSlopes.setItemChecked(1,true);
                selectedOrientation = (OrientationDiscontinuities) mListSlopes.getItemAtPosition(1);
                getRMRCalculationContext().setOrientationDiscontinuities(selectedOrientation);
                break;
        }
    }
}
