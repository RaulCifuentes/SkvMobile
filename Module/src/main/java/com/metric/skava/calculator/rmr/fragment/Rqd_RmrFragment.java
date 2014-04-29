package com.metric.skava.calculator.rmr.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.calculator.adapter.MultiColumnMappedIndexArrayAdapter;
import com.metric.skava.calculator.barton.model.RQD;
import com.metric.skava.calculator.rmr.model.RQD_RMR;

import java.util.List;

public class Rqd_RmrFragment extends RMRCalculatorBaseFragment {

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
		title.setText(getString(R.string.title_section_rqd));

		FragmentActivity context = this.getActivity();

//        final List<RQD_RMR> listRQDs = getMappedIndexDataProvider().getAllRqdRmr();
        final List<RQD_RMR> listRQDs = RQD_RMR.getAllRqdRmr();

        final MultiColumnMappedIndexArrayAdapter<RQD_RMR> adapter = new MultiColumnMappedIndexArrayAdapter<RQD_RMR>(context, R.layout.calculator_two_column_list_view_row_checked_radio, listRQDs);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(RQD_RMR.DESCRIPTION);

        listview.addHeaderView(headerView, null, false);
        final int numberOfHeaders = listview.getHeaderViewsCount();
        listview.setAdapter(adapter);

        RQD rqd = getRMRCalculationContext().getRqd();
        if (rqd != null) {
            RQD_RMR wrapperRqd = RQD_RMR.findWrapper(rqd);
            int posIndex = adapter.getPosition(wrapperRqd);
            posIndex += numberOfHeaders;
            listview.setItemChecked(posIndex, true);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                RQD_RMR selectedItem = (RQD_RMR) parent.getItemAtPosition(position);
                RQD bareRqd = selectedItem.getWrappedRqd();
                getRMRCalculationContext().setRqd(bareRqd);
            }

        });

	}

}
