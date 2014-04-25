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
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

public class JaFragment extends QBartonCalculatorBaseFragment implements RadioGroup.OnCheckedChangeListener {

    private FragmentActivity context;
    private View headerView;
    private ListView mListFirstGroup;
    private ListView mListSecondGroup;
    private ListView mListThirdGroup;
    private MultiColumnMappedIndexArrayAdapter<Ja> jaAdapter;
    private Ja selectedJa;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        context = this.getActivity();

        selectedJa = getQCalculationContext().getJa();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_radios_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.jaTitle));


        RadioButton firstRadio = (RadioButton) view.findViewById(R.id.radioButtonFirst);
        firstRadio.setText("a. Only coatings");
        RadioButton secondRadio = (RadioButton) view.findViewById(R.id.radioButtonSecond);
        secondRadio.setText("b. Thin mineral fillings");
        RadioButton thirdRadio = (RadioButton) view.findViewById(R.id.radioButtonThird);
        thirdRadio.setText("c. Thick mineral fillings");
        RadioButton fourthRadio = (RadioButton) view.findViewById(R.id.radioButtonFourth);
        fourthRadio.setVisibility(View.GONE);

        mListFirstGroup = (ListView) view.findViewById(R.id.listview_a);
        mListFirstGroup.setVisibility(View.GONE);
        mListSecondGroup = (ListView) view.findViewById(R.id.listview_b);
        mListSecondGroup.setVisibility(View.GONE);
        mListThirdGroup = (ListView) view.findViewById(R.id.listview_c);
        mListThirdGroup.setVisibility(View.GONE);


        if (selectedJa != null) {
            //what (type) group of Jr is this in order to show the correspondant list
            switch (selectedJa.getGroupType()) {
                case Ja.a:
//                    radioGroup.check(R.id.radioButtonFirst);
                    firstRadio.setChecked(true);
                    secondRadio.setChecked(false);
                    thirdRadio.setChecked(false);
                    mListFirstGroup.setVisibility(View.VISIBLE);
                    mListSecondGroup.setVisibility(View.GONE);
                    mListThirdGroup.setVisibility(View.GONE);
                    break;
                case Ja.b:
//                    radioGroup.check(R.id.radioButtonSecond);
                    firstRadio.setChecked(false);
                    secondRadio.setChecked(true);
                    thirdRadio.setChecked(false);
                    mListFirstGroup.setVisibility(View.GONE);
                    mListSecondGroup.setVisibility(View.VISIBLE);
                    mListThirdGroup.setVisibility(View.GONE);
                    break;
                case Ja.c:
//                    radioGroup.check(R.id.radioButtonThird);
                    firstRadio.setChecked(false);
                    secondRadio.setChecked(false);
                    thirdRadio.setChecked(true);
                    mListFirstGroup.setVisibility(View.GONE);
                    mListSecondGroup.setVisibility(View.GONE);
                    mListThirdGroup.setVisibility(View.VISIBLE);
                    break;
            }
        }
        setupFirstGroupList();
        setupSecondGroupList();
        setupThirdGroupList();

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

    }

    public void setupFirstGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_a);
        final List<Ja> listJa;
        try {
            listJa = daoFactory.getLocalJaDAO().getAllJas(Ja.a);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }
        jaAdapter = new MultiColumnMappedIndexArrayAdapter<Ja>(
                context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listJa);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Ja.DESCRIPTION);

        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        listview.setAdapter(jaAdapter);

        selectedJa = getQCalculationContext().getJa();
        if (selectedJa != null) {
            int posIndex = jaAdapter.getPosition(selectedJa);
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
                selectedJa = (Ja) parent.getItemAtPosition(position);
                getQCalculationContext().setJa(selectedJa);
            }

        });

    }

    public void setupSecondGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_b);
        final List<Ja> listJa;
        try {
            listJa = daoFactory.getLocalJaDAO().getAllJas(Ja.b);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }
        jaAdapter = new MultiColumnMappedIndexArrayAdapter<Ja>(
                context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listJa);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Ja.DESCRIPTION);

        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        listview.setAdapter(jaAdapter);

        selectedJa = getQCalculationContext().getJa();
        if (selectedJa != null) {
            int posIndex = jaAdapter.getPosition(selectedJa);
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
                selectedJa = (Ja) parent.getItemAtPosition(position);
                getQCalculationContext().setJa(selectedJa);
            }

        });
    }

    public void setupThirdGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_c);
        final List<Ja> listJa;
        try {
            listJa = daoFactory.getLocalJaDAO().getAllJas(Ja.c);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }
        jaAdapter = new MultiColumnMappedIndexArrayAdapter<Ja>(
                context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listJa);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Ja.DESCRIPTION);

        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        listview.setAdapter(jaAdapter);

        selectedJa = getQCalculationContext().getJa();
        if (selectedJa != null) {
            int posIndex = jaAdapter.getPosition(selectedJa);
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
                selectedJa = (Ja) parent.getItemAtPosition(position);
                getQCalculationContext().setJa(selectedJa);
            }

        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonFirst:
                mListFirstGroup.setVisibility(View.VISIBLE);
                mListFirstGroup.setSelection(1);
                mListFirstGroup.setItemChecked(1, true);
                selectedJa = (Ja) mListFirstGroup.getItemAtPosition(1);
                getQCalculationContext().setJa(selectedJa);
                mListSecondGroup.setVisibility(View.GONE);
                mListSecondGroup.clearChoices();
                mListThirdGroup.setVisibility(View.GONE);
                mListThirdGroup.clearChoices();
                break;
            case R.id.radioButtonSecond:
                mListFirstGroup.setVisibility(View.GONE);
                mListFirstGroup.clearChoices();
                mListSecondGroup.setVisibility(View.VISIBLE);
                mListSecondGroup.setSelection(1);
                mListSecondGroup.setItemChecked(1, true);
                selectedJa = (Ja) mListSecondGroup.getItemAtPosition(1);
                getQCalculationContext().setJa(selectedJa);
                mListThirdGroup.setVisibility(View.GONE);
                mListThirdGroup.clearChoices();
                break;
            case R.id.radioButtonThird:
                mListFirstGroup.setVisibility(View.GONE);
                mListFirstGroup.clearChoices();
                mListSecondGroup.setVisibility(View.GONE);
                mListSecondGroup.clearChoices();
                mListThirdGroup.setVisibility(View.VISIBLE);
                mListThirdGroup.setSelection(1);
                mListThirdGroup.setItemChecked(1, true);
                selectedJa = (Ja) mListSecondGroup.getItemAtPosition(1);
                getQCalculationContext().setJa(selectedJa);
                break;
        }
    }


}
