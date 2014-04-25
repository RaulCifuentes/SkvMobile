package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class AssessmentTable extends SkavaTable {

    public static final String ASSESSMENT_DATABASE_TABLE = "Assessments";

    public static final String INTERNAL_CODE_COLUMN = "INTERNAL_CODE";

    public static final String CODE_COLUMN = "CODE";

    public static final String GEOLOGIST_CODE_COLUMN = "GEOLOGIST_CODE";

    public static final String TUNEL_FACE_CODE_COLUMN = "TUNNEL_FACE_CODE";

    public static final String DATE_COLUMN = "DATE";

    public static final String EXCAVATION_SECTION_CODE_COLUMN = "SECTION_CODE";

    public static final String EXCAVATION_METHOD_CODE_COLUMN = "METHOD_CODE";

    public static final String PK_INITIAL_COLUMN = "PEG_INITIAL";

    public static final String PK_FINAL_COLUMN = "PEG_FINAL";

    public static final String ADVANCE_ACUMM_COLUMN = "ADVANCE";

    public static final String ORIENTATION_COLUMN = "ORIENTATION";

    public static final String SLOPE_COLUMN = "SLOPE";

    public static final String FRACTURE_TYPE_CODE_COLUMN = "FRACTURE_TYPE_CODE";

    public static final String BLOCKS_SIZE_COLUMN = "BLOCKS_SIZE";

    public static final String NUMBER_JOINTS_COLUMN = "NUMBER_JOINTS";

    public static final String OUTCROP_COLUMN = "OUTCROP";


    //TODO Solved the relations on the dependant side
    //    public static final String RECOMENDATION_CODE_COLUMN = "SUPPORT_RECOMENDATION_CODE";


    public static final String CREATE_ASSESSMENT_TABLE = "create table " +
            ASSESSMENT_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            INTERNAL_CODE_COLUMN + " text not null, " +
            GEOLOGIST_CODE_COLUMN + " text not null, " +
            TUNEL_FACE_CODE_COLUMN + " text null, " +
            DATE_COLUMN + " integer not null, " +
            EXCAVATION_SECTION_CODE_COLUMN + " text null, " +
            EXCAVATION_METHOD_CODE_COLUMN + " text null, " +
            PK_INITIAL_COLUMN + " real null, " +
            PK_FINAL_COLUMN + " real null, " +
            ADVANCE_ACUMM_COLUMN + " real null, " +
            ORIENTATION_COLUMN + " integer null, " +
            SLOPE_COLUMN + " real null, " +
            FRACTURE_TYPE_CODE_COLUMN + " text null, " +
            BLOCKS_SIZE_COLUMN + " real null, " +
            NUMBER_JOINTS_COLUMN + " integer null, " +
            OUTCROP_COLUMN + " text null  );";

    private static String INSERT_SCRIPT = "insert into " + ASSESSMENT_DATABASE_TABLE + "(" +
            CODE_COLUMN + "," +
            INTERNAL_CODE_COLUMN + "," +
            GEOLOGIST_CODE_COLUMN + "," +
            TUNEL_FACE_CODE_COLUMN + "," +
            DATE_COLUMN + "," +
            EXCAVATION_SECTION_CODE_COLUMN + "," +
            EXCAVATION_METHOD_CODE_COLUMN + "," +
            PK_INITIAL_COLUMN + "," +
            PK_FINAL_COLUMN + "," +
            ADVANCE_ACUMM_COLUMN + "," +
            ORIENTATION_COLUMN + "," +
            SLOPE_COLUMN + "," +
            FRACTURE_TYPE_CODE_COLUMN + "," +
            BLOCKS_SIZE_COLUMN + "," +
            NUMBER_JOINTS_COLUMN  + "," +
            OUTCROP_COLUMN +") ";

    public static final String INSERT_ASSESSMENT_TABLE          = INSERT_SCRIPT + " values('SKV_A', 'GLG_A', 'TFC_1', 'SCT_A', 20131015011500, 9999999, 100, 'MTH_D', 210, 5,  'FRC_B', 100, 10, 'China deploys ships to new search areas, as Thailand says its radars may have tracked the missing Malaysia Airlines plane shortly after it lost contact.')";
    public static final String INSERT_ASSESSMENT_TABLE_SECOND   = INSERT_SCRIPT + " values('SKV_B', 'GLG_A', 'TFC_3', 'SCT_B', 20140229223000, 9999999, 100, 'MTH_B', 125, 30, 'FRC_E', 220, 15, 'Researchers outline compelling evidence for an occasion when the ancient Earth was battered by two asteroids at the same time.')";
    public static final String INSERT_ASSESSMENT_TABLE_THIRD    = INSERT_SCRIPT + " values('SKV_C', 'GLG_A', 'TFC_5', 'SCT_C', 20131218144500, 9999999, 100, 'MTH_C', 80, 10,  'FRC_F', 80, 17,  'The 2008 crisis with Georgia, which saw Moscow effectively establishing two Russian-speaking protectorates carved out from Georgia-proper, was just a taster. Crimea itself is not the main course; that is Ukraine itself.')";


}
