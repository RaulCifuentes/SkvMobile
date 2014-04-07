package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.RemoteExcavationProjectDAO;
import com.metric.skava.data.dao.RemoteTunnelDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class TunnelDAODropboxImpl extends DropBoxBaseDAO implements RemoteTunnelDAO {

    private TunnelDropboxTable mTunnelsTable;
    private RemoteExcavationProjectDAO remoteExcavationProjectDAO;

    public TunnelDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mTunnelsTable = new TunnelDropboxTable(getDatastore());
        this.remoteExcavationProjectDAO = DAOFactory.getInstance(mContext).getRemoteExcavationProjectDAO(DAOFactory.Flavour.DROPBOX);
    }


    @Override
    public List<Tunnel> getAllTunnels() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            List<Tunnel> listTunnels = new ArrayList<Tunnel>();
            List<DbxRecord> tunnelList = mTunnelsTable.findAll();
            for (DbxRecord currentTunnelRecord : tunnelList) {
                String codigo = currentTunnelRecord.getString("TunnelId");
                String nombre = currentTunnelRecord.getString("Name");
                Tunnel newTunnel = new Tunnel(codigo, nombre);
                String projectCode = currentTunnelRecord.getString("ProjectId");
                ExcavationProject project = remoteExcavationProjectDAO.getExcavationProjectByCode(projectCode);
                newTunnel.setProject(project);
                listTunnels.add(newTunnel);
            }
            return listTunnels;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }

//       @Override
//    public List<Tunnel> getTunnelsByProject(ExcavationProject project) throws DAOException {
//        try {
//            DbxDatastoreStatus status = getDatastore().getSyncStatus();
//            if (status.hasIncoming || status.isDownloading) {
//                getDatastore().sync();
//            }
//            List<Tunnel> listTunnels = new ArrayList<Tunnel>();
//            List<DbxRecord> tunnelList = mTunnelsTable.findRecordsByCriteria(new String[]{"ProjectId"}, new String[]{project.getCode()});
//            for (DbxRecord currentTunnelRecord : tunnelList) {
//                String codigo = currentTunnelRecord.getString("TunnelId");
//                String nombre = currentTunnelRecord.getString("Name");
//                Tunnel newTunnel = new Tunnel(codigo, nombre);
//                newTunnel.setProject(project);
//                listTunnels.add(newTunnel);
//            }
//            return listTunnels;
//        } catch (DbxException e) {
//            throw new DAOException(e);
//        }
//    }
//
//    @Override
//    public List<Tunnel> getTunnelsByProject(ExcavationProject project, User user) throws DAOException {
//        if (user == null) {
//            return getTunnelsByProject(project);
//        } else {
//            List<Tunnel> projectList = new ArrayList<Tunnel>();
//            List<Tunnel> allTunnelsOfUser = getTunnelsByUser(user);
//            for (Tunnel currTunnel : allTunnelsOfUser) {
//                if (currTunnel.getProject().equals(project)){
//                    projectList.add(currTunnel);
//                }
//            }
//            return projectList;
//        }
//    }
//
//
//    @Override
//    public List<Tunnel> getTunnelsByUser(User user) throws DAOException {
//        List<Tunnel> tunnelList = new ArrayList<Tunnel>();
//        //find the faces of this user
//        LocalTunnelFaceDAO localTunnelFaceDAO = DAOFactory.getInstance(mContext).getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
//        List<TunnelFace> faces = localTunnelFaceDAO.getTunnelFacesByUser(user);
//        //find the correspondant tunnels
//        for (TunnelFace currentFace : faces) {
//            Tunnel tunnel = currentFace.getTunnel();
//            tunnelList.add(tunnel);
//        }
//        return tunnelList;
//    }


    @Override
    public Tunnel getTunnelByCode(String code) throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            DbxRecord tunnelRecord = mTunnelsTable.findRecordByCode(code);
            String codigo = tunnelRecord.getString("TunnelId");
            String nombre = tunnelRecord.getString("Name");
            Tunnel tunnel = new Tunnel(codigo, nombre);
            String projectCode = tunnelRecord.getString("ProjectId");
            ExcavationProject project = remoteExcavationProjectDAO.getExcavationProjectByCode(projectCode);
            tunnel.setProject(project);
            return tunnel;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }

}
