package com.metric.skava.calculator.barton.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;

public class QBartonCalculatorMainFragment extends SkavaFragment
        implements OnTabChangeListener {

    public static final String TAB_RQD = "RQD";
    public static final String TAB_JN = "Jn";
    private static final String TAB_JR = "Jr";
    public static final String TAB_JA = "Ja";
    private static final String TAB_JW = "Jw";
    private static final String TAB_SRF = "SRF";
    private static final String TAB_Q = "Q";

    private FragmentTabHost mTabHost;
    private Context mContext;
    private int mCurrentTab;
    private View mRoot;
    private TabWidget mTabWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRoot = inflater.inflate(R.layout.calculator_tabhost_fragment,
                container, false);

        mTabHost = (FragmentTabHost) mRoot.findViewById(android.R.id.tabhost);

        setupTabs();
        colorTabs();


        return mRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
//		MenuItem menuQBarton = menu.findItem(R.id.barton_indicator);
//		MenuItem menuRMRBienowski = menu.findItem(R.id.bienowski_indicator);
//		if (menuRMRBienowski != null) {
//			menuRMRBienowski.setVisible(false);
//		}
//		if (menuQBarton != null) {
//			menuQBarton.setVisible(false);
//		}
        super.onPrepareOptionsMenu(menu);
    }

    private void setupTabs() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + QBartonCalculatorMainFragment.class.getSimpleName() + " : setupTabs ");
        }

        mTabHost.setup(getActivity(), getChildFragmentManager(),
                R.id.realtabcontent);


        Bundle bundle = new Bundle();


        mTabHost.addTab(mTabHost.newTabSpec(TAB_RQD).setIndicator(mContext.getString(R.string.title_section_rqd)),
                RQDFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_JN).setIndicator(mContext.getString(R.string.title_section_jn)),
                JnFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_JR).setIndicator(mContext.getString(R.string.title_section_jr)),
                JrFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_JA).setIndicator(mContext.getString(R.string.title_section_ja)),
                JaFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_JW).setIndicator(mContext.getString(R.string.title_section_jw)),
                JwFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_SRF).setIndicator(mContext.getString(R.string.title_section_srf)),
                SrfFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_Q).setIndicator(mContext.getString(R.string.title_section_q)),
                QResultsFragment.class, bundle);

        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Exiting "
                    + QBartonCalculatorMainFragment.class.getSimpleName() + " : setupTabs ");
        }

    }

    private void colorTabs() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + QBartonCalculatorMainFragment.class.getSimpleName() + " : colorTabs ");
        }
        mTabWidget = (TabWidget) mRoot.findViewById(android.R.id.tabs);
        for (int i = 0; i < mTabWidget.getChildCount(); i++) {
            View view = mTabWidget.getChildAt(i);
            TextView tv = (TextView) view.findViewById(android.R.id.title);
            tv.setTextColor(this.getResources()
                    .getColor(R.color.Black));
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(SkavaConstants.LOG, "onTabChanged(): tabId	=" + tabId);
        if (TAB_RQD.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            mCurrentTab = 0;
            return;
        }
        if (TAB_JN.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            mCurrentTab = 1;
            return;
        }
        if (TAB_JR.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            mCurrentTab = 2;
            return;
        }
        if (TAB_JA.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            mCurrentTab = 3;
            return;
        }
        if (TAB_JW.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            mCurrentTab = 4;
            return;
        }
        if (TAB_SRF.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            mCurrentTab = 5;
            return;
        }
        if (TAB_Q.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            mCurrentTab = 6;
            return;
        }
    }

}
