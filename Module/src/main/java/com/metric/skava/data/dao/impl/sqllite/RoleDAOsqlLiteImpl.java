package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Role;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.RoleTable;
import com.metric.skava.data.dao.impl.sqllite.table.UserRolesTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
//public class RoleDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Role> implements LocalRoleDAO {
public class RoleDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<Role> implements LocalRoleDAO {


    public RoleDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
    }


    @Override
    protected List<Role> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Role> list = new ArrayList<Role>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(RoleTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(RoleTable.NAME_COLUMN, cursor);
            Role newInstance = new Role(code, name);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public Role getRoleByCode(String code) throws DAOException {
        Role entity = getIdentifiableEntityByCode(RoleTable.ROLE_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<Role> getAllRoles() throws DAOException {
        List<Role> list = getAllPersistentEntities(RoleTable.ROLE_DATABASE_TABLE);
        return list;
    }

    public List<Role> getRolesByUserCode(String userCode) throws DAOException {
        List<Role> roleList = new ArrayList<Role>();
        Cursor userRolesCursor = getRecordsFilteredByColumn(UserRolesTable.USER_ROLES_DATABASE_TABLE, UserRolesTable.USER_CODE_COLUMN, userCode, null);
        while (userRolesCursor.moveToNext()) {
            String roleCode = CursorUtils.getString(UserRolesTable.ROLE_CODE_COLUMN, userRolesCursor);
            Role role = getRoleByCode(roleCode);
            roleList.add(role);
        }
        return roleList;
    }

    @Override
    public void saveRole(Role newEntity) throws DAOException {
        //merge :: insert or update
        savePersistentEntity(RoleTable.ROLE_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, Role newSkavaEntity) throws DAOException {
        saveSkavaEntity(tableName, newSkavaEntity);
    }


    @Override
    public boolean deleteRole(String code) {
        return false;
    }

    @Override
    public int deleteAllRoles() {
        return deleteAllPersistentEntities(RoleTable.ROLE_DATABASE_TABLE);
    }
}
