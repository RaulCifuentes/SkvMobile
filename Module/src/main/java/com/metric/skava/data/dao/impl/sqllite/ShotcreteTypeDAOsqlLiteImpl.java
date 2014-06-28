package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalShotcreteTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ShotcreteTypeTable;
import com.metric.skava.instructions.model.ShotcreteType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class ShotcreteTypeDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<ShotcreteType> implements LocalShotcreteTypeDAO {

    public ShotcreteTypeDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<ShotcreteType> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<ShotcreteType> list = new ArrayList<ShotcreteType>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(ShotcreteTypeTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(ShotcreteTypeTable.NAME_COLUMN, cursor);
            ShotcreteType newInstance = new ShotcreteType(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public ShotcreteType getShotcreteTypeByCode(String code) throws DAOException {
        if (code != null){
            ShotcreteType entity = getIdentifiableEntityByCode(ShotcreteTypeTable.SHOTCRETE_DATABASE_TABLE, code);
            return entity;
        }
        return null;
    }

    @Override
    public List<ShotcreteType> getAllShotcreteTypes() throws DAOException {
        List<ShotcreteType> list = getAllPersistentEntities(ShotcreteTypeTable.SHOTCRETE_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveShotcreteType(ShotcreteType newEntity) throws DAOException {
        savePersistentEntity(ShotcreteTypeTable.SHOTCRETE_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, ShotcreteType newSkavaEntity) throws DAOException {
        saveSkavaEntity(tableName, newSkavaEntity);
    }

    @Override
    public boolean deleteShotcreteType(String code) {
        return deleteIdentifiableEntity(ShotcreteTypeTable.SHOTCRETE_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllShotcreteTypes() {
        return deleteAllPersistentEntities(ShotcreteTypeTable.SHOTCRETE_DATABASE_TABLE);
    }

    @Override
    public Long countShotcreteTypes() {
        return countRecords(ShotcreteTypeTable.SHOTCRETE_DATABASE_TABLE);
    }


}
