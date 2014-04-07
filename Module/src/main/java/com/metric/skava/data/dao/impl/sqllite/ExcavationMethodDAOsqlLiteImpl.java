package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

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
public class ExcavationMethodDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<ExcavationMethod> implements LocalExcavationMethodDAO {

    public ExcavationMethodDAOsqlLiteImpl(Context context) {
        super(context);
    }

    @Override
    protected List<ExcavationMethod> assamblePersistentEntities(Cursor cursor) throws DAOException {
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

    }

    @Override
    public boolean deleteExcavationMethod(String code) {
        return false;
    }

    @Override
    public int deleteAllExcavationMethods() {
        return 0;
    }


}
