package com.metric.skava.sync.helper;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Client;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalApertureDAO;
import com.metric.skava.data.dao.LocalClientDAO;
import com.metric.skava.data.dao.LocalDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.LocalDiscontinuityShapeDAO;
import com.metric.skava.data.dao.LocalDiscontinuityTypeDAO;
import com.metric.skava.data.dao.LocalDiscontinuityWaterDAO;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.LocalGroundwaterDAO;
import com.metric.skava.data.dao.LocalInfillingDAO;
import com.metric.skava.data.dao.LocalOrientationDiscontinuitiesDAO;
import com.metric.skava.data.dao.LocalPersistenceDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.LocalRoughnessDAO;
import com.metric.skava.data.dao.LocalSpacingDAO;
import com.metric.skava.data.dao.LocalStrengthDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.LocalWeatheringDAO;
import com.metric.skava.data.dao.RemoteApertureDAO;
import com.metric.skava.data.dao.RemoteClientDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityShapeDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityTypeDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityWaterDAO;
import com.metric.skava.data.dao.RemoteExcavationMethodDAO;
import com.metric.skava.data.dao.RemoteExcavationProjectDAO;
import com.metric.skava.data.dao.RemoteExcavationSectionDAO;
import com.metric.skava.data.dao.RemoteInfillingDAO;
import com.metric.skava.data.dao.RemotePersistenceDAO;
import com.metric.skava.data.dao.RemoteRoleDAO;
import com.metric.skava.data.dao.RemoteRoughnessDAO;
import com.metric.skava.data.dao.RemoteSpacingDAO;
import com.metric.skava.data.dao.RemoteStrengthDAO;
import com.metric.skava.data.dao.RemoteTunnelDAO;
import com.metric.skava.data.dao.RemoteTunnelFaceDAO;
import com.metric.skava.data.dao.RemoteUserDAO;
import com.metric.skava.data.dao.RemoteWeatheringDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.List;

/**
 * Created by metricboy on 4/26/14.
 */
public class SyncHelper {

    private SkavaContext skavaContext;
    private static SyncHelper instance;
    private DAOFactory daoFactory;

    public static SyncHelper getInstance(SkavaContext skavaContext) {
        if (instance == null) {
            instance = new SyncHelper(skavaContext);
        }
        return instance;
    }

    private SyncHelper(SkavaContext skavaContext) {
        this.skavaContext = skavaContext;
        this.daoFactory = skavaContext.getDAOFactory();
    }


    public boolean downloadGlobalData() throws DAOException {
        boolean success;
        try {
            clearRoles();
            syncRoles();

            clearMethods();
            syncMethods();

            clearSections();
            syncSections();

            clearDiscontinuityTypes();
            syncDiscontinuityTypes();

            clearDiscontinuityRelevances();
            syncDiscontinuityRelevances();

            clearSpacings();
            syncSpacings();

            clearPersistences();
            syncPersistences();

            clearApertures();
            syncApertures();

            clearDiscontinuityShapes();
            syncDiscontinuityShapes();

            clearRoughnesses();
            syncRoughnesses();

            clearInfillings();
            syncInfillings();

            clearWeatherings();
            syncWeatherings();

            clearDiscontinuityWaters();
            syncDiscontinuityWaters();

            clearStrengths();
            syncStrengths();

            clearGroudwaters();
            syncGroundwaters();

            clearOrientation();
            syncOrientation();

//            clearJn();
//            syncJn();
//
//            clearJr();
//            syncJr();
//
//            clearJa();
//            syncJa();
//
//            clearJw();
//            syncJw();
//
//            clearSRF();
//            syncSRF();
//
//            clearFractureTypes();
//            syncFractureTypes();



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
            //HEADS UP: on this strategy users shuold be loaded last coz the assembling will look up some roles and faces DAOs
            //This is not exactly th best option as it will load the entire data and not just what this user can see
            //TODO evaluate and implement the userSpecifidData
            clearUsers();
            syncUsers();
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
        LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO();
        sqlLiteLocalUserDAO.deleteAllUsers();
    }

    private void syncSections() throws DAOException {
        //Read from DropBox
        RemoteExcavationSectionDAO dropBoxSectionDAO = daoFactory.getRemoteExcavationSectionDAO(DAOFactory.Flavour.DROPBOX);
        List<ExcavationSection> downloadedSections = dropBoxSectionDAO.getAllExcavationSections();
        //Write into the SQLLite
        LocalExcavationSectionDAO sqlLiteSectionDAO = daoFactory.getLocalExcavationSectionDAO();
        for (ExcavationSection downloadedSection : downloadedSections) {
            sqlLiteSectionDAO.saveExcavationSection(downloadedSection);
        }
    }

    private void clearMethods() throws DAOException {
        LocalExcavationMethodDAO sqlLiteMethodDAO = daoFactory.getLocalExcavationMethodDAO();
        sqlLiteMethodDAO.deleteAllExcavationMethods();
    }

    private void syncMethods() throws DAOException {
        /**Update the excavation methods data*/
        RemoteExcavationMethodDAO dropBoxMethodDAO = daoFactory.getRemoteExcavationMethodDAO(DAOFactory.Flavour.DROPBOX);
        LocalExcavationMethodDAO sqlLiteMethodDAO = daoFactory.getLocalExcavationMethodDAO();
        //Read from DropBox
        List<ExcavationMethod> downloadedMethods = dropBoxMethodDAO.getAllExcavationMethods();
        for (ExcavationMethod downloadedMethod : downloadedMethods) {
            //Write into the SQLLite
            sqlLiteMethodDAO.saveExcavationMethod(downloadedMethod);
        }
    }

    private void clearRoles() throws DAOException {
        LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO();
        sqlLiteLocalRoleDAO.deleteAllRoles();
    }


    private void syncRoles() throws DAOException {
        //Read from DropBox
        RemoteRoleDAO dropBoxRemoteRoleDAO = daoFactory.getRemoteRoleDAO(DAOFactory.Flavour.DROPBOX);
        List<Role> downloadedMethods = dropBoxRemoteRoleDAO.getAllRoles();
        //Write into the SQLLite
        LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO();
        for (Role downloadedMethod : downloadedMethods) {
            sqlLiteLocalRoleDAO.saveRole(downloadedMethod);
        }
    }

    private void clearUsers() throws DAOException {
        LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO();
        sqlLiteLocalUserDAO.deleteAllUsers();
    }

    private void syncUsers() throws DAOException {
        //Read from DropBox
        RemoteUserDAO dropBoxUserDAO = daoFactory.getRemoteUserDAO(DAOFactory.Flavour.DROPBOX);
        List<User> downloadedUsers = dropBoxUserDAO.getAllUsers();
        //Write into the SQLLite
        LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO();
        for (User downloadedUser : downloadedUsers) {
            sqlLiteLocalUserDAO.saveUser(downloadedUser);
        }
    }


    private void clearTunnels() throws DAOException {
        LocalTunnelDAO sqlLiteLocalTunnelDAO = daoFactory.getLocalTunnelDAO();
        sqlLiteLocalTunnelDAO.deleteAllTunnels();
    }

    private void syncTunnels() throws DAOException {
        //Read from DropBox
        RemoteTunnelDAO dropBoxTunnelDAO = daoFactory.getRemoteTunnelDAO(DAOFactory.Flavour.DROPBOX);
        List<Tunnel> downloadedTunnels = dropBoxTunnelDAO.getAllTunnels();
        //Write into the SQLLite
        LocalTunnelDAO sqlLiteLocalTunnelDAO = daoFactory.getLocalTunnelDAO();
        for (Tunnel downloadedTunnel : downloadedTunnels) {
            sqlLiteLocalTunnelDAO.saveTunnel(downloadedTunnel);
        }
    }

    private void clearProjects() throws DAOException {
        LocalExcavationProjectDAO sqlLiteProjectDAO = daoFactory.getLocalExcavationProjectDAO();
        sqlLiteProjectDAO.deleteAllExcavationProjects();
    }

    private void syncProjects() throws DAOException {
        //Read from DropBox
        RemoteExcavationProjectDAO dropBoxProjectDAO = daoFactory.getRemoteExcavationProjectDAO(DAOFactory.Flavour.DROPBOX);
        List<ExcavationProject> downloadedProjects = dropBoxProjectDAO.getAllExcavationProjects();
        //Write into the SQLLite
        LocalExcavationProjectDAO sqlLiteProjectDAO = daoFactory.getLocalExcavationProjectDAO();
        for (ExcavationProject downloadedProject : downloadedProjects) {
            sqlLiteProjectDAO.saveExcavationProject(downloadedProject);
        }
    }


    private void clearFaces() throws DAOException {
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        sqlLiteFaceDAO.deleteAllTunnelFaces();
    }

    private void syncFaces() throws DAOException {
        //Read from DropBox
        RemoteTunnelFaceDAO dropBoxFaceDAO = daoFactory.getRemoteTunnelFaceDAO(DAOFactory.Flavour.DROPBOX);
        List<TunnelFace> downloadedFaces = dropBoxFaceDAO.getAllTunnelFaces();
        //Write into the SQLLite
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        for (TunnelFace downloadedFace : downloadedFaces) {
            sqlLiteFaceDAO.saveTunnelFace(downloadedFace);
        }
    }

    private void syncFacesCascade(User user) throws DAOException {
        //Read from DropBox
        RemoteTunnelFaceDAO dropBoxFaceDAO = daoFactory.getRemoteTunnelFaceDAO(DAOFactory.Flavour.DROPBOX);
        List<TunnelFace> downloadedFaces = dropBoxFaceDAO.getTunnelFacesByUser(user);
        //Write into the SQLLite
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        for (TunnelFace downloadedFace : downloadedFaces) {
            sqlLiteFaceDAO.saveTunnelFace(downloadedFace);
        }
    }

    private void clearClients() throws DAOException {
        LocalClientDAO sqlLiteLocalClientDAO = daoFactory.getLocalClientDAO();
        sqlLiteLocalClientDAO.deleteAllClients();
    }

    private void syncClients() throws DAOException {
        //Read from DropBox
        RemoteClientDAO dropBoxClientDAO = daoFactory.getRemoteClientDAO(DAOFactory.Flavour.DROPBOX);
        List<Client> downloadedClients = dropBoxClientDAO.getAllClients();
        //Write into the SQLLite
        LocalClientDAO sqlLiteLocalClientDAO = daoFactory.getLocalClientDAO();
        for (Client downloadedClient : downloadedClients) {
            sqlLiteLocalClientDAO.saveClient(downloadedClient);
        }
    }

    private void clearDiscontinuityTypes() throws DAOException {
        LocalDiscontinuityTypeDAO sqlLiteDiscontinuityTypeDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        sqlLiteDiscontinuityTypeDAO.deleteAllDiscontinuityTypes();
    }

    private void syncDiscontinuityTypes() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityTypeDAO dropBoxDiscontinuityTypeDAO = daoFactory.getRemoteDiscontinuityTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<DiscontinuityType> downloadedDiscontinuityTypes = dropBoxDiscontinuityTypeDAO.getAllDiscontinuityTypes();
        //Write into the SQLLite
        LocalDiscontinuityTypeDAO sqlLiteDiscontinuityTypeDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        for (DiscontinuityType downloadedDiscontinuityType : downloadedDiscontinuityTypes) {
            sqlLiteDiscontinuityTypeDAO.saveDiscontinuityType(downloadedDiscontinuityType);
        }
    }


    private void clearDiscontinuityRelevances() throws DAOException {
        LocalDiscontinuityRelevanceDAO sqlLiteDiscontinuityRelevanceDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        sqlLiteDiscontinuityRelevanceDAO.deleteAllDiscontinuityRelevances();
    }

    private void syncDiscontinuityRelevances() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityRelevanceDAO dropBoxDiscontinuityRelevanceDAO = daoFactory.getRemoteDiscontinuityRelevanceDAO(DAOFactory.Flavour.DROPBOX);
        List<DiscontinuityRelevance> downloadedDiscontinuityRelevances = dropBoxDiscontinuityRelevanceDAO.getAllDiscontinuityRelevances();
        //Write into the SQLLite
        LocalDiscontinuityRelevanceDAO sqlLiteDiscontinuityRelevanceDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        for (DiscontinuityRelevance downloadedDiscontinuityRelevance : downloadedDiscontinuityRelevances) {
            sqlLiteDiscontinuityRelevanceDAO.saveDiscontinuityRelevance(downloadedDiscontinuityRelevance);
        }
    }


    private void clearStrengths() throws DAOException {
        LocalStrengthDAO sqlLiteStrengthDAO = daoFactory.getLocalStrengthDAO();
        sqlLiteStrengthDAO.deleteAllStrengths();
    }

    private void syncStrengths() throws DAOException {
        //Read from DropBox
        RemoteStrengthDAO dropBoxStrengthDAO = daoFactory.getRemoteStrengthDAO(DAOFactory.Flavour.DROPBOX);
        List<StrengthOfRock> dowloadedStrengths = dropBoxStrengthDAO.getAllStrengths();
        //Write into the SQLLite
        LocalStrengthDAO sqlLiteStrengthDAO = daoFactory.getLocalStrengthDAO();
        for (StrengthOfRock dowloadedStrength : dowloadedStrengths) {
            sqlLiteStrengthDAO.saveStrength(dowloadedStrength);
        }
    }

    private void clearGroudwaters() throws DAOException {
        LocalGroundwaterDAO sqlLiteGroudwaterDAO = daoFactory.getLocalGroundwaterDAO();
        sqlLiteGroudwaterDAO.deleteAllGroundwaters();
    }

    private void syncGroundwaters() {

    }

    private void clearOrientation() throws DAOException {
        LocalOrientationDiscontinuitiesDAO sqlLiteGroudwaterDAO = daoFactory.getLocalOrientationDiscontinuitiesDAO();
        sqlLiteGroudwaterDAO.deleteAllOrientationDiscontinuities();
    }

    private void syncOrientation() throws DAOException {

    }


    private void clearSpacings() throws DAOException {
        LocalSpacingDAO sqlLiteSpacingDAO = daoFactory.getLocalSpacingDAO();
        sqlLiteSpacingDAO.deleteAllSpacings();

    }

    private void syncSpacings() throws DAOException {
        //Read from DropBox
        RemoteSpacingDAO dropBoxSpacingDAO = daoFactory.getRemoteSpacingDAO(DAOFactory.Flavour.DROPBOX);
        List<Spacing> dowloadedSpacings = dropBoxSpacingDAO.getAllSpacings();
        //Write into the SQLLite
        LocalSpacingDAO sqlLiteSpacingDAO = daoFactory.getLocalSpacingDAO();
        for (Spacing dowloadedSpacing : dowloadedSpacings) {
            sqlLiteSpacingDAO.saveSpacing(dowloadedSpacing);
        }
    }

    private void clearPersistences() throws DAOException {
        LocalPersistenceDAO sqlLitePersistenceDAO = daoFactory.getLocalPersistenceDAO();
        sqlLitePersistenceDAO.deleteAllPersistences();
    }

    private void syncPersistences() throws DAOException {
        //Read from DropBox
        RemotePersistenceDAO dropBoxPersistenceDAO = daoFactory.getRemotePersistenceDAO(DAOFactory.Flavour.DROPBOX);
        List<Persistence> downloadedPersistences = dropBoxPersistenceDAO.getAllPersistences();
        //Write into the SQLLite
        LocalPersistenceDAO sqlLitePersistenceDAO = daoFactory.getLocalPersistenceDAO();
        for (Persistence downloadedPersistence : downloadedPersistences) {
            sqlLitePersistenceDAO.savePersistence(downloadedPersistence);
        }
    }

    private void clearApertures() throws DAOException {
        LocalApertureDAO sqlLiteApertureDAO = daoFactory.getLocalApertureDAO();
        sqlLiteApertureDAO.deleteAllApertures();
    }

    private void syncApertures() throws DAOException {
        //Read from DropBox
        RemoteApertureDAO dropBoxApertureDAO = daoFactory.getRemoteApertureDAO(DAOFactory.Flavour.DROPBOX);
        List<Aperture> downloadedApertures = dropBoxApertureDAO.getAllApertures();
        //Write into the SQLLite
        LocalApertureDAO sqlLiteApertureDAO = daoFactory.getLocalApertureDAO();
        for (Aperture downloadedAperture : downloadedApertures) {
            sqlLiteApertureDAO.saveAperture(downloadedAperture);
        }
    }


    private void clearDiscontinuityShapes() throws DAOException {
        LocalDiscontinuityShapeDAO sqlLiteDiscontinuityShapeDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        sqlLiteDiscontinuityShapeDAO.deleteAllDiscontinuityShapes();
    }

    private void syncDiscontinuityShapes() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityShapeDAO dropBoxDiscontinuityShapeDAO = daoFactory.getRemoteDiscontinuityShapeDAO(DAOFactory.Flavour.DROPBOX);
        List<DiscontinuityShape> downloadedDiscontinuityShapes = dropBoxDiscontinuityShapeDAO.getAllDiscontinuityShapes();
        //Write into the SQLLite
        LocalDiscontinuityShapeDAO sqlLiteDiscontinuityShapeDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        for (DiscontinuityShape downloadedDiscontinuityShape : downloadedDiscontinuityShapes) {
            sqlLiteDiscontinuityShapeDAO.saveDiscontinuityShape(downloadedDiscontinuityShape);
        }
    }

    private void clearRoughnesses() throws DAOException {
        LocalRoughnessDAO sqlLiteRoughnessDAO = daoFactory.getLocalRoughnessDAO();
        sqlLiteRoughnessDAO.deleteAllRoughnesses();
    }


    private void syncRoughnesses() throws DAOException {
        //Read from DropBox
        RemoteRoughnessDAO dropBoxRoughnessDAO = daoFactory.getRemoteRoughnessDAO(DAOFactory.Flavour.DROPBOX);
        List<Roughness> downloadedRoughnesss = dropBoxRoughnessDAO.getAllRoughnesses();
        //Write into the SQLLite
        LocalRoughnessDAO sqlLiteRoughnessDAO = daoFactory.getLocalRoughnessDAO();
        for (Roughness downloadedRoughness : downloadedRoughnesss) {
            sqlLiteRoughnessDAO.saveRoughness(downloadedRoughness);
        }
    }

    private void clearInfillings() throws DAOException {
        LocalInfillingDAO sqlLiteInfillingDAO = daoFactory.getLocalInfillingDAO();
        sqlLiteInfillingDAO.deleteAllInfillings();
    }

    private void syncInfillings() throws DAOException {
        //Read from DropBox
        RemoteInfillingDAO dropBoxInfillingDAO = daoFactory.getRemoteInfillingDAO(DAOFactory.Flavour.DROPBOX);
        List<Infilling> downloadedInfillings = dropBoxInfillingDAO.getAllInfillings();
        //Write into the SQLLite
        LocalInfillingDAO sqlLiteInfillingDAO = daoFactory.getLocalInfillingDAO();
        for (Infilling downloadedInfilling : downloadedInfillings) {
            sqlLiteInfillingDAO.saveInfilling(downloadedInfilling);
        }
    }

    private void clearWeatherings() throws DAOException {
        LocalWeatheringDAO sqlLiteWeatheringDAO = daoFactory.getLocalWeatheringDAO();
        sqlLiteWeatheringDAO.deleteAllWeatherings();
    }

    private void syncWeatherings() throws DAOException {
        //Read from DropBox
        RemoteWeatheringDAO dropBoxWeatheringDAO = daoFactory.getRemoteWeatheringDAO(DAOFactory.Flavour.DROPBOX);
        List<Weathering> downloadedWeatherings = dropBoxWeatheringDAO.getAllWeatherings();
        //Write into the SQLLite
        LocalWeatheringDAO sqlLiteWeatheringDAO = daoFactory.getLocalWeatheringDAO();
        for (Weathering downloadedWeathering : downloadedWeatherings) {
            sqlLiteWeatheringDAO.saveWeathering(downloadedWeathering);
        }
    }

    private void clearDiscontinuityWaters() throws DAOException {
        LocalDiscontinuityWaterDAO sqlLiteDiscontinuityWaterDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        sqlLiteDiscontinuityWaterDAO.deleteAllDiscontinuityWaters();
    }

    private void syncDiscontinuityWaters() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityWaterDAO dropBoxDiscontinuityWaterDAO = daoFactory.getRemoteDiscontinuityWaterDAO(DAOFactory.Flavour.DROPBOX);
        List<DiscontinuityWater> downloadedDiscontinuityWaters = dropBoxDiscontinuityWaterDAO.getAllDiscontinuityWaters();
        //Write into the SQLLite
        LocalDiscontinuityWaterDAO sqlLiteDiscontinuityWaterDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        for (DiscontinuityWater downloadedDiscontinuityWater : downloadedDiscontinuityWaters) {
            sqlLiteDiscontinuityWaterDAO.saveDiscontinuityWater(downloadedDiscontinuityWater);
        }
    }


}
