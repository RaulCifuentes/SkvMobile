package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.data.dao.LocalGroupDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.MappedIndexGroupsTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class GroupDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Group> implements LocalGroupDAO {


    public GroupDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
    }


    @Override
    protected List<Group> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Group> list = new ArrayList<Group>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(MappedIndexGroupsTable.CODE_COLUMN, cursor);
            String key = CursorUtils.getString(MappedIndexGroupsTable.KEY_COLUMN, cursor);
            String name = CursorUtils.getString(MappedIndexGroupsTable.NAME_COLUMN, cursor);
            Group newInstance = new Group(code, key, name);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public Group getGroupByCode(String code) throws DAOException {
        Group entity = getIdentifiableEntityByCode(MappedIndexGroupsTable.GROUPS_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<Group> getAllGroups() throws DAOException {
        List<Group> list = getAllPersistentEntities(MappedIndexGroupsTable.GROUPS_DATABASE_TABLE);
        return list;
    }


    @Override
    public void saveGroup(Group newEntity) throws DAOException {
        //merge :: insert or update
        savePersistentEntity(MappedIndexGroupsTable.GROUPS_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, Group newSkavaEntity) throws DAOException {
        String[] colNames = {MappedIndexGroupsTable.INDEX_CODE_COLUMN, MappedIndexGroupsTable.CODE_COLUMN, MappedIndexGroupsTable.KEY_COLUMN, MappedIndexGroupsTable.NAME_COLUMN};
        String[] colValues = {newSkavaEntity.getIndex().getCode(), newSkavaEntity.getCode(), newSkavaEntity.getKey(), newSkavaEntity.getName() };
        saveRecord(tableName, colNames, colValues);
    }


    @Override
    public boolean deleteGroup(String code) {
        return deleteIdentifiableEntity(MappedIndexGroupsTable.GROUPS_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllGroups() {
        return deleteAllPersistentEntities(MappedIndexGroupsTable.GROUPS_DATABASE_TABLE);
    }

    @Override
    public Long countGroups() {
        return countRecords(MappedIndexGroupsTable.GROUPS_DATABASE_TABLE);
    }
}
