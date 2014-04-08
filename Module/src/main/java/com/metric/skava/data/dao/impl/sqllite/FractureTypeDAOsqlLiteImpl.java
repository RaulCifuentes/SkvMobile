package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalFractureTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.FractureTypeTable;
import com.metric.skava.rockmass.model.FractureType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class FractureTypeDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<FractureType> implements LocalFractureTypeDAO {

    public FractureTypeDAOsqlLiteImpl(Context context) {
        super(context);
    }



    @Override
    protected List<FractureType> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<FractureType> list = new ArrayList<FractureType>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(FractureTypeTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(FractureTypeTable.NAME_COLUMN, cursor);
            FractureType newInstance = new FractureType(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public FractureType getFractureTypeByCode(String code) throws DAOException {
        FractureType entity = getIdentifiableEntityByCode(FractureTypeTable.FRACTURE_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<FractureType> getAllFractureTypes() throws DAOException {
        List<FractureType> list = getAllPersistentEntities(FractureTypeTable.FRACTURE_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveFractureType(FractureType newEntity) throws DAOException {
        savePersistentEntity(FractureTypeTable.FRACTURE_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, FractureType newSkavaEntity) throws DAOException {

    }

    @Override
    public boolean deleteFractureType(String code) {
        return false;
    }

    @Override
    public int deleteAllFractureTypes() {
        return 0;
    }


}
