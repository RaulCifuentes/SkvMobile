package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class SpacingTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Spacings";

    public static final String CREATE_SPACING_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;


    public static final String INSERT_SPACING_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Spacing', null, 'A', '> 2 m', '> 2 m', 20 )";
    public static final String INSERT_SPACING_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Spacing', null, 'B', '0.6 - 2 m', '0.6 - 2 m', 15 )";
    public static final String INSERT_SPACING_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Spacing', null, 'C', '200 - 600 mm', '200 - 600 mm', 10 )";
    public static final String INSERT_SPACING_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Spacing', null, 'D', '60 - 200 mm', '60 - 200 mm', 8 )";
    public static final String INSERT_SPACING_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Spacing', null, 'E', '< 60 mm', '< 60 mm', 5 )";



}

