package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public abstract class MappexIndexInstanceBaseTable extends SkavaTable {

    public static final String INDEX_CODE_COLUMN = "INDEX_CODE";
    public static final String GROUP_CODE_COLUMN = "GROUP_CODE";
    public static final String CODE_COLUMN = "CODE";
    public static final String KEY_COLUMN = "KEY";
    public static final String SHORT_DESCRIPTION_COLUMN = "SHORT_DESCRIPTION";
    public static final String DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String VALUE_COLUMN  = "VALUE";

    public static final String CREATE_SCRIPT = " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            INDEX_CODE_COLUMN + " text not null, " +
            GROUP_CODE_COLUMN + " text null, " +
            CODE_COLUMN + " text not null, " +
            KEY_COLUMN + " text not null, " +
            SHORT_DESCRIPTION_COLUMN + " text not null, " +
            DESCRIPTION_COLUMN + " text not null, " +
            VALUE_COLUMN + " real not null );";


    public static final String INSERT_SCRIPT = " (" +
            INDEX_CODE_COLUMN + "," +
            GROUP_CODE_COLUMN + "," +
            CODE_COLUMN + "," +
            KEY_COLUMN + "," +
            SHORT_DESCRIPTION_COLUMN + "," +
            DESCRIPTION_COLUMN + "," +
            VALUE_COLUMN +
            ") ";

}
