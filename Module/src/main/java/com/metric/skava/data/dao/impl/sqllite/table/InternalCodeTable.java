package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class InternalCodeTable extends SkavaEntityTable {

    public static final String INTERNAL_CODE_DATABASE_TABLE = "Internals";

    public static final String CREATE_INTERNAL_CODES_TABLE = "create table " +
            INTERNAL_CODE_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_INTERNAL_CODES_TABLE = "insert into " + INTERNAL_CODE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SKV_A','Skava Internal Code A')";
    public static final String INSERT_INTERNAL_CODES_TABLE_SECOND = "insert into " + INTERNAL_CODE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SKV_B','Skava Internal Code B')";
    public static final String INSERT_INTERNAL_CODES_TABLE_THIRD = "insert into " + INTERNAL_CODE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SKV_C','Skava Internal Code C')";



}
