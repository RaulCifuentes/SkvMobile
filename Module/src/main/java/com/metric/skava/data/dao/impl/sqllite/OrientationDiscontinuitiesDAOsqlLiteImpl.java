package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.data.dao.LocalOrientationDiscontinuitiesDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.OrientationTable;
import com.metric.skava.data.dao.impl.sqllite.table.StrengthTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class OrientationDiscontinuitiesDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<OrientationDiscontinuities> implements LocalOrientationDiscontinuitiesDAO {


    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public OrientationDiscontinuitiesDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    protected List<OrientationDiscontinuities> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<OrientationDiscontinuities> list = new ArrayList<OrientationDiscontinuities>();
        while (cursor.moveToNext()) {
            OrientationDiscontinuities newInstance = mappedIndexInstaceBuilder.buildOrientationDiscontinuitiesFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }

    @Override
    public OrientationDiscontinuities getOrientationDiscontinuities(String groupCode, String code) throws DAOException {
        String[] names = new String[]{OrientationTable.INDEX_CODE_COLUMN, OrientationTable.GROUP_CODE_COLUMN, OrientationTable.CODE_COLUMN};
        String[] values = new String[]{OrientationDiscontinuities.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(OrientationTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<OrientationDiscontinuities> list = assambleOrientationDiscontinuities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + OrientationDiscontinuities.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + OrientationDiscontinuities.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public OrientationDiscontinuities getOrientationDiscontinuitiesByUniqueCode(String code) throws DAOException {
        String[] names = new String[]{OrientationTable.INDEX_CODE_COLUMN, OrientationTable.CODE_COLUMN};
        String[] values = new String[]{OrientationDiscontinuities.INDEX_CODE, code};
        Cursor cursor = getRecordsFilteredByColumns(OrientationTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<OrientationDiscontinuities> list = assambleOrientationDiscontinuities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + OrientationDiscontinuities.INDEX_CODE +  ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + OrientationDiscontinuities.INDEX_CODE + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<OrientationDiscontinuities> assambleOrientationDiscontinuities(Cursor cursor) throws DAOException {
        List<OrientationDiscontinuities> list = new ArrayList<OrientationDiscontinuities>();
        while (cursor.moveToNext()) {
            OrientationDiscontinuities newInstance = mappedIndexInstaceBuilder.buildOrientationDiscontinuitiesFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<OrientationDiscontinuities> getAllOrientationDiscontinuities(OrientationDiscontinuities.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(OrientationTable.MAPPED_INDEX_DATABASE_TABLE, OrientationTable.GROUP_CODE_COLUMN, group.name(), null);
        List<OrientationDiscontinuities> list = assambleOrientationDiscontinuities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveOrientationDiscontinuities(OrientationDiscontinuities newOrientation) throws DAOException {
        savePersistentEntity(OrientationTable.MAPPED_INDEX_DATABASE_TABLE, newOrientation);
    }

    @Override
    protected void savePersistentEntity(String tableName, OrientationDiscontinuities newSkavaEntity) throws DAOException {

        String indexCode = OrientationDiscontinuities.INDEX_CODE;

        String[] colNames = {StrengthTable.INDEX_CODE_COLUMN,
                OrientationTable.GROUP_CODE_COLUMN,
                OrientationTable.CODE_COLUMN,
                OrientationTable.KEY_COLUMN,
                OrientationTable.SHORT_DESCRIPTION_COLUMN,
                OrientationTable.DESCRIPTION_COLUMN,
                OrientationTable.VALUE_COLUMN};

        Object[] colValues = {
                indexCode,
                newSkavaEntity.getGroupName(),
                newSkavaEntity.getCode(),
                newSkavaEntity.getKey(),
                newSkavaEntity.getShortDescription(),
                newSkavaEntity.getDescription(),
                newSkavaEntity.getValue()
        };
        saveRecord(tableName, colNames, colValues);
    }

    @Override
    public boolean deleteOrientationDiscontinuities(String indexCode, String groupCode, String code) {
        String[] colNames = {OrientationTable.INDEX_CODE_COLUMN,
                OrientationTable.GROUP_CODE_COLUMN,
                OrientationTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(OrientationTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllOrientationDiscontinuities() {
        return deleteAllPersistentEntities(OrientationTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
