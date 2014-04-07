package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class InfillingTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Infillings";

    public static final String CREATE_INFILLING_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_INFILLING_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Infilling', null,'A', 'None', 'None', 6 )";
    public static final String INSERT_INFILLING_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Infilling', null,'B', 'Hard filling < 5 mm', 'Hard filling < 5 mm', 4 )";
    public static final String INSERT_INFILLING_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Infilling', null,'C', 'Hard filling > 5 mm', 'Hard filling > 5 mm', 2 )";
    public static final String INSERT_INFILLING_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Infilling', null,'D', 'Soft filling < 5 mm', 'Soft filling < 5 mm', 1 )";
    public static final String INSERT_INFILLING_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Infilling', null,'E', 'Soft filling > 5 mm', 'Soft filling > 5 mm', 0 )";


    public static final String DELETE_INFILLING_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;




}
