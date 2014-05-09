package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportRequirementTable extends SkavaEntityTable {

    public static final String SUPPORT_REQUIREMENT_DATABASE_TABLE = "SupportRequirements";

    public static final String TUNNEL_CODE_COLUMN = "TUNNEL_CODE";
    public static final String Q_LOWER_BOUND_COLUMN = "Q_LOWER_BOUND";
    public static final String Q_UPPER_BOUND_COLUMN = "Q_UPPER_BOUND";
    public static final String BOLT_TYPE_CODE_COLUMN = "BOLT_TYPE_CODE";
    public static final String BOLT_DIAMETER_COLUMN = "BOLT_DIAMETER";
    public static final String BOLT_LENGTH_COLUMN = "BOLT_LENGTH";
    public static final String ROOF_PATTERN_TYPE_CODE_COLUMN = "ROOF_PATTERN_TYPE_CODE";
    public static final String ROOF_PATTERN_DX_COLUMN = "ROOF_PATTERN_DX";
    public static final String ROOF_PATTERN_DY_COLUMN = "ROOF_PATTERN_DY";
    public static final String WALL_PATTERN_TYPE_CODE_COLUMN = "WALL_PATTERN_TYPE_CODE";
    public static final String WALL_PATTERN_DX_COLUMN= "WALL_PATTERN_DX";
    public static final String WALL_PATTERN_DY_COLUMN = "WALL_PATTERN_DY";
    public static final String SHOTCRETE_TYPE_CODE_COLUMN = "SHOTCRETE_TYPE_CODE";
    public static final String THICKNESS_COLUMN = "THICKNESS";
    public static final String MESH_TYPE_CODE_COLUMN = "MESH_TYPE_CODE";
    public static final String COVERAGE_CODE_COLUMN = "COVERAGE_CODE";
    public static final String ARCH_TYPE_CODE_COLUMN = "ARCH_TYPE_CODE";
    public static final String SEPARATION_COLUMN = "SEPARATION";


    public static final String CREATE_SUPPORTS_REQUIREMENT_TABLE = "create table " +
            SUPPORT_REQUIREMENT_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            TUNNEL_CODE_COLUMN + " text not null, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text null, " +
            Q_LOWER_BOUND_COLUMN + " real null, " +
            Q_UPPER_BOUND_COLUMN + " real null, " +
            BOLT_TYPE_CODE_COLUMN + " text null, " +
            BOLT_DIAMETER_COLUMN + " real null, " +
            BOLT_LENGTH_COLUMN + " real null, " +
            ROOF_PATTERN_TYPE_CODE_COLUMN + " text null, " +
            ROOF_PATTERN_DX_COLUMN + " real null, " +
            ROOF_PATTERN_DY_COLUMN + " real null, " +
            WALL_PATTERN_TYPE_CODE_COLUMN + " text null, " +
            WALL_PATTERN_DX_COLUMN + " real null, " +
            WALL_PATTERN_DY_COLUMN + " real  null, " +
            SHOTCRETE_TYPE_CODE_COLUMN + " text null, " +
            THICKNESS_COLUMN + " real  null, " +
            MESH_TYPE_CODE_COLUMN + " text null, " +
            COVERAGE_CODE_COLUMN + " text null, " +
            ARCH_TYPE_CODE_COLUMN + " text null, " +
            SEPARATION_COLUMN + " real  null " +
            " );";


   public static final String DELETE_SUPPORT_REQUIREMENTS_TABLE = "delete from " + SUPPORT_REQUIREMENT_DATABASE_TABLE;

}
