package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ExcavationFactorTable extends SkavaTable {

    public static final String FACTOR_DATABASE_TABLE = "Factors";

    //The name and column index of each column in your database.
    public static final String TUNNEL_CODE_COLUMN = "TUNNEL_CODE";
    public static final String ESR_CODE_COLUMN = "ESR_CODE";
    public static final String SPAN_COLUMN = "SPAN";

    public static final String CREATE_FACTOR_TABLE = "create table " +
            FACTOR_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            TUNNEL_CODE_COLUMN + " text not null, " +
            ESR_CODE_COLUMN + " text not null, " +
            SPAN_COLUMN + " real not null  );";

    public static final String INSERT_FACTOR_TABLE = "insert into " + FACTOR_DATABASE_TABLE + "(" +  TUNNEL_CODE_COLUMN +  "," +  ESR_CODE_COLUMN + "," + SPAN_COLUMN + ") values('TNL_A', 'A', 650)";
    public static final String INSERT_FACTOR_TABLE_SECOND = "insert into " + FACTOR_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + ESR_CODE_COLUMN + "," + SPAN_COLUMN + ") values('TNL_B','C', 300)";
    public static final String INSERT_FACTOR_TABLE_THIRD = "insert into " + FACTOR_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + ESR_CODE_COLUMN + "," + SPAN_COLUMN +  ") values('TNL_C','D', 280)";
    public static final String INSERT_FACTOR_TABLE_FOURTH = "insert into " + FACTOR_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + ESR_CODE_COLUMN + "," + SPAN_COLUMN + ") values('TNL_D','E', 430)";
    public static final String INSERT_FACTOR_TABLE_FIFTH = "insert into " + FACTOR_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + ESR_CODE_COLUMN + "," + SPAN_COLUMN + ") values('TNL_E','F', 310)";

    public static final String DELETE_FACTOR_TABLE = "delete from " + FACTOR_DATABASE_TABLE ;
}
