package com.metric.skava.report.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.report.fragment.MappingReportMainFragment;


public class MappingReportMainActivity extends SkavaFragmentActivity {

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mapping_report_main_menu, menu);
        return true;
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
            saveDraft();
            return true;
        }
        if (id == R.id.action_mapping_report_send) {
            sendAsCompleted();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveDraft(){
        LocalAssessmentDAO localAssessmentDAO;
        try {
            localAssessmentDAO = DAOFactory.getInstance(this).getAssessmentDAO(DAOFactory.Flavour.SQLLITE);
            Assessment currentAssessment = getCurrentAssessment();
            localAssessmentDAO.saveDraft(currentAssessment);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }
    }


    private void sendAsCompleted(){
        LocalAssessmentDAO localAssessmentDAO;
        try {
            localAssessmentDAO = DAOFactory.getInstance(this).getAssessmentDAO(DAOFactory.Flavour.SQLLITE);
            localAssessmentDAO.send(getCurrentAssessment());
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            throw new SkavaSystemException(e);
        }
    }



}
