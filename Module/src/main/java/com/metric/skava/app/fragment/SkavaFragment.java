package com.metric.skava.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.metric.skava.BuildConfig;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.rocksupport.model.ExcavationFactors;

/**
 * Created by metricboy on 2/21/14.
 */
public class SkavaFragment extends Fragment {

    /**
     * The fragment argument (just a placeholder as it comes in handy)
     */
    public static final String ARG_BASKET_ID = "PARCELABLE_DATA_BASKET_ID";


    //This is setted when an anctivity context is available onAttach() method
    private SkavaContext mSkavaContext;


    public SkavaContext getSkavaContext() {
        return mSkavaContext;
    }

    public Assessment getCurrentAssessment() {
        return getSkavaContext().getAssessment();
    }


    public SkavaFragmentActivity getSkavaActivity() {
        return (SkavaFragmentActivity) getActivity();
    }

    public Q_Calculation getQCalculationContext() {
        return getCurrentAssessment().getQCalculation();
    }


    public RMR_Calculation getRMRCalculationContext() {
        return getCurrentAssessment().getRmrCalculation();
    }

    public ExcavationFactors getSupportRequirementsContext() {
        return getCurrentAssessment().getExcavationFactors();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SkavaFragment.class.getSimpleName() + " : onCreate ");
        }
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Sometimes is necessary to clear the menu
        // menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onAttach(Activity activity) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SkavaFragment.class.getSimpleName() + " : onAttach ");
        }
        super.onAttach(activity);
        SkavaApplication application = (SkavaApplication) (activity.getApplication());
        mSkavaContext = application.getSkavaContext();
    }

    @Override
    public void onDestroy() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SkavaFragment.class.getSimpleName() + " : onDestroy ");
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SkavaFragment.class.getSimpleName() + " : onDestroyView ");
        }
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SkavaFragment.class.getSimpleName()
                    + " : onSaveInstanceState ");
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SkavaFragment.class.getSimpleName()
                    + " : onViewStateRestored ");
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SkavaFragment.class.getSimpleName() + " : onViewCreated ");
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + SkavaFragment.class.getSimpleName() + " : onResume ");
        }
        super.onResume();
    }


    @Override
    public void onDetach() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering"
                    + SkavaFragment.class.getSimpleName() + " : onDetach ");
        }
        super.onDetach();
    }


}
