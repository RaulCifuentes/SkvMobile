package com.metric.skava.about.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.metric.skava.R;
import com.metric.skava.about.fragment.AboutMainFragment;
import com.metric.skava.about.fragment.AppDetailsFragment;
import com.metric.skava.app.activity.SkavaFragmentActivity;

public class AboutMainActivity extends SkavaFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AboutMainFragment())
                    .commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_app_details) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AppDetailsFragment())
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
