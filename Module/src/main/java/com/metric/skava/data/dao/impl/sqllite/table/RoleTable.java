package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class RoleTable extends SkavaEntityTable {

    public static final String ROLE_DATABASE_TABLE = "Role";

    public static final String CREATE_ROLES_TABLE = "create table " +
            ROLE_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_ROLES_TABLE = "insert into " + ROLE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('ADMINISTRATOR','Administrator')";
    public static final String INSERT_ROLES_TABLE_SECOND = "insert into " + ROLE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('GEOLOGIST','Geologist')";
    public static final String INSERT_ROLES_TABLE_THIRD = "insert into " + ROLE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('ANALYST','Analyst')";


    public static final String DELETE_ROLES_TABLE = "delete from " + ROLE_DATABASE_TABLE;


}
