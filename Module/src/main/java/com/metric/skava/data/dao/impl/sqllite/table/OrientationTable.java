package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class OrientationTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Orientations";

    public static final String CREATE_ORIENTATION_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_ORIENTATION_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Orientation', 'TunnelsAndMines','A', 'Very favourable', 'Very favourable', 0 )";
    public static final String INSERT_ORIENTATION_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Orientation', 'TunnelsAndMines','B', 'Favourable', 'Favourable', -2 )";
    public static final String INSERT_ORIENTATION_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Orientation', 'TunnelsAndMines','C', 'Fair', 'Fair', -5 )";
    public static final String INSERT_ORIENTATION_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Orientation', 'TunnelsAndMines','D', 'Unfavourable', 'Unfavourable', -10 )";
    public static final String INSERT_ORIENTATION_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Orientation', 'TunnelsAndMines','E', 'Very unfavourable', 'Very unfavourable', -12 )";


    public static final String INSERT_ORIENTATION_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Orientation', 'Foundations', 'A', 'Very favourable', 'Very favourable', 0 )";
    public static final String INSERT_ORIENTATION_TABLE_SEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Orientation', 'Foundations','B', 'Favourable', 'Favourable', -2 )";
    public static final String INSERT_ORIENTATION_TABLE_EIGHTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Orientation', 'Foundations','C', 'Fair', 'Fair', -7 )";
    public static final String INSERT_ORIENTATION_TABLE_NINETH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Orientation', 'Foundations','D', 'Unfavourable', 'Unfavourable', -15 )";
    public static final String INSERT_ORIENTATION_TABLE_TENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Orientation', 'Foundations','E', 'Very unfavourable', 'Very unfavourable', -25 )";


    public static final String INSERT_ORIENTATION_TABLE_ELEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Orientation', 'Slopes', 'A', 'Very favourable', 'Very favourable', 0 )";
    public static final String INSERT_ORIENTATION_TABLE_TWELFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Orientation', 'Slopes','B', 'Favourable', 'Favourable', -5 )";
    public static final String INSERT_ORIENTATION_TABLE_THIRDTEENH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Orientation', 'Slopes','C', 'Fair', 'Fair', -25 )";
    public static final String INSERT_ORIENTATION_TABLE_FOURTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Orientation', 'Slopes','D', 'Unfavourable', 'Unfavourable', -50 )";


    public static final String DELETE_ORIENTATION_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;


}
