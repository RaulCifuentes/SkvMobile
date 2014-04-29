package com.metric.skava.assessment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.assessment.fragment.AssessmentListFragment;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.exception.DAOException;

public class AssessmentsListActivity extends SkavaFragmentActivity implements AssessmentListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessments_list_activity);

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
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
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
    public void onFragmentInteraction(Assessment selectedAssessment) {

    }
}
