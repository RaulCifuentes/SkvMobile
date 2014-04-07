package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class WeatheringTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Weatherings";

    public static final String CREATE_WEATHERING_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_WEATHERING_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Weathering', null,'A', 'Unweathered', 'Unweathered', 6 )";
    public static final String INSERT_WEATHERING_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Weathering', null,'B', 'Slightly', 'Slightly', 4 )";
    public static final String INSERT_WEATHERING_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Weathering', null,'C', 'Moderately', 'Moderately', 2 )";
    public static final String INSERT_WEATHERING_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Weathering', null,'D', 'Highly', 'Highly', 1 )";
    public static final String INSERT_WEATHERING_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Weathering', null,'E', 'Decomposed', 'Decomposed', 0 )";

    public static final String DELETE_WEATHERING_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;


}
