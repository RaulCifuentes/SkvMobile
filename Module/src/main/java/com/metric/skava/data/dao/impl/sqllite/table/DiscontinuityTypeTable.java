package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityTypeTable extends SkavaEntityTable {

    public static final String DISCONTINUITY_DATABASE_TABLE = "Discontinuities";

    public static final String CREATE_DISCONTINUITIES_TABLE = "create table " +
            DISCONTINUITY_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_DISCONTINUITIES_TABLE = "insert into " + DISCONTINUITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('DSC_A','Fault')";
    public static final String INSERT_DISCONTINUITIES_TABLE_SECOND = "insert into " + DISCONTINUITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('DSC_B','Joint')";
    public static final String INSERT_DISCONTINUITIES_TABLE_THIRD = "insert into " + DISCONTINUITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('DSC_C','Stratification')";
    public static final String INSERT_DISCONTINUITIES_TABLE_FOURTH = "insert into " + DISCONTINUITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('DSC_C','Foliation')";

    public static final String DELETE_DISCONTINUITIES_TABLE = "delete from " + DISCONTINUITY_DATABASE_TABLE ;

}
