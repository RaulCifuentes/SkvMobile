package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityRelevanceTable;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityRelevanceDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<DiscontinuityRelevance> implements LocalDiscontinuityRelevanceDAO {

    public DiscontinuityRelevanceDAOsqlLiteImpl(Context context) {
        super(context);
    }


    @Override
    protected List<DiscontinuityRelevance> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<DiscontinuityRelevance> list = new ArrayList<DiscontinuityRelevance>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(DiscontinuityRelevanceTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(DiscontinuityRelevanceTable.NAME_COLUMN, cursor);
            DiscontinuityRelevance newInstance = new DiscontinuityRelevance(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public DiscontinuityRelevance getDiscontinuityRelevanceByCode(String code) throws DAOException {
        DiscontinuityRelevance entity = getIdentifiableEntityByCode(DiscontinuityRelevanceTable.RELEVANCES_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<DiscontinuityRelevance> getAllDiscontinuityRelevances() throws DAOException {
        List<DiscontinuityRelevance> list = getAllPersistentEntities(DiscontinuityRelevanceTable.RELEVANCES_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveDiscontinuityRelevance(DiscontinuityRelevance newEntity) throws DAOException {
        savePersistentEntity(DiscontinuityRelevanceTable.RELEVANCES_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, DiscontinuityRelevance newSkavaEntity) throws DAOException {

    }

    @Override
    public boolean deleteDiscontinuityRelevance(String code) {
        return false;
    }

    @Override
    public int deleteAllDiscontinuityRelevances() {
        return 0;
    }


}
