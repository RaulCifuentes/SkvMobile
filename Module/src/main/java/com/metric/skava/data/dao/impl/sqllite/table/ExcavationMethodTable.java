package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ExcavationMethodTable extends SkavaEntityTable {

    public static final String METHOD_DATABASE_TABLE = "Methods";


    public static final String CREATE_METHODS_TABLE = "create table " +
            METHOD_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            SkavaEntityTable.CODE_COLUMN + " text not null, " +
            SkavaEntityTable.NAME_COLUMN + " text not null  );";

    public static final String INSERT_METHODS_TABLE = "insert into " + METHOD_DATABASE_TABLE + "(" + CODE_COLUMN + ","
            + NAME_COLUMN + ") values('MTH_A','Drill & Blast')";

    public static final String INSERT_METHODS_TABLE_SECOND = "insert into " + METHOD_DATABASE_TABLE + "(" + CODE_COLUMN + ","
            + NAME_COLUMN + ") values('MTH_B','TBM')";

    public static final String INSERT_METHODS_TABLE_THIRD = "insert into " + METHOD_DATABASE_TABLE + "(" + CODE_COLUMN + ","
            + NAME_COLUMN + ") values('MTH_C','Excavation')";

    public static final String INSERT_METHODS_TABLE_FOURTH = "insert into " + METHOD_DATABASE_TABLE + "(" + CODE_COLUMN + ","
            + NAME_COLUMN + ") values('MTH_D','Partial Blast')";


    public static final String DELETE_METHODS_TABLE = "delete from " + METHOD_DATABASE_TABLE ;

}
