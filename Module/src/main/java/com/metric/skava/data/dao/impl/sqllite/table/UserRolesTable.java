package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class UserRolesTable extends SkavaTable {

    public static final String USER_ROLES_DATABASE_TABLE = "UserRoles";

    public static final String USER_CODE_COLUMN = "USER_CODE";
    public static final String ROLE_CODE_COLUMN = "ROLE_CODE";


    public static final String CREATE_USER_ROLES_TABLE = "create table " +
            USER_ROLES_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            USER_CODE_COLUMN + " text not null, " +
            ROLE_CODE_COLUMN + " text not null );";


    public static final String INSERT_USER_ROLES_TABLE = "insert into " + USER_ROLES_DATABASE_TABLE + "(" + USER_CODE_COLUMN + "," + ROLE_CODE_COLUMN + ") values('USR_A','ADMINISTRATOR')";
    public static final String INSERT_USER_ROLES_TABLE_SECOND = "insert into " + USER_ROLES_DATABASE_TABLE + "(" + USER_CODE_COLUMN + "," + ROLE_CODE_COLUMN + ") values('USR_B','ANALYST')";
    public static final String INSERT_USER_ROLES_TABLE_THIRD = "insert into " + USER_ROLES_DATABASE_TABLE + "(" + USER_CODE_COLUMN + "," + ROLE_CODE_COLUMN + ") values('USR_C','GEOLOGIST')";

    public static final String DELETE_USER_ROLES_TABLE = "delete from " + USER_ROLES_DATABASE_TABLE;

}
