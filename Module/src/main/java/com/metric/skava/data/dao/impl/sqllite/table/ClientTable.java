package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ClientTable extends SkavaEntityTable {

    public static final String CLIENT_DATABASE_TABLE = "Clients";

    public static final String CREATE_CLIENTS_TABLE = "create table " +
            CLIENT_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_CLIENTS_TABLE = "insert into " + CLIENT_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('CLN_A','Client A')";
    public static final String INSERT_CLIENTS_TABLE_SECOND = "insert into " + CLIENT_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('CLN_B','Client B')";

    public static final String DELETE_CLIENTS_TABLE  = "delete from " + CLIENT_DATABASE_TABLE ;


}
