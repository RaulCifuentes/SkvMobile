package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalDiscontinuityTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityTypeTable;
import com.metric.skava.discontinuities.model.DiscontinuityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityTypeDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<DiscontinuityType> implements LocalDiscontinuityTypeDAO {

    public DiscontinuityTypeDAOsqlLiteImpl(Context context) {
        super(context);
    }



    @Override
    protected List<DiscontinuityType> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<DiscontinuityType> list = new ArrayList<DiscontinuityType>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(DiscontinuityTypeTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(DiscontinuityTypeTable.NAME_COLUMN, cursor);
            DiscontinuityType newInstance = new DiscontinuityType(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public DiscontinuityType getDiscontinuityTypeByCode(String code) throws DAOException {
        DiscontinuityType entity = getIdentifiableEntityByCode(DiscontinuityTypeTable.DISCONTINUITY_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<DiscontinuityType> getAllDiscontinuityTypes() throws DAOException {
        List<DiscontinuityType> list = getAllPersistentEntities(DiscontinuityTypeTable.DISCONTINUITY_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveDiscontinuityType(DiscontinuityType newEntity) throws DAOException {
        savePersistentEntity(DiscontinuityTypeTable.DISCONTINUITY_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, DiscontinuityType newSkavaEntity) throws DAOException {

    }


    @Override
    public boolean deleteDiscontinuityType(String code) {
        return false;
    }

    @Override
    public int deleteAllDiscontinuityTypes() {
        return 0;
    }


}
