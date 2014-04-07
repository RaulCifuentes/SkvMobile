package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ArchTypeTable extends SkavaEntityTable {

    public static final String ARCH_DATABASE_TABLE = "Archs";

    public static final String CREATE_ARCHS_TABLE = "create table " +
            ARCH_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_ARCHS_TABLE = "insert into " + ARCH_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('ARC_A','SRR')";
    public static final String INSERT_ARCHS_TABLE_SECOND = "insert into " + ARCH_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('ARC_B','Frame')";
    public static final String INSERT_ARCHS_TABLE_THIRD = "insert into " + ARCH_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('ARC_C','Steel')";

    public static final String DELETE_ARCHS_TABLE = "delete from " + ARCH_DATABASE_TABLE;


}
