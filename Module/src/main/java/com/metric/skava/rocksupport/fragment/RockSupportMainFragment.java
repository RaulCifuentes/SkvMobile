package com.metric.skava.rocksupport.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;

/**
 * Created by metricboy on 3/7/14.
 */
public class RockSupportMainFragment extends SkavaFragment {

    private FragmentTabHost mTabHost;
    private Context mContext;
    private int mCurrentTab;
    private View mRootView;
    private TabWidget mTabWidget;

    public static final String TAB_ESR = "ESR";
    public static final String TAB_SPAN = "SPAN";

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
        mRootView = inflater.inflate(R.layout.rock_support_tabhost_fragment, container, false);
        mTabHost = (FragmentTabHost) mRootView.findViewById(android.R.id.tabhost);
        setupTabs();
        colorTabs();
        return mRootView;
    }

    private void setupTabs() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + RockSupportMainFragment.class.getSimpleName() + " : setupTabs ");
        }

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        Bundle bundle = new Bundle();

        mTabHost.addTab(mTabHost.newTabSpec(TAB_SPAN).setIndicator(mContext.getString(R.string.title_tab_span)),
                SpanFragment.class, bundle);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_ESR).setIndicator(mContext.getString(R.string.title_tab_esr)),
                ESRFragment.class, bundle);
    }

    private void colorTabs() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + RockSupportMainFragment.class.getSimpleName() + " : colorTabs ");
        }
        mTabWidget = (TabWidget) mRootView.findViewById(android.R.id.tabs);
        for (int i = 0; i < mTabWidget.getChildCount(); i++) {
            View view = mTabWidget.getChildAt(i);
            TextView tv = (TextView) view.findViewById(android.R.id.title);
            tv.setTextColor(this.getResources().getColor(R.color.Black));
        }
    }

}
