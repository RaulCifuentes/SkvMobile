package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class RoughnessTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Roughnesses";

    public static final String CREATE_ROUGHNESS_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_ROUGHNESS_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Roughness', null,'A', 'Very rough', 'Very rough', 6 )";
    public static final String INSERT_ROUGHNESS_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Roughness', null,'B', 'Rough', 'Rough', 4 )";
    public static final String INSERT_ROUGHNESS_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Roughness', null,'C', 'Slightly rough', 'Slightly rough', 2 )";
    public static final String INSERT_ROUGHNESS_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Roughness', null,'D', 'Smooth', 'Smooth', 1 )";
    public static final String INSERT_ROUGHNESS_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Roughness', null,'E', 'Slickensided', 'Slickensided', 0 )";

    public static final String DELETE_ROUGHNESS_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
