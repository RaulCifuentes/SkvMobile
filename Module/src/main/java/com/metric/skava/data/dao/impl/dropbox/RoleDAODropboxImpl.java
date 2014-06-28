package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.RemoteRoleDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RoleDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/6/14.
 */
public class RoleDAODropboxImpl extends DropBoxBaseDAO implements RemoteRoleDAO {

    private RoleDropboxTable mRolesTable;
//    private LocalUserDAO userDAO;

    public RoleDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mRolesTable = new RoleDropboxTable(getDatastore());
//        userDAO = new UserDAODropboxImpl(context);
    }

    @Override
    public List<Role> getAllRoles() throws DAOException {
        try {
//            DbxDatastoreStatus status = getDatastore().getSyncStatus();
//            if (status.hasIncoming || status.isDownloading) {
//                getDatastore().sync();
//            }
            List<Role> listRoles = new ArrayList<Role>();
            List<DbxRecord> recordList = mRolesTable.findAll();
            for (DbxRecord currentDbxRecord : recordList) {
                String codigo = currentDbxRecord.getString("RoleCode");
                String nombre = currentDbxRecord.getString("RoleName");
                Role newRole = new Role(codigo, nombre);
                listRoles.add(newRole);
            }
            return listRoles;
//        } catch (DbxException e) {
        } catch (Exception e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
        }
    }



//    @Override
//    public Role getRoleByCode(String code) throws DAOException {
//        try {
//            DbxDatastoreStatus status = getDatastore().getSyncStatus();
//            if (status.hasIncoming) {
//                getDatastore().sync();
//            }
////        DbxRecord roleRecord = mRolesTable.findRecordByCode(code);
//            DbxRecord roleRecord = mRolesTable.findRecordByCandidateKey("RoleCode", code);
//            Role role = null;
//            if (roleRecord != null) {
//                String codigo = roleRecord.getString("Id");
//                String nombre = roleRecord.getString("Name");
//                role = new Role(codigo, nombre);
//            }
//            return role;
//        } catch (DbxException e) {
//            throw new DAOException(e);
//        }
//    }


//    @Override
//    public List<Role> getRolesByUserCode(String userCode) throws DAOException {
//        //get the user
//        User user = userDAO.getUserByCode(userCode);
//        List<Role> listRoles  = user.getRoles();
//        //get the list
////        = new ArrayList<Role>();
////        List<DbxRecord> recordList = mRolesTable.findRecordsByCriteria(new String[]{"UserId"}, new String[]{userCode});
////        for (DbxRecord currentDbxRecord : recordList) {
////            String codigo = currentDbxRecord.getString("RoleCode");
////            String nombre = currentDbxRecord.getString("RoleName");
////            Role newRole = new Role(codigo, nombre);
////            listRoles.add(newRole);
////        }
//        return listRoles;
//    }


}
