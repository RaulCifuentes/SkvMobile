package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportPatternTypeTable extends SkavaEntityTable {

    public static final String PATTERN_TYPE_DATABASE_TABLE = "PatternType";

    public static final String CREATE_PATTERN_TYPE_TABLE = "create table " +
            PATTERN_TYPE_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String DELETE_PATTERN_TYPE_TABLE = "delete from " + PATTERN_TYPE_DATABASE_TABLE;


}
