package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

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

    public ArchTypeDAOsqlLiteImpl(Context context) {
        super(context);
    }


    @Override
    protected List<ArchType> assamblePersistentEntities(Cursor cursor) throws DAOException {
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
        ArchType entity = getIdentifiableEntityByCode(ArchTypeTable.ARCH_DATABASE_TABLE, code);
        return entity;
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
    public boolean deleteArchType(String code) {
        return false;
    }

    @Override
    public int deleteAllArchTypes() {
        return 0;
    }

    @Override
    protected void savePersistentEntity(String tableName, ArchType newSkavaEntity) throws DAOException {
        saveSkavaEntity(tableName, newSkavaEntity);
    }

}
