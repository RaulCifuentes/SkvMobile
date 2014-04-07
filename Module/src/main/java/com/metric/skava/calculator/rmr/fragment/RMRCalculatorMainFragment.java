package com.metric.skava.calculator.rmr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.util.SkavaConstants;

public class RMRCalculatorMainFragment extends Fragment
        implements TabHost.OnTabChangeListener {


    /**
     * The fragment argument (just a placeholder for when it comes in handy)
     */
    public static final String ARG_BASKET_ID = "PARCELABLE_DATA_BASKET_ID";

    public static final String TAB_STRENGTH = "Strength";
    public static final String TAB_RQD = "RQD";
    private static final String TAB_SPACING = "Spacing";
    public static final String TAB_CONDITION = "Condition";
    private static final String TAB_GROUNDWATER = "Groundwater";
    private static final String TAB_ORIENTATION = "Orientation";
    private static final String TAB_RMR = "RMR";

    private FragmentTabHost mTabHost;
    private Context mContext;
    private int mCurrentTab;
    private View mRoot;
    private TabWidget mTabWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + RMRCalculatorMainFragment.class.getSimpleName() + " : onCreate ");
        }
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + RMRCalculatorMainFragment.class.getSimpleName() + " : onCreateView ");
        }
        mRoot = inflater.inflate(R.layout.calculator_tabhost_fragment,
                container, false);

        mTabHost = (FragmentTabHost) mRoot.findViewById(android.R.id.tabhost);

        setupTabs();
        colorTabs();

        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Exiting "
                    + RMRCalculatorMainFragment.class.getSimpleName() + " : onCreateView ");
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + RMRCalculatorMainFragment.class.getSimpleName() + " : onViewCreated ");
        }
        super.onViewCreated(view, savedInstanceState);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);
        // manually start loading stuff in the first tab
        // updateTab(TAB_RQD, R.id.tab_1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    private void setupTabs() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + RMRCalculatorMainFragment.class.getSimpleName() + " : setupTabs ");
        }
        mTabHost.setup(getActivity(), getChildFragmentManager(),
                R.id.realtabcontent);

        Bundle bundle = new Bundle();


        mTabHost.addTab(mTabHost.newTabSpec(TAB_STRENGTH).setIndicator(mContext.getString(R.string.title_section_strenght)),
                StrengthFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_RQD).setIndicator(mContext.getString(R.string.title_section_rqd)),
                Rqd_RmrFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_SPACING).setIndicator(mContext.getString(R.string.title_section_spacing)),
                SpacingFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_CONDITION).setIndicator(mContext.getString(R.string.title_section_condition)),
                ConditionFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_GROUNDWATER).setIndicator(mContext.getString(R.string.title_section_groundwater)),
                GroundwaterFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_ORIENTATION).setIndicator(mContext.getString(R.string.title_section_orientation)),
                OrientationFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_RMR).setIndicator(mContext.getString(R.string.title_section_rmr)),
                RmrResultsFragment.class, bundle);

        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Exiting "
                    + RMRCalculatorMainFragment.class.getSimpleName() + " : setupTabs ");
        }

    }

    private void colorTabs() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + RMRCalculatorMainFragment.class.getSimpleName() + " : colorTabs ");
        }
        mTabWidget = (TabWidget) mRoot.findViewById(android.R.id.tabs);
        for (int i = 0; i < mTabWidget.getChildCount(); i++) {
            View view = mTabWidget.getChildAt(i);
            TextView tv = (TextView) view.findViewById(android.R.id.title);
            tv.setTextColor(this.getResources().getColor(R.color.Black));
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(SkavaConstants.LOG, "onTabChanged(): tabId	=" + tabId);

        if (TAB_STRENGTH.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            mCurrentTab = 0;
            return;
        }
        if (TAB_RQD.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            mCurrentTab = 1;
            return;
        }
        if (TAB_SPACING.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            mCurrentTab = 2;
            return;
        }
        if (TAB_CONDITION.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            mCurrentTab = 3;
            return;
        }
        if (TAB_GROUNDWATER.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            mCurrentTab = 4;
            return;
        }
        if (TAB_ORIENTATION.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            mCurrentTab = 5;
            return;
        }
        if (TAB_RMR.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            mCurrentTab = 6;
            return;
        }
    }
}