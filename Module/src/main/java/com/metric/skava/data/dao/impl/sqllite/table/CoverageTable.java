package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class CoverageTable extends SkavaEntityTable {

    public static final String COVERAGE_DATABASE_TABLE = "Coverages";

    public static final String CREATE_COVERAGES_TABLE = "create table " +
            COVERAGE_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_COVERAGES_TABLE = "insert into " + COVERAGE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('CVR_A','Complete')";
    public static final String INSERT_COVERAGES_TABLE_SECOND = "insert into " + COVERAGE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('CVR_B','Right Side')";
    public static final String INSERT_COVERAGES_TABLE_THIRD = "insert into " + COVERAGE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('CVR_C','Left Side')";
    public static final String INSERT_COVERAGES_TABLE_FOURTH = "insert into " + COVERAGE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('CVR_D','Roof')";

    public static final String DELETE_COVERAGES_TABLE = "delete from " + COVERAGE_DATABASE_TABLE ;


}
