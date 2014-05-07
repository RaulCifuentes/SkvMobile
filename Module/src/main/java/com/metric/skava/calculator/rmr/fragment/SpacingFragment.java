package com.metric.skava.calculator.rmr.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

public class SpacingFragment extends RMRCalculatorBaseFragment {

    private View headerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_base_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listview = (ListView) getView().findViewById(
                R.id.listview);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.title_section_spacing));

        FragmentActivity context = this.getActivity();

//        final List<Spacing> listSpacings = getMappedIndexDataProvider().getAllSpacings();
        final List<Spacing> listSpacings;
        try {
            listSpacings = getDAOFactory().getLocalSpacingDAO().getAllSpacings();
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }


        final MultiColumnMappedIndexArrayAdapter<Spacing> adapter = new MultiColumnMappedIndexArrayAdapter<Spacing>(context, R.layout.calculator_two_column_list_view_row_checked_radio, listSpacings);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Spacing.DESCRIPTION);

        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        listview.setAdapter(adapter);


        Spacing spacingDiscontinuities = getRMRCalculationContext().getSpacing();
        if (spacingDiscontinuities != null) {
			int posIndex = adapter.getPosition(spacingDiscontinuities);
            posIndex += numberOfHeaders;
            listview.setItemChecked(posIndex, true);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Spacing selectedItem = (Spacing) parent.getItemAtPosition(position);
                getRMRCalculationContext().setSpacing(selectedItem);
            }

        });

    }

}
