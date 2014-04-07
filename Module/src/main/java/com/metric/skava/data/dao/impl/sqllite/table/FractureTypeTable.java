package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class FractureTypeTable extends SkavaEntityTable {

    public static final String FRACTURE_DATABASE_TABLE = "Fractures";

    public static final String CREATE_FRACTURES_TABLE = "create table " +
            FRACTURE_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_FRACTURES_TABLE = "insert into " + FRACTURE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('FRC_A','Massive')";
    public static final String INSERT_FRACTURES_TABLE_SECOND = "insert into " + FRACTURE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('FRC_B','Blocks')";
    public static final String INSERT_FRACTURES_TABLE_THIRD = "insert into " + FRACTURE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('FRC_C','Tabular')";
    public static final String INSERT_FRACTURES_TABLE_FOURTH = "insert into " + FRACTURE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('FRC_D','Columnar')";
    public static final String INSERT_FRACTURES_TABLE_FIFTH = "insert into " + FRACTURE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('FRC_E','Irregular')";
    public static final String INSERT_FRACTURES_TABLE_SIXTH = "insert into " + FRACTURE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('FRC_F','Crushed')";


    public static final String DELETE_FRACTURES_TABLE = "delete from " + FRACTURE_DATABASE_TABLE;

}
