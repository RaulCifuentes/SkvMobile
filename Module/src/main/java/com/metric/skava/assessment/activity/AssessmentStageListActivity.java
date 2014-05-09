package com.metric.skava.assessment.activity;


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
import com.metric.skava.assessment.fragment.AssessmentStageListFragment;
import com.metric.skava.calculator.barton.activity.QBartonCalculatorDetailActivity;
import com.metric.skava.calculator.barton.fragment.QBartonCalculatorMainFragment;
import com.metric.skava.calculator.rmr.activity.RMRCalculatorDetailActivity;
import com.metric.skava.calculator.rmr.fragment.RMRCalculatorMainFragment;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;
import com.metric.skava.discontinuities.activity.DiscontinuitiesMainActivity;
import com.metric.skava.discontinuities.fragment.DiscontinuitiesMainFragment;
import com.metric.skava.identification.activity.IdentificationMainActivity;
import com.metric.skava.identification.fragment.IdentificationMainFragment;
import com.metric.skava.instructions.activity.InstructionsMainActivity;
import com.metric.skava.instructions.fragment.InstructionsMainFragment;
import com.metric.skava.pictures.activity.PicturesMainActivity;
import com.metric.skava.pictures.fragment.PicturesMainFragment;
import com.metric.skava.report.activity.MappingReportMainActivity;
import com.metric.skava.report.fragment.MappingReportMainFragment;
import com.metric.skava.rockmass.activity.RockMassDescriptionMainActivity;
import com.metric.skava.rockmass.fragment.RockMassDescriptionMainFragment;


/**
 * An activity representing a list of Assessment stages. This activity
 * has different presentations for portrait or landscape orientations. On
 * portrait, the activity presents a list of items, which when touched,
 * lead to the corresponding Activity representing On landscape, the activity presents
 * the list of stages and the details are shown side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link com.metric.skava.assessment.fragment.AssessmentStageListFragment}
 * <p/>
 * This activity also implements the required
 * {@link com.metric.skava.assessment.fragment.AssessmentStageListFragment.StageSelectionCallback} interface
 * to listen for item selections.
 */
public class AssessmentStageListActivity extends SkavaFragmentActivity
        implements AssessmentStageListFragment.StageSelectionCallback, IdentificationMainFragment.TunnelFaceIdentificationListener {

    private static final String SHOW_LOAD_ASSESSMENT_DIALOG_TAG = "SHOW_LOAD_ASSESSMENT_DIALOG";

    private static final String FRAGMENT_GRAL_INFO_STAGE_TAG = "FRAGMENT_GRAL_INFO_STAGE_TAG";
    private static final String FRAGMENT_DISCONTINUITIES_STAGE_TAG = "FRAGMENT_DISCONTINUITIES_STAGE_TAG";
    private static final String FRAGMENT_Q_STAGE_TAG = "FRAGMENT_Q_STAGE_TAG";
    private static final String FRAGMENT_ROCK_SUPPORT_STAGE_TAG = "FRAGMENT_ROCK_SUPPORT_STAGE_TAG";
    private static final String FRAGMENT_RMR_STAGE_TAG = "FRAGMENT_RMR_STAGE_TAG";
    private static final String FRAGMENT_PICTURES_STAGE_TAG = "FRAGMENT_PICTURES_STAGE_TAG";
    private static final String FRAGMENT_DESCRIPTION_STAGE_TAG = "FRAGMENT_DESCRIPTION_STAGE_TAG";
    private static final String FRAGMENT_RECOMENDATION_STAGE_TAG = "FRAGMENT_RECOMENDATION_STAGE_TAG";
    private static final String FRAGMENT_SUMMARY_STAGE_TAG = "FRAGMENT_SUMMARY_STAGE_TAG";
    private static final String FRAGMENT_SAVE_STAGE_TAG = "FRAGMENT_SAVE_STAGE_TAG";


    public static final String EXCEPTION_DIALOG_TAG = "EXCEPTION_DIALOG_TAG ";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private boolean isRedirected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_stage_list_activity);

        Assessment currentAssessment = getSkavaContext().getAssessment();
        if (currentAssessment == null) {
            try {
                getSkavaContext().setAssessment(SkavaUtils.createInitialAssessment(getSkavaContext()));
            } catch (DAOException e) {
                e.printStackTrace();
                Log.d(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            }
        }

        // The detail container view will be present only in the
        // landscape configuration.
        if (findViewById(R.id.stage_detail_container) != null) {
            //If this view is present, then the activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((AssessmentStageListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.stage_list))
                    .setActivateOnItemClick(true);


        }
        // TODO: If exposing deep links into your app, handle intents here.
        //This is after getting back from the external picture editor application
        Intent intent = getIntent();
        isRedirected = intent.getBooleanExtra("REDIRECT", false);
        if (isRedirected) {
            //I want to go back directly to pictures
            onItemSelected(AssesmentStageDataProvider.PICS);
            //And I want to have the stages items enabled
            onTunelFaceIdentified();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean menuHandled = super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.assessment_main_menu, menu);
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
            if (id.equalsIgnoreCase(AssesmentStageDataProvider.GRAL_INFO)) {
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

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.DISCONTINUITIES)) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fx = fragmentManager.beginTransaction();
                Fragment previous = getSupportFragmentManager().findFragmentById(R.id.stage_detail_container);
                if (previous != null) {
                    fx.detach(previous);
                }
                //use the previous fragment if there is one
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_DISCONTINUITIES_STAGE_TAG);
                DiscontinuitiesMainFragment discontinuitiesFragment;
                if (fragment != null) {
                    discontinuitiesFragment = (DiscontinuitiesMainFragment) fragment;
                    fx.attach(discontinuitiesFragment);
                } else {
                    discontinuitiesFragment = new DiscontinuitiesMainFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString(DiscontinuitiesMainFragment.ARG_BASKET_ID, id);
                    discontinuitiesFragment.setArguments(arguments);
                    fx.add(R.id.stage_detail_container, discontinuitiesFragment, FRAGMENT_DISCONTINUITIES_STAGE_TAG);
                }
                fx.commit();
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.Q)) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fx = fragmentManager.beginTransaction();
                Fragment previous = getSupportFragmentManager().findFragmentById(R.id.stage_detail_container);
                if (previous != null) {
                    fx.detach(previous);
                }
                Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_Q_STAGE_TAG);
                QBartonCalculatorMainFragment qBartonFragment;
                //use the previous fragment if there is one
                if (fragment != null) {
                    qBartonFragment = (QBartonCalculatorMainFragment) fragment;
                    fx.attach(qBartonFragment);
                } else {
                    Bundle arguments = new Bundle();
                    arguments.putString(QBartonCalculatorMainFragment.ARG_BASKET_ID, id);
                    qBartonFragment = new QBartonCalculatorMainFragment();
                    qBartonFragment.setArguments(arguments);
                    fx.add(R.id.stage_detail_container, qBartonFragment, FRAGMENT_Q_STAGE_TAG);
                }
                fx.commit();
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.RMR)) {
                FragmentTransaction fx = getSupportFragmentManager().beginTransaction();
                Fragment previous = getSupportFragmentManager().findFragmentById(R.id.stage_detail_container);
                if (previous != null) {
                    fx.detach(previous);
                }
                //use the previous fragment if there is one
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_RMR_STAGE_TAG);
                RMRCalculatorMainFragment rmrFragment;
                if (fragment != null) {
                    rmrFragment = (RMRCalculatorMainFragment) fragment;
                    fx.attach(rmrFragment);
                } else {
                    rmrFragment = new RMRCalculatorMainFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString(RMRCalculatorMainFragment.ARG_BASKET_ID, id);
                    rmrFragment.setArguments(arguments);
                    fx.add(R.id.stage_detail_container, rmrFragment, FRAGMENT_RMR_STAGE_TAG);
                }
                fx.commit();
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.PICS)) {
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

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.DESC)) {
                FragmentTransaction fx = getSupportFragmentManager().beginTransaction();
                Fragment previous = getSupportFragmentManager().findFragmentById(R.id.stage_detail_container);
                if (previous != null) {
                    fx.detach(previous);
                }
                //use the previous fragment if there is one
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_DESCRIPTION_STAGE_TAG);
                RockMassDescriptionMainFragment rockMassDescriptionFragment;
                if (fragment != null) {
                    rockMassDescriptionFragment = (RockMassDescriptionMainFragment) fragment;
                    fx.attach(rockMassDescriptionFragment);
                } else {
                    rockMassDescriptionFragment = new RockMassDescriptionMainFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString(RockMassDescriptionMainFragment.ARG_BASKET_ID, id);
                    rockMassDescriptionFragment.setArguments(arguments);
                    fx.add(R.id.stage_detail_container, rockMassDescriptionFragment, FRAGMENT_DESCRIPTION_STAGE_TAG);
                }
                fx.commit();
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.INSTRUCTIONS)) {
                FragmentTransaction fx = getSupportFragmentManager().beginTransaction();
                Fragment previous = getSupportFragmentManager().findFragmentById(R.id.stage_detail_container);
                if (previous != null) {
                    fx.detach(previous);
                }
                //use the previous fragment if there is one
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECOMENDATION_STAGE_TAG);
                InstructionsMainFragment supportRecomendationFragment;
                if (fragment != null) {
                    supportRecomendationFragment = (InstructionsMainFragment) fragment;
                    fx.attach(supportRecomendationFragment);
                } else {
                    supportRecomendationFragment = new InstructionsMainFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString(InstructionsMainFragment.ARG_BASKET_ID, id);
                    supportRecomendationFragment.setArguments(arguments);
                    fx.add(R.id.stage_detail_container, supportRecomendationFragment, FRAGMENT_RECOMENDATION_STAGE_TAG);
                }
                fx.commit();
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.REPORT)) {
                //To make it full screen size launh it as an activity instead of as a fragment
                Intent detailIntent = new Intent(this, MappingReportMainActivity.class);
                detailIntent.putExtra(MappingReportMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
//                Bundle arguments = new Bundle();
//                arguments.putString(MappingReportMainFragment.ARG_BASKET_ID, id);
//                MappingReportMainFragment fragment = new MappingReportMainFragment();
//                fragment.setArguments(arguments);
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.stage_detail_container, fragment)
//                        .commit();
//                return;
            }




        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            if (id.equalsIgnoreCase(AssesmentStageDataProvider.GRAL_INFO)) {
                Intent detailIntent = new Intent(this, IdentificationMainActivity.class);
                detailIntent.putExtra(IdentificationMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.DISCONTINUITIES)) {
                Intent detailIntent = new Intent(this, DiscontinuitiesMainActivity.class);
                detailIntent.putExtra(DiscontinuitiesMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.Q)) {
                Intent detailIntent = new Intent(this, QBartonCalculatorDetailActivity.class);
                detailIntent.putExtra(QBartonCalculatorMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }


            if (id.equalsIgnoreCase(AssesmentStageDataProvider.RMR)) {
                Intent detailIntent = new Intent(this, RMRCalculatorDetailActivity.class);
                detailIntent.putExtra(RMRCalculatorMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.PICS)) {
                Intent detailIntent = new Intent(this, PicturesMainActivity.class);
                detailIntent.putExtra(PicturesMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.DESC)) {
                Intent detailIntent = new Intent(this, RockMassDescriptionMainActivity.class);
                detailIntent.putExtra(RMRCalculatorMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;

            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.INSTRUCTIONS)) {
                Intent detailIntent = new Intent(this, InstructionsMainActivity.class);
                detailIntent.putExtra(InstructionsMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

            if (id.equalsIgnoreCase(AssesmentStageDataProvider.REPORT)) {
                Intent detailIntent = new Intent(this, MappingReportMainActivity.class);
                detailIntent.putExtra(MappingReportMainFragment.ARG_BASKET_ID, id);
                startActivity(detailIntent);
                return;
            }

        }
    }


    @Override
    public void onTunelFaceIdentified() {
        AssessmentStageListFragment assessmentStageListFragment = (AssessmentStageListFragment)
                getSupportFragmentManager().findFragmentById(R.id.stage_list);
        assessmentStageListFragment.enableAllStages(true);
    }

    @Override
    public void onTunelFaceNotIdentified() {
        AssessmentStageListFragment assessmentStageListFragment = (AssessmentStageListFragment)
                getSupportFragmentManager().findFragmentById(R.id.stage_list);
        assessmentStageListFragment.enableAllStages(false);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DatastoreHelper.REQUEST_LINK_TO_DROPBOX) {
            if (resultCode == RESULT_OK) {
                //showPreviousAssessmentsDTOs();
                //just to see if comes here
                Toast.makeText(this, "AssessmentStageListActivity :: onActivityResult", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Link to Dropbox failed.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
