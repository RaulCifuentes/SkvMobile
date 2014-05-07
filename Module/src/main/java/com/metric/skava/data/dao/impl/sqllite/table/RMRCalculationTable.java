package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class RMRCalculationTable extends SkavaEntityTable {

    public static final String RMR_CALCULATION_DATABASE_TABLE = "RMRCalculations";

    public static final String ASSESSMENT_CODE_COLUMN = "ASSESMENT_CODE";
    public static final String STRENGTHOFROCK_CODE_COLUMN = "STRENGTHOFROCK_CODE";
    public static final String RQD_RMR_CODE_COLUMN = "RQD_RMR_CODE";
    public static final String SPACING_CODE_COLUMN = "SPACING_CODE";
    public static final String PERSISTENCE_CODE_COLUMN = "PERSISTENCE_CODE";
    public static final String APERTURE_CODE_COLUMN = "APERTURE_CODE";
    public static final String ROUGHNESS_CODE_COLUMN = "ROUGHNESS_CODE";
    public static final String INFILLING_CODE_COLUMN = "INFILLING_CODE";
    public static final String WEATHERING_CODE_COLUMN = "WEATHERING_CODE";
    public static final String GROUNDWATER_CODE_COLUMN = "GROUNDWATER_CODE";
    public static final String ORIENTATION_CODE_COLUMN = "ORIENTATION_CODE";

    public static final String CREATE_RMRCALCULATION_TABLE = "create table " +
            RMR_CALCULATION_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            ASSESSMENT_CODE_COLUMN + " text not null, " +
            STRENGTHOFROCK_CODE_COLUMN + " text not null, " +
            RQD_RMR_CODE_COLUMN + " text not null, " +
            SPACING_CODE_COLUMN + " text not null, " +
            PERSISTENCE_CODE_COLUMN + " text not null, " +
            APERTURE_CODE_COLUMN + " text not null, " +
            ROUGHNESS_CODE_COLUMN + " text not null, " +
            INFILLING_CODE_COLUMN + " text not null, " +
            WEATHERING_CODE_COLUMN + " text not null, " +
            GROUNDWATER_CODE_COLUMN + " text not null, " +
            ORIENTATION_CODE_COLUMN + " text not null " +
            " );";
}
