package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityFamilyTable extends SkavaEntityTable {

    public static final String DISCONTINUITY_FAMILY_DATABASE_TABLE = "DiscontinuityFamilies";

    public static final String ASSESSMENT_CODE_COLUMN = "ASSESSMENT_CODE";
    public static final String NUMBER_CODE_COLUMN = "NUMBER_CODE";
    public static final String TYPE_CODE_COLUMN = "TYPE_CODE";
    public static final String RELEVANCE_CODE_COLUMN = "RELEVANCE_CODE";
    public static final String DIPDIRDEGREES_CODE_COLUMN = "DIPDIRDEGREES_CODE";
    public static final String DIPDEGREES_CODE_COLUMN = "DIPDEGREES_CODE";
    public static final String SHAPE_CODE_COLUMN = "SHAPE_CODE";
    public static final String SPACING_CODE_COLUMN = "SPACING_CODE";
    public static final String ROUGHNESS_CODE_COLUMN = "ROUGHNESS_CODE";
    public static final String WEATHERING_CODE_COLUMN = "WEATHERING_CODE";
    public static final String DISCONTINUITYWATER_CODE_COLUMN = "DISCONTINUITYWATER_CODE";
    public static final String PERSISTENCE_CODE_COLUMN = "PERSISTENCE_CODE";
    public static final String APERTURE_CODE_COLUMN = "APERTURE_CODE";
    public static final String INFILLING_CODE_COLUMN = "INFILLING_CODE";
    public static final String JA_CODE_COLUMN = "JA_CODE";
    public static final String JR_CODE_COLUMN = "JR_CODE";

    public static final String CREATE_DISCONTINUITY_FAMILIES_TABLE = "create table " +
            DISCONTINUITY_FAMILY_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            ASSESSMENT_CODE_COLUMN + " text not null, " +
            NUMBER_CODE_COLUMN + " integer not null, " +
            TYPE_CODE_COLUMN + " text not null, " +
            RELEVANCE_CODE_COLUMN + " text not null, " +
            DIPDIRDEGREES_CODE_COLUMN + " integer not null, " +
            DIPDEGREES_CODE_COLUMN + " integer not null, " +
            SHAPE_CODE_COLUMN + " text not null, " +
            SPACING_CODE_COLUMN + " text not null, " +
            ROUGHNESS_CODE_COLUMN + " text not null, " +
            WEATHERING_CODE_COLUMN + " text not null, " +
            DISCONTINUITYWATER_CODE_COLUMN + " text not null, " +
            PERSISTENCE_CODE_COLUMN + " text not null, " +
            APERTURE_CODE_COLUMN + " text not null, " +
            INFILLING_CODE_COLUMN + " text not null, " +
            JA_CODE_COLUMN + " text not null, " +
            JR_CODE_COLUMN + " text not null " +
            " );";
}
