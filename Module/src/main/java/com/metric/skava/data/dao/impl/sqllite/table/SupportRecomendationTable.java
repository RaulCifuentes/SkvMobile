package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportRecomendationTable extends SkavaTable {

    public static final String RECOMENDATION_DATABASE_TABLE = "SupportRecomendations";

    public static final String ASSESSMENT_CODE_COLUMN = "ASSESSMENT_CODE";
    public static final String SUPPORT_REQUIREMENT_BASE_CODE_COLUMN = "SUPPORT_REQUIREMENT_BASE_CODE";
    public static final String BOLT_TYPE_CODE_COLUMN = "BOLT_TYPE_CODE";
    public static final String BOLT_DIAMETER_COLUMN = "BOLT_DIAMETER";
    public static final String BOLT_LENGTH_COLUMN = "BOLT_LENGTH";
    public static final String ROOF_PATTERN_TYPE_CODE_COLUMN = "ROOF_PATTERN_TYPE_CODE";
    public static final String ROOF_PATTERN_DX_COLUMN = "ROOF_PATTERN_DX";
    public static final String ROOF_PATTERN_DY_COLUMN = "ROOF_PATTERN_DY";
    public static final String WALL_PATTERN_TYPE_CODE_COLUMN = "WALL_PATTERN_TYPE_CODE";
    public static final String WALL_PATTERN_DX_COLUMN = "WALL_PATTERN_DX";
    public static final String WALL_PATTERN_DY_COLUMN = "WALL_PATTERN_DY";
    public static final String SHOTCRETE_TYPE_CODE_COLUMN = "SHOTCRETE_TYPE_CODE";
    public static final String THICKNESS_COLUMN = "THICKNESS";
    public static final String MESH_TYPE_CODE_COLUMN = "MESH_TYPE_CODE";
    public static final String COVERAGE_CODE_COLUMN = "COVERAGE_CODE";
    public static final String ARCH_TYPE_CODE_COLUMN = "ARCH_TYPE_CODE";
    public static final String SEPARATION_COLUMN = "SEPARATION";
    public static final String OBSERVATIONS_COLUMN = "OBSERVATIONS";


    public static final String CREATE_RECOMENDATIONS_TABLE = "create table " +
            RECOMENDATION_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            ASSESSMENT_CODE_COLUMN + " text not null, " +
            SUPPORT_REQUIREMENT_BASE_CODE_COLUMN + " text null, " +
            BOLT_TYPE_CODE_COLUMN + " text null, " +
            BOLT_DIAMETER_COLUMN + " real null, " +
            BOLT_LENGTH_COLUMN + " real  null, " +
            ROOF_PATTERN_TYPE_CODE_COLUMN + " text null, " +
            ROOF_PATTERN_DX_COLUMN + " real null, " +
            ROOF_PATTERN_DY_COLUMN + " real null, " +
            WALL_PATTERN_TYPE_CODE_COLUMN + " text null, " +
            WALL_PATTERN_DX_COLUMN + " real null, " +
            WALL_PATTERN_DY_COLUMN + " real null, " +
            SHOTCRETE_TYPE_CODE_COLUMN + " text null, " +
            THICKNESS_COLUMN + " real null, " +
            MESH_TYPE_CODE_COLUMN + " text null, " +
            COVERAGE_CODE_COLUMN + " text null, " +
            ARCH_TYPE_CODE_COLUMN + " text null, " +
            SEPARATION_COLUMN + " real null, " +
            OBSERVATIONS_COLUMN + " text null " +
            " );";



        public static final String DELETE_RECOMENDATIONS_TABLE = "delete from " + RECOMENDATION_DATABASE_TABLE ;

}
