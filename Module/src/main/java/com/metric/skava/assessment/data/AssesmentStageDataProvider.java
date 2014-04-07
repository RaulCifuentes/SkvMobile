package com.metric.skava.assessment.data;

import android.content.Context;

import com.metric.skava.R;
import com.metric.skava.assessment.model.AssessmentStage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 2/21/14.
 */
public class AssesmentStageDataProvider {

    private static AssesmentStageDataProvider instance;
    private static Context context;

    public static String GRAL_INFO = "GRAL_INFO";
    public static String DISCONTINUITIES = "DISCONTINUITIES";
    public static String Q = "Q";
    public static String ROCK_SUPPORT = "ROCK_SUPPORT";
    public static String RMR = "RMR";
    public static String PICS = "PICS";
    public static String DESC = "DESC";
    public static String INSTRUCTIONS = "INSTRUCTIONS";
    public static String REPORT = "REPORT";
    public static String SAVE = "SAVE";



    public static AssesmentStageDataProvider getInstance(Context ctx) {
        if (instance == null) {
            instance = new AssesmentStageDataProvider();
            context = ctx;
        }
        return instance;
    }


    public List<AssessmentStage> getAllStages() {
        ArrayList<AssessmentStage> list = new ArrayList<AssessmentStage>();
        list.add(new AssessmentStage(GRAL_INFO, context.getString(R.string.assessment_master_menu_identification_label)));
        list.add(new AssessmentStage(DISCONTINUITIES, context.getString(R.string.assessment_master_menu_discontinuities_label)));
        list.add(new AssessmentStage(Q, context.getString(R.string.assessment_master_menu_qvalue_label)));
        list.add(new AssessmentStage(ROCK_SUPPORT, context.getString(R.string.assessment_master_menu_rock_support_label)));
        list.add(new AssessmentStage(RMR, context.getString(R.string.assessment_master_menu_rmr_label)));
        list.add(new AssessmentStage(PICS, context.getString(R.string.assessment_master_menu_pictures_label)));
        list.add(new AssessmentStage(DESC, context.getString(R.string.assessment_master_menu_description_label)));
        list.add(new AssessmentStage(INSTRUCTIONS, context.getString(R.string.assessment_master_menu_instructions_label)));
        list.add(new AssessmentStage(REPORT, context.getString(R.string.assessment_master_menu_summary_label)));
        list.add(new AssessmentStage(SAVE, context.getString(R.string.assessment_master_menu_save_label)));
        return list;
    }
}
