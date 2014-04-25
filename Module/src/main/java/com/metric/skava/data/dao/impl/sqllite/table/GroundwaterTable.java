package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class GroundwaterTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Groundwaters";

    public static final String CREATE_GROUNDWATER_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_GROUNDWATER_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Groundwater', 'Lenght','A', 'None', 'None', 15 )";
    public static final String INSERT_GROUNDWATER_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Groundwater', 'Lenght','B', '< 10', '< 10', 10 )";
    public static final String INSERT_GROUNDWATER_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Groundwater', 'Lenght','C', '10-25', '10-25', 7 )";
    public static final String INSERT_GROUNDWATER_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Groundwater', 'Lenght','D', '25 - 125', '25 - 125', 4 )";
    public static final String INSERT_GROUNDWATER_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Groundwater', 'Lenght','E', '> 125', '> 125', 0 )";

    public static final String INSERT_GROUNDWATER_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Groundwater', 'Press', 'A', '0', '0', 15 )";
    public static final String INSERT_GROUNDWATER_TABLE_SEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Groundwater', 'Press','B', '< 0.1', '< 0.1', 10 )";
    public static final String INSERT_GROUNDWATER_TABLE_EIGHTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Groundwater', 'Press','C', '0.1 - 0.2', '0.1 - 0.2', 7 )";
    public static final String INSERT_GROUNDWATER_TABLE_NINETH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Groundwater', 'Press','D', '0.2 - 0.5', '0.2 - 0.5', 4 )";
    public static final String INSERT_GROUNDWATER_TABLE_TENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Groundwater', 'Press','E', '> 0.5', ''> 0.5', 0 )";


    public static final String INSERT_GROUNDWATER_TABLE_ELEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Groundwater', 'Conditions', 'A', 'Completely Dry', 'Completely Dry', 15 )";
    public static final String INSERT_GROUNDWATER_TABLE_TWELFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Groundwater', 'Conditions','B', 'Damp', 'Damp', 10 )";
    public static final String INSERT_GROUNDWATER_TABLE_THIRDTEENH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Groundwater', 'Conditions','C', 'Wet', 'Wet', 7 )";
    public static final String INSERT_GROUNDWATER_TABLE_FOURTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Groundwater', 'Conditions','D', 'Dripping', 'Dripping', 4 )";
    public static final String INSERT_GROUNDWATER_TABLE_FIFTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Groundwater', 'Conditions','E', 'Flowing', 'Flowing', 0 )";


    public static final String DELETE_GROUNDWATER_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
