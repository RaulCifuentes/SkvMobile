package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class SyncLoggingTable extends SkavaTable {

    public static String SYNC_LOGGING_TABLE = "Synchronizations";

    public static final String DATE_COLUMN = "DATE";
    public static final String SOURCE_COLUMN = "SOURCE";
    public static final String STATUS_COLUMN = "STATUS";


    public static final String CREATE_SYNC_LOGGING_TABLE = "create table " +
            SYNC_LOGGING_TABLE +
            " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            DATE_COLUMN + " integer not null, " +
            SOURCE_COLUMN + " text not null, " +
            STATUS_COLUMN  + " text not null  );";



}
