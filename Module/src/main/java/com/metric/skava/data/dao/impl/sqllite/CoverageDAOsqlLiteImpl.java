package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalCoverageDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.CoverageTable;
import com.metric.skava.instructions.model.Coverage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class CoverageDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<Coverage> implements LocalCoverageDAO {

    public CoverageDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<Coverage> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Coverage> list = new ArrayList<Coverage>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(CoverageTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(CoverageTable.NAME_COLUMN, cursor);
            Coverage newInstance = new Coverage(code, name);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public Coverage getCoverageByCode(String code) throws DAOException {
        if (code != null) {
            Coverage entity = getIdentifiableEntityByCode(CoverageTable.COVERAGE_DATABASE_TABLE, code);
            return entity;
        }
        return null;
    }

    @Override
    public List<Coverage> getAllCoverages() throws DAOException {
        List<Coverage> list = getAllPersistentEntities(CoverageTable.COVERAGE_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveCoverage(Coverage newEntity) throws DAOException {
        savePersistentEntity(CoverageTable.COVERAGE_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, Coverage newSkavaEntity) throws DAOException {
        saveSkavaEntity(tableName, newSkavaEntity);
    }

    @Override
    public boolean deleteCoverage(String code) {
        return deleteIdentifiableEntity(CoverageTable.COVERAGE_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllCoverages() {
        return deleteAllPersistentEntities(CoverageTable.COVERAGE_DATABASE_TABLE);
    }


}
