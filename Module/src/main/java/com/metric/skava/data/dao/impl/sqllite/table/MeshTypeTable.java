package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class MeshTypeTable extends SkavaEntityTable {

    public static final String MESH_DATABASE_TABLE = "Meshes";

    public static final String CREATE_MESHES_TABLE = "create table " +
            MESH_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
            " integer primary key autoincrement, " +
            CODE_COLUMN + " text not null, " +
            NAME_COLUMN + " text not null  );";

    public static final String INSERT_MESHES_TABLE = "insert into " + MESH_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('MSH_A','Chain Linked Mesh')";
    public static final String INSERT_MESHES_TABLE_SECOND = "insert into " + MESH_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + ") values('MSH_B','Welded Wire Fabric')";

    public static final String DELETE_MESHES_TABLE  = "delete from " + MESH_DATABASE_TABLE ;


}
