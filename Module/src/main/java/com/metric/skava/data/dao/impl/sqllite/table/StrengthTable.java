package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class StrengthTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Strengths";

    public static final String CREATE_STRENGTH_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_STRENGTH_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Strength', 'Point-load','A', '> 10 MPa', '> 10 MPa', 15 )";
    public static final String INSERT_STRENGTH_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Strength', 'Point-load','B', '4 - 10 MPa', '4 - 10 MPa', 12 )";
    public static final String INSERT_STRENGTH_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Strength', 'Point-load','C', '2 - 4 MPa', '2 - 4 MPa', 7 )";
    public static final String INSERT_STRENGTH_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Strength', 'Point-load','D', '1 - 2 MPa', '1 - 2 MPa', 4 )";

    public static final String INSERT_STRENGTH_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Strength', 'UCS','E', '> 250 MPa', '> 250 MPa', 15 )";
    public static final String INSERT_STRENGTH_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Strength', 'UCS', 'A', '100 - 250 MPa ', '100 - 250 MPa', 12 )";
    public static final String INSERT_STRENGTH_TABLE_SEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Strength', 'UCS','B', '50 - 100 MPa', '50 - 100 MPa', 7 )";
    public static final String INSERT_STRENGTH_TABLE_EIGHTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Strength', 'UCS','C', '25 - 50 MPa', '25 - 50 MPa', 4 )";
    public static final String INSERT_STRENGTH_TABLE_NINETH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Strength', 'UCS','D', '5 - 25 MPa', '5 - 25 MPa', 2 )";
    public static final String INSERT_STRENGTH_TABLE_TENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Strength', 'UCS','E', '1 - 5 MPa', '1 - 5 MPa', 1 )";
    public static final String INSERT_STRENGTH_TABLE_ELEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Strength', 'UCS', 'A', '< 1 MPa', '< 1 MPa', 0 )";


    public static final String DELETE_STRENGTH_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
