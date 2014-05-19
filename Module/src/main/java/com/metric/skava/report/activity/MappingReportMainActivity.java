package com.metric.skava.report.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.assessment.activity.AssessmentStageListActivity;
import com.metric.skava.assessment.activity.AssessmentsListActivity;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.RemoteAssessmentDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.report.fragment.MappingReportMainFragment;


public class MappingReportMainActivity extends SkavaFragmentActivity {

//    private Boolean isPreview;
//    public static final String IS_PREVIEW = "MAPPING_REPORT_MAIN_ACTIVITY_IS_PREVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapping_report_main_activity);
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MappingReportMainFragment())
                    .commit();
        }
//        isPreview = getIntent().getBooleanExtra(IS_PREVIEW, false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mapping_report_main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (getCurrentAssessment().isSentToCloud()) {
            // show no buttons as we dont want edit, re save nor resend
        } else {
            menu.findItem(R.id.action_mapping_report_draft).setVisible(true);
            if (getSkavaContext().getDatastore() != null) {
                menu.findItem(R.id.action_mapping_report_draft).setVisible(true);
            } else {
                menu.findItem(R.id.action_mapping_report_send).setVisible(false);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_mapping_report_draft) {
            boolean successOnSaving = saveDraft();
            if (successOnSaving) {
                Log.i(SkavaConstants.LOG, "Geological mapping draft succesfully saved.");
                Toast.makeText(this, "", Toast.LENGTH_LONG);
                backToAssessmentList();
            } else {
                Log.e(SkavaConstants.LOG, "Failed when saving geological mapping draft.");
                Toast.makeText(this, "Failed when saving geological mapping " + getCurrentAssessment().getInternalCode() + " :: " + getCurrentAssessment().getCode(), Toast.LENGTH_LONG);
            }
            return true;
        }
        if (id == R.id.action_mapping_report_send) {
            boolean successOnSend = sendAsCompleted();
            if (successOnSend) {
                Log.i(SkavaConstants.LOG, "Geological mapping succesfully send.");
                Toast.makeText(this, "", Toast.LENGTH_LONG);
                backToAssessmentList();
            } else {
                Log.e(SkavaConstants.LOG, "Failed when saving geological mapping.");
                Toast.makeText(this, "Failed when saving geological mapping " + getCurrentAssessment().getInternalCode() + " :: " + getCurrentAssessment().getCode(), Toast.LENGTH_LONG);
            }
            return true;
        }
        if (id == android.R.id.home) {
            backToStagesMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void backToStagesMenu() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.putExtra(AssessmentStageListActivity.REDIRECT_FROM_REPORT, true);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            // This activity is NOT part of this app's task, so create a new task
            // when navigating up, with a synthesized back stack.
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                    .startActivities();
        } else {
            // This activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    private void backToAssessmentList() {
        //Clear the current assessment, as this is a new stating point
        try {
            getSkavaContext().setAssessment(SkavaUtils.createInitialAssessment(getSkavaContext()));
        } catch (DAOException e) {
            throw new SkavaSystemException(e);
        }

        Intent upIntent = new Intent(this, AssessmentsListActivity.class);
//        upIntent.putExtra("REDIRECT", true);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            // This activity is NOT part of this app's task, so create a new task
            // when navigating up, with a synthesized back stack.
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                    .startActivities();
        } else {
            // This activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            NavUtils.navigateUpTo(this, upIntent);
        }

    }


    private boolean saveDraft() {
        try {
            Assessment currentAssessment = getCurrentAssessment();
            LocalAssessmentDAO localAssessmentDAO = getDAOFactory().getLocalAssessmentDAO();
            localAssessmentDAO.saveAssessment(currentAssessment);
            return true;
        } catch (DAOException e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }


    private boolean sendAsCompleted() {
        try {
            //First save locally
            saveDraft();
            Assessment currentAssessment = getCurrentAssessment();
            RemoteAssessmentDAO remoteAssessmentDAO = getDAOFactory().getRemoteAssessmentDAO(DAOFactory.Flavour.DROPBOX);
            remoteAssessmentDAO.saveAssessment(currentAssessment);

            currentAssessment.setSentToCloud(true);
            LocalAssessmentDAO localAssessmentDAO = getDAOFactory().getLocalAssessmentDAO();
            localAssessmentDAO.updateAssessment(currentAssessment, false);
            return true;
        } catch (DAOException e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }


}
