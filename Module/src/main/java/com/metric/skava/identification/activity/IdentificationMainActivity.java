package com.metric.skava.identification.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.identification.fragment.IdentificationMainFragment;

public class IdentificationMainActivity extends SkavaFragmentActivity implements IdentificationMainFragment.TunnelFaceIdentificationListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identification_main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new IdentificationMainFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.identification_main, menu);
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
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTunelFaceIdentified() {
        //activated (enable) the assessmentStages beyond Identification
        //TODO navigate Up to the activity that fires me (AssessmentStageListActivity) to delegate the handling of this method?
    }
    @Override
    public void onTunelFaceNotIdentified() {
        //activated (enable) the assessmentStages beyond Identification
        //TODO navigate Up to the activity that fires me (AssessmentStageListActivity) to delegate the handling of this method?
    }

    public void onPreExecuteImportAppData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    public void onPreExecuteImportUserData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    public void onPostExecuteImportAppData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.VISIBLE);
    }

    public void onPostExecuteImportUserData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.VISIBLE);
    }
    public void showProgressBar(final boolean show, String text, boolean longTime) {

    }


}
