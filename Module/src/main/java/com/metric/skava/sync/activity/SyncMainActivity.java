package com.metric.skava.sync.activity;

import android.os.Bundle;
import android.view.Menu;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.sync.fragment.SyncMainFragment;

public class SyncMainActivity extends SkavaFragmentActivity  {

    public String FRAGMENT_TAG = "SYNC_FRAGMENT_TAG";
    private SyncMainFragment mMainContainedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SyncMainFragment(), FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mMainContainedFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sync_main, menu);
        return true;
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
