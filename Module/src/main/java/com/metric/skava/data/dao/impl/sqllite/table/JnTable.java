package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class JnTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Jn";

    public static final String CREATE_Jn_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_Jn_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null,'A', 'Keywords A', 'Massive, no or few joints', 1 )";
    public static final String INSERT_Jn_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null,'B', 'Keywords B', 'One joint set.', 2 )";
    public static final String INSERT_Jn_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null,'C', 'Keywords C', 'One joint set plus random joints.', 3 )";
    public static final String INSERT_Jn_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null,'D', 'Keywords D', 'Two joint sets.', 4 )";
    public static final String INSERT_Jn_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null,'E', 'Keywords E', 'Two joint sets plus random joints.', 6 )";
    public static final String INSERT_Jn_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null, 'F', 'Keywords F', 'Three joint sets.', 9 )";
    public static final String INSERT_Jn_TABLE_SEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null, 'G', 'Keywords F', 'Three joint sets plus random joints.', 12 )";
    public static final String INSERT_Jn_TABLE_EIGHTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null, 'H', 'Keywords F', 'Four or more joints sets, random heavily jointed ''sugar cube'', etc.', 15 )";
    public static final String INSERT_Jn_TABLE_NINETH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jn', null, 'J', 'Keywords F', 'Crushed rock, earth like.', 20 )";

    public static final String DELETE_Jn_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;



}
