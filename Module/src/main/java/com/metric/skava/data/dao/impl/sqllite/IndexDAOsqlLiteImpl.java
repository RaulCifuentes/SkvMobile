package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.calculator.model.Index;
import com.metric.skava.data.dao.LocalIndexDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.MappedIndexTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class IndexDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Index> implements LocalIndexDAO {


    public IndexDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
    }


    @Override
    protected List<Index> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Index> list = new ArrayList<Index>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(MappedIndexTable.CODE_COLUMN, cursor);
            String key = CursorUtils.getString(MappedIndexTable.KEY_COLUMN, cursor);
            String name = CursorUtils.getString(MappedIndexTable.NAME_COLUMN, cursor);
            Index newInstance = new Index(code, key, name);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public Index getIndexByCode(String code) throws DAOException {
        Index entity = getIdentifiableEntityByCode(MappedIndexTable.INDEX_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<Index> getAllIndexes() throws DAOException {
        List<Index> list = getAllPersistentEntities(MappedIndexTable.INDEX_DATABASE_TABLE);
        return list;
    }


    @Override
    public void saveIndex(Index newEntity) throws DAOException {
        //merge :: insert or update
        savePersistentEntity(MappedIndexTable.INDEX_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, Index newSkavaEntity) throws DAOException {
        String[] colNames = {MappedIndexTable.CODE_COLUMN, MappedIndexTable.KEY_COLUMN, MappedIndexTable.NAME_COLUMN};
        String[] colValues = {newSkavaEntity.getCode(), newSkavaEntity.getKey(), newSkavaEntity.getName() };
        saveRecord(tableName, colNames, colValues);
    }


    @Override
    public boolean deleteIndex(String code) {
        return deleteIdentifiableEntity(MappedIndexTable.INDEX_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllIndexes() {
        return deleteAllPersistentEntities(MappedIndexTable.INDEX_DATABASE_TABLE);
    }

    @Override
    public Long countIndexes() {
        return countRecords(MappedIndexTable.INDEX_DATABASE_TABLE);
    }
}
