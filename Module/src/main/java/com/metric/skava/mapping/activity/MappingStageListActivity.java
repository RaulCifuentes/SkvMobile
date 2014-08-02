package com.metric.skava.mapping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.assessment.data.AssesmentStageDataProvider;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.expandedview.activity.ExpandedTunnelMainActivity;
import com.metric.skava.expandedview.fragment.ExpandedTunnelMainFragment;
import com.metric.skava.identification.activity.IdentificationMainActivity;
import com.metric.skava.identification.fragment.IdentificationMainFragment;
import com.metric.skava.mapping.data.MappingStageDataProvider;
import com.metric.skava.mapping.fragment.MappingStageListFragment;
import com.metric.skava.pictures.activity.PicturesMainActivity;
import com.metric.skava.pictures.fragment.PicturesMainFragment;
import com.metric.skava.report.activity.MappingReportMainActivity;
import com.metric.skava.report.fragment.MappingReportMainFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by metricboy on 7/31/14.
 */
public class MappingStageListActivity extends SkavaFragmentActivity
        implements MappingStageListFragment.StageSelectionCallback, IdentificationMainFragment.TunnelFaceIdentificationListener {

    private static final String FRAGMENT_GRAL_INFO_STAGE_TAG = "FRAGMENT_GRAL_INFO_STAGE_TAG";
    private static final String FRAGMENT_PICTURES_STAGE_TAG = "FRAGMENT_PICTURES_STAGE_TAG";
    private static final String FRAGMENT_SUMMARY_STAGE_TAG = "FRAGMENT_SUMMARY_STAGE_TAG";

    public static final String REDIRECT_FROM_PICTURES = "PICTURES";
    public static final String REDIRECT_FROM_REPORT = "REPORT";
    public static final String REDIRECT_FROM_EXPANDED = "EXPANDED";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private boolean cameFromPictures;
    private boolean cameFromReport;
    private boolean cameFromExpanded;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapping_stage_list_activity);

        Assessment currentAssessment = getSkavaContext().getAssessment();
        if (currentAssessment == null) {
            try {
                getSkavaContext().setAssessment(SkavaUtils.createInitialAssessment(getSkavaContext()));
            } catch (DAOException e) {
                e.printStackTrace();
                Log.d(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        // The detail container view will be present only in the
        // landscape configuration.
        if (findViewById(R.id.stage_detail_container) != null) {
            //If this view is present, then the activity should be in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MappingStageListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.stage_list))
                    .setActivateOnItemClick(true);
        }
        // TODO: If exposing deep links into your app, handle intents here.
        //This is after getting back from the external picture editor application
        Intent intent = getIntent();
        boolean cameFromPictures = intent.getBooleanExtra(REDIRECT_FROM_PICTURES, false);
        boolean cameFromReport = intent.getBooleanExtra(REDIRECT_FROM_REPORT, false);
        boolean cameFromExpanded = intent.getBooleanExtra(REDIRECT_FROM_EXPANDED, false);
        if (cameFromPictures) {
            //I want to go back directly to pictures
            onItemSelected(AssesmentStageDataProvider.PICS);
            //And I want to have the stages items enabled
            onTunelFaceIdentified();
        } else if (cameFromReport){
            //I want to go back directly to General Information
            onItemSelected(AssesmentStageDataProvider.GRAL_INFO);
            //And I want to have the stages items enabled
            onTunelFaceIdentified();
        } else if (cameFromExpanded){
            //I want to go back directly to General Information
            onItemSelected(AssesmentStageDataProvider.GRAL_INFO);
            //And I want to have the stages items enabled
            onTunelFaceIdentified();
        } else {
            //By default to General Information
            onItemSelected(AssesmentStageDataProvider.GRAL_INFO);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean menuHandled = super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mapping_main_menu, menu);
        return menuHandled;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
//            case R.id.load_assessment_menu_item:
//                showLoadAssessmentDialog();
//                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /**
     * Callback method from {@link com.metric.skava.assessment.fragment.AssessmentStageListFragment.StageSelectionCallback}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (id.equalsIgnoreCase(MappingStageDataProvider.GRAL_INFO)) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fx = fragmentManager.beginTransaction();
                //whats the current fragment
                Fragment previous = fragmentManager.findFragmentById(R.id.stage_detail_container);
                if (previous != null) {
                    fx.detach(previous);
                }
                //use the previous fragment if there is one
                Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_GRAL_INFO_STAGE_TAG);
                IdentificationMainFragment gralInfoFragment;
                if (fragment != null) {
                    gralInfoFragment = (IdentificationMainFragment) fragment;
                    fx.attach(gralInfoFragment);
                } else {
                    gralInfoFragment = new IdentificationMainFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString(IdentificationMainFragment.ARG_BASKET_ID, id);
                    gralInfoFragment.setArguments(arguments);
                    fx.add(R.id.stage_detail_container, gralInfoFragment, FRAGMENT_GRAL_INFO_STAGE_TAG);
                }
                fx.commit();
                return;
            }

            if (id.equalsIgnoreCase(MappingStageDataProvider.PICS)) {
                saveDraft();
                FragmentTransaction fx = getSupportFragmentManager().beginTransaction();
                Fragment previous = getSupportFragmentManager().findFragmentById(R.id.stage_detail_container);
                if (previous != null) {
                    fx.detach(previous);
                }
                //use the previous fragment if there is one
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_PICTURES_STAGE_TAG);
                PicturesMainFragment picturesFragment;
                if (fragment != null) {
                    picturesFragment = (PicturesMainFragment) fragment;
                    fx.attach(picturesFragment);
                } else {
                    picturesFragment = new PicturesMainFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString(PicturesMainFragment.ARG_BASKET_ID, id);
                    picturesFragment.setArguments(arguments);
                    fx.add(R.id.stage_detail_container, picturesFragment, FRAGMENT_PICTURES_STAGE_TAG);
                }
                fx.commit();
                return;
            }

            if (id.equalsIgnoreCase(MappingStageDataProvider.EXPANDED)) {
                saveDraft();
                Intent expandedViewIntent = new Intent(this, ExpandedTunnelMainActivity.class);
                expandedViewIntent.putExtra(ExpandedTunnelMainFragment.ARG_BASKET_ID, id);
                startActivity(expandedViewIntent);
                return;
            }

            if (id.equalsIgnoreCase(MappingStageDataProvider.REPORT)) {
                saveDraft();
                //To make it full screen size launh it as an activity instead of as a fragment
                Intent detailIntent = new Intent(this, MappingReportMainActivity.class);
                detailIntent.putExtra(MappingReportMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            if (id.equalsIgnoreCase(MappingStageDataProvider.GRAL_INFO)) {
                Intent detailIntent = new Intent(this, IdentificationMainActivity.class);
                detailIntent.putExtra(IdentificationMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

            if (id.equalsIgnoreCase(MappingStageDataProvider.PICS)) {
                Intent detailIntent = new Intent(this, PicturesMainActivity.class);
                detailIntent.putExtra(PicturesMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

            if (id.equalsIgnoreCase(MappingStageDataProvider.REPORT)) {
                Intent detailIntent = new Intent(this, MappingReportMainActivity.class);
                detailIntent.putExtra(MappingReportMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

        }
    }


    @Override
    public void onTunelFaceIdentified() {
        MappingStageListFragment assessmentStageListFragment = (MappingStageListFragment)
                getSupportFragmentManager().findFragmentById(R.id.stage_list);
        if (getCurrentAssessment().getInternalCode() == null && getCurrentAssessment().getFinalPeg() != null){
            String tunnelName = getCurrentAssessment().getTunnel().getName();
            String faceName = getCurrentAssessment().getFace().getName();
            NumberFormat numberFormatter = DecimalFormat.getNumberInstance();
            String finalPegAsString = numberFormatter.format(getCurrentAssessment().getFinalPeg());
            getCurrentAssessment().setInternalCode(tunnelName + "-" + faceName + "-" + finalPegAsString );
        }
        assessmentStageListFragment.enableAllStages(true);
    }


    @Override
    public void onTunelFaceNotIdentified() {
        MappingStageListFragment assessmentStageListFragment = (MappingStageListFragment)
                getSupportFragmentManager().findFragmentById(R.id.stage_list);
        assessmentStageListFragment.enableAllStages(false);
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
