package com.metric.skava.calculator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.calculator.barton.activity.QBartonCalculatorDetailActivity;
import com.metric.skava.calculator.barton.fragment.QBartonCalculatorMainFragment;
import com.metric.skava.calculator.fragment.CalculatorFlavoursListFragment;
import com.metric.skava.calculator.rmr.activity.RMRCalculatorDetailActivity;
import com.metric.skava.calculator.rmr.fragment.RMRCalculatorMainFragment;

public class CalculatorFlavoursListActivity extends SkavaFragmentActivity implements CalculatorFlavoursListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Heey be aware that calculator_flavours_list
        //is defined multiple times depending on screen resolution
        //This is the trick !!
        setContentView(R.layout.calculator_flavours_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((CalculatorFlavoursListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean menuHandled = super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.calculator_main_menu, menu);
        return menuHandled ;
    }

    /**
     * Callback method from {@link CalculatorFlavoursListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (id.equalsIgnoreCase("Q")){
                Bundle arguments = new Bundle();
                arguments.putString(QBartonCalculatorMainFragment.ARG_BASKET_ID, id);
                QBartonCalculatorMainFragment fragment = new QBartonCalculatorMainFragment();
                fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
                return;
            }

            if (id.equalsIgnoreCase("RMR")){
                Bundle arguments = new Bundle();
                arguments.putString(RMRCalculatorMainFragment.ARG_BASKET_ID, id);
                RMRCalculatorMainFragment fragment = new RMRCalculatorMainFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
                return;
            }


        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            if (id.equalsIgnoreCase("Q")){
                Intent detailIntent = new Intent(this, QBartonCalculatorDetailActivity.class);
                detailIntent.putExtra(QBartonCalculatorMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
            }
            if (id.equalsIgnoreCase("RMR")){
                Intent detailIntent = new Intent(this, RMRCalculatorDetailActivity.class);
                detailIntent.putExtra(RMRCalculatorMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
            }

        }
    }
}
