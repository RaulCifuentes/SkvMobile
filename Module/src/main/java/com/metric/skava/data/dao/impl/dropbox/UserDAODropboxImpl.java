package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxList;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Permission;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalPermissionDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.RemoteUserDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.UserDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/6/14.
 */
public class UserDAODropboxImpl extends DropBoxBaseDAO implements RemoteUserDAO {

    private UserDropboxTable mUsersTable;
    private LocalRoleDAO mRolDAO;
    private LocalTunnelFaceDAO mLocalTunnelFaceDAO;
    private LocalPermissionDAO mLocalPermissionDAO;


    public UserDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mUsersTable = new UserDropboxTable(getDatastore());
        this.mRolDAO = DAOFactory.getInstance(context).getLocalRoleDAO(DAOFactory.Flavour.SQLLITE);
        this.mLocalTunnelFaceDAO = DAOFactory.getInstance(context).getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
        this.mLocalPermissionDAO = DAOFactory.getInstance(context).getLocalPermissionDAO(DAOFactory.Flavour.SQLLITE);
    }


    @Override
    public List<User> getAllUsers() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming) {
                getDatastore().sync();
            }
            List<User> listUsers = new ArrayList<User>();
            List<DbxRecord> recordList = mUsersTable.findAll();
            for (DbxRecord currentDbxRecord : recordList) {
                String codigo = String.valueOf(currentDbxRecord.getLong("UserId"));
                String nombre = currentDbxRecord.getString("UserName");
                String email = currentDbxRecord.getString("UserMail");
                List<Role> roleList = new ArrayList<Role>();
                DbxList roles = currentDbxRecord.getList("Roles");
                for (int i = 0; i < roles.size(); i++) {
                    String roleCode = roles.getString(i);
                    roleList.add(mRolDAO.getRoleByCode(roleCode));
                }

                User newUser = new User(codigo, nombre, email, null);
                newUser.grantRoles(roleList);

                DbxList faces = currentDbxRecord.getList("Faces");
                for (int i = 0; i < faces.size(); i++) {
                    String faceCode = faces.getString(i);
                    TunnelFace tunnelFace = mLocalTunnelFaceDAO.getTunnelFaceByCode(faceCode);
                    Permission newPermission = new Permission(newUser, Permission.Action.ALL, Permission.IdentifiableEntityType.FACE, tunnelFace);
                    mLocalPermissionDAO.savePermission(newPermission);
                }
                listUsers.add(newUser);
            }
            return listUsers;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming) {
                getDatastore().sync();
            }
            DbxRecord userRecord = mUsersTable.findRecordByCandidateKey("UserMail", email);
            String codigo = String.valueOf(userRecord.getString("UserId"));
            String nombre = userRecord.getString("UserName");
            List<Role> roleList = new ArrayList<Role>();
            DbxList roles = userRecord.getList("Roles");
            for (int i = 0; i < roles.size(); i++) {
                String roleCode = roles.getString(i);
                roleList.add(mRolDAO.getRoleByCode(roleCode));
            }
            User newUser = new User(codigo, nombre, email, null);
            newUser.grantRoles(roleList);

            DbxList faces = userRecord.getList("Faces");
            for (int i = 0; i < faces.size(); i++) {
                String faceCode = faces.getString(i);
                TunnelFace tunnelFace = mLocalTunnelFaceDAO.getTunnelFaceByCode(faceCode);
                Permission newPermission = new Permission(newUser, Permission.Action.ALL, Permission.IdentifiableEntityType.FACE, tunnelFace);
                mLocalPermissionDAO.savePermission(newPermission);
            }

            return newUser;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public User getUserByCode(String code) throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming) {
                getDatastore().sync();
            }
            DbxRecord userRecord = mUsersTable.findRecordByCode(code);
            String codigo = String.valueOf(userRecord.getString("UserId"));
            String nombre = userRecord.getString("UserName");
            String email = userRecord.getString("UserMail");
            List<Role> roleList = new ArrayList<Role>();
            DbxList roles = userRecord.getList("Roles");
            for (int i = 0; i < roles.size(); i++) {
                String roleCode = roles.getString(i);
                roleList.add(mRolDAO.getRoleByCode(roleCode));
            }
            User newUser = new User(codigo, nombre, email, null);
            newUser.grantRoles(roleList);

            DbxList faces = userRecord.getList("Faces");
            for (int i = 0; i < faces.size(); i++) {
                String faceCode = faces.getString(i);
                TunnelFace tunnelFace = mLocalTunnelFaceDAO.getTunnelFaceByCode(faceCode);
                Permission newPermission = new Permission(newUser, Permission.Action.ALL, Permission.IdentifiableEntityType.FACE, tunnelFace);
                mLocalPermissionDAO.savePermission(newPermission);
            }

            return newUser;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }



}
