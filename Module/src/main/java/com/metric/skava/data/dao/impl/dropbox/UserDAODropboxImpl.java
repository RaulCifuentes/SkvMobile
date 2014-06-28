package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.sync.android.DbxList;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Permission;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
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
        this.mRolDAO = getDAOFactory().getLocalRoleDAO();
        this.mLocalTunnelFaceDAO = getDAOFactory().getLocalTunnelFaceDAO();
        this.mLocalPermissionDAO = getDAOFactory().getLocalPermissionDAO();
    }


    @Override
    public List<User> getAllUsers() throws DAOException {
        try {
//            DbxDatastoreStatus status = getDatastore().getSyncStatus();
//            if (status.hasIncoming) {
//                getDatastore().sync();
//            }
            List<User> listUsers = new ArrayList<User>();
            List<DbxRecord> recordList = mUsersTable.findAll();
            for (DbxRecord currentDbxRecord : recordList) {
                User newUser = mapUserFromDbxRecord(currentDbxRecord);

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
//        } catch (DbxException e) {
            //Just to keep the try
        } catch (Exception e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
        }
    }

    private User mapUserFromDbxRecord(DbxRecord currentDbxRecord) throws DAOException {
        String codigo = String.valueOf(currentDbxRecord.getLong("UserId"));
        String nombre = currentDbxRecord.getString("UserName");
        String email = readString(currentDbxRecord, "UserMail");
        String password = readString(currentDbxRecord, "Password");
        List<Role> roleList = new ArrayList<Role>();
        DbxList roles = currentDbxRecord.getList("Roles");
        for (int i = 0; i < roles.size(); i++) {
            String roleCode = roles.getString(i);
            roleList.add(mRolDAO.getRoleByCode(roleCode));
        }

        User newUser = new User(codigo, nombre, email, password, null);
        newUser.grantRoles(roleList);
        return newUser;
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        try {
//            DbxDatastoreStatus status = getDatastore().getSyncStatus();
//            if (status.hasIncoming) {
//                getDatastore().sync();
//            }
            DbxRecord userRecord = mUsersTable.findRecordByCandidateKey("UserMail", email);
            User newUser = mapUserFromDbxRecord(userRecord);

            DbxList faces = userRecord.getList("Faces");
            for (int i = 0; i < faces.size(); i++) {
                String faceCode = faces.getString(i);
                TunnelFace tunnelFace = mLocalTunnelFaceDAO.getTunnelFaceByCode(faceCode);
                Permission newPermission = new Permission(newUser, Permission.Action.ALL, Permission.IdentifiableEntityType.FACE, tunnelFace);
                mLocalPermissionDAO.savePermission(newPermission);
            }

            return newUser;
//        } catch (DbxException e) {
            //Just to keep the try
        } catch (Exception e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
        }
    }


    @Override
    public User getUserByCode(String code) throws DAOException {
        try {
//            DbxDatastoreStatus status = getDatastore().getSyncStatus();
//            if (status.hasIncoming) {
//                getDatastore().sync();
//            }
            DbxRecord userRecord = mUsersTable.findRecordByCode(code);
            User newUser = mapUserFromDbxRecord(userRecord);

            DbxList faces = userRecord.getList("Faces");
            for (int i = 0; i < faces.size(); i++) {
                String faceCode = faces.getString(i);
                TunnelFace tunnelFace = mLocalTunnelFaceDAO.getTunnelFaceByCode(faceCode);
                Permission newPermission = new Permission(newUser, Permission.Action.ALL, Permission.IdentifiableEntityType.FACE, tunnelFace);
                mLocalPermissionDAO.savePermission(newPermission);
            }

            return newUser;
//        } catch (DbxException e) {
            //Just to keep the try
        } catch (Exception e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
        }
    }



}
