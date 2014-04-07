package com.metric.skava.sync.helper;

import android.app.Activity;
import android.content.Context;

import com.metric.skava.app.model.Client;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalClientDAO;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.RemoteClientDAO;
import com.metric.skava.data.dao.RemoteExcavationMethodDAO;
import com.metric.skava.data.dao.RemoteExcavationProjectDAO;
import com.metric.skava.data.dao.RemoteExcavationSectionDAO;
import com.metric.skava.data.dao.RemoteRoleDAO;
import com.metric.skava.data.dao.RemoteTunnelDAO;
import com.metric.skava.data.dao.RemoteTunnelFaceDAO;
import com.metric.skava.data.dao.RemoteUserDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;

import java.util.List;

/**
 * Created by metricboy on 4/3/14.
 */
public class SyncAgent {

    static SyncAgent instance;
    private final DAOFactory daoFactory;
    private final Context mContext;
    private DatastoreHelper datastoreHelper;

    public static SyncAgent getInstance(Context appContext, Activity dropboxLinkAccountActivity) throws DAOException {
        if (instance == null) {
            instance = new SyncAgent(appContext, dropboxLinkAccountActivity);
        }
        return instance;
    }

    private SyncAgent(Context context, Activity dropboxLinkAccountActivity) throws DAOException {
        mContext = context;
        daoFactory = DAOFactory.getInstance(mContext);
        datastoreHelper = DatastoreHelper.getInstance(mContext, dropboxLinkAccountActivity);
        datastoreHelper.wakeUp();

    }

    public boolean shouldUpdate() throws DAOException {
        return datastoreHelper.getDatastore().getSyncStatus().hasIncoming;
    }

    public void sleep() {
        datastoreHelper.goSleep();
    }


    public void downloadGlobalData() throws DAOException {
        clearRoles();
        syncRoles();
        clearUsers();
        syncUsers();
        clearMethods();
        syncMethods();
        clearSections();
        syncSections();
    }


    public void downloadNonSpecificData() throws DAOException {
        clearClients();
        syncClients();
        clearProjects();
        syncProjects();
        clearTunnels();
        syncTunnels();
        clearFaces();
        syncFaces();
    }

    //TODO Use the user information to pull just the faces, tunnels, projects anc clients for that user
    public void downloadSpecificData(User user) throws DAOException {
        syncFacesCascade(user);
    }

    private void clearSections() throws DAOException {
        LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE);
        sqlLiteLocalUserDAO.deleteAllUsers();
    }

    private void clearMethods() throws DAOException {
        LocalExcavationMethodDAO sqlLiteMethodDAO = daoFactory.getLocalExcavationMethodDAO(DAOFactory.Flavour.SQLLITE);
        sqlLiteMethodDAO.deleteAllExcavationMethods();
    }

    private void clearUsers() throws DAOException {
        LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE);
        sqlLiteLocalUserDAO.deleteAllUsers();
    }

    private void clearRoles() throws DAOException {
        LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO(DAOFactory.Flavour.SQLLITE);
        sqlLiteLocalRoleDAO.deleteAllRoles();
    }


    private void syncRoles() throws DAOException {
        //Read from DropBox
        RemoteRoleDAO dropBoxRemoteRoleDAO = daoFactory.getRemoteRoleDAO(DAOFactory.Flavour.DROPBOX);
        List<Role> downloadedMethods = dropBoxRemoteRoleDAO.getAllRoles();
        //Write into the SQLLite
        LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO(DAOFactory.Flavour.SQLLITE);
        for (Role downloadedMethod : downloadedMethods) {
            sqlLiteLocalRoleDAO.saveRole(downloadedMethod);
        }
    }

    private void syncUsers() throws DAOException {
        //Read from DropBox
        RemoteUserDAO dropBoxUserDAO = daoFactory.getRemoteUserDAO(DAOFactory.Flavour.DROPBOX);
        List<User> downloadedUsers = dropBoxUserDAO.getAllUsers();
        //Write into the SQLLite
        LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE);
        for (User downloadedUser : downloadedUsers) {
            sqlLiteLocalUserDAO.saveUser(downloadedUser);
        }
    }

    private void syncSections() throws DAOException {
        //Read from DropBox
        RemoteExcavationSectionDAO dropBoxSectionDAO = daoFactory.getRemoteExcavationSectionDAO(DAOFactory.Flavour.DROPBOX);
        List<ExcavationSection> downloadedSections = dropBoxSectionDAO.getAllExcavationSections();
        //Write into the SQLLite
        LocalExcavationSectionDAO sqlLiteSectionDAO = daoFactory.getLocalExcavationSectionDAO(DAOFactory.Flavour.SQLLITE);
        for (ExcavationSection downloadedSection : downloadedSections) {
            sqlLiteSectionDAO.saveExcavationSection(downloadedSection);
        }
    }

    private void syncMethods() throws DAOException {
        /**Update the excavation methods data*/
        RemoteExcavationMethodDAO dropBoxMethodDAO = daoFactory.getRemoteExcavationMethodDAO(DAOFactory.Flavour.DROPBOX);
        LocalExcavationMethodDAO sqlLiteMethodDAO = daoFactory.getLocalExcavationMethodDAO(DAOFactory.Flavour.SQLLITE);
        //Read from DropBox
        List<ExcavationMethod> downloadedMethods = dropBoxMethodDAO.getAllExcavationMethods();
        for (ExcavationMethod downloadedMethod : downloadedMethods) {
            //Write into the SQLLite
            sqlLiteMethodDAO.saveExcavationMethod(downloadedMethod);
        }
    }


    private void clearFaces() throws DAOException {
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
        sqlLiteFaceDAO.deleteAllTunnelFaces();
    }

    private void clearTunnels() throws DAOException {
        LocalTunnelDAO sqlLiteLocalTunnelDAO = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE);
        sqlLiteLocalTunnelDAO.deleteAllTunnels();
    }

    private void clearProjects() throws DAOException {
        LocalExcavationProjectDAO sqlLiteProjectDAO = daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE);
        sqlLiteProjectDAO.deleteAllExcavationProjects();
    }

    private void clearClients() throws DAOException {
        LocalClientDAO sqlLiteLocalClientDAO = daoFactory.getLocalClientDAO(DAOFactory.Flavour.SQLLITE);
        sqlLiteLocalClientDAO.deleteAllClients();
    }



    private void syncClients() throws DAOException {
        //Read from DropBox
        RemoteClientDAO dropBoxClientDAO = daoFactory.getRemoteClientDAO(DAOFactory.Flavour.DROPBOX);
        List<Client> downloadedClients = dropBoxClientDAO.getAllClients();
        //Write into the SQLLite
        LocalClientDAO sqlLiteLocalClientDAO = daoFactory.getLocalClientDAO(DAOFactory.Flavour.SQLLITE);
        for (Client downloadedClient : downloadedClients) {
            sqlLiteLocalClientDAO.saveClient(downloadedClient);
        }
    }


    private void syncProjects() throws DAOException {
        //Read from DropBox
        RemoteExcavationProjectDAO dropBoxProjectDAO = daoFactory.getRemoteExcavationProjectDAO(DAOFactory.Flavour.DROPBOX);
        List<ExcavationProject> downloadedProjects = dropBoxProjectDAO.getAllExcavationProjects();
        //Write into the SQLLite
        LocalExcavationProjectDAO sqlLiteProjectDAO = daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE);
        for (ExcavationProject downloadedProject : downloadedProjects) {
            sqlLiteProjectDAO.saveExcavationProject(downloadedProject);
        }
    }

    private void syncTunnels() throws DAOException {
        //Read from DropBox
        RemoteTunnelDAO dropBoxTunnelDAO = daoFactory.getRemoteTunnelDAO(DAOFactory.Flavour.DROPBOX);
        List<Tunnel> downloadedTunnels = dropBoxTunnelDAO.getAllTunnels();
        //Write into the SQLLite
        LocalTunnelDAO sqlLiteLocalTunnelDAO = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE);
        for (Tunnel downloadedTunnel : downloadedTunnels) {
            sqlLiteLocalTunnelDAO.saveTunnel(downloadedTunnel);
        }
    }


    private void syncFaces() throws DAOException {
        //Read from DropBox
        RemoteTunnelFaceDAO dropBoxFaceDAO = daoFactory.getRemoteTunnelFaceDAO(DAOFactory.Flavour.DROPBOX);
        List<TunnelFace> downloadedFaces = dropBoxFaceDAO.getAllTunnelFaces();
        //Write into the SQLLite
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
        for (TunnelFace downloadedFace : downloadedFaces) {
            sqlLiteFaceDAO.saveTunnelFace(downloadedFace);
        }
    }



    private void syncFacesCascade(User user) throws DAOException {
        //Read from DropBox
        RemoteTunnelFaceDAO dropBoxFaceDAO = daoFactory.getRemoteTunnelFaceDAO(DAOFactory.Flavour.DROPBOX);
        List<TunnelFace> downloadedFaces = dropBoxFaceDAO.getTunnelFacesByUser(user);
        //Write into the SQLLite
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
        for (TunnelFace downloadedFace : downloadedFaces) {
            sqlLiteFaceDAO.saveTunnelFace(downloadedFace);
        }
    }


}

