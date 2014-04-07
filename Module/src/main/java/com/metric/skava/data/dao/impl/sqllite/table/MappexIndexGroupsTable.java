package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class MappexIndexGroupsTable extends SkavaEntityTable {

    //The name and column index of each column in your database.
    //These should be descriptive.

    public static final String GROUPS_DATABASE_TABLE = "Groups";


    public static final String INDEX_CODE_COLUMN = "INDEX_CODE";

    public static final String CREATE_GROUP_TABLE = "create table " +
            GROUPS_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            INDEX_CODE_COLUMN + " text not null, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";


    public static final String INSERT_GROUPS_TABLE = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN + "," +  CODE_COLUMN + "," + NAME_COLUMN + ") values('ESR','i','Circular sections')";
    public static final String INSERT_GROUPS_TABLE_SECOND = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN + "," +  CODE_COLUMN + "," + NAME_COLUMN + ") values('ESR','ii','Rectangular sections')";


    public static final String INSERT_GROUPS_TABLE_THIRD = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('SRF','a','Weak zones intersecting the underground opening, which may cause loosening of rock mass.')";
    public static final String INSERT_GROUPS_TABLE_FOURTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('SRF','b','Competent, mainly massive rock, stress problems.')";
    public static final String INSERT_GROUPS_TABLE_FIFTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('SRF','c','Squeezing rock: plastic deformation in incompetent rock under the influence of high pressure.')";
    public static final String INSERT_GROUPS_TABLE_SIXTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('SRF','d','Swelling rock: chemical swelling activity depending on the presence of water.')";

    public static final String INSERT_GROUPS_TABLE_SEVENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Ja','a','Rock-wall contact (no mineral fillings, only coatings)')";
    public static final String INSERT_GROUPS_TABLE_EIGTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Ja','b','Rock-wall contact before 10 cm shear (thin mineral fillings)')";
    public static final String INSERT_GROUPS_TABLE_NINETH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Ja','c','No rock-wall contact when sheared (thick mineral fillings)')";

    public static final String INSERT_GROUPS_TABLE_TENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Jr','a','Rock-wall contact')";
    public static final String INSERT_GROUPS_TABLE_ELEVENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Jr','b','Rock-wall contact before 10 cm of shear movement')";
    public static final String INSERT_GROUPS_TABLE_TWELFTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Jr','c','No rock-wall contact when sheared')";

    public static final String INSERT_GROUPS_TABLE_THIRDTEENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Strength','Point-load','Point-load strength index')";
    public static final String INSERT_GROUPS_TABLE_FORTHTEENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Strength','UCS','Uniaxial compression strenght')";

    public static final String INSERT_GROUPS_TABLE_FIFTEENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Groundwater','Length','Inflow per 10m tunnel lenght')";
    public static final String INSERT_GROUPS_TABLE_SIXTEENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Groundwater','Press','Joint water press/Major principal')";
    public static final String INSERT_GROUPS_TABLE_SEVENTEENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Groundwater','Conditions','General Conditions')";

    public static final String INSERT_GROUPS_TABLE_EIGHTEENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Orientation','TunnelsAndMines','Tunnels & Mines')";
    public static final String INSERT_GROUPS_TABLE_NINETEENTH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Orientation','Foundations','Foundations')";
    public static final String INSERT_GROUPS_TABLE_TWENTIETH = "insert into " + GROUPS_DATABASE_TABLE + "(" + INDEX_CODE_COLUMN  + "," + CODE_COLUMN + "," + NAME_COLUMN + ") values('Orientation','Slopes','Slopes')";

    public static final String DELETE_GROUPS_TABLE = "delete from " + GROUPS_DATABASE_TABLE;

}
