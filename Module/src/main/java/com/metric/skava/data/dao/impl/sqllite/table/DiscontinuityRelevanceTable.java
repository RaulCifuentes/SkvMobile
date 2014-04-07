package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityRelevanceTable extends SkavaEntityTable {

    public static final String RELEVANCES_DATABASE_TABLE = "DiscontinuityRelevances";

    public static final String CREATE_RELEVANCES_TABLE = "create table " +
            RELEVANCES_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_RELEVANCES_TABLE = "insert into " + RELEVANCES_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('RLV_A','Primary')";
    public static final String INSERT_RELEVANCES_TABLE_SECOND = "insert into " + RELEVANCES_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('RLV_B','Random')";


    public static final String DELETE_RELEVANCES_TABLE = "delete from " + RELEVANCES_DATABASE_TABLE;


}
