package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class AssessmentTable extends SkavaTable {

    public static final String ASSESSMENT_DATABASE_TABLE = "Assessments";

    public static final String DEVICE_ID_COLUMN = "ORIGINATOR_DEVICE";

    public static final String ENVIRONMENT_COLUMN = "ENVIRONMENT";

    public static final String INTERNAL_CODE_COLUMN = "INTERNAL_CODE";

    public static final String CODE_COLUMN = "CODE";

    public static final String GEOLOGIST_CODE_COLUMN = "GEOLOGIST_CODE";

    public static final String TUNEL_FACE_CODE_COLUMN = "TUNNEL_FACE_CODE";

    public static final String DATE_COLUMN = "DATE";

    public static final String EXCAVATION_SECTION_CODE_COLUMN = "SECTION_CODE";

    public static final String EXCAVATION_METHOD_CODE_COLUMN = "METHOD_CODE";

    public static final String INITIAL_CHAINAGE_COLUMN = "INITIAL_CHAINAGE";

    public static final String FINAL_CHAINAGE_COLUMN = "FINAL_CHAINAGE";

    public static final String REFERENCE_CHAINAGE_COLUMN = "REFERENCE_CHAINAGE";

    public static final String ADVANCE_COLUMN = "ADVANCE";

    public static final String ACCUM_ADVANCE_COLUMN = "ACCUM_ADVANCE";

    public static final String ORIENTATION_COLUMN = "ORIENTATION";

    public static final String SLOPE_COLUMN = "SLOPE";

    public static final String FRACTURE_TYPE_CODE_COLUMN = "FRACTURE_TYPE_CODE";

    public static final String BLOCKS_SIZE_COLUMN = "BLOCKS_SIZE";

    public static final String NUMBER_JOINTS_COLUMN = "NUMBER_JOINTS";

    public static final String OUTCROP_COLUMN = "OUTCROP";

    public static final String ROCK_SAMPLE_IDENTIFICATION_COLUMN = "ROCK_SAMPLE_IDENTIFICATION";

    public static final String DATA_SENT_STATUS_COLUMN = "DATA_SENT_STATUS";

    public static final String FILES_SENT_STATUS_COLUMN = "FILES_SENT_STATUS";

    public static final String SAVING_STATUS_COLUMN = "SAVING_STATUS";


    public static final String CREATE_ASSESSMENT_TABLE = "create table " +
            ASSESSMENT_DATABASE_TABLE + " (" +
//            GLOBAL_KEY_ID + " integer primary key autoincrement, " +
            CODE_COLUMN + " text primary key not null, " +
            DEVICE_ID_COLUMN + " text not null, " +
            ENVIRONMENT_COLUMN + " text not null, " +
            INTERNAL_CODE_COLUMN + " text null, " +
            GEOLOGIST_CODE_COLUMN + " text null, " +
            TUNEL_FACE_CODE_COLUMN + " text null, " +
            DATE_COLUMN + " integer null, " +
            EXCAVATION_SECTION_CODE_COLUMN + " text null, " +
            EXCAVATION_METHOD_CODE_COLUMN + " text null, " +
            INITIAL_CHAINAGE_COLUMN + " real null, " +
            FINAL_CHAINAGE_COLUMN + " real null, " +
            REFERENCE_CHAINAGE_COLUMN + " real null, " +
            ADVANCE_COLUMN + " real null, " +
            ACCUM_ADVANCE_COLUMN + " real null, " +
            ORIENTATION_COLUMN + " integer null, " +
            SLOPE_COLUMN + " real null, " +
            FRACTURE_TYPE_CODE_COLUMN + " text null, " +
            BLOCKS_SIZE_COLUMN + " real null, " +
            NUMBER_JOINTS_COLUMN + " integer null, " +
            OUTCROP_COLUMN + " text null, " +
            ROCK_SAMPLE_IDENTIFICATION_COLUMN + " text null, " +
            DATA_SENT_STATUS_COLUMN + " text not null, " +
            FILES_SENT_STATUS_COLUMN + " text not null, " +
            SAVING_STATUS_COLUMN + " text not null " +
            " );";

}
