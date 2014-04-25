package com.metric.skava.calculator.rmr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.adapter.MultiColumnMappedIndexArrayAdapter;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

public class StrengthFragment extends RMRCalculatorBaseFragment {

    private View headerView;
    private Context mContext;
    private MultiColumnMappedIndexArrayAdapter<StrengthOfRock> strengthAdapter;
    private StrengthOfRock selectedStrength;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
//        List<StrengthOfRock> listStrenght = getMappedIndexDataProvider().getAllStrenghts();
        List<StrengthOfRock> listStrenght;
        try {
            listStrenght = daoFactory.getLocalStrengthDAO().getAllStrengths();
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        strengthAdapter = new MultiColumnMappedIndexArrayAdapter<StrengthOfRock>(mContext, R.layout.calculator_three_column_list_view_row_checked_radio, listStrenght);
        selectedStrength = getRMRCalculationContext().getStrengthOfRock();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_three_column_list_view_header_checked_radio, null, false);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_base_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.strength_title_label));

        final ListView listview = (ListView) getView().findViewById(R.id.listview);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
//        firstTextView.setText("Key");
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(StrengthOfRock.POINT_LOAD_KEY);
        TextView thirdTextView = (TextView) headerView.findViewById(R.id.third_column_text_view);
        thirdTextView.setText(StrengthOfRock.UNIAXIAL_KEY);

        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        listview.setAdapter(strengthAdapter);

        if (selectedStrength != null) {
            int posIndex = strengthAdapter.getPosition(selectedStrength);
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
                selectedStrength = (StrengthOfRock) parent.getItemAtPosition(position);
                getRMRCalculationContext().setStrengthOfRock(selectedStrength);
            }

        });

    }

}
