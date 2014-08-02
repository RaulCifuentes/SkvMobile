package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Permission;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.PermissionTable;
import com.metric.skava.data.dao.impl.sqllite.table.TunnelFaceTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class TunnelFaceDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<TunnelFace> implements LocalTunnelFaceDAO {

    public TunnelFaceDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<TunnelFace> assemblePersistentEntities(Cursor cursor) throws DAOException {

        List<TunnelFace> list = new ArrayList<TunnelFace>();

        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(TunnelFaceTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(TunnelFaceTable.NAME_COLUMN, cursor);
            String tunnelCode = CursorUtils.getString(TunnelFaceTable.TUNNEL_CODE_COLUMN, cursor);
            Integer orientation = CursorUtils.getInt(TunnelFaceTable.ORIENTATION_COLUMN, cursor);
            Double slope = CursorUtils.getDouble(TunnelFaceTable.SLOPE_COLUMN, cursor);
            Double referencePK = CursorUtils.getDouble(TunnelFaceTable.REFERENCE_PK_COLUMN, cursor);

            LocalTunnelDAO localTunnelDAO = getDAOFactory().getLocalTunnelDAO();
            Tunnel tunnel = localTunnelDAO.getTunnelByUniqueCode(tunnelCode);

            TunnelFace newInstance = new TunnelFace(tunnel, code, name, orientation.shortValue(), slope);
            newInstance.setReferencePK(referencePK);
            list.add(newInstance);
        }
        return list;
    }


//    @Override
//    public List<TunnelFace> getTunnelFacesByTunnel(Tunnel tunnel) throws DAOException {
//        String tunnelCode = null;
//        if (tunnel != null) {
//            tunnelCode = tunnel.getCode();
//        }
//        Cursor cursor = getRecordsFilteredByColumn(TunnelFaceTable.FACE_DATABASE_TABLE, TunnelFaceTable.TUNNEL_CODE_COLUMN, tunnelCode, null);
//        List<TunnelFace> list = assemblePersistentEntities(cursor);
//        cursor.close();
//        return list;
//    }

//    @Override
//    public List<TunnelFace> getTunnelFacesByTunnel(String environment, Tunnel tunnel, User user) throws DAOException {
//        if (user == null) {
//            return getTunnelFacesByTunnel(tunnel);
//        } else {
//            List<TunnelFace> listFaces = new ArrayList<TunnelFace>();
//            List<TunnelFace> allFacesOfUser = getTunnelFacesByUser(environment, user);
//            for (TunnelFace currTunnelFace : allFacesOfUser) {
//                if (currTunnelFace.getTunnel().equals(tunnel)) {
//                    listFaces.add(currTunnelFace);
//                }
//            }
//            return listFaces;
//        }
//    }


    //ALTERNATIVE TO FACES PER USER
//    //find the user tunnel faces
//    List<Permission> permissionList = mPermissionDAO.getPermissionsByUser(user);
//    List<TunnelFace> allFacesGrantedToUser = new ArrayList<TunnelFace>();
//    for (Permission permission : permissionList) {
//        Permission.IdentifiableEntityType currentEntityGranted = permission.getWhere();
//        if (currentEntityGranted.equals(Permission.IdentifiableEntityType.FACE)) {
//            TunnelFace targetFace = (TunnelFace) permission.getWhereExactly();
//            allFacesGrantedToUser.add(targetFace);
//        }
//    }

    @Override
    public List<TunnelFace> getTunnelFacesByUser(String environment, User user) throws DAOException {
        List<TunnelFace> tunnelList = new ArrayList<TunnelFace>();
        if (environment != null && user != null) {
            String[] names = new String[]{PermissionTable.ENVIRONMENT_COLUMN, PermissionTable.USER_CODE_COLUMN, PermissionTable.TARGET_TYPE_CODE_COLUMN};
            String[] values = new String[]{environment, user.getCode(), Permission.IdentifiableEntityType.FACE.name()};
            Cursor cursor = getRecordsFilteredByColumns(PermissionTable.PERMISSION_DATABASE_TABLE, names, values, null);
            while (cursor.moveToNext()) {
                String faceCode = CursorUtils.getString(PermissionTable.TARGET_CODE_COLUMN, cursor);
                TunnelFace tunnelFace = getTunnelFaceByCode(faceCode);
                tunnelList.add(tunnelFace);
            }
        }
        return tunnelList;
    }

    @Override
    public TunnelFace getTunnelFaceByCode(String code) throws DAOException {
        TunnelFace tunnelFace = getIdentifiableEntityByCode(TunnelFaceTable.FACE_DATABASE_TABLE, code);
        return tunnelFace;
    }

    @Override
    public List<TunnelFace> getAllTunnelFaces() throws DAOException {
        List<TunnelFace> list = getAllPersistentEntities(TunnelFaceTable.FACE_DATABASE_TABLE);
        return list;
    }

    @Override
    protected void savePersistentEntity(String tableName, TunnelFace newSkavaEntity) throws DAOException {
        String[] colNames = {TunnelFaceTable.TUNNEL_CODE_COLUMN, TunnelFaceTable.CODE_COLUMN, TunnelFaceTable.NAME_COLUMN, TunnelFaceTable.ORIENTATION_COLUMN, TunnelFaceTable.SLOPE_COLUMN, TunnelFaceTable.REFERENCE_PK_COLUMN};
        Object[] colValues = {newSkavaEntity.getTunnel().getCode(), newSkavaEntity.getCode(),newSkavaEntity.getName(), newSkavaEntity.getOrientation(), newSkavaEntity.getSlope(), newSkavaEntity.getReferencePK() };
        saveRecord(TunnelFaceTable.FACE_DATABASE_TABLE, colNames, colValues);
    }

    @Override
    public void saveTunnelFace(TunnelFace newFace) throws DAOException {
        savePersistentEntity(TunnelFaceTable.FACE_DATABASE_TABLE, newFace);
    }

//    @Override
//    public boolean deleteTunnelFace(String code) {
//        return deleteIdentifiableEntity(TunnelFaceTable.FACE_DATABASE_TABLE, code);
//    }

    @Override
    public int deleteAllTunnelFaces() {
        return deleteAllPersistentEntities(TunnelFaceTable.FACE_DATABASE_TABLE);
    }

    @Override
    public Long countFaces() {
        return countRecords(TunnelFaceTable.FACE_DATABASE_TABLE);
    }


}
