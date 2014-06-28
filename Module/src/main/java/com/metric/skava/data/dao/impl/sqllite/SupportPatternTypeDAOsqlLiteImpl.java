package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalSupportPatternTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.SupportPatternTypeTable;
import com.metric.skava.instructions.model.SupportPatternType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportPatternTypeDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<SupportPatternType> implements LocalSupportPatternTypeDAO {

    public SupportPatternTypeDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<SupportPatternType> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<SupportPatternType> list = new ArrayList<SupportPatternType>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(SupportPatternTypeTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(SupportPatternTypeTable.NAME_COLUMN, cursor);
            String groupName = CursorUtils.getString(SupportPatternTypeTable.GROUP_CODE_COLUMN, cursor);
            SupportPatternType newInstance = new SupportPatternType(code, name);
            SupportPatternType.Group group = SupportPatternType.Group.valueOf(groupName);
            newInstance.setGroup(group);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public SupportPatternType getSupportPatternTypeByUniqueCode(String code) throws DAOException {
        if (code != null){
            SupportPatternType entity = getIdentifiableEntityByCode(SupportPatternTypeTable.PATTERN_TYPE_DATABASE_TABLE, code);
            return entity;
        }
        return null;
    }

    @Override
    public List<SupportPatternType> getAllSupportPatternTypes(SupportPatternType.Group group ) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(SupportPatternTypeTable.PATTERN_TYPE_DATABASE_TABLE, SupportPatternTypeTable.GROUP_CODE_COLUMN, group.name(), null);
        List<SupportPatternType> list = assemblePersistentEntities(cursor);
        return list;
    }

    @Override
    public void saveSupportPatternType(SupportPatternType newEntity) throws DAOException {
        savePersistentEntity(SupportPatternTypeTable.PATTERN_TYPE_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, SupportPatternType newSkavaEntity) throws DAOException {
        String[] colNames = {
                SupportPatternTypeTable.GROUP_CODE_COLUMN,
                SupportPatternTypeTable.CODE_COLUMN,
                SupportPatternTypeTable.NAME_COLUMN
              };
        Object[] colValues = {
                newSkavaEntity.getGroup().name(),
                newSkavaEntity.getCode(),
                newSkavaEntity.getName()
        };
        saveRecord(tableName, colNames, colValues);
    }

    @Override
    public boolean deleteSupportPatternType(String code) {
        return deleteIdentifiableEntity(SupportPatternTypeTable.PATTERN_TYPE_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllSupportPatternTypes() {
        return deleteAllPersistentEntities(SupportPatternTypeTable.PATTERN_TYPE_DATABASE_TABLE);
    }

    @Override
    public Long countSupportPatternTypes() {
        return countRecords(SupportPatternTypeTable.PATTERN_TYPE_DATABASE_TABLE);
    }

}
