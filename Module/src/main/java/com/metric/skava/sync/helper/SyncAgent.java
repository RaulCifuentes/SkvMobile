package com.metric.skava.sync.helper;

import android.app.Activity;
import android.content.Context;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreStatus;
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
import com.metric.skava.data.dao.LocalDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.LocalDiscontinuityShapeDAO;
import com.metric.skava.data.dao.LocalDiscontinuityTypeDAO;
import com.metric.skava.data.dao.LocalDiscontinuityWaterDAO;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.RemoteClientDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityShapeDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityTypeDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityWaterDAO;
import com.metric.skava.data.dao.RemoteExcavationMethodDAO;
import com.metric.skava.data.dao.RemoteExcavationProjectDAO;
import com.metric.skava.data.dao.RemoteExcavationSectionDAO;
import com.metric.skava.data.dao.RemoteRoleDAO;
import com.metric.skava.data.dao.RemoteTunnelDAO;
import com.metric.skava.data.dao.RemoteTunnelFaceDAO;
import com.metric.skava.data.dao.RemoteUserDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.List;

/**
 * Created by metricboy on 4/3/14.
 */
public class SyncAgent {

    static SyncAgent instance;
    private final DAOFactory daoFactory;
    private final Context mContext;

    public DatastoreHelper getDatastoreHelper() {
        return datastoreHelper;
    }


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
        DbxDatastore datastore = datastoreHelper.getDatastore();
        DbxDatastoreStatus syncStatus = datastore.getSyncStatus();
        return syncStatus.hasIncoming;
    }

    public boolean isAlreadyLinked(){
        return getDatastoreHelper().getAccountManager().hasLinkedAccount();
    }

    public void sleep() {
        datastoreHelper.goSleep();
    }


    public boolean downloadGlobalData() throws DAOException {
        boolean success;
        try {
            clearRoles();
            syncRoles();
            clearUsers();
            syncUsers();
            clearMethods();
            syncMethods();
            clearSections();
            syncSections();
            clearDiscontinuityTypes();
            syncDiscontinuityTypes();
            clearDiscontinuityRelevances();
            syncDiscontinuityRelevances();
            clearDiscontinuityWaters();
            syncDiscontinuityWaters();
            clearDiscontinuityShapes();
            syncDiscontinuityShapes();
            success = true;
        } catch (DAOException e) {
            throw e;
        }
        return success;
    }



    public boolean downloadNonSpecificData() throws DAOException {
        boolean success;
        try {
            clearClients();
            syncClients();
            clearProjects();
            syncProjects();
            clearTunnels();
            syncTunnels();
            clearFaces();
            syncFaces();
            success = true;
        } catch (DAOException e) {
            throw e;
        }
        return success;

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

    private void clearDiscontinuityTypes() throws DAOException {
        LocalDiscontinuityTypeDAO sqlLiteDiscontinuityTypeDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        sqlLiteDiscontinuityTypeDAO.deleteAllDiscontinuityTypes();
    }


    private void clearDiscontinuityShapes() throws DAOException {
        LocalDiscontinuityShapeDAO sqlLiteDiscontinuityShapeDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        sqlLiteDiscontinuityShapeDAO.deleteAllDiscontinuityShapes();
    }

    private void clearDiscontinuityRelevances() throws DAOException {
        LocalDiscontinuityRelevanceDAO sqlLiteDiscontinuityRelevanceDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        sqlLiteDiscontinuityRelevanceDAO.deleteAllDiscontinuityRelevances();
    }

    private void clearDiscontinuityWaters() throws DAOException {
        LocalDiscontinuityWaterDAO sqlLiteDiscontinuityWaterDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        sqlLiteDiscontinuityWaterDAO.deleteAllDiscontinuityWaters();
    }

    private void syncDiscontinuityTypes() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityTypeDAO dropBoxDiscontinuityTypeDAO = daoFactory.getRemoteDiscontinuityTypeDAO();
        List<DiscontinuityType> downloadedDiscontinuityTypes = dropBoxDiscontinuityTypeDAO.getAllDiscontinuityTypes();
        //Write into the SQLLite
        LocalDiscontinuityTypeDAO sqlLiteDiscontinuityTypeDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        for (DiscontinuityType downloadedDiscontinuityType : downloadedDiscontinuityTypes) {
            sqlLiteDiscontinuityTypeDAO.saveDiscontinuityType(downloadedDiscontinuityType);
        }
    }

    private void syncDiscontinuityWaters() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityWaterDAO dropBoxDiscontinuityWaterDAO = daoFactory.getRemoteDiscontinuityWaterDAO();
        List<DiscontinuityWater> downloadedDiscontinuityWaters = dropBoxDiscontinuityWaterDAO.getAllDiscontinuityWaters();
        //Write into the SQLLite
        LocalDiscontinuityWaterDAO sqlLiteDiscontinuityWaterDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        for (DiscontinuityWater downloadedDiscontinuityWater : downloadedDiscontinuityWaters) {
            sqlLiteDiscontinuityWaterDAO.saveDiscontinuityWater(downloadedDiscontinuityWater);
        }
    }

    private void syncDiscontinuityShapes() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityShapeDAO dropBoxDiscontinuityShapeDAO = daoFactory.getRemoteDiscontinuityShapeDAO();
        List<DiscontinuityShape> downloadedDiscontinuityShapes = dropBoxDiscontinuityShapeDAO.getAllDiscontinuityShapes();
        //Write into the SQLLite
        LocalDiscontinuityShapeDAO sqlLiteDiscontinuityShapeDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        for (DiscontinuityShape downloadedDiscontinuityShape : downloadedDiscontinuityShapes) {
            sqlLiteDiscontinuityShapeDAO.saveDiscontinuityShape(downloadedDiscontinuityShape);
        }
    }

    private void syncDiscontinuityRelevances() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityRelevanceDAO dropBoxDiscontinuityRelevanceDAO = daoFactory.getRemoteDiscontinuityRelevanceDAO();
        List<DiscontinuityRelevance> downloadedDiscontinuityRelevances = dropBoxDiscontinuityRelevanceDAO.getAllDiscontinuityRelevances();
        //Write into the SQLLite
        LocalDiscontinuityRelevanceDAO sqlLiteDiscontinuityRelevanceDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        for (DiscontinuityRelevance downloadedDiscontinuityRelevance : downloadedDiscontinuityRelevances) {
            sqlLiteDiscontinuityRelevanceDAO.saveDiscontinuityRelevance(downloadedDiscontinuityRelevance);
        }
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

