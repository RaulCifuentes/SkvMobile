package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Client;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.data.dao.LocalClientDAO;
import com.metric.skava.data.dao.RemoteExcavationProjectDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ExcavationProjectDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/6/14.
 */
public class ExcavationProjectDAODropboxImpl extends DropBoxBaseDAO implements RemoteExcavationProjectDAO {

    private ExcavationProjectDropboxTable mProjectsTable;

    public ExcavationProjectDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mProjectsTable = new ExcavationProjectDropboxTable(getDatastore());
    }


    @Override
    public List<ExcavationProject> getAllExcavationProjects() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            List<ExcavationProject> listProjects = new ArrayList<ExcavationProject>();
            List<DbxRecord> recordList = mProjectsTable.findAll();
            for (DbxRecord currentDbxRecord : recordList) {
                String codigo = currentDbxRecord.getString("ProjectId");
                String nombre = currentDbxRecord.getString("Name");
                String clientCode = currentDbxRecord.getString("FkClientId");
                String internalCode = currentDbxRecord.getString("code");

                LocalClientDAO clientDAO = getDAOFactory().getLocalClientDAO();
                Client client = clientDAO.getClientByCode(clientCode);
                ExcavationProject newProject = new ExcavationProject(codigo, nombre, internalCode);
                newProject.setClient(client);
                listProjects.add(newProject);
            }
            return listProjects;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ExcavationProject getExcavationProjectByCode(String code) throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            DbxRecord projectRecord = mProjectsTable.findRecordByCode(code);
            String codigo = projectRecord.getString("ProjectId");
            String nombre = projectRecord.getString("Name");
            String clientCode = projectRecord.getString("FkClientId");
            LocalClientDAO clientDAO = getDAOFactory().getLocalClientDAO();
            Client client = clientDAO.getClientByCode(clientCode);
            String internalCode = projectRecord.getString("code");
            ExcavationProject newProject = new ExcavationProject(codigo, nombre, internalCode);
            newProject.setClient(client);
            return newProject;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }

//    @Override
//    public List<ExcavationProject> getExcavationProjectsByUser(User user) throws DAOException {
//        List<ExcavationProject> projectsList = new ArrayList<ExcavationProject>();
//        //find the faces of this user
//        LocalTunnelDAO localTunnelDAO = DAOFactory.getInstance(mContext).getTunnelDAO(DAOFactory.Flavour.SQLLITE);
//        List<Tunnel> tunnels = localTunnelDAO.getTunnelsByUser(user);
//        //find the correspondant tunnels
//        for (Tunnel currentTunnel : tunnels) {
//            ExcavationProject project = currentTunnel.getProject();
//            projectsList.add(project);
//        }
//        return projectsList;
//    }






}
