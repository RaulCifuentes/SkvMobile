package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class MappedIndexTable extends SkavaEntityTable {

    //The name and column index of each column in your database.
    //These should be descriptive.

    public static final String INDEX_DATABASE_TABLE = "Indexes";

    public static final String CREATE_INDEXES_TABLE = "create table " +
            INDEX_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_INDEXES_TABLE = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('RQD','Rock Quality Designation')";
    public static final String INSERT_INDEXES_TABLE_SECOND = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Jn','Joint Set Number')";
    public static final String INSERT_INDEXES_TABLE_THIRD = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Jr','Joint Roughness Number')";
    public static final String INSERT_INDEXES_TABLE_FOURTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Ja','Joint Alteration Number')";
    public static final String INSERT_INDEXES_TABLE_FIFTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Jw','Joint Water Reduction')";
    public static final String INSERT_INDEXES_TABLE_SIXTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SRF','Stress Reduction Factor')";

    public static final String INSERT_INDEXES_TABLE_SEVENTTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Strenght','Strenght of intact rock material')";
    public static final String INSERT_INDEXES_TABLE_EIGTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Spacing','Spacing of Discontinuities')";
    public static final String INSERT_INDEXES_TABLE_NINETH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Condition','Condition of Discontinuities')";
    public static final String INSERT_INDEXES_TABLE_TENTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Aperture','Condition of Discontinuities')";
    public static final String INSERT_INDEXES_TABLE_ELEVENTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Persistence','Condition of Discontinuities')";
    public static final String INSERT_INDEXES_TABLE_TWELFTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Roughness','Condition of Discontinuities')";
    public static final String INSERT_INDEXES_TABLE_THIRDTEENTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Infilling','Condition of Discontinuities')";
    public static final String INSERT_INDEXES_TABLE_FOURTEENTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Weathering','Condition of Discontinuities')";
    public static final String INSERT_INDEXES_TABLE_FIFTEENTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Groundwater','Groundwater')";
    public static final String INSERT_INDEXES_TABLE_SIXTEENTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('Orientation','Orientation')";
    public static final String INSERT_INDEXES_TABLE_SEVENTEENTH = "insert into " + INDEX_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('ESR','Excavation Support Ratio')";


    public static final String DELETE_INDEXES_TABLE = "delete from " + INDEX_DATABASE_TABLE;

}
