package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityWaterTable extends SkavaEntityTable {

    public static final String WATER_DATABASE_TABLE = "DiscontinuityWaters";

    public static final String CREATE_WATERS_TABLE = "create table " +
            WATER_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_WATERS_TABLE = "insert into " + WATER_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('WTR_A','Dry')";
    public static final String INSERT_WATERS_TABLE_SECOND = "insert into " + WATER_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('WTR_B','Damp')";
    public static final String INSERT_WATERS_TABLE_THIRD = "insert into " + WATER_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('WTR_C','Wet')";
    public static final String INSERT_WATERS_TABLE_FOURTH = "insert into " + WATER_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('WTR_D','Dripping')";
    public static final String INSERT_WATERS_TABLE_FIFTH = "insert into " + WATER_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('WTR_D','Flowing')";

    public static final String DELETE_WATERS_TABLE = "delete from " + WATER_DATABASE_TABLE;

}
