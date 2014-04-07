package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class JwTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Jw";

    public static final String CREATE_Jw_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_Jw_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jw', null,'A', 'Keywords A', 'Dry excavations or minor inflow (humid or a few drips)', 1 )";
    public static final String INSERT_Jw_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jw', null,'B', 'Keywords B', 'Medium indflow, ocassional outwash of joint filling (many drips/''rain'')', 0.66 )";
    public static final String INSERT_Jw_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jw', null,'C', 'Keywords C', 'Jet inflow or high pressure in competent rock with unfilled joints.', 0.5 )";
    public static final String INSERT_Jw_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jw', null,'D', 'Keywords D', 'Large inflow or high pressure, considerable outwash of joint fillings.', 0.33 )";
    public static final String INSERT_Jw_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jw', null,'E', 'Keywords E', 'Exceptionally high inflow or water pressure decaying with time. Causes outwash of material and perhaps cave in.', 0.15 )";
    public static final String INSERT_Jw_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Jw', null, 'F', 'Keywords F', 'Exceptionally high inflow or water pressure continuing without noticeable decay. Causes outwash of material and perhaps cave in.', 0.075 )";


    public static final String DELETE_Jw_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
