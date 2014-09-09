package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.data.dao.LocalRockQualityDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.RockQualityTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class RockQualityDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<RockQuality> implements LocalRockQualityDAO {

    public RockQualityDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<RockQuality> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<RockQuality> list = new ArrayList<RockQuality>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(RockQualityTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(RockQualityTable.NAME_COLUMN, cursor);
            String accordintToAsString = CursorUtils.getString(RockQualityTable.ACCORDING_TO_COLUMN, cursor);
            String classification = CursorUtils.getString(RockQualityTable.CLASSIFICATION_COLUMN, cursor);
            Double lower = CursorUtils.getDouble(RockQualityTable.LOWERBOUND_COLUMN, cursor);
            Double upper = CursorUtils.getDouble(RockQualityTable.UPPERBOUND_COLUMN, cursor);
            RockQuality.AccordingTo accordingTo = RockQuality.AccordingTo.valueOf(accordintToAsString);
            RockQuality newInstance = new RockQuality(accordingTo, code, name, lower, upper);
            newInstance.setClassification(classification);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public RockQuality getRockQualityByCode(String code) throws DAOException {
        RockQuality entity = getIdentifiableEntityByCode(RockQualityTable.ROCK_QUALITY_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<RockQuality> getAllRockQualities(RockQuality.AccordingTo accordingTo) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(RockQualityTable.ROCK_QUALITY_DATABASE_TABLE, RockQualityTable.ACCORDING_TO_COLUMN, accordingTo.name(), RockQualityTable.LOWERBOUND_COLUMN);
        List<RockQuality> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }

    @Override
    public void saveRockQuality(RockQuality newEntity) throws DAOException {
        savePersistentEntity(RockQualityTable.ROCK_QUALITY_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, RockQuality newSkavaEntity) throws DAOException {
        String[] columns = new String[]{RockQualityTable.CODE_COLUMN, RockQualityTable.NAME_COLUMN, RockQualityTable.ACCORDING_TO_COLUMN, RockQualityTable.CLASSIFICATION_COLUMN, RockQualityTable.LOWERBOUND_COLUMN, RockQualityTable.UPPERBOUND_COLUMN};
        //Currently the relation is mantained by the Tunnel side of the relations so ...
        Object[] values = new Object[]{newSkavaEntity.getCode(), newSkavaEntity.getName(), newSkavaEntity.getAccordingTo().name(), newSkavaEntity.getClassification(), newSkavaEntity.getLowerBoundary(), newSkavaEntity.getHigherBoundary()};
        saveRecord(tableName, columns, values);
    }

    @Override
    public boolean deleteRockQuality(String code) {
        return deleteIdentifiableEntity(RockQualityTable.ROCK_QUALITY_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllRockQualities() {
        return deleteAllPersistentEntities(RockQualityTable.ROCK_QUALITY_DATABASE_TABLE);
    }

    @Override
    public Long countRockQualities() {
        return countRecords(RockQualityTable.ROCK_QUALITY_DATABASE_TABLE);
    }


}
