package com.metric.skava.sync.fragment;

/**
 * Created by metricboy on 3/7/14.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.ViewUtils;
import com.metric.skava.data.adapter.AssessmentListViewAdapter;
import com.metric.skava.data.adapter.RemoteAssessmentListViewAdapter;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.LocalDiscontinuityFamilyDAO;
import com.metric.skava.data.dao.LocalQCalculationDAO;
import com.metric.skava.data.dao.LocalRMRCalculationDAO;
import com.metric.skava.data.dao.LocalSupportRecommendationDAO;
import com.metric.skava.data.dao.RemoteAssessmentDAO;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SyncMainFragment extends SkavaFragment {

    private ListView localAssessmentsListView;
    private ListView remoteAssessmentsListView;

    private AssessmentListViewAdapter localAssessmentListViewAdapter;
    private RemoteAssessmentListViewAdapter remoteAssessmentListViewAdapter;

    private LayoutInflater mInflater;

    private LocalAssessmentDAO mLocalAsssessmentDAO;
    private LocalSupportRecommendationDAO mLocalRecommendationDAO;
    private LocalDiscontinuityFamilyDAO mLocalDiscontinuitiesFamilyDAO;
    private LocalRMRCalculationDAO mLocalRMRCalculationDAO;
    private LocalQCalculationDAO mLocalBartonCalculationDAO;

    private RemoteAssessmentDAO mRemoteAsssessmentDAO;


    public SyncMainFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mLocalAsssessmentDAO = getDAOFactory().getLocalAssessmentDAO();
            mLocalRecommendationDAO = getDAOFactory().getLocalSupportRecommendationDAO();
            mLocalDiscontinuitiesFamilyDAO = getDAOFactory().getLocalDiscontinuityFamilyDAO();
            mLocalRMRCalculationDAO = getDAOFactory().getLocalRMRCalculationDAO();
            mLocalBartonCalculationDAO = getDAOFactory().getLocalQCalculationDAO();

            mRemoteAsssessmentDAO = getDAOFactory().getRemoteAssessmentDAO(DAOFactory.Flavour.DROPBOX);


        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sync_main_fragment, container, false);
        mInflater = inflater;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupLocalAssessmentsListView(mInflater);
        setupRemoteAssessmentsListView(mInflater);
    }

    private void setupLocalAssessmentsListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.test_list_header, null, false);
        localAssessmentsListView = (ListView) getView().findViewById(R.id.listview_local_assessments);
        final List<Assessment> listAssessments;
        try {
            listAssessments = mLocalAsssessmentDAO.getAllAssessments();
            localAssessmentListViewAdapter = new AssessmentListViewAdapter(getSkavaActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listAssessments);

            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.headerText);
            firstTextView.setText("Local Assessments");

            localAssessmentsListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = localAssessmentsListView.getHeaderViewsCount();
            localAssessmentsListView.setAdapter(localAssessmentListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(localAssessmentsListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void setupRemoteAssessmentsListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.test_list_header, null, false);
        remoteAssessmentsListView = (ListView) getView().findViewById(R.id.listview_remote_assessments);
        final List<Assessment> listAssessments;
        try {
            listAssessments = mRemoteAsssessmentDAO.getAllAssessments();
            remoteAssessmentListViewAdapter = new RemoteAssessmentListViewAdapter(getSkavaActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listAssessments);

            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.headerText);
            firstTextView.setText("Remote Assessments");

            remoteAssessmentsListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = remoteAssessmentsListView.getHeaderViewsCount();
            remoteAssessmentsListView.setAdapter(remoteAssessmentListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(remoteAssessmentsListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_clear_local_assessments:
                try {
                    mLocalRecommendationDAO.deleteAllSupportRecommendations();
                    mLocalRMRCalculationDAO.deleteAllRMRCalculations();
                    mLocalBartonCalculationDAO.deleteAllQCalculations();
                    mLocalDiscontinuitiesFamilyDAO.deleteAllDiscontinuitiesFamilies();
                    mLocalAsssessmentDAO.deleteAllAssessments();
                    refreshListViews();
                } catch (DAOException daoe) {
                    Log.d(SkavaConstants.LOG, daoe.getMessage());
                    Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG);
                }
                break;
            case R.id.action_clear_remote_assessments:
                try {
                    mRemoteAsssessmentDAO.deleteAllAssessments(true);
                    refreshListViews();
                } catch (DAOException daoe){
                    Log.d(SkavaConstants.LOG, daoe.getMessage());
                    Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG);
                }
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void refreshListViews() throws DAOException {
        localAssessmentListViewAdapter.clear();
        localAssessmentListViewAdapter.addAll(mLocalAsssessmentDAO.getAllAssessments());
        localAssessmentListViewAdapter.notifyDataSetChanged();

        remoteAssessmentListViewAdapter.clear();
        remoteAssessmentListViewAdapter.addAll(mRemoteAsssessmentDAO.getAllAssessments());
        remoteAssessmentListViewAdapter.notifyDataSetChanged();
    }
}