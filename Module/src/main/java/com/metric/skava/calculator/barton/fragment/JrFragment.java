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
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * JnFragment.OnFragmentInteractionListener interface to handle
 * interaction events.
 */
public class JrFragment extends QBartonCalculatorBaseFragment implements RadioGroup.OnCheckedChangeListener {


    private FragmentActivity context;
    private View headerView;
    private ListView mListFirstGroup;
    private ListView mListSecondGroup;
    private ListView mListThirdGroup;
    private Jr selectedJr;
    private MultiColumnMappedIndexArrayAdapter<Jr> jrAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        context = this.getActivity();

        selectedJr = getQCalculationContext().getJr();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_radios_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.jrTitle));

        RadioButton firstRadio = (RadioButton) view.findViewById(R.id.radioButtonFirst);
        firstRadio.setText(Jr.Group.a.toString());
        RadioButton secondRadio = (RadioButton) view.findViewById(R.id.radioButtonSecond);
        secondRadio.setText(Jr.Group.b.toString());
        RadioButton thirdRadio = (RadioButton) view.findViewById(R.id.radioButtonThird);
        thirdRadio.setText(Jr.Group.c.toString());
        RadioButton fourthRadio = (RadioButton) view.findViewById(R.id.radioButtonFourth);
        fourthRadio.setVisibility(View.GONE);

        mListFirstGroup = (ListView) view.findViewById(R.id.listview_a);
        mListFirstGroup.setVisibility(View.INVISIBLE);
        mListSecondGroup = (ListView) view.findViewById(R.id.listview_b);
        mListSecondGroup.setVisibility(View.INVISIBLE);
        mListThirdGroup = (ListView) view.findViewById(R.id.listview_c);
        mListThirdGroup.setVisibility(View.INVISIBLE);


        if (selectedJr != null) {
            //what (type) group of Jr is this in order to show the correspondant list
            switch (selectedJr.getGroup()) {
                case a:
//                    radioGroup.check(R.id.radioButtonFirst);
                    firstRadio.setChecked(true);
                    secondRadio.setChecked(false);
                    thirdRadio.setChecked(false);
                    mListFirstGroup.setVisibility(View.VISIBLE);
                    mListSecondGroup.setVisibility(View.GONE);
                    mListThirdGroup.setVisibility(View.GONE);
                    break;
                case b:
//                    radioGroup.check(R.id.radioButtonSecond);
                    firstRadio.setChecked(false);
                    secondRadio.setChecked(true);
                    thirdRadio.setChecked(false);
                    mListFirstGroup.setVisibility(View.GONE);
                    mListSecondGroup.setVisibility(View.VISIBLE);
                    mListThirdGroup.setVisibility(View.GONE);
                    break;
                case c:
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
        //selects the tunnels as default if theres no previous info
        setupFirstGroupList();
        setupSecondGroupList();
        setupThirdGroupList();
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
    }

    public void setupFirstGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_a);
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Jr.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        final List<Jr> listJr;
        try {
            listJr = getDAOFactory().getLocalJrDAO().getAllJrs(Jr.Group.a);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }
        jrAdapter = new MultiColumnMappedIndexArrayAdapter<Jr>(context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listJr);
        listview.setAdapter(jrAdapter);

        if (selectedJr != null) {
            int posIndex = jrAdapter.getPosition(selectedJr);
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
                selectedJr = (Jr) parent.getItemAtPosition(position);
                getQCalculationContext().setJr(selectedJr);
            }
        });
    }

    public void setupSecondGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_b);
        final List<Jr> listJr;
        try {
            listJr = getDAOFactory().getLocalJrDAO().getAllJrs(Jr.Group.b);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }
        jrAdapter = new MultiColumnMappedIndexArrayAdapter<Jr>(context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listJr);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Jr.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        listview.setAdapter(jrAdapter);

        selectedJr = getQCalculationContext().getJr();
        if (selectedJr != null) {
            int posIndex = jrAdapter.getPosition(selectedJr);
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
                selectedJr = (Jr) parent.getItemAtPosition(position);
                getQCalculationContext().setJr(selectedJr);
            }
        });
    }

    public void setupThirdGroupList() {
        final ListView listview = (ListView) getView().findViewById(R.id.listview_c);
        final List<Jr> listJr;
        try {
            listJr = getDAOFactory().getLocalJrDAO().getAllJrs(Jr.Group.c);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }
        jrAdapter = new MultiColumnMappedIndexArrayAdapter<Jr>(context,
                R.layout.calculator_two_column_list_view_row_checked_radio, listJr);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Jr.DESCRIPTION);
        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        listview.setAdapter(jrAdapter);

        selectedJr = getQCalculationContext().getJr();
        if (selectedJr != null) {
            int posIndex = jrAdapter.getPosition(selectedJr);
            posIndex += numberOfHeaders;
            listview.setItemChecked(posIndex, true);
            listview.setSelection(posIndex);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedJr = (Jr) parent.getItemAtPosition(position);
                getQCalculationContext().setJr(selectedJr);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonFirst:
                mListFirstGroup.setVisibility(View.VISIBLE);
                if (selectedJr == null) {
                    //i though the reset was only for null values but second thoughts on this ..
                }
                mListFirstGroup.setSelection(1);
                mListFirstGroup.setItemChecked(1, true);
                selectedJr = (Jr) mListFirstGroup.getItemAtPosition(1);
                getQCalculationContext().setJr(selectedJr);
                mListSecondGroup.setVisibility(View.GONE);
                mListSecondGroup.clearChoices();
                mListThirdGroup.setVisibility(View.GONE);
                mListThirdGroup.clearChoices();
                break;
            case R.id.radioButtonSecond:
                mListFirstGroup.setVisibility(View.GONE);
                mListFirstGroup.clearChoices();
                mListSecondGroup.setVisibility(View.VISIBLE);
                if (selectedJr == null) {
                    //i though the reset was only for null values but second thoughts on this ..
                }
                mListSecondGroup.setSelection(1);
                mListSecondGroup.setItemChecked(1, true);
                selectedJr = (Jr) mListSecondGroup.getItemAtPosition(1);
                getQCalculationContext().setJr(selectedJr);
                mListThirdGroup.setVisibility(View.GONE);
                mListThirdGroup.clearChoices();
                break;
            case R.id.radioButtonThird:
                mListFirstGroup.setVisibility(View.GONE);
                mListFirstGroup.clearChoices();
                mListSecondGroup.setVisibility(View.GONE);
                mListSecondGroup.clearChoices();
                mListThirdGroup.setVisibility(View.VISIBLE);
                if (selectedJr == null) {
                    //i though the reset was only for null values but second thoughts on this ..
                }
                mListThirdGroup.setSelection(1);
                mListThirdGroup.setItemChecked(1, true);
                selectedJr = (Jr) mListThirdGroup.getItemAtPosition(1);
                getQCalculationContext().setJr(selectedJr);
                break;
        }

    }

}
