package com.metric.skava.app.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;

import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.navigation.NavigationController;
import com.metric.skava.app.style.ThemeChangeListener;
import com.metric.skava.data.dao.DAOFactory;

public class SkavaActivity extends Activity implements ThemeChangeListener {


    protected NavigationController navController;
    protected ActionBar mActionBar;

    private void pseudoInjection() {
        navController = NavigationController.getInstance();
    }

    public NavigationController getNavController() {
        return navController;
    }


    public SkavaContext getSkavaContext(){
        SkavaApplication application = (SkavaApplication) (getApplication());
        return application.getSkavaContext();
    }

    public DAOFactory getDAOFactory() {
        return getSkavaContext().getDAOFactory();
    }

//    public SkavaDataProvider getSkavaDataProvider() {
//        return ((SkavaApplication) getApplication()).getSkavaDataProvider();
//    }
//
//    public MappedIndexDataProvider getMappedIndexDataProvider() {
//        return ((SkavaApplication) getApplication()).getMappedIndexDataProvider();
//    }


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_main_menu, menu);
        return true;
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
    public void onThemeChanged(int newThemeId) {
        //TODO implement onChangeTheme para cambios de theme
//        mplement a ThemeChangeListener and add it to your activities. When the theme changes, call the ThemeChangeListener and call finish() on any open activities (apart from the page you are in). Then in onBack() manually recreate the top level activity the user navigated to the settings page from using the code Lisa supplied.
//
//                You can use intent extras/data to manage re-creating the parent activity with the data previously populated.
    }




//
//	public void onSaveInstanceState(Bundle savedInstanceState) {
//		if (BuildConfig.DEBUG) {
//			Log.d(SkavaConstants.LOG, "Exiting " + this.getLocalClassName()
//					+ " : onSaveInstanceState ");
//		}
//		super.onSaveInstanceState(savedInstanceState);
//		savedInstanceState.putParcelable(SkavaConstants.CALCULATION_DATA,
//				mCalculation);
//	}
//
//
//
//	public void onRestoreInstanceState(Bundle savedInstanceState) {
//		if (BuildConfig.DEBUG) {
//			Log.d(SkavaConstants.LOG, "Exiting " + this.getLocalClassName()
//					+ " : onRestoreInstanceState ");
//		}
//		super.onRestoreInstanceState(savedInstanceState);
//		mCalculation = savedInstanceState
//				.getParcelable(SkavaConstants.CALCULATION_DATA);
//	}


}
