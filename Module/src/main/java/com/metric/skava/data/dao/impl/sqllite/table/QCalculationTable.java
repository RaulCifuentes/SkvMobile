package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class QCalculationTable extends SkavaEntityTable {

    public static final String Q_CALCULATION_DATABASE_TABLE = "QCalculations";

    public static final String ASSESSMENT_CODE_COLUMN = "ASSESSMENT_CODE";
    public static final String RQD_COLUMN = "RQD";
    public static final String Jn_CODE_COLUMN = "JN_CODE";
    public static final String INTERSECTION_CODE_COLUMN = "IS_INTERSECTION";
    public static final String Jr_CODE_COLUMN = "JR_CODE";
    public static final String Ja_CODE_COLUMN = "JA_CODE";
    public static final String Jw_CODE_COLUMN = "JW_CODE";
    public static final String SRF_CODE_COLUMN = "SRF_CODE";
    public static final String Q_COLUMN = "Q_VALUE";


    public static final String CREATE_QCALCULATION_TABLE = "create table " +
            Q_CALCULATION_DATABASE_TABLE + " (" +
//            GLOBAL_KEY_ID + " integer primary key autoincrement, " +
            ASSESSMENT_CODE_COLUMN + " text primary key not null, " +
            RQD_COLUMN + " integer  null, " +
            Jn_CODE_COLUMN + " text null, " +
            INTERSECTION_CODE_COLUMN + " integer  null, " +
            Jr_CODE_COLUMN + " text null, " +
            Ja_CODE_COLUMN + " text null, " +
            Jw_CODE_COLUMN + " text null, " +
            SRF_CODE_COLUMN + " text null, " +
            Q_COLUMN + " real null " +
            " );";


}
