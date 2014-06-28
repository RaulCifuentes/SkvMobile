package com.metric.skava.discontinuities.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.util.SkavaConstants;

/**
 * Created by metricboy on 2/27/14.
 */
public class DiscontinuitiesMainFragment extends SkavaFragment implements TabHost.OnTabChangeListener {


    public static final String TAB_FAMILY_1 = "FAMILY_1";
    public static final String TAB_FAMILY_2 = "FAMILY_2";
    public static final String TAB_FAMILY_3 = "FAMILY_3";
    public static final String TAB_FAMILY_4 = "FAMILY_4";
    public static final String TAB_FAMILY_5 = "FAMILY_5";
    public static final String TAB_FAMILY_6 = "FAMILY_6";
    public static final String TAB_FAMILY_7 = "FAMILY_7";


    private FragmentTabHost mTabHost;
    private Context mContext;
    private int mCurrentTab;
    private View mRootView;
    private TabWidget mTabWidget;

    private String firstTabFragmentTag;
    private String secondTabFragmentTag;
    private String thirdTabFragmentTag;
    private String fourthTabFragmentTag;
    private String fifthTabFragmentTag;
    private String sixthTabFragmentTag;
    private String seventhTabFragmentTag;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.discontinuities_main_fragment, container, false);
        mRootView = inflater.inflate(R.layout.discontinuities_tabhost_fragment, container, false);

        mTabHost = (FragmentTabHost) mRootView.findViewById(android.R.id.tabhost);

        this.setupTabs();
        this.colorTabs();


        return mRootView;
    }

    private void setupTabs() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + DiscontinuitiesMainFragment.class.getSimpleName() + " : setupTabs ");
        }

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        Bundle bundle = new Bundle();
        bundle.putInt(DiscontinuitySystemBaseFragment.ARG_BASKET_ID, 1);
        TabHost.TabSpec firstTabSpec = mTabHost.newTabSpec(TAB_FAMILY_1).setIndicator(mContext.getString(R.string.title_tab_discontinuities_family_1));
        firstTabFragmentTag = firstTabSpec.getTag();
        mTabHost.addTab(firstTabSpec, DiscontinuitySystemBaseFragment.class, bundle);

        bundle = new Bundle();
        bundle.putInt(DiscontinuitySystemBaseFragment.ARG_BASKET_ID, 2);
        TabHost.TabSpec secondTabSpec = mTabHost.newTabSpec(TAB_FAMILY_2).setIndicator(mContext.getString(R.string.title_tab_discontinuities_family_2));
        secondTabFragmentTag = secondTabSpec.getTag();
        mTabHost.addTab(secondTabSpec,
                DiscontinuitySystemBaseFragment.class, bundle);

        bundle = new Bundle();
        bundle.putInt(DiscontinuitySystemBaseFragment.ARG_BASKET_ID, 3);
        TabHost.TabSpec thirdTabSpec = mTabHost.newTabSpec(TAB_FAMILY_3).setIndicator(mContext.getString(R.string.title_tab_discontinuities_family_3));
        thirdTabFragmentTag = thirdTabSpec.getTag();
        mTabHost.addTab(thirdTabSpec,
                DiscontinuitySystemBaseFragment.class, bundle);

        bundle = new Bundle();
        bundle.putInt(DiscontinuitySystemBaseFragment.ARG_BASKET_ID, 4);
        TabHost.TabSpec fourthTabSpec = mTabHost.newTabSpec(TAB_FAMILY_4).setIndicator(mContext.getString(R.string.title_tab_discontinuities_family_4));
        fourthTabFragmentTag = fourthTabSpec.getTag();
        mTabHost.addTab(fourthTabSpec, DiscontinuitySystemBaseFragment.class, bundle);

        bundle = new Bundle();
        bundle.putInt(DiscontinuitySystemBaseFragment.ARG_BASKET_ID, 5);
        TabHost.TabSpec fifthTabSpec = mTabHost.newTabSpec(TAB_FAMILY_5).setIndicator(mContext.getString(R.string.title_tab_discontinuities_family_5));
        fifthTabFragmentTag = fifthTabSpec.getTag();
        mTabHost.addTab(fifthTabSpec, DiscontinuitySystemBaseFragment.class, bundle);


        bundle = new Bundle();
        bundle.putInt(DiscontinuitySystemBaseFragment.ARG_BASKET_ID, 6);
        TabHost.TabSpec sixthTabSpec = mTabHost.newTabSpec(TAB_FAMILY_6).setIndicator(mContext.getString(R.string.title_tab_discontinuities_family_6));
        sixthTabFragmentTag = fourthTabSpec.getTag();
        mTabHost.addTab(sixthTabSpec, DiscontinuitySystemBaseFragment.class, bundle);


        bundle = new Bundle();
        bundle.putInt(DiscontinuitySystemBaseFragment.ARG_BASKET_ID, 7);
        TabHost.TabSpec seventhTabSpec = mTabHost.newTabSpec(TAB_FAMILY_7).setIndicator(mContext.getString(R.string.title_tab_discontinuities_family_7));
        seventhTabFragmentTag = seventhTabSpec.getTag();
        mTabHost.addTab(seventhTabSpec, DiscontinuitySystemBaseFragment.class, bundle);

        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Exiting "
                    + DiscontinuitiesMainFragment.class.getSimpleName() + " : setupTabs ");
        }

    }

    private void colorTabs() {
        if (BuildConfig.DEBUG) {
            Log.d(SkavaConstants.LOG, "Entering "
                    + DiscontinuitiesMainFragment.class.getSimpleName() + " : colorTabs ");
        }
        mTabWidget = (TabWidget) mRootView.findViewById(android.R.id.tabs);
        for (int i = 0; i < mTabWidget.getChildCount(); i++) {
            View view = mTabWidget.getChildAt(i);
            TextView tv = (TextView) view.findViewById(android.R.id.title);
            tv.setTextColor(this.getResources().getColor(R.color.Black));
            // center text
            tv.setGravity(Gravity.CENTER);
            // wrap text
            tv.setSingleLine(false);
            // explicitly set layout parameters
            tv.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            tv.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
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
    public void onTabChanged(String tabId) {

        Log.d(SkavaConstants.LOG, "onTabChanged(): tabId	=" + tabId);
        if (TAB_FAMILY_1.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            ((SkavaFragmentActivity) getActivity()).saveDraft();
            mCurrentTab = 0;
            return;
        }
        if (TAB_FAMILY_2.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            ((SkavaFragmentActivity) getActivity()).saveDraft();
            mCurrentTab = 1;
            return;
        }
        if (TAB_FAMILY_3.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            //save the data entered in the previous tab and so on
            ((SkavaFragmentActivity) getActivity()).saveDraft();
            mCurrentTab = 2;
            return;
        }
        if (TAB_FAMILY_4.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            //save the data entered in the previous tab and so on
            ((SkavaFragmentActivity) getActivity()).saveDraft();
            mCurrentTab = 3;
            return;
        }
        if (TAB_FAMILY_5.equals(tabId)) {
            // updateTab(tabId, R.id.tab_2);
            //save the data entered in the previous tab and so on
            ((SkavaFragmentActivity) getActivity()).saveDraft();
            mCurrentTab = 4;
            return;
        }
        if (TAB_FAMILY_6.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            //save the data entered in the previous tab and so on
            ((SkavaFragmentActivity) getActivity()).saveDraft();
            mCurrentTab = 5;
            return;
        }
        if (TAB_FAMILY_7.equals(tabId)) {
            // updateTab(tabId, R.id.tab_1);
            ((SkavaFragmentActivity) getActivity()).saveDraft();
            mCurrentTab = 6;
            return;
        }
    }


    public void onDiscontinuityFamilyChanged(int familyNumber) {

        switch (familyNumber) {

            case 1:
                //save the data entered in the previous tab and so on
                DiscontinuitySystemBaseFragment firstTabfragment = (DiscontinuitySystemBaseFragment) getChildFragmentManager().findFragmentByTag(firstTabFragmentTag);
                if (firstTabfragment != null) {
//                    firstTabfragment.saveDiscontinuityFamily();
                }
                break;
            case 2:
                DiscontinuitySystemBaseFragment secondTabfragment = (DiscontinuitySystemBaseFragment) getChildFragmentManager().findFragmentByTag(secondTabFragmentTag);
                if (secondTabfragment != null) {
//                    secondTabfragment.saveDiscontinuityFamily();
                }
                break;
            case 3:
                DiscontinuitySystemBaseFragment thirdTabfragment = (DiscontinuitySystemBaseFragment) getChildFragmentManager().findFragmentByTag(thirdTabFragmentTag);
                if (thirdTabfragment != null) {
//                    thirdTabfragment.saveDiscontinuityFamily();
                }
                break;
            case 4:
                DiscontinuitySystemBaseFragment fourthTabfragment = (DiscontinuitySystemBaseFragment) getChildFragmentManager().findFragmentByTag(fourthTabFragmentTag);
                if (fourthTabfragment != null) {
//                    fourthTabfragment.saveDiscontinuityFamily();
                }
                break;
            case 5:
                DiscontinuitySystemBaseFragment fifthTabfragment = (DiscontinuitySystemBaseFragment) getChildFragmentManager().findFragmentByTag(fifthTabFragmentTag);
                if (fifthTabfragment != null) {
//                    fifthTabfragment.saveDiscontinuityFamily();
                }
                break;
            case 6:
                DiscontinuitySystemBaseFragment sixthTabfragment = (DiscontinuitySystemBaseFragment) getChildFragmentManager().findFragmentByTag(sixthTabFragmentTag);
                if (sixthTabfragment != null) {
//                    sixthTabfragment.saveDiscontinuityFamily();
                }
                break;
            case 7:
                DiscontinuitySystemBaseFragment seventhTabfragment = (DiscontinuitySystemBaseFragment) getChildFragmentManager().findFragmentByTag(seventhTabFragmentTag);
                if (seventhTabfragment != null) {
//                    seventhTabfragment.saveDiscontinuityFamily();
                }
                break;
        }


    }
}
