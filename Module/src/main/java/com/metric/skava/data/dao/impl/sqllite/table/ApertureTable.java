package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ApertureTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Apertures";

    public static final String CREATE_APERTURE_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_APERTURE_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Aperture', null,'A', 'None', 'None', 6 )";
    public static final String INSERT_APERTURE_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Aperture', null,'B', '< 0.1 mm', '< 0.1 mm', 4 )";
    public static final String INSERT_APERTURE_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Aperture', null,'C', '0.1 - 1 mm', '0.1 - 1 mm', 2 )";
    public static final String INSERT_APERTURE_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Aperture', null,'D', '1 - 5 mm', '1 - 5 mm', 1 )";
    public static final String INSERT_APERTURE_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Aperture', null,'E', '> 5 mm', '> 5 mm', 0 )";


    public static final String DELETE_APERTURE_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
