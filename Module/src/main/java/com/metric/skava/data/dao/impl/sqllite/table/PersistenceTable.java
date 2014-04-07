package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class PersistenceTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Persistences";

    public static final String CREATE_PERSISTENCE_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_PERSISTENCE_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Persistence', null,'A', '< 1 m', '< 1 m', 6 )";
    public static final String INSERT_PERSISTENCE_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Persistence', null,'B', ' 1 - 3 m', ' 1 - 3 m', 4 )";
    public static final String INSERT_PERSISTENCE_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Persistence', null,'C', '3 - 10 m', '3 - 10 m', 2 )";
    public static final String INSERT_PERSISTENCE_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Persistence', null,'D', '10 - 20 m', '10 - 20 m', 1 )";
    public static final String INSERT_PERSISTENCE_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Persistence', null,'E', '> 20 m', '> 20 m', 0 )";

    public static final String DELETE_PERSISTENCE_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
