package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationSectionTable;
import com.metric.skava.data.dao.impl.sqllite.table.TunnelFaceTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
//public class ExcavationSectionDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<ExcavationSection> implements LocalExcavationSectionDAO {
public class ExcavationSectionDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<ExcavationSection> implements LocalExcavationSectionDAO {

    public ExcavationSectionDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<ExcavationSection> assemblePersistentEntities(Cursor cursor) throws DAOException {

        List<ExcavationSection> list = new ArrayList<ExcavationSection>();

        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(TunnelFaceTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(TunnelFaceTable.NAME_COLUMN, cursor);

            ExcavationSection newInstance = new ExcavationSection(code, name);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public ExcavationSection getExcavationSectionByCode(String code) throws DAOException {
        ExcavationSection section = getIdentifiableEntityByCode(ExcavationSectionTable.SECTION_DATABASE_TABLE, code);
        return section;
    }

    @Override
    public List<ExcavationSection> getAllExcavationSections() throws DAOException {
        List<ExcavationSection> list = getAllPersistentEntities(ExcavationSectionTable.SECTION_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveExcavationSection(ExcavationSection newEntity) throws DAOException {
        savePersistentEntity(ExcavationSectionTable.SECTION_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, ExcavationSection newSkavaEntity) throws DAOException {
        saveSkavaEntity(tableName, newSkavaEntity);
    }

    @Override
    public boolean deleteExcavationSection(String code) {
        return deleteIdentifiableEntity(ExcavationSectionTable.SECTION_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllExcavationSections() {
        return deleteAllPersistentEntities(ExcavationSectionTable.SECTION_DATABASE_TABLE);
    }
}
