package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalArchTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ArchTypeTable;
import com.metric.skava.instructions.model.ArchType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class ArchTypeDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<ArchType> implements LocalArchTypeDAO {

    public ArchTypeDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<ArchType> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<ArchType> list = new ArrayList<ArchType>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(ArchTypeTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(ArchTypeTable.NAME_COLUMN, cursor);
            ArchType newInstance = new ArchType(code, name);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public ArchType getArchTypeByCode(String code) throws DAOException {
        if (code != null) {
            ArchType entity = getIdentifiableEntityByCode(ArchTypeTable.ARCH_DATABASE_TABLE, code);
            return entity;
        }
        return null;
    }

    @Override
    public List<ArchType> getAllArchTypes() throws DAOException {
        List<ArchType> list = getAllPersistentEntities(ArchTypeTable.ARCH_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveArchType(ArchType newEntity) throws DAOException {
        savePersistentEntity(ArchTypeTable.ARCH_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, ArchType newSkavaEntity) throws DAOException {
        saveSkavaEntity(tableName, newSkavaEntity);
    }

    @Override
    public boolean deleteArchType(String code) {
        return deleteIdentifiableEntity(ArchTypeTable.ARCH_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllArchTypes() {
        return deleteAllPersistentEntities(ArchTypeTable.ARCH_DATABASE_TABLE);
    }

}
