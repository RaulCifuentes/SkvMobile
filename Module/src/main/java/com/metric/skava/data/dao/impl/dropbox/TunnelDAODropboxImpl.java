package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.LocalExcavationFactorDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.RemoteTunnelDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelDropboxTable;
import com.metric.skava.rocksupport.model.ESR;
import com.metric.skava.rocksupport.model.ExcavationFactors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class TunnelDAODropboxImpl extends DropBoxBaseDAO implements RemoteTunnelDAO {

    private TunnelDropboxTable mTunnelsTable;
    //    private RemoteExcavationProjectDAO remoteExcavationProjectDAO;
    private LocalExcavationProjectDAO excavationProjectDAO;
    private LocalExcavationFactorDAO factorDAO;

    public TunnelDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mTunnelsTable = new TunnelDropboxTable(getDatastore());
//        this.remoteExcavationProjectDAO = getDAOFactory().getRemoteExcavationProjectDAO(DAOFactory.Flavour.DROPBOX);
        this.excavationProjectDAO = getDAOFactory().getLocalExcavationProjectDAO();
        this.factorDAO = getDAOFactory().getLocalExcavationFactorsDAO();
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
                Double span = currentTunnelRecord.getDouble("Q_SPAN");
                String esrCode = currentTunnelRecord.getString("ESR_Code");
                ESR esr = getDAOFactory().getLocalEsrDAO().getESRByUniqueCode(esrCode);
                ExcavationFactors excavationFactors = new ExcavationFactors(esr, span);
                String projectCode = currentTunnelRecord.getString("ProjectId");
                ExcavationProject project = excavationProjectDAO.getExcavationProjectByCode(projectCode);
                Tunnel newTunnel = new Tunnel(project, codigo, nombre, excavationFactors);
                listTunnels.add(newTunnel);
            }
            return listTunnels;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }

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
            String projectCode = tunnelRecord.getString("ProjectId");
            ExcavationProject project = excavationProjectDAO.getExcavationProjectByCode(projectCode);
            Double span = tunnelRecord.getDouble("Q_SPAN");
            String esrCode =  tunnelRecord.getString("Q_ESR");

            ESR esr = getDAOFactory().getLocalEsrDAO().getESRByUniqueCode(esrCode);
            ExcavationFactors excavationFactors = new ExcavationFactors(esr, span);

            Tunnel tunnel = new Tunnel(project, codigo, nombre, excavationFactors);
            return tunnel;
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




}
