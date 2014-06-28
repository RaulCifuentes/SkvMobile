package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.data.dao.LocalSpacingDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.SpacingTable;
import com.metric.skava.data.dao.impl.sqllite.table.StrengthTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class SpacingDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Spacing> implements LocalSpacingDAO {

    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public SpacingDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    public Spacing getSpacing(String groupCode, String code) throws DAOException {
        String[] names = new String[]{SpacingTable.INDEX_CODE_COLUMN, SpacingTable.GROUP_CODE_COLUMN, SpacingTable.CODE_COLUMN};
        String[] values = new String[]{Spacing.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(SpacingTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Spacing> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Spacing.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + Spacing.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public Spacing getSpacingByUniqueCode(String spacingCode) throws DAOException {
        String[] names = new String[]{SpacingTable.INDEX_CODE_COLUMN, SpacingTable.CODE_COLUMN};
        String[] values = new String[]{Spacing.INDEX_CODE, spacingCode};
        Cursor cursor = getRecordsFilteredByColumns(SpacingTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Spacing> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Spacing Code : " + spacingCode + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + spacingCode + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    protected List<Spacing> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Spacing> list = new ArrayList<Spacing>();
        while (cursor.moveToNext()) {
            Spacing newInstance = mappedIndexInstaceBuilder.buildSpacingFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Spacing> getAllSpacings() throws DAOException {
        Cursor cursor = getAllRecords(SpacingTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Spacing> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveSpacing(Spacing newSpacing) throws DAOException {
        savePersistentEntity(SpacingTable.MAPPED_INDEX_DATABASE_TABLE, newSpacing);
    }

    @Override
    protected void savePersistentEntity(String tableName, Spacing newSkavaEntity) throws DAOException {

        String[] colNames = {SpacingTable.INDEX_CODE_COLUMN,
                SpacingTable.GROUP_CODE_COLUMN,
                SpacingTable.CODE_COLUMN,
                SpacingTable.KEY_COLUMN,
                SpacingTable.SHORT_DESCRIPTION_COLUMN,
                SpacingTable.DESCRIPTION_COLUMN,
                SpacingTable.VALUE_COLUMN};

        Object[] colValues = {
                Spacing.INDEX_CODE,
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
    public boolean deleteSpacing(String groupCode, String code) {
        String[] colNames = {SpacingTable.INDEX_CODE_COLUMN,
                SpacingTable.GROUP_CODE_COLUMN,
                SpacingTable.CODE_COLUMN};
        Object[] colValues = {
                Spacing.INDEX_CODE,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(SpacingTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllSpacings() {
        return deleteAllPersistentEntities(SpacingTable.MAPPED_INDEX_DATABASE_TABLE);
    }

    @Override
    public Long countSpacings() {
        return countRecords(StrengthTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
