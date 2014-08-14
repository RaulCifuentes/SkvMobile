package com.metric.skava.report.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
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
import com.metric.skava.report.fragment.AssessmentReportMainFragment;


public class AssessmentReportMainActivity extends SkavaFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.assessment_report_main_activity);
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
                    .add(R.id.container, new AssessmentReportMainFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.assessment_report_main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (getCurrentAssessment().getSavedStatus()) {
            case DRAFT:
                menu.findItem(R.id.action_assessment_report_use_as_template).setVisible(false);
                break;
        }

        if (getCurrentAssessment().getDataSentStatus() != null){
            switch (getCurrentAssessment().getDataSentStatus()) {
                case SENT_TO_CLOUD:
                case SENT_TO_DATASTORE:
                    // show no buttons as we dont want edit, re save nor resend
                    menu.findItem(R.id.action_assessment_report_save).setVisible(false);
                    menu.findItem(R.id.action_assessment_report_send).setVisible(false);
                    break;
                case NOT_SENT:
                    //Save as draft available as this is still editable
                    menu.findItem(R.id.action_assessment_report_save).setVisible(true);
                    if (getSkavaContext().getDatastore() != null) {
                        menu.findItem(R.id.action_assessment_report_send).setVisible(true);
                    } else {
                        menu.findItem(R.id.action_assessment_report_send).setVisible(false);
                    }
                    break;
            }
        } else {
            //i.e It has never been sent, just saved locally
            //Save as draft available as this is still editable
            menu.findItem(R.id.action_assessment_report_save).setVisible(true);
            if (getSkavaContext().getDatastore() != null) {
                menu.findItem(R.id.action_assessment_report_send).setVisible(true);
            } else {
                menu.findItem(R.id.action_assessment_report_send).setVisible(false);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_assessment_report_save) {
            boolean successOnSaving = save();
            if (successOnSaving) {
                Log.i(SkavaConstants.LOG, "Geological mapping draft succesfully saved.");
                backToAssessmentList();
            } else {
                Log.e(SkavaConstants.LOG, "Failed when saving geological mapping draft.");
                Toast.makeText(this, "Failed when saving geological mapping " + getCurrentAssessment().getInternalCode() + " :: " + getCurrentAssessment().getCode(), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (id == R.id.action_assessment_report_send) {
            boolean successOnSend = sendAsCompleted();
            if (successOnSend) {
                Log.d(SkavaConstants.LOG, "Geological mapping succesfully send :: " + getCurrentAssessment().getInternalCode() + " - " + getCurrentAssessment().getCode());
                backToAssessmentList();
            } else {
                Log.d(SkavaConstants.LOG, "Failed when saving geological mapping :: " + getCurrentAssessment().getInternalCode() + " - " + getCurrentAssessment().getCode());
            }
            return true;
        }
        if (id == R.id.action_assessment_report_use_as_template) {
            try {
                //clone the assessment (except pictures, expanded, chaingaes, etc)
                Assessment clonedAssessment = SkavaUtils.cloneAssessment(getSkavaContext(), getCurrentAssessment());
                //set that cloned assessment as the current one (SkavaContext)
                getSkavaContext().setAssessment(clonedAssessment);
                //jump to the assessment stages list
                backToStagesMenu();
            } catch (DAOException e) {
                throw new SkavaSystemException(e);
            }
            return true;
        }
        if (id == R.id.action_assessment_report_delete) {
            //delete current assessment
            LocalAssessmentDAO mLocalAsssessmentDAO = null;
            try {
                mLocalAsssessmentDAO = getDAOFactory().getLocalAssessmentDAO();
                mLocalAsssessmentDAO.deleteAssessment(getCurrentAssessment().getCode());
                getSkavaContext().setAssessment(null);
            } catch (DAOException e) {
                e.printStackTrace();
                Log.e(SkavaConstants.LOG, e.getMessage());
                BugSenseHandler.sendException(e);
            }
            //back to assesment list
            backToAssessmentList();
        }
        if (id == android.R.id.home) {
            backToStagesMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean sendAsCompleted() {
        try {
            //First save locally
            save();
            Assessment currentAssessment = getCurrentAssessment();
            RemoteAssessmentDAO remoteAssessmentDAO = getDAOFactory().getRemoteAssessmentDAO(DAOFactory.Flavour.DROPBOX);
            remoteAssessmentDAO.saveAssessment(currentAssessment);
            return true;
        } catch (final DAOException e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
            // Handle exception or at least show the user an alert
            DialogFragment theDialog = new DialogFragment() {
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentReportMainActivity.this);
                    builder.setTitle("Problems on sending ...");
                    builder.setMessage("There was an error while saving the geological assessment remotely: " + e.getMessage());
                    // Create the AlertDialog object and return it
                    return builder.create();
                }
            };
            // Showing Alert Message
            theDialog.show(getSupportFragmentManager(), "assertAppDataDialog");
            return false;
        }
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
            //NavUtils.navigateUpTo(this, upIntent) does not work on JellyBean
            //NavUtils.navigateUpTo(this, upIntent);
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(upIntent);
            finish();
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
