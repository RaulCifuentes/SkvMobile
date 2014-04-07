package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ExcavationSectionTable extends SkavaEntityTable {

    public static final String SECTION_DATABASE_TABLE = "Sections";

//    public static final String TUNNEL_FACE_CODE_COLUMN =
//            "FACE_CODE";

    public static final String CREATE_SECTIONS_TABLE = "create table " +
            SECTION_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
//            TUNNEL_FACE_CODE_COLUMN + " text not null, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_SECTIONS_TABLE = "insert into " + SECTION_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SCT_A','Complete')";
    public static final String INSERT_SECTIONS_TABLE_SECOND = "insert into " + SECTION_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SCT_B','Bottom')";
    public static final String INSERT_SECTIONS_TABLE_THIRD = "insert into " + SECTION_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN  + ") values('SCT_C','Right Side')";
    public static final String INSERT_SECTIONS_TABLE_FOURTH = "insert into " + SECTION_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SCT_D','Left Side')";
    public static final String INSERT_SECTIONS_TABLE_FIFTH = "insert into " + SECTION_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SCT_E','Roof')";
    public static final String INSERT_SECTIONS_TABLE_SIXTH = "insert into " + SECTION_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SCT_F','Center')";

    public static final String DELETE_SECTIONS_TABLE = "delete from " + SECTION_DATABASE_TABLE;

}
