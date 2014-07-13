package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class PermissionTable extends SkavaTable {

    public static final String PERMISSION_DATABASE_TABLE = "Permissions";

    public static final String ENVIRONMENT_COLUMN = "ENVIRONMENT";
    public static String USER_CODE_COLUMN = "USER_CODE";
    public static String ACTION_COLUMN = "ACTION";
    public static String TARGET_TYPE_CODE_COLUMN = "TARGET_TYPE_CODE";
    public static String TARGET_CODE_COLUMN = "TARGET_CODE";

    public static final String CREATE_PERMISSIONS_TABLE = "create table " +
            PERMISSION_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            ENVIRONMENT_COLUMN + " text not null, " +
            USER_CODE_COLUMN + " text not null, " +
            ACTION_COLUMN + " text not null, " +
            TARGET_TYPE_CODE_COLUMN + " text not null, " +
            TARGET_CODE_COLUMN + " text not null  );";


    public static final String DELETE_PERMISSION_TABLE = "delete from " + PERMISSION_DATABASE_TABLE;

}
