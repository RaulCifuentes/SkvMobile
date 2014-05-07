package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportRequirementTable extends SkavaTable {

    public static final String SUPPORT_DATABASE_TABLE = "SupportRequirements";

    public static final String TUNNEL_CODE_COLUMN = "TUNNEL_CODE";
    public static final String SPAN_ESR_RATIO_LOWERBOUND_COLUMN = "SPAN_ESR_RATIO_LOWER";
    public static final String SPAN_ESR_RATIO_UPPERBOUND_COLUMN = "SPAN_ESR_RATIO_UPPER";
    public static final String ROCK_QUALITY_CODE_COLUMN = "ROCK_QUALITY_CODE";
    public static final String BOLT_TYPE_CODE_COLUMN = "BOLT_TYPE_CODE";
    public static final String BOLT_DIAMETER_COLUMN = "BOLT_DIAMETER";
    public static final String BOLT_LENGTH_COLUMN = "BOLT_LENGTH";
    public static final String ROOF_PATTERN_COLUMN = "ROOF_PATTERN";
    public static final String ROOF_PATTERN_TYPE_CODE_COLUMN = "ROOF_PATTERN_TYPE_CODE";
    public static final String WALL_PATTERN_COLUMN = "WALL_PATTERN";
    public static final String WALL_PATTERN_TYPE_CODE_COLUMN = "WALL_PATTERN_TYPE_CODE";
    public static final String SHOTCRETE_TYPE_CODE_COLUMN = "SHOTCRETE_TYPE_CODE";
    public static final String THICKNESS_COLUMN = "THICKNESS";
    public static final String MESH_TYPE_CODE_COLUMN = "MESH_TYPE_CODE";
    public static final String COVERAGE_CODE_COLUMN = "COVERAGE_CODE";
    public static final String ARCH_TYPE_CODE_COLUMN = "ARCH_TYPE_CODE";
    public static final String SEPARATION_COLUMN = "SEPARATION";

    public static final String CREATE_SUPPORTS_TABLE = "create table " +
            SUPPORT_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            TUNNEL_CODE_COLUMN + " text not null, " +
            SPAN_ESR_RATIO_LOWERBOUND_COLUMN + " integer not null, " +
            SPAN_ESR_RATIO_UPPERBOUND_COLUMN + " integer not null, " +
            ROCK_QUALITY_CODE_COLUMN + " text not null, " +
            BOLT_TYPE_CODE_COLUMN + " text not null, " +
            BOLT_DIAMETER_COLUMN + " real not null, " +
            BOLT_LENGTH_COLUMN + " real not null, " +
            ROOF_PATTERN_COLUMN + " text not null, " +
            ROOF_PATTERN_TYPE_CODE_COLUMN + " text not null, " +
            WALL_PATTERN_COLUMN + " text not null, " +
            WALL_PATTERN_TYPE_CODE_COLUMN + " text not null, " +
            SHOTCRETE_TYPE_CODE_COLUMN + " text not null, " +
            THICKNESS_COLUMN + " real not null, " +
            MESH_TYPE_CODE_COLUMN + " text not null, " +
            COVERAGE_CODE_COLUMN + " text not null, " +
            ARCH_TYPE_CODE_COLUMN + " text not null, " +
            SEPARATION_COLUMN + " real not null " +
            " );";

    public static final String INSERT_SUPPORTS_TABLE = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
            ") values('TNL_A', 0, 100, 'EXCEPTIONALLY_POOR', 'BLT_B', 10, 10, 'SHT_B', 10, 'MSH_A', 'CVR_A', 'ARC_B', 10 )";

//    public static final String INSERT_SUPPORTS_TABLE_SECOND = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
//            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
//            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
//            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
//            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
//            ") values('TNL_B', 0, 100, 'EXTREMELY_POOR', 'BLT_C', 10, 10, 'SHT_A', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";
//
//    public static final String INSERT_SUPPORTS_TABLE_THIRD = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
//            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
//            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
//            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
//            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
//            ") values('TNL_C', 0, 100, 'VERY_POOR', 'BLT_A', 10, 10, 'SHT_B', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";
//
//    public static final String INSERT_SUPPORTS_TABLE_FOURTH = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
//            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
//            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
//            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
//            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
//            ") values('TNL_D', 0, 100, 'POOR', 'BLT_B', 10, 10, 'SHT_A', 10, 'MSH_A', 'CVR_A', 'ARC_B', 10 )";
//
//
//    public static final String INSERT_SUPPORTS_TABLE_FIFTH = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
//            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
//            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
//            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
//            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
//            ") values('TNL_D', 0, 100, 'FAIR', 'BLT_C', 10, 10, 'SHT_B', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";
//
//
//    public static final String INSERT_SUPPORTS_TABLE_SIXTH = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
//            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
//            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
//            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
//            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
//            ") values('TNL_D', 0, 100, 'GOOD', 'BLT_D', 10, 10, 'SHT_C', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";
//
//    public static final String INSERT_SUPPORTS_TABLE_SEVENTH = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
//            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
//            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
//            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
//            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
//            ") values('TNL_D', 0, 100, 'VERY_GOOD', 'BLT_C', 10, 10, 'SHT_C', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";
//
//    public static final String INSERT_SUPPORTS_TABLE_EIGHTH = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
//            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
//            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
//            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
//            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
//            ") values('TNL_D', 0, 100, 'EXTREMELY_GOOD', 'BLT_B', 10, 10, 'SHT_A', 10, 'MSH_A', 'CVR_C', 'ARC_C', 10 )";
//
//    public static final String INSERT_SUPPORTS_TABLE_NINETH = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
//            TUNNEL_CODE_COLUMN + "," + SPAN_ESR_RATIO_LOWERBOUND_COLUMN + "," + SPAN_ESR_RATIO_UPPERBOUND_COLUMN + "," + ROCK_QUALITY_CODE_COLUMN + "," +
//            BOLT_TYPE_CODE_COLUMN + "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
//            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
//            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
//            ") values('TNL_D', 0, 100, 'EXCEPTIONALLY_GOOD', 'BLT_A', 10, 10, 'SHT_B', 10, 'MSH_B', 'CVR_A', 'ARC_B', 10 )";
//

   public static final String DELETE_SUPPORTS_TABLE = "delete from " + SUPPORT_DATABASE_TABLE;

}
