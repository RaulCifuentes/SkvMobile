package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class PermissionTable extends SkavaTable {

    public static final String PERMISSION_DATABASE_TABLE = "Permissions";

    public static String USER_CODE_COLUMN = "USER_CODE";
    public static String ACTION_COLUMN = "ACTION";
    public static String TARGET_TYPE_CODE_COLUMN = "TARGET_TYPE_CODE";
    public static String TARGET_CODE_COLUMN = "TARGET_CODE";

    public static final String CREATE_PERMISSIONS_TABLE = "create table " +
            PERMISSION_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            USER_CODE_COLUMN + " text not null, " +
            ACTION_COLUMN + " text not null, " +
            TARGET_TYPE_CODE_COLUMN + " text not null, " +
            TARGET_CODE_COLUMN + " text not null  );";

    public static final String INSERT_PERMISSION_TABLE = "insert into " + PERMISSION_DATABASE_TABLE + "(" + USER_CODE_COLUMN + "," + ACTION_COLUMN + "," + TARGET_TYPE_CODE_COLUMN +  "," + TARGET_CODE_COLUMN + " ) values('USR_C','ALL', 'FACE', 'TFC_1')";
    public static final String INSERT_PERMISSION_TABLE_SECOND = "insert into " + PERMISSION_DATABASE_TABLE  + "(" + USER_CODE_COLUMN + "," + ACTION_COLUMN + "," + TARGET_TYPE_CODE_COLUMN +  "," + TARGET_CODE_COLUMN + " ) values('USR_C','ALL', 'FACE', 'TFC_2')";
    public static final String INSERT_PERMISSION_TABLE_THIRD = "insert into " + PERMISSION_DATABASE_TABLE  + "(" + USER_CODE_COLUMN + "," + ACTION_COLUMN + "," + TARGET_TYPE_CODE_COLUMN +  "," + TARGET_CODE_COLUMN + " ) values('USR_C','ALL', 'FACE', 'TFC_3')";
    public static final String INSERT_PERMISSION_TABLE_FOURTH = "insert into " + PERMISSION_DATABASE_TABLE + "(" + USER_CODE_COLUMN + "," + ACTION_COLUMN + "," + TARGET_TYPE_CODE_COLUMN +  "," + TARGET_CODE_COLUMN + " ) values('USR_C','ALL', 'FACE', 'TFC_4')";

    public static final String INSERT_PERMISSION_TABLE_FIFTH = "insert into " + PERMISSION_DATABASE_TABLE  + "(" + USER_CODE_COLUMN + "," + ACTION_COLUMN + "," + TARGET_TYPE_CODE_COLUMN +  "," + TARGET_CODE_COLUMN + " ) values('USR_A','ALL', 'FACE', 'TFC_5')";
    public static final String INSERT_PERMISSION_TABLE_SIXTH = "insert into " + PERMISSION_DATABASE_TABLE  + "(" + USER_CODE_COLUMN + "," + ACTION_COLUMN + "," + TARGET_TYPE_CODE_COLUMN +  "," + TARGET_CODE_COLUMN + " ) values('USR_A','ALL', 'FACE', 'TFC_6')";
    public static final String INSERT_PERMISSION_TABLE_SEVENTH = "insert into " + PERMISSION_DATABASE_TABLE  + "(" + USER_CODE_COLUMN + "," + ACTION_COLUMN + "," + TARGET_TYPE_CODE_COLUMN +  "," + TARGET_CODE_COLUMN + " ) values('USR_A','ALL', 'FACE', 'TFC_7')";


    public static final String DELETE_PERMISSION_TABLE = "delete from " + PERMISSION_DATABASE_TABLE;

}
