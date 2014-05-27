package com.metric.skava.report.activity;

import android.os.Bundle;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.report.fragment.MappingReportMainFragment;


public class ReviewReportMainActivity extends SkavaFragmentActivity {

    private Boolean isPreview;
    public static final String IS_PREVIEW = "MAPPING_REPORT_MAIN_ACTIVITY_IS_PREVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapping_report_main_activity);
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
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MappingReportMainFragment())
                    .commit();
        }

    }



}
