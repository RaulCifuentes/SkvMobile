package com.metric.skava.user.activity;

import android.os.Bundle;
import android.view.Menu;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.user.fragment.UserAccountMainFragment;

/**
 * Created by metricboy on 11/21/14.
 */
public class UserAccountMainActivity extends SkavaFragmentActivity {

    public String FRAGMENT_TAG = "USR_ACCOUNT_FRAGMENT_TAG";
    private UserAccountMainFragment mMainContainedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account_main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new UserAccountMainFragment(), FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mMainContainedFragment = (UserAccountMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sync_main, menu);
        return true;
    }


    @Override
    public void onPreExecuteImportAppData() {

    }

    @Override
    public void onPreExecuteImportUserData() {

    }

    @Override
    public void onPostExecuteImportAppData(boolean success, Long result) {

    }

    @Override
    public void onPostExecuteImportUserData(boolean success, Long result) {

    }

    @Override
    public void showProgressBar(boolean show, String text, boolean longTime) {

    }
}
