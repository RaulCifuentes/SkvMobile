package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ExcavationProjectTable extends SkavaEntityTable {

    public static final String PROJECT_DATABASE_TABLE = "Projects";

    public static final String CLIENT_CODE_COLUMN = "CLIENT_CODE";

    public static final String CREATE_PROJECTS_TABLE = "create table " +
            PROJECT_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CLIENT_CODE_COLUMN + " text not null, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String DELETE_PROJECTS_TABLE = "delete from " + PROJECT_DATABASE_TABLE;

    public static final String INSERT_PROJECTS_TABLE = "insert into " + PROJECT_DATABASE_TABLE + "(" + CLIENT_CODE_COLUMN + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('CLN_A', 'PRJ_A','ExcavationProject A')";
    public static final String INSERT_PROJECTS_TABLE_SECOND = "insert into " + PROJECT_DATABASE_TABLE + "(" + CLIENT_CODE_COLUMN + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('CLN_B', 'PRJ_B','ExcavationProject B')";



}
