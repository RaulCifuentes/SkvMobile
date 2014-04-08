package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.UserRolesTable;
import com.metric.skava.data.dao.impl.sqllite.table.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class UserDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<User> implements LocalUserDAO {

    private LocalRoleDAO localRoleDAO;

    public UserDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        localRoleDAO = DAOFactory.getInstance(context).getLocalRoleDAO(DAOFactory.Flavour.SQLLITE);
    }


    @Override
    protected List<User> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<User> list = new ArrayList<User>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(UserTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(UserTable.NAME_COLUMN, cursor);
            String email = CursorUtils.getString(UserTable.EMAIL_COLUMN, cursor);
            //find out the roles associated
            List<Role> listRoles = ((RoleDAOsqlLiteImpl) localRoleDAO).getRolesByUserCode(code);
            User newInstance = new User(code, name, email, null);
            newInstance.grantRoles(listRoles);
            list.add(newInstance);
        }
        return list;
    }

    @Override
    public User getUserByCode(String code) throws DAOException {
        User entity = getIdentifiableEntityByCode(UserTable.USER_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        List<User> list = getAllPersistentEntities(UserTable.USER_DATABASE_TABLE);
        return list;
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        User entity = getPersistentEntityByCandidateKey(UserTable.USER_DATABASE_TABLE, UserTable.EMAIL_COLUMN, email);
        return entity;
    }

    @Override
    public void saveUser(User newUser) throws DAOException {
        savePersistentEntity(UserTable.USER_DATABASE_TABLE, newUser);
    }

    @Override
    protected void savePersistentEntity(String tableName, User newSkavaEntity) throws DAOException {
        String[] colNames = {UserTable.CODE_COLUMN, UserTable.NAME_COLUMN, UserTable.EMAIL_COLUMN};
        String[] colValues = {newSkavaEntity.getCode(), newSkavaEntity.getName(), newSkavaEntity.getEmail()};
        saveRecord(tableName, colNames, colValues);

        List<Role> userRoles = newSkavaEntity.getRoles();
        for (Role userRole : userRoles) {
            colNames = new String[]{UserRolesTable.USER_CODE_COLUMN, UserRolesTable.ROLE_CODE_COLUMN};
            colValues = new String[]{newSkavaEntity.getCode(), userRole.getCode()};
            saveRecord(UserRolesTable.USER_ROLES_DATABASE_TABLE, colNames, colValues);
        }
    }


    @Override
    public boolean deleteUser(String code) {
        return false;
    }

    @Override
    public int deleteAllUsers() {
        deleteAllPersistentEntities(UserRolesTable.USER_ROLES_DATABASE_TABLE);
        return deleteAllPersistentEntities(UserTable.USER_DATABASE_TABLE);

    }
}
