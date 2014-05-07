package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class RockQualityTable extends SkavaEntityTable {

    public static final String ROCK_QUALITY_DATABASE_TABLE = "RockQualities";

    public static final String LOWERBOUND_COLUMN = "LOWER_BOUND";
    public static final String UPPERBOUND_COLUMN = "UPPER_BOUND";
    public static final String ACCORDING_TO_COLUMN = "ACCORDING_TO";
    public static final String CLASSIFICATION_COLUMN = "CLASSIFICATION";


    public static final String CREATE_ROCK_QUALITIES_TABLE = "create table " +
            ROCK_QUALITY_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null, " +
            LOWERBOUND_COLUMN + " real not null, " +
            UPPERBOUND_COLUMN + " real not null, " +
            ACCORDING_TO_COLUMN + " text not null, " +
            CLASSIFICATION_COLUMN + " text null " + ");";


//    public static final String INSERT_ROCK_QUALITIES_TABLE = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('EXCEPTIONALLY_GOOD','Exceptionally good')";
//    public static final String INSERT_ROCK_QUALITIES_TABLE_SECOND = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('EXTREMELY_GOOD','Extremely good')";
//    public static final String INSERT_ROCK_QUALITIES_TABLE_THIRD = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('VERY_GOOD','Very good')";
//    public static final String INSERT_ROCK_QUALITIES_TABLE_FOURTH = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('GOOD','Good')";
//    public static final String INSERT_ROCK_QUALITIES_TABLE_FIFTH = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('FAIR','Fair')";
//    public static final String INSERT_ROCK_QUALITIES_TABLE_SIXTH = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('POOR','Poor')";
//    public static final String INSERT_ROCK_QUALITIES_TABLE_SEVENTH = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('VERY_POOR','Very poor')";
//    public static final String INSERT_ROCK_QUALITIES_TABLE_EIGHTH = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('EXTREMELY_POOR','Extremely poor')";
//    public static final String INSERT_ROCK_QUALITIES_TABLE_NINETH = "insert into " + ROCK_QUALITY_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('EXCEPTIONALLY_POOR','Exceptionally poor')";
//

    public static final String DELETE_ROCK_QUALITIES_TABLE = "delete from " + ROCK_QUALITY_DATABASE_TABLE;


}
