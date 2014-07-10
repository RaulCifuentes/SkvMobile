package com.metric.skava.calculator.rmr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.calculator.activity.CalculatorFlavoursListActivity;
import com.metric.skava.calculator.rmr.fragment.RMRCalculatorMainFragment;


/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CalculatorFlavoursListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link CalculatorFlavoursListActivity}.
 */
public class RMRCalculatorDetailActivity extends SkavaFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_detail_activity_item_detail);
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
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(RMRCalculatorMainFragment.ARG_BASKET_ID,
                    getIntent().getParcelableExtra(RMRCalculatorMainFragment.ARG_BASKET_ID));


            RMRCalculatorMainFragment fragment = new RMRCalculatorMainFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, CalculatorFlavoursListActivity.class));
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

    @Override
    public void onPostExecuteImportAppData(boolean success, Long result) {

    }

    @Override
    public void onPostExecuteImportUserData(boolean success, Long result) {

    }

    public void showProgressBar(final boolean show, String text, boolean longTime) {

    }


}
