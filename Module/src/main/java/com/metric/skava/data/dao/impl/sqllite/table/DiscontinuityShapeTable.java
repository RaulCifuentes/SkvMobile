package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityShapeTable extends SkavaEntityTable {

    public static final String SHAPE_DATABASE_TABLE = "DiscontinuityShapes";

    public static final String CREATE_SHAPES_TABLE = "create table " +
            SHAPE_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_SHAPES_TABLE = "insert into " + SHAPE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SHP_A','Stepped')";
    public static final String INSERT_SHAPES_TABLE_SECOND = "insert into " + SHAPE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SHP_B','Undulating')";
    public static final String INSERT_SHAPES_TABLE_THIRD = "insert into " + SHAPE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SHP_C','Planar')";


    public static final String DELETE_SHAPES_TABLE = "delete from " + SHAPE_DATABASE_TABLE;


}
