//package com.metric.skava.data.dao.impl.dropbox;
//
//import android.content.Context;
//
//import com.dropbox.sync.android.DbxRecord;
//import com.metric.skava.app.data.IdentifiableEntity;
//import com.metric.skava.app.model.Permission;
//import com.metric.skava.app.model.User;
//import com.metric.skava.data.dao.LocalPermissionDAO;
//import com.metric.skava.data.dao.exception.DAOException;
//import com.metric.skava.data.dao.impl.dropbox.datastore.tables.PermissionDropboxTable;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by metricboy on 3/6/14.
// */
//public class PermissionDAODropboxImpl extends DropBoxBaseDAO implements LocalPermissionDAO {
//
//    private PermissionDropboxTable mPermissionsTable;
//
//
//    public PermissionDAODropboxImpl(Context context) throws DAOException {
//        super(context);
//        this.mPermissionsTable = new PermissionDropboxTable(getDatastore());
//    }
//
//
//    @Override
//    public List<Permission> getAllPermissions() throws DAOException {
//        List<Permission> listPermissions = new ArrayList<Permission>();
//        List<DbxRecord> recordList = mPermissionsTable.findAll();
//        for (DbxRecord currentDbxRecord : recordList) {
//            String codigo = currentDbxRecord.getString("code");
//
//            String nombre = currentDbxRecord.getString("name");
//
//            String email = currentDbxRecord.getString("email");
//
//            Permission permission = new Permission();
//            listPermissions.add(permission);
//
//
//        }
//        return listPermissions;
//    }
//
//    @Override
//    public List<Permission> getPermissionsByUser(User user) throws DAOException {
//        return null;
//    }
//
//    @Override
//    public List<User> getUsersByPermissionTarget(Permission.Action what, IdentifiableEntity where) throws DAOException {
//        return null;
//    }
//
//
//
//    @Override
//    public void savePermission(Permission newPermission) throws DAOException {
//
//    }
//
//    @Override
//    public boolean deletePermission(String code) {
//        return false;
//    }
//
//    @Override
//    public int deleteAllPermissions() throws DAOException {
//        return 0;
//    }
//
//
//
//
//}
