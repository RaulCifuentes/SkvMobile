package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class UserTable extends SkavaEntityTable {

    public static final String USER_DATABASE_TABLE = "Users";

    public static final String EMAIL_COLUMN = "email";

    public static final String CREATE_USERS_TABLE = "create table " +
            USER_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null, " +
            EMAIL_COLUMN + " text null );";

    public static final String INSERT_USERS_TABLE = "insert into " + USER_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + EMAIL_COLUMN +  ") values('USR_A','User A','admin@skava.cl')";
    public static final String INSERT_USERS_TABLE_SECOND = "insert into " + USER_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + EMAIL_COLUMN + ") values('USR_B','User B','analyst@skava.cl')";
    public static final String INSERT_USERS_TABLE_THIRD = "insert into " + USER_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + EMAIL_COLUMN + ") values('USR_C','User C','geologist@skava.cl')";

    public static final String DELETE_USERS_TABLE = "delete from " + USER_DATABASE_TABLE;

}
