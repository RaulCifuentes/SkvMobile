package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalMeshTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.MeshTypeTable;
import com.metric.skava.instructions.model.MeshType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class MeshTypeDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<MeshType> implements LocalMeshTypeDAO {

    public MeshTypeDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<MeshType> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<MeshType> list = new ArrayList<MeshType>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(MeshTypeTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(MeshTypeTable.NAME_COLUMN, cursor);
            MeshType newInstance = new MeshType(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public MeshType getMeshTypeByCode(String code) throws DAOException {
        MeshType entity = getIdentifiableEntityByCode(MeshTypeTable.MESH_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<MeshType> getAllMeshTypes() throws DAOException {
        List<MeshType> list = getAllPersistentEntities(MeshTypeTable.MESH_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveMeshType(MeshType newEntity) throws DAOException {
        savePersistentEntity(MeshTypeTable.MESH_DATABASE_TABLE, newEntity );
    }

    @Override
    protected void savePersistentEntity(String tableName, MeshType newSkavaEntity) throws DAOException {

    }

    @Override
    public boolean deleteMeshType(String code) {
        return false;
    }

    @Override
    public int deleteAllMeshTypes() {
        return 0;
    }


}
