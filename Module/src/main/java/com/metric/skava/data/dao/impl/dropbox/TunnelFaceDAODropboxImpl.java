package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxList;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.RemoteTunnelDAO;
import com.metric.skava.data.dao.RemoteTunnelFaceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelFaceDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.UserDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class TunnelFaceDAODropboxImpl extends DropBoxBaseDAO implements RemoteTunnelFaceDAO {

    private TunnelFaceDropboxTable mTunnelsFaceTable;
    private UserDropboxTable mUsersTable;

    private RemoteTunnelDAO remoteTunnelDAO;

    public TunnelFaceDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mTunnelsFaceTable = new TunnelFaceDropboxTable(getDatastore());
        mUsersTable = new UserDropboxTable(getDatastore());
        remoteTunnelDAO = getDAOFactory().getRemoteTunnelDAO(DAOFactory.Flavour.DROPBOX);
    }

    @Override
    public List<TunnelFace> getAllTunnelFaces() throws DAOException {
        List<TunnelFace> listFaces = new ArrayList<TunnelFace>();
        List<DbxRecord> facesList = mTunnelsFaceTable.findAll();
        for (DbxRecord faceRecord : facesList) {
            String codigo = faceRecord.getString("FaceId");
            String nombre = faceRecord.getString("Name");
            TunnelFace face = new TunnelFace(codigo, nombre);
            String tunnelCode = faceRecord.getString("TunnelId");
            Tunnel tunnel = remoteTunnelDAO.getTunnelByCode(tunnelCode);
            face.setTunnel(tunnel);
            listFaces.add(face);
        }
        return listFaces;
    }

    @Override
    public List<TunnelFace> getTunnelFacesByUser(User user) throws DAOException {
        List<TunnelFace> listFaces = new ArrayList<TunnelFace>();
        DbxRecord userRecord = mUsersTable.findRecordByCode(user.getCode());
        DbxList faceCodeList = userRecord.getList("Faces");
        for (int i = 0; i < faceCodeList.size(); i++){
            String codigoFace = faceCodeList.getString(i);
            TunnelFace tunnelFace = getTunnelFaceByCode(codigoFace);
            listFaces.add(tunnelFace);
        }
        return listFaces;
    }

    @Override
    public TunnelFace getTunnelFaceByCode(String code) throws DAOException {
        DbxRecord faceRecord = mTunnelsFaceTable.findRecordByCode(code);
        String codigo = faceRecord.getString("FaceId");
        String nombre = faceRecord.getString("Name");
        TunnelFace face = new TunnelFace(codigo, nombre);
        String tunnelCode = faceRecord.getString("TunnelId");
        Tunnel tunnel = remoteTunnelDAO.getTunnelByCode(tunnelCode);
        face.setTunnel(tunnel);
        return face;
    }



//    @Override
//    public List<TunnelFace> getTunnelFacesByTunnel(Tunnel tunnel) throws DAOException {
//        List<TunnelFace> listFaces = new ArrayList<TunnelFace>();
//        List<DbxRecord> facesList;
//
//        facesList = mTunnelsFaceTable.findRecordsByCriteria(new String[]{"TunnelId"}, new String[]{tunnel.getCode()});
//
//        for (DbxRecord faceRecord : facesList) {
//            String codigo = faceRecord.getString("FaceId");
//            String nombre = faceRecord.getString("Name");
//            TunnelFace face = new TunnelFace(codigo, nombre);
//            face.setTunnel(tunnel);
//            listFaces.add(face);
//        }
//        return listFaces;
//    }
//
//    @Override
//    public List<TunnelFace> getTunnelFacesByTunnel(Tunnel tunnel, User user) throws DAOException {
//        if (user == null) {
//            return getTunnelFacesByTunnel(tunnel);
//        } else {
//            List<TunnelFace> listFaces = new ArrayList<TunnelFace>();
//            List<TunnelFace> allFacesOfUser = getTunnelFacesByUser(user);
//            for (TunnelFace currTunnelFace : allFacesOfUser) {
//                if (currTunnelFace.getTunnel().equals(tunnel)) {
//                    listFaces.add(currTunnelFace);
//                }
//            }
//            return listFaces;
//        }
//    }
//

}
