package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ConditionTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Conditions";

    public static final String CREATE_CONDITION_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;


    public static final String INSERT_CONDITION_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Condition', null, 'A', 'A', 'Rough-Unweathered', 'Very rough surfaces. Not continuous. No separation. Unweathered wall rock', 30 )";
    public static final String INSERT_CONDITION_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Condition', null, 'B','B', 'Rough-Weathered', 'Rough surfaces. Separation < 1 mm. Slightly weathered walls', 10 )";
    public static final String INSERT_CONDITION_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Condition', null, 'C','C', 'Weathered', 'Slightly rough surfaces. Separation < 1 mm. Highly weathered walls', 7 )";
    public static final String INSERT_CONDITION_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Condition', null, 'D','D', 'Slicken-Continous', 'Slickensided surfaces or Gouge < 5 mm thick or Separation 1-5 mm. Continuous', 4 )";
    public static final String INSERT_CONDITION_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Condition', null, 'E','E', 'Soft-Continous', 'Soft gouge >5 mm thick or Separation > 5 mm. Continuous', 0 )";


}

