package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class JrTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Jr";

    public static final String CREATE_Jr_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_Jr_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jr', 'a','A', 'Discontinuous joints.', 'Discontinuous joints.', 4 )";
    public static final String INSERT_Jr_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jr', 'a','B', 'Rough or irregular, undulating.', 'Rough or irregular, undulating.', 3 )";
    public static final String INSERT_Jr_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jr', 'a','C', 'Smooth, undulating.', 'Smooth, undulating.', 2 )";
    public static final String INSERT_Jr_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jr', 'a','D', 'Slickensided, undulating', 'Slickensided, undulating.', 1.5 )";
    public static final String INSERT_Jr_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jr', 'a','E', 'Rough, irregular, planar.', 'Rough, irregular, planar.', 1.5 )";
    public static final String INSERT_Jr_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jr', 'a', 'F', 'Smooth, planar.', 'Smooth, planar.', 1 )";
    public static final String INSERT_Jr_TABLE_SEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'a','G', 'Slickensided, planar.', 'Slickensided, planar.', 0.5 )";

    public static final String INSERT_Jr_TABLE_EIGHTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'b','A', 'Discontinuous joints.', 'Discontinuous joints.', 4 )";
    public static final String INSERT_Jr_TABLE_NINETH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'b','B', 'Rough or irregular, undulating.', 'Rough or irregular, undulating.', 3 )";
    public static final String INSERT_Jr_TABLE_TENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'b','C', 'Smooth, undulating.', 'Smooth, undulating.', 2 )";
    public static final String INSERT_Jr_TABLE_ELEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'b','D', 'Slickensided, undulating', 'Slickensided, undulating.', 1.5 )";
    public static final String INSERT_Jr_TABLE_TWELFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'b','E', 'Rough, irregular, planar.', 'Rough, irregular, planar.', 1.5 )";
    public static final String INSERT_Jr_TABLE_THIRDTEENH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'b','F', 'Smooth, planar.', 'Smooth, planar.', 1 )";
    public static final String INSERT_Jr_TABLE_FOURTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'b','G', 'Slickensided, planar.', 'Slickensided, planar.', 0.5 )";

    public static final String INSERT_Jr_TABLE_FIFTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Jr', 'c','H', 'Keywords H', 'Zone containing clay minerals thick enough to prevent rock-wall contact when sheared.', 1 )";

    public static final String DELETE_Jr_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
