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
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

public class GroundwaterFragment extends RMRCalculatorBaseFragment {

    private View headerView;
    private Context mContext;
    private List<Groundwater> listGroundwater;
    private MultiColumnMappedIndexArrayAdapter<Groundwater> groudwaterAdapter;
    private Groundwater selectedGroundwater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        try {
            listGroundwater = daoFactory.getLocalGroundwaterDAO().getAllGroundwaters();
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            throw new SkavaSystemException(e);
        }
        groudwaterAdapter = new MultiColumnMappedIndexArrayAdapter<Groundwater>(mContext, R.layout.calculator_four_column_list_view_row_checked_radio, listGroundwater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_four_column_list_view_header_checked_radio, null, false);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_base_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.title_section_groundwater));

        final ListView listview = (ListView) getView().findViewById(R.id.listview);

        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Groundwater.INFLOW_LENGHT);

        TextView thirdTextView = (TextView) headerView.findViewById(R.id.third_column_text_view);
        thirdTextView.setText(Groundwater.JOINT_PRESS_PRINCIPAL);

        TextView fourthTextView = (TextView) headerView.findViewById(R.id.fourth_column_text_view);
        fourthTextView.setText(Groundwater.GENERAL_CONDITIONS);

        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        listview.setAdapter(groudwaterAdapter);

        selectedGroundwater = getRMRCalculationContext().getGroundwater();
        if (selectedGroundwater != null) {
            int posIndex = groudwaterAdapter.getPosition(selectedGroundwater);
            if (posIndex != -1){
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
                listview.setSelection(posIndex);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                selectedGroundwater = (Groundwater) parent.getItemAtPosition(position);
                getRMRCalculationContext().setGroundwater(selectedGroundwater);
            }

        });

    }

}
