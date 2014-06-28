package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationMethodTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
//public class ExcavationMethodDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<ExcavationMethod> implements LocalExcavationMethodDAO {
public class ExcavationMethodDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<ExcavationMethod> implements LocalExcavationMethodDAO {

    public ExcavationMethodDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }

    @Override
    protected List<ExcavationMethod> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<ExcavationMethod> list = new ArrayList<ExcavationMethod>();

        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(ExcavationMethodTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(ExcavationMethodTable.NAME_COLUMN, cursor);
            ExcavationMethod newInstance = new ExcavationMethod(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public ExcavationMethod getExcavationMethodByCode(String code) throws DAOException {
        ExcavationMethod entity = getIdentifiableEntityByCode(ExcavationMethodTable.METHOD_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<ExcavationMethod> getAllExcavationMethods() throws DAOException {
        List<ExcavationMethod> list = getAllPersistentEntities(ExcavationMethodTable.METHOD_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveExcavationMethod(ExcavationMethod newEntity) throws DAOException {
        savePersistentEntity(ExcavationMethodTable.METHOD_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, ExcavationMethod newSkavaEntity) throws DAOException {
        saveSkavaEntity(tableName, newSkavaEntity);
    }

    @Override
    public boolean deleteExcavationMethod(String code) {
        return deleteIdentifiableEntity(ExcavationMethodTable.METHOD_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllExcavationMethods() {
        return deleteAllPersistentEntities(ExcavationMethodTable.METHOD_DATABASE_TABLE);
    }

    @Override
    public Long countExcavationMethods() {
        return countRecords(ExcavationMethodTable.METHOD_DATABASE_TABLE);
    }


}
