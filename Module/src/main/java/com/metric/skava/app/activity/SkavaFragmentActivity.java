package com.metric.skava.app.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;

import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.navigation.NavigationController;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.sync.helper.SyncHelper;

public class SkavaFragmentActivity extends FragmentActivity {

	protected NavigationController navController;
	protected ActionBar mActionBar;


    public SkavaContext getSkavaContext(){
        SkavaApplication application = (SkavaApplication) (getApplication());
        return application.getSkavaContext();
    }

    public SyncHelper getSyncHelper() {
        return getSkavaContext().getSyncHelper();
    }

    public DAOFactory getDAOFactory() {
        return getSkavaContext().getDAOFactory();
    }

    public Assessment getCurrentAssessment() {
        return getSkavaContext().getAssessment();
    }

    public boolean shouldUpdateAutomatically() {
        return ((SkavaApplication) getApplication()).isUpdateDatamodelAutomatically();
    }


    private void pseudoInjection() {
		navController =  NavigationController.getInstance();
	}

	public NavigationController getNavController() {
		return navController;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        int themeID = ((SkavaApplication) getApplication()).getCustomThemeId();
        setTheme(themeID);
        super.onCreate(savedInstanceState);
		pseudoInjection();
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);

	}

	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
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
                return true;
		}
		return (super.onOptionsItemSelected(menuItem));
	}


    @Override
    protected void onResume() {
        super.onResume();
        if (((SkavaApplication) getApplication()).isRequiresRestart()) {
            ((SkavaApplication) getApplication()).setRequiresRestart(false);
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }



}
