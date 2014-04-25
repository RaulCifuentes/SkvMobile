package com.metric.skava.calculator.barton.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;


public class SrfFragment extends QBartonCalculatorBaseFragment implements RadioGroup.OnCheckedChangeListener {

    private View headerView;
    private FragmentActivity context;
    private ListView mListFirstGroup;
    private ListView mListSecondGroup;
    private ListView mListThirdGroup;
    private ListView mListFourthGroup;

    private MultiColumnMappedIndexArrayAdapter<SRF> srfAdapter;
    private SRF selectedSRF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        context = this.getActivity();

        selectedSRF = getQCalculationContext().getSrf();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_radios_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText("Stress Reduction Factor");

        RadioButton firstRadio = (RadioButton) view.findViewById(R.id.radioButtonFirst);
        firstRadio.setText("a. Weak zones");
        RadioButton secondRadio = (RadioButton) view.findViewById(R.id.radioButtonSecond);
        secondRadio.setText("b. Competent");
        RadioButton thirdRadio = (RadioButton) view.findViewById(R.id.radioButtonThird);
        thirdRadio.setText("c. Squeezing rock");
        RadioButton fourthRadio = (RadioButton) view.findViewById(R.id.radioButtonFourth);
        fourthRadio.setText("d. Swelling rock");


        mListFirstGroup = (ListView) view.findViewById(R.id.listview_a);
        mListFirstGroup.setVisibility(View.GONE);
        mListSecondGroup = (ListView) view.findViewById(R.id.listview_b);
        mListSecondGroup.setVisibility(View.GONE);
        mListThirdGroup = (ListView) view.findViewById(R.id.listview_c);
        mListThirdGroup.setVisibility(View.GONE);
        mListFourthGroup = (ListView) view.findViewById(R.id.listview_d);
        mListFourthGroup.setVisibility(View.GONE);


        if (selectedSRF != null) {
            //what (type) group of Jr is this in order to show the correspondant list
            switch (selectedSRF.getGroupType()) {
                case SRF.a:
//                    radioGroup.check(R.id.radioButtonFirst);
                    firstRadio.setChecked(true);
                    secondRadio.setChecked(false);
                    thirdRadio.setChecked(false);
                    fourthRadio.setChecked(false);
                    mListFirstGroup.setVisibility(View.VISIBLE);
                    mListSecondGroup.setVisibility(View.GONE);
                    mListThirdGroup.setVisibility(View.GONE);
                    mListFourthGroup.setVisibility(View.GONE);
                    break;
                case SRF.b:
//                    radioGroup.check(R.id.radioButtonSecond);
                    firstRadio.setChecked(false);
                    secondRadio.setChecked(true);
                    thirdRadio.setChecked(false);
                    fourthRadio.setChecked(false);
                    mListFirstGroup.setVisibility(View.GONE);
                    mListSecondGroup.setVisibility(View.VISIBLE);
                    mListThirdGroup.setVisibility(View.GONE);
                    mListFourthGroup.setVisibility(View.GONE);
                    break;
                case SRF.c:
//                    radioGroup.check(R.id.radioButtonThird);
                    firstRadio.setChecked(false);
                    secondRadio.setChecked(false);
                    thirdRadio.setChecked(true);
                    fourthRadio.setChecked(false);
                    mListFirstGroup.setVisibility(View.GONE);
                    mListSecondGroup.setVisibility(View.GONE);
                    mListThirdGroup.setVisibility(View.VISIBLE);
                    mListFourthGroup.setVisibility(View.GONE);
                    break;
                case SRF.d:
//                    radioGroup.check(R.id.radioButtonFourth);
                    firstRadio.setChecked(false);
                    secondRadio.setChecked(false);
                    thirdRadio.setChecked(false);
                    fourthRadio.setChecked(true);
                    mListFirstGroup.setVisibility(View.GONE);
                    mListSecondGroup.setVisibility(View.GONE);
                    mListThirdGroup.setVisibility(View.GONE);
                    mListFourthGroup.setVisibility(View.VISIBLE);
                    break;
            }
        }

        setupFirstGroupList();
        setupSecondGroupList();
        setupThirdGroupList();
        setupFourthGroupList();

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

    }

    public void setupFirstGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_a);
        final List<SRF> listSrf;
        try {
            listSrf = daoFactory.getLocalSrfDAO().getAllSrfs(SRF.a);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        srfAdapter = new MultiColumnMappedIndexArrayAdapter<SRF>(context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listSrf);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(SRF.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        listview.setAdapter(srfAdapter);

        selectedSRF = getQCalculationContext().getSrf();
        if (selectedSRF != null) {
            int posIndex = srfAdapter.getPosition(selectedSRF);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedSRF = (SRF) parent.getItemAtPosition(position);
                getQCalculationContext().setSrf(selectedSRF);
            }

        });
    }


    public void setupSecondGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_b);
        final List<SRF> listSrf;
        try {
            listSrf = daoFactory.getLocalSrfDAO().getAllSrfs(SRF.b);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        final MultiColumnMappedIndexArrayAdapter<SRF> adapter = new MultiColumnMappedIndexArrayAdapter<SRF>(context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listSrf);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(SRF.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        listview.setAdapter(adapter);

        selectedSRF = getQCalculationContext().getSrf();
        if (selectedSRF != null) {
            int posIndex = adapter.getPosition(selectedSRF);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
                listview.setSelection(posIndex);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedSRF = (SRF) parent.getItemAtPosition(position);
                getQCalculationContext().setSrf(selectedSRF);
            }

        });
    }

    public void setupThirdGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_c);
        final List<SRF> listSrf;
        try {
            listSrf = daoFactory.getLocalSrfDAO().getAllSrfs(SRF.c);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        final MultiColumnMappedIndexArrayAdapter<SRF> adapter = new MultiColumnMappedIndexArrayAdapter<SRF>(context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listSrf);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(SRF.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        listview.setAdapter(adapter);

        selectedSRF = getQCalculationContext().getSrf();
        if (selectedSRF != null) {
            int posIndex = adapter.getPosition(selectedSRF);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
                listview.setSelection(posIndex);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedSRF = (SRF) parent.getItemAtPosition(position);
                getQCalculationContext().setSrf(selectedSRF);
            }

        });
    }

    public void setupFourthGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_d);
        final List<SRF> listSrf;
        try {
            listSrf = daoFactory.getLocalSrfDAO().getAllSrfs(SRF.d);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        srfAdapter = new MultiColumnMappedIndexArrayAdapter<SRF>(context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listSrf);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(SRF.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        listview.setAdapter(srfAdapter);

        selectedSRF = getQCalculationContext().getSrf();
        if (selectedSRF != null) {
            int posIndex = srfAdapter.getPosition(selectedSRF);
            if (posIndex != -1) {
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
                listview.setSelection(posIndex);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedSRF = (SRF) parent.getItemAtPosition(position);
                getQCalculationContext().setSrf(selectedSRF);
            }

        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonFirst:
                mListFirstGroup.setVisibility(View.VISIBLE);
                mListFirstGroup.setItemChecked(1, true);
                mListFirstGroup.setSelection(1);
                selectedSRF = (SRF) mListFirstGroup.getItemAtPosition(1);
                getQCalculationContext().setSrf(selectedSRF);
                mListSecondGroup.setVisibility(View.GONE);
                mListSecondGroup.clearChoices();
                mListThirdGroup.setVisibility(View.GONE);
                mListThirdGroup.clearChoices();
                mListFourthGroup.setVisibility(View.GONE);
                mListFourthGroup.clearChoices();
                break;
            case R.id.radioButtonSecond:
                mListFirstGroup.setVisibility(View.GONE);
                mListFirstGroup.clearChoices();
                mListSecondGroup.setVisibility(View.VISIBLE);
                mListSecondGroup.setItemChecked(1, true);
                mListSecondGroup.setSelection(1);
                selectedSRF = (SRF) mListSecondGroup.getItemAtPosition(1);
                getQCalculationContext().setSrf(selectedSRF);
                mListThirdGroup.setVisibility(View.GONE);
                mListThirdGroup.clearChoices();
                mListFourthGroup.setVisibility(View.GONE);
                mListFourthGroup.clearChoices();
                break;
            case R.id.radioButtonThird:
                mListFirstGroup.setVisibility(View.GONE);
                mListFirstGroup.clearChoices();
                mListSecondGroup.setVisibility(View.GONE);
                mListSecondGroup.clearChoices();
                mListThirdGroup.setVisibility(View.VISIBLE);
                mListThirdGroup.setItemChecked(1, true);
                mListThirdGroup.setSelection(1);
                selectedSRF = (SRF) mListThirdGroup.getItemAtPosition(1);
                getQCalculationContext().setSrf(selectedSRF);
                mListFourthGroup.setVisibility(View.GONE);
                mListFourthGroup.clearChoices();
                break;
            case R.id.radioButtonFourth:
                mListFirstGroup.setVisibility(View.GONE);
                mListFirstGroup.clearChoices();
                mListSecondGroup.setVisibility(View.GONE);
                mListSecondGroup.clearChoices();
                mListThirdGroup.setVisibility(View.GONE);
                mListThirdGroup.clearChoices();
                mListFourthGroup.setVisibility(View.VISIBLE);
                mListFourthGroup.setItemChecked(1, true);
                mListFourthGroup.setSelection(1);
                selectedSRF = (SRF) mListFourthGroup.getItemAtPosition(1);
                getQCalculationContext().setSrf(selectedSRF);
                break;
        }
    }


}
