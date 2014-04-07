package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/12/14.
 */
public class TunnelTable extends SkavaEntityTable {

    public static final String TUNNEL_DATABASE_TABLE = "Tunnels";

    //The name and column index of each column in your database.
    public static final String PROJECT_CODE_COLUMN =
            "PROJECT_CODE";

    public static final String CREATE_TUNNELS_TABLE = "create table " +
            TUNNEL_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            PROJECT_CODE_COLUMN + " text not null, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_TUNNELS_TABLE = "insert into " + TUNNEL_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + PROJECT_CODE_COLUMN + ") values('TNL_A','Tunnel A', 'PRJ_A')";
    public static final String INSERT_TUNNELS_TABLE_SECOND = "insert into " + TUNNEL_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + PROJECT_CODE_COLUMN + ") values('TNL_B','Tunnel B', 'PRJ_A')";
    public static final String INSERT_TUNNELS_TABLE_THIRD = "insert into " + TUNNEL_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + PROJECT_CODE_COLUMN + ") values('TNL_C','Tunnel C', 'PRJ_A')";
    public static final String INSERT_TUNNELS_TABLE_FOURTH = "insert into " + TUNNEL_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + PROJECT_CODE_COLUMN + ") values('TNL_D','Tunnel D', 'PRJ_B')";
    public static final String INSERT_TUNNELS_TABLE_FIFTH = "insert into " + TUNNEL_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + PROJECT_CODE_COLUMN + ") values('TNL_E','Tunnel E', 'PRJ_B')";

    public static final String DELETE_TUNNELS_TABLE = "delete from " + TUNNEL_DATABASE_TABLE;
}
