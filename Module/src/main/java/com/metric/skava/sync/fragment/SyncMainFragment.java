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
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.LocalDiscontinuityFamilyDAO;
import com.metric.skava.data.dao.LocalQCalculationDAO;
import com.metric.skava.data.dao.LocalRMRCalculationDAO;
import com.metric.skava.data.dao.LocalSupportRecommendationDAO;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SyncMainFragment extends SkavaFragment {



    private ListView assessmentsListView;
    private ListView qCalculationsListView;
    private ListView rmrCalculationsListView;
    private ListView discontinuitiesFamiliesListView;
    private ListView supportRecommendationsListView;

    private AssessmentListViewAdapter assessmentListViewAdapter;

//    private SupportRecommendationListViewAdapter recommendationListViewAdapter;
//    private QCalculationListViewAdapter userListViewAdapter;
//    private RMRCalculationListViewAdapter projectListAdapter;
//    private DiscontinuitiesListViewAdapter tunnelListViewAdapter;

    private LayoutInflater mInflater;

    private LocalAssessmentDAO mAsssessmentDAO;
    private LocalSupportRecommendationDAO mRecommendationDAO;
    private LocalDiscontinuityFamilyDAO mDiscontinuitiesFamilyDAO;
    private LocalRMRCalculationDAO mRMRCalculationDAO;
    private LocalQCalculationDAO mBartonCalculationDAO;


    public SyncMainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mAsssessmentDAO = getDAOFactory().getLocalAssessmentDAO();
            mRecommendationDAO = getDAOFactory().getLocalSupportRecommendationDAO();
            mDiscontinuitiesFamilyDAO = getDAOFactory().getLocalDiscontinuityFamilyDAO();
            mRMRCalculationDAO = getDAOFactory().getLocalRMRCalculationDAO();
            mBartonCalculationDAO = getDAOFactory().getLocalQCalculationDAO();

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
        setupAssessmentsListView(mInflater);
    }

    private void setupAssessmentsListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        assessmentsListView = (ListView) getView().findViewById(R.id.listview_assessments);
        final List<Assessment> listAssessments;
        try {
            listAssessments = mAsssessmentDAO.getAllAssessments();
            assessmentListViewAdapter = new AssessmentListViewAdapter(getSkavaActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listAssessments);

            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
            firstTextView.setText("Internal Code");
            TextView secondTextView = (TextView) usersHeaderView.findViewById(R.id.second_column_text_view);
            secondTextView.setText("Code");

            assessmentsListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = assessmentsListView.getHeaderViewsCount();
            assessmentsListView.setAdapter(assessmentListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(assessmentsListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

//    private void setupRolesListView(LayoutInflater inflater) {
//        View usersHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
//        rolesListView = (ListView) getView().findViewById(R.id.listviewRoles);
//        final List<Role> listRoles;
//        try {
//            listRoles = daoFactory.getLocalRoleDAO().getAllRoles();
//            roleListViewAdapter = new RoleListViewAdapter(getActivity(),
//                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listRoles);
//
////            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
//            TextView secondTextView = (TextView) usersHeaderView.findViewById(R.id.second_column_text_view);
//            secondTextView.setText("Roles");
//
//            rolesListView.addHeaderView(usersHeaderView, null, false);
//            final int numberOfHeaders = rolesListView.getHeaderViewsCount();
//            rolesListView.setAdapter(roleListViewAdapter);
//
//            //This method is necessary only to use a ListView inside a ScrollView
//            ViewUtils.adjustListViewHeightBasedOnChildren(rolesListView);
//
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //summarized table
//    private void setupUsersListView(LayoutInflater inflater) {
//        View usersHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
//        usersListView = (ListView) getView().findViewById(R.id.listviewUsers);
//        final List<User> listUsers;
//        try {
//            listUsers = daoFactory.getLocalUserDAO().getAllUsers();
//            userListViewAdapter = new UserListViewAdapter(getActivity(),
//                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listUsers);
//
////            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
//            TextView secondTextView = (TextView) usersHeaderView.findViewById(R.id.second_column_text_view);
//            secondTextView.setText("Users");
//
//            usersListView.addHeaderView(usersHeaderView, null, false);
//            final int numberOfHeaders = usersListView.getHeaderViewsCount();
//            usersListView.setAdapter(userListViewAdapter);
//
//            //This method is necessary only to use a ListView inside a ScrollView
//            ViewUtils.adjustListViewHeightBasedOnChildren(usersListView);
//
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setupProjectsListView(LayoutInflater inflater) {
//        View usersHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
//        projectsListView = (ListView) getView().findViewById(R.id.listviewProjects);
//        final List<ExcavationProject> listProjects;
//        try {
//            listProjects = daoFactory.getLocalExcavationProjectDAO().getAllExcavationProjects();
//            projectListAdapter = new ProjectListViewAdapter(getActivity(),
//                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listProjects);
//
////            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
//            TextView secondTextView = (TextView) usersHeaderView.findViewById(R.id.second_column_text_view);
//            secondTextView.setText("Projects");
//
//            projectsListView.addHeaderView(usersHeaderView, null, false);
//            final int numberOfHeaders = projectsListView.getHeaderViewsCount();
//            projectsListView.setAdapter(projectListAdapter);
//
//            //This method is necessary only to use a ListView inside a ScrollView
//            ViewUtils.adjustListViewHeightBasedOnChildren(projectsListView);
//
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setupTunnelsListView(LayoutInflater inflater) {
//        View tunnelsHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
//        tunnelsListView = (ListView) getView().findViewById(R.id.listviewTunnels);
//        final List<Tunnel> listTunnels;
//        try {
//            listTunnels = daoFactory.getLocalTunnelDAO().getAllTunnels();
//            tunnelListViewAdapter = new TunnelListViewAdapter(getActivity(),
//                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listTunnels);
//
////            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
//            TextView secondTextView = (TextView) tunnelsHeaderView.findViewById(R.id.second_column_text_view);
//            secondTextView.setText("Tunnels");
//
//            tunnelsListView.addHeaderView(tunnelsHeaderView, null, false);
//            final int numberOfHeaders = tunnelsListView.getHeaderViewsCount();
//            tunnelsListView.setAdapter(tunnelListViewAdapter);
//
//            //This method is necessary only to use a ListView inside a ScrollView
//            ViewUtils.adjustListViewHeightBasedOnChildren(tunnelsListView);
//
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_clear_assessments:
                try {
                    mRecommendationDAO.deleteAllSupportRecommendations();
                    mRMRCalculationDAO.deleteAllRMRCalculations();
                    mBartonCalculationDAO.deleteAllQCalculations();
                    mDiscontinuitiesFamilyDAO.deleteAllDiscontinuitiesFamilies();

                    mAsssessmentDAO.deleteAllAssessments();
                    refreshListViews();
                } catch (DAOException e) {
                    Log.d(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
                }
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void refreshListViews() throws DAOException {
        assessmentListViewAdapter.clear();
        assessmentListViewAdapter.addAll(mAsssessmentDAO.getAllAssessments());
        assessmentListViewAdapter.notifyDataSetChanged();

    }
}