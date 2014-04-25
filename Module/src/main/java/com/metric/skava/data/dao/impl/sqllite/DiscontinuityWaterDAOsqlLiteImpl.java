package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalDiscontinuityWaterDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityWaterTable;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityWaterDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<DiscontinuityWater> implements LocalDiscontinuityWaterDAO {

    public DiscontinuityWaterDAOsqlLiteImpl(Context context) {
        super(context);
    }


    @Override
    protected List<DiscontinuityWater> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<DiscontinuityWater> list = new ArrayList<DiscontinuityWater>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(DiscontinuityWaterTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(DiscontinuityWaterTable.NAME_COLUMN, cursor);
            DiscontinuityWater newInstance = new DiscontinuityWater(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public DiscontinuityWater getDiscontinuityWaterByCode(String code) throws DAOException {
        DiscontinuityWater entity = getIdentifiableEntityByCode(DiscontinuityWaterTable.WATER_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<DiscontinuityWater> getAllDiscontinuityWaters() throws DAOException {
        List<DiscontinuityWater> list = getAllPersistentEntities(DiscontinuityWaterTable.WATER_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveDiscontinuityWater(DiscontinuityWater newEntity) throws DAOException {
        savePersistentEntity(DiscontinuityWaterTable.WATER_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, DiscontinuityWater newSkavaEntity) throws DAOException {

    }

    @Override
    public boolean deleteDiscontinuityWater(String code) {
        return false;
    }

    @Override
    public int deleteAllDiscontinuityWaters() {
        return 0;
    }


}
