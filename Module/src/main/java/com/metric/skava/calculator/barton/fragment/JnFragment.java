package com.metric.skava.calculator.barton.fragment;

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

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.adapter.MultiColumnMappedIndexArrayAdapter;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

public class JnFragment extends QBartonCalculatorBaseFragment {

    private View headerView;
    private Context mContext;
    private MultiColumnMappedIndexArrayAdapter<Jn> jnAdapter;
    private Jn selectedJn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<Jn> listJn;
        try {
            listJn = daoFactory.getLocalJnDAO().getAllJns();
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }

        mContext = this.getActivity();
        jnAdapter = new MultiColumnMappedIndexArrayAdapter<Jn>(
                mContext, R.layout.calculator_two_column_list_view_row_checked_radio, listJn);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (BuildConfig.DEBUG) {
			Log.d(SkavaConstants.LOG, "Entering " + JnFragment.class.getSimpleName()
					+ " : onCreateView ");
		}
        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);

        selectedJn = getQCalculationContext().getJn();

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.calculator_mapped_index_list_base_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {	
		super.onViewCreated(view, savedInstanceState);
		final ListView listview = (ListView) getView().findViewById(R.id.listview);

		TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
		title.setText(getString(R.string.jnTitle));
        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Jn.DESCRIPTION);

        listview.addHeaderView(headerView, null, false);
		listview.setAdapter(jnAdapter);
        final int numberOfHeaders = listview.getHeaderViewsCount();

        if (selectedJn != null) {
            if (jnAdapter != null) {
                int posIndex = jnAdapter.getPosition(selectedJn);
                //adjust as the adapter takes header also into account
                posIndex += numberOfHeaders;
                listview.setItemChecked(posIndex, true);
                listview.setSelection(posIndex);
            }
        } else {
            //ask the user to select a value display hint
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
                selectedJn = (Jn) parent.getItemAtPosition(position);
				getQCalculationContext().setJn(selectedJn);
			}
		});		
		if (BuildConfig.DEBUG) {
			Log.d(SkavaConstants.LOG, "Exiting " + JnFragment.class.getSimpleName()
					+ " : onViewCreated ");
		}
	}

}
