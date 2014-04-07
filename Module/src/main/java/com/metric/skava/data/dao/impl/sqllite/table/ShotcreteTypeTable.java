package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ShotcreteTypeTable extends SkavaEntityTable {

    public static final String SHOTCRETE_DATABASE_TABLE = "Shotcretes";

    public static final String CREATE_SHOTCRETES_TABLE = "create table " +
            SHOTCRETE_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_SHOTCRETES_TABLE = "insert into " + SHOTCRETE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SHT_A','Plain Shotcrete')";
    public static final String INSERT_SHOTCRETES_TABLE_SECOND = "insert into " + SHOTCRETE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SHT_B','Steel Fibre Reinforced Shotcrete')";
    public static final String INSERT_SHOTCRETES_TABLE_THIRD = "insert into " + SHOTCRETE_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('SHT_C','Plastic Fibre Reinforced Shotcrete')";

    public static final String DELETE_SHOTCRETES_TABLE = "delete from " + SHOTCRETE_DATABASE_TABLE ;


}
