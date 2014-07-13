package com.metric.skava.assessment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.sync.android.DbxDatastore;
import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.assessment.data.AssesmentStageDataProvider;
import com.metric.skava.assessment.dialog.adapter.AssessmentListAdapter;
import com.metric.skava.assessment.fragment.AssessmentListFragment;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.report.activity.MappingReportMainActivity;
import com.metric.skava.report.activity.ReviewReportMainActivity;
import com.metric.skava.report.fragment.MappingReportMainFragment;

import java.util.List;

public class AssessmentsListActivity extends SkavaFragmentActivity implements AssessmentListFragment.OnFragmentInteractionListener {


    private LocalAssessmentDAO localAssessmentDAO;
    private AssessmentListAdapter mAssessmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessments_list_activity);
        try {
            localAssessmentDAO = getDAOFactory().getLocalAssessmentDAO();
            mAssessmentAdapter = new AssessmentListAdapter(this);
            updateAssessmentList();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            BugSenseHandler.sendException(e);
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AssessmentListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        User loggedUser = getSkavaContext().getLoggedUser();
        LocalRoleDAO localRoleDAO;
       Role adminRole = null, geologistRole = null, analystRole = null;
        try {
            localRoleDAO = getDAOFactory().getLocalRoleDAO();
            adminRole = localRoleDAO.getRoleByCode(SkavaConstants.ROLE_ADMIN_NAME);
            geologistRole = localRoleDAO.getRoleByCode(SkavaConstants.ROLE_GEOLOGIST_NAME);
            analystRole = localRoleDAO.getRoleByCode(SkavaConstants.ROLE_ANALYST_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            BugSenseHandler.sendException(e);
        }
        if (loggedUser != null) {
            if (loggedUser.hasRole(adminRole)) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.assessments_list_menu_admin, menu);
            }
            if (loggedUser.hasRole(geologistRole)) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.assessments_list_menu_geologist, menu);
            }
            if (loggedUser.hasRole(analystRole)) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.assessments_list_menu_analyst, menu);
            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_new_assessment) {
            getSkavaContext().setAssessment(null);
            Intent intent = new Intent(this, AssessmentStageListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDatastoreStatusChange(DbxDatastore store) {
        super.onDatastoreStatusChange(store);
        this.updateAssessmentList();
    }

    @Override
    public AssessmentListAdapter getAssessmentListAdapter() {
        return mAssessmentAdapter;
    }

    @Override
    public void onAssessmentListRowClicked(Assessment selectedAssessment) {
        // Lanzar activity que muestra el reporte
        getSkavaContext().setAssessment(selectedAssessment);
        Intent detailIntent;
        int sentStatus = selectedAssessment.getDataSentStatus();
        switch (sentStatus) {
            case Assessment.DATA_SENT_TO_CLOUD:
            case Assessment.DATA_SENT_TO_DATASTORE:
                detailIntent = new Intent(this, ReviewReportMainActivity.class);
                detailIntent.putExtra(MappingReportMainFragment.ARG_BASKET_ID, AssesmentStageDataProvider.REPORT);
                break;
            default:
                detailIntent = new Intent(this, MappingReportMainActivity.class);
                detailIntent.putExtra(MappingReportMainFragment.ARG_BASKET_ID, AssesmentStageDataProvider.REPORT);
        }
        startActivity(detailIntent);
    }

    public void updateAssessmentList() {
        List<Assessment> updatedList = null;
        try {
            updatedList = localAssessmentDAO.getAssessmentsByUser(getTargetEnvironment(), getSkavaContext().getLoggedUser());
        } catch (Exception daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            BugSenseHandler.sendException(daoe);
        }
        if (mAssessmentAdapter != null) {
            mAssessmentAdapter.setAssessmentList(updatedList);
            mAssessmentAdapter.notifyDataSetChanged();
        }
    }


    public void onPreExecuteImportAppData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    public void onPreExecuteImportUserData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    @Override
    public void onPostExecuteImportAppData(boolean success, Long result) {

    }

    @Override
    public void onPostExecuteImportUserData(boolean success, Long result) {

    }

    public void showProgressBar(final boolean show, String text, boolean longTime) {

    }

}
