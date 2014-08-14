package com.metric.skava.assessment.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.ViewUtils;
import com.metric.skava.assessment.dialog.adapter.AssessmentListAdapter;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the Callbacks
 * interface.
 */
public class AssessmentListFragment extends SkavaFragment implements AbsListView.OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Context mContext;

    private OnFragmentInteractionListener mListener;

    private ListView mListView;


    public static AssessmentListFragment newInstance(String userCode, String projectCode) {
        AssessmentListFragment fragment = new AssessmentListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userCode);
        args.putString(ARG_PARAM2, projectCode);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mContext = getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assessment_list, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(android.R.id.list);

        View listHeaderView = inflater.inflate(R.layout.fragment_assessment_list_header, null, false);

        TextView zeroTextView = (TextView) listHeaderView.findViewById(R.id.code_column_text_view);
        zeroTextView.setText("Code");
        TextView firstTextView = (TextView) listHeaderView.findViewById(R.id.chainage_column_text_view);
        firstTextView.setText("Chainage");
        TextView secondTextView = (TextView) listHeaderView.findViewById(R.id.date_column_text_view);
        secondTextView.setText("Date");
        TextView thirdTextView = (TextView) listHeaderView.findViewById(R.id.project_column_text_view);
        thirdTextView.setText("Project");
        TextView sixthTextView = (TextView) listHeaderView.findViewById(R.id.status_column_text_view);
        sixthTextView.setText("Status");
        mListView.addHeaderView(listHeaderView, null, false);

        AssessmentListAdapter adapter = mListener.getAssessmentListAdapter();
        ((AdapterView<ListAdapter>) mListView).setAdapter(adapter);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        //This method is necessary only to use a ListView inside a ScrollView
        ViewUtils.adjustListViewHeightBasedOnChildren(mListView);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            AssessmentListAdapter adapter = mListener.getAssessmentListAdapter();
            mListener.onAssessmentListRowClicked(adapter.getItem(position - 1));
        }
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface OnFragmentInteractionListener {
        public void onAssessmentListRowClicked(Assessment selectedAssessment);
        public AssessmentListAdapter getAssessmentListAdapter();
    }

}
