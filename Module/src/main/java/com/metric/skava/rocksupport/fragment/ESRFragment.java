package com.metric.skava.rocksupport.fragment;

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

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.adapter.MultiColumnMappedIndexArrayAdapter;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.data.dao.LocalEsrDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.adapter.MappedIndexSpinnerArrayAdapter;
import com.metric.skava.rocksupport.model.ESR;

import java.util.List;

/**
* Created by metricboy on 3/9/14.
*/
public class ESRFragment extends SkavaFragment {

    private View headerView;
    private MappedIndexSpinnerArrayAdapter esrAdapter;
    private ESR selectedESR;
    private List<ESR> esrList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalEsrDAO localEsrDAO = null;
        try {
            localEsrDAO = getDAOFactory().getLocalEsrDAO();
            esrList = localEsrDAO.getAllESRs(ESR.Group.i);
            esrList.add(new ESR(null, null, "HINT", "Select one ESR ...", "Select one ESR ...", 1d));
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        // Create an ArrayAdapter using the string array and a default spinner layout
        esrAdapter = new MappedIndexSpinnerArrayAdapter<ESR>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, esrList);
        // Specify the layout to use when the list of choices appears
        esrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        headerView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calculator_mapped_index_list_base_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listview = (ListView) getView().findViewById(
                R.id.listview);

        TextView title = (TextView) getView().findViewById(R.id.fragmentTitle);
        title.setText(getString(R.string.esrTitle));

        FragmentActivity context = this.getActivity();

        final List<ESR> listESR ;
        try {
            listESR = getDAOFactory().getLocalEsrDAO().getAllESRs(ESR.Group.i);
        } catch (DAOException e){
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }


        final MultiColumnMappedIndexArrayAdapter<ESR> adapter = new MultiColumnMappedIndexArrayAdapter<ESR>(
                context, R.layout.calculator_two_column_list_view_row_checked_radio, listESR);

        TextView firstTextView = (TextView) headerView.findViewById(R.id.first_column_text_view);
        TextView secondTextView = (TextView) headerView.findViewById(R.id.second_column_text_view);
        secondTextView.setText(Jn.DESCRIPTION);

        listview.addHeaderView(headerView, null, false);

        listview.setAdapter(adapter);

        final int numberOfHeaders = listview.getHeaderViewsCount();

//        ESR esr = getSupportRequirementsContext().getEsr();
        ESR esr = getCurrentAssessment().getTunnel().getExcavationFactors().getEsr();
        if (esr != null) {
            int posIndex = adapter.getPosition(esr);
            //adjust as the adapter takes header also into account
            posIndex += numberOfHeaders;
            listview.setItemChecked(posIndex, true);
            listview.setSelection(posIndex);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                ESR selectedItem = (ESR) parent.getItemAtPosition(position);
//                getSupportRequirementsContext().setEsr(selectedItem);
                getCurrentAssessment().getTunnel().getExcavationFactors().setEsr(selectedItem);
            }
        });
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Exiting " + ESRFragment.class.getSimpleName()
                    + " : onViewCreated ");
        }
    }



}
