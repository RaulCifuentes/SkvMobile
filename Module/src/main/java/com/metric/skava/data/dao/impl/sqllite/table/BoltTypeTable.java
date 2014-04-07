package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class BoltTypeTable extends SkavaEntityTable {

    public static final String BOLT_DATABASE_TABLE = "Bolts";

    public static final String CREATE_BOLTS_TABLE = "create table " +
            BOLT_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_BOLTS_TABLE = "insert into " + BOLT_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('BLT_A','Resin')";
    public static final String INSERT_BOLTS_TABLE_SECOND = "insert into " + BOLT_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('BLT_B','Self-Drilling')";
    public static final String INSERT_BOLTS_TABLE_THIRD = "insert into " + BOLT_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('BLT_C','Fully Grouted')";
    public static final String INSERT_BOLTS_TABLE_FOURTH = "insert into " + BOLT_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('BLT_D','End Anchored')";

    public static final String DELETE_BOLTS_TABLE = "delete from " + BOLT_DATABASE_TABLE ;


}
