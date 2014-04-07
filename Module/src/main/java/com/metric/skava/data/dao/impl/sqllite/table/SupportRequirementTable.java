package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportRequirementTable extends SkavaEntityTable {

    public static final String SUPPORT_DATABASE_TABLE = "Supports";

    public static final String TUNNEL_CODE_COLUMN =
            "TUNNEL_CODE";

    public static final String BOLT_TYPE_CODE_COLUMN = "BOLT_TYPE_CODE";
    public static final String BOLT_DIAMETER_COLUMN = "BOLT_DIAMETER";
    public static final String BOLT_LENGTH_COLUMN = "BOLT_LENGTH";
    public static final String SHOTCRETE_TYPE_CODE_COLUMN = "SHOTCRETE_TYPE";
    public static final String THICKNESS_COLUMN = "THICKNESS";
    public static final String MESH_TYPE_CODE_COLUMN = "MESH_TYPE";
    public static final String COVERAGE_CODE_COLUMN = "COVERAGE";
    public static final String ARCH_TYPE_CODE_COLUMN = "ARCH_TYPE";
    public static final String SEPARATION_COLUMN = "SEPARATION";


    public static final String CREATE_SUPPORTS_TABLE = "create table " +
            SUPPORT_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            TUNNEL_CODE_COLUMN + " text not null, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null, " +
            BOLT_TYPE_CODE_COLUMN + " text not null, " +
            BOLT_DIAMETER_COLUMN + " real not null, " +
            BOLT_LENGTH_COLUMN + " real not null, " +
            SHOTCRETE_TYPE_CODE_COLUMN + " text not null, " +
            THICKNESS_COLUMN + " text not null, " +
            MESH_TYPE_CODE_COLUMN + " text not null, " +
            COVERAGE_CODE_COLUMN + " text not null, " +
            ARCH_TYPE_CODE_COLUMN + " text not null, " +
            SEPARATION_COLUMN + " real not null " +
            " );";

    public static final String INSERT_SUPPORTS_TABLE = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
            TUNNEL_CODE_COLUMN + "," + CODE_COLUMN + "," + NAME_COLUMN + "," +
            BOLT_TYPE_CODE_COLUMN +  "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
            ") values('TNL_A', 'SPT_A','Support A', 'BLT_A', 10, 10, 'SHT_A', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";

    public static final String INSERT_SUPPORTS_TABLE_SECOND = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
            TUNNEL_CODE_COLUMN + "," + CODE_COLUMN + "," + NAME_COLUMN + "," +
            BOLT_TYPE_CODE_COLUMN +  "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
            ") values('TNL_B', 'SPT_A','Support A', 'BLT_A', 10, 10, 'SHT_A', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";

    public static final String INSERT_SUPPORTS_TABLE_THIRD = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
            TUNNEL_CODE_COLUMN + "," + CODE_COLUMN + "," + NAME_COLUMN + "," +
            BOLT_TYPE_CODE_COLUMN +  "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
            ") values('TNL_C', 'SPT_A','Support A', 'BLT_A', 10, 10, 'SHT_A', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";

    public static final String INSERT_SUPPORTS_TABLE_FOURTH = "insert into " + SUPPORT_DATABASE_TABLE + "(" +
            TUNNEL_CODE_COLUMN + "," + CODE_COLUMN + "," + NAME_COLUMN + "," +
            BOLT_TYPE_CODE_COLUMN +  "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
            ") values('TNL_D', 'SPT_A','Support A', 'BLT_A', 10, 10, 'SHT_A', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";

   public static final String DELETE_SUPPORTS_TABLE = "delete from " + SUPPORT_DATABASE_TABLE ;

}
