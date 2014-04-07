package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class TunnelFaceTable extends SkavaEntityTable {

    public static final String FACE_DATABASE_TABLE = "Faces";

    //The name and column index of each column in your database.
    public static final String TUNNEL_CODE_COLUMN =
            "TUNNEL_CODE";

    public static final String ORIENTATION_COLUMN =
            "ORIENTATION";

    public static final String SLOPE_COLUMN =
            "SLOPE";

    public static final String CREATE_FACES_TABLE = "create table " +
            FACE_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            TUNNEL_CODE_COLUMN + " text not null, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null, " +
            ORIENTATION_COLUMN + " integer not null, " +
            SLOPE_COLUMN + " integer not null " + ");";

    public static final String INSERT_FACES_TABLE = "insert into " + FACE_DATABASE_TABLE + "(" +  TUNNEL_CODE_COLUMN +  "," +  CODE_COLUMN + "," + NAME_COLUMN + "," + ORIENTATION_COLUMN + "," + SLOPE_COLUMN + ") values('TNL_A', 'TFC_1','Face One',10,20)";
    public static final String INSERT_FACES_TABLE_SECOND = "insert into " + FACE_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + CODE_COLUMN + "," + NAME_COLUMN + "," + ORIENTATION_COLUMN +"," + SLOPE_COLUMN + ") values('TNL_A','TFC_2','Face Two',30,40)";
    public static final String INSERT_FACES_TABLE_THIRD = "insert into " + FACE_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + CODE_COLUMN + "," + NAME_COLUMN + "," + ORIENTATION_COLUMN + "," + SLOPE_COLUMN + ") values('TNL_B','TFC_3','Face Three',50,60)";
    public static final String INSERT_FACES_TABLE_FOURTH = "insert into " + FACE_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + CODE_COLUMN + "," + NAME_COLUMN + "," + ORIENTATION_COLUMN + "," + SLOPE_COLUMN + ") values('TNL_B','TFC_4','Face Four',20,30)";
    public static final String INSERT_FACES_TABLE_FIFTH = "insert into " + FACE_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + CODE_COLUMN + "," + NAME_COLUMN + "," + ORIENTATION_COLUMN +"," + SLOPE_COLUMN + ") values('TNL_C','TFC_5','Face Five',15,25)";
    public static final String INSERT_FACES_TABLE_SIXTH = "insert into " + FACE_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + CODE_COLUMN + "," + NAME_COLUMN + "," + ORIENTATION_COLUMN +"," + SLOPE_COLUMN + ") values('TNL_D','TFC_6','Face Six',25,35)";
    public static final String INSERT_FACES_TABLE_SEVETH = "insert into " + FACE_DATABASE_TABLE + "(" + TUNNEL_CODE_COLUMN +  "," + CODE_COLUMN + "," + NAME_COLUMN + "," + ORIENTATION_COLUMN +"," + SLOPE_COLUMN + ") values('TNL_D','TFC_7','Face Seven',60,70)";

    public static final String DELETE_FACES_TABLE = "delete from " + FACE_DATABASE_TABLE ;
}
