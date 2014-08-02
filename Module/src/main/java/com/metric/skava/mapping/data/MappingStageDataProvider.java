package com.metric.skava.mapping.data;

import android.content.Context;

import com.metric.skava.R;
import com.metric.skava.mapping.model.MappingStage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 7/31/14.
 */
public class MappingStageDataProvider {
    private static MappingStageDataProvider instance;
    private static Context context;

    public static String GRAL_INFO = "GRAL_INFO";
    public static String PICS = "PICS";
    public static String EXPANDED = "EXPANDED";
    public static String REPORT = "REPORT";

    public static MappingStageDataProvider getInstance(Context ctx) {
        if (instance == null) {
            instance = new MappingStageDataProvider();
            context = ctx;
        }
        return instance;
    }

    public List<MappingStage> getAllStages() {
        ArrayList<MappingStage> list = new ArrayList<MappingStage>();
        list.add(new MappingStage(GRAL_INFO, context.getString(R.string.assessment_master_menu_identification_label)));
        list.add(new MappingStage(PICS, context.getString(R.string.assessment_master_menu_pictures_label)));
        list.add(new MappingStage(EXPANDED, context.getString(R.string.assessment_master_menu_expanded_tunnel_label)));
        list.add(new MappingStage(REPORT, context.getString(R.string.assessment_master_menu_summary_label)));
        return list;
    }
}
