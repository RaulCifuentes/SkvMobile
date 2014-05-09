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
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.calculator.model.Index;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalApertureDAO;
import com.metric.skava.data.dao.LocalArchTypeDAO;
import com.metric.skava.data.dao.LocalBoltTypeDAO;
import com.metric.skava.data.dao.LocalClientDAO;
import com.metric.skava.data.dao.LocalCoverageDAO;
import com.metric.skava.data.dao.LocalDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.LocalDiscontinuityShapeDAO;
import com.metric.skava.data.dao.LocalDiscontinuityTypeDAO;
import com.metric.skava.data.dao.LocalDiscontinuityWaterDAO;
import com.metric.skava.data.dao.LocalEsrDAO;
import com.metric.skava.data.dao.LocalExcavationFactorDAO;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.LocalFractureTypeDAO;
import com.metric.skava.data.dao.LocalGroundwaterDAO;
import com.metric.skava.data.dao.LocalGroupDAO;
import com.metric.skava.data.dao.LocalIndexDAO;
import com.metric.skava.data.dao.LocalInfillingDAO;
import com.metric.skava.data.dao.LocalJaDAO;
import com.metric.skava.data.dao.LocalJnDAO;
import com.metric.skava.data.dao.LocalJrDAO;
import com.metric.skava.data.dao.LocalJwDAO;
import com.metric.skava.data.dao.LocalMeshTypeDAO;
import com.metric.skava.data.dao.LocalOrientationDiscontinuitiesDAO;
import com.metric.skava.data.dao.LocalPermissionDAO;
import com.metric.skava.data.dao.LocalPersistenceDAO;
import com.metric.skava.data.dao.LocalRockQualityDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.LocalRoughnessDAO;
import com.metric.skava.data.dao.LocalShotcreteTypeDAO;
import com.metric.skava.data.dao.LocalSpacingDAO;
import com.metric.skava.data.dao.LocalSrfDAO;
import com.metric.skava.data.dao.LocalStrengthDAO;
import com.metric.skava.data.dao.LocalSupportPatternTypeDAO;
import com.metric.skava.data.dao.LocalSupportRequirementDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.LocalWeatheringDAO;
import com.metric.skava.data.dao.RemoteApertureDAO;
import com.metric.skava.data.dao.RemoteArchTypeDAO;
import com.metric.skava.data.dao.RemoteBoltTypeDAO;
import com.metric.skava.data.dao.RemoteClientDAO;
import com.metric.skava.data.dao.RemoteCoverageDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityShapeDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityTypeDAO;
import com.metric.skava.data.dao.RemoteDiscontinuityWaterDAO;
import com.metric.skava.data.dao.RemoteEsrDAO;
import com.metric.skava.data.dao.RemoteExcavationMethodDAO;
import com.metric.skava.data.dao.RemoteExcavationProjectDAO;
import com.metric.skava.data.dao.RemoteExcavationSectionDAO;
import com.metric.skava.data.dao.RemoteFractureTypeDAO;
import com.metric.skava.data.dao.RemoteGroundwaterDAO;
import com.metric.skava.data.dao.RemoteGroupDAO;
import com.metric.skava.data.dao.RemoteIndexDAO;
import com.metric.skava.data.dao.RemoteInfillingDAO;
import com.metric.skava.data.dao.RemoteJaDAO;
import com.metric.skava.data.dao.RemoteJnDAO;
import com.metric.skava.data.dao.RemoteJrDAO;
import com.metric.skava.data.dao.RemoteJwDAO;
import com.metric.skava.data.dao.RemoteMeshTypeDAO;
import com.metric.skava.data.dao.RemoteOrientationDiscontinuitiesDAO;
import com.metric.skava.data.dao.RemotePersistenceDAO;
import com.metric.skava.data.dao.RemoteRockQualityDAO;
import com.metric.skava.data.dao.RemoteRoleDAO;
import com.metric.skava.data.dao.RemoteRoughnessDAO;
import com.metric.skava.data.dao.RemoteShotcreteTypeDAO;
import com.metric.skava.data.dao.RemoteSpacingDAO;
import com.metric.skava.data.dao.RemoteSrfDAO;
import com.metric.skava.data.dao.RemoteStrengthDAO;
import com.metric.skava.data.dao.RemoteSupportPatternTypeDAO;
import com.metric.skava.data.dao.RemoteSupportRequirementDAO;
import com.metric.skava.data.dao.RemoteTunnelDAO;
import com.metric.skava.data.dao.RemoteTunnelFaceDAO;
import com.metric.skava.data.dao.RemoteUserDAO;
import com.metric.skava.data.dao.RemoteWeatheringDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPatternType;
import com.metric.skava.rockmass.model.FractureType;
import com.metric.skava.rocksupport.model.ESR;
import com.metric.skava.rocksupport.model.SupportRequirement;

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


    public Long downloadGlobalData() throws DAOException {

        Long numRecords = 0L;

        clearRoles();
        numRecords+= syncRoles();

        clearExcavationMethods();
        numRecords+=syncExcavationMethods();

        clearExcavationSections();
        numRecords+=syncExcavationSections();

        clearDiscontinuityTypes();
        numRecords+=syncDiscontinuityTypes();

        clearDiscontinuityRelevances();
        numRecords+=syncDiscontinuityRelevances();

        clearIndexes();
        numRecords+=syncIndexes();

        clearGroups();
        numRecords+=syncGroups();

        clearSpacings();
        numRecords+=syncSpacings();

        clearPersistences();
        numRecords+=syncPersistences();

        clearApertures();
        numRecords+=syncApertures();

        clearDiscontinuityShapes();
        numRecords+=syncDiscontinuityShapes();

        clearRoughnesses();
        numRecords+=syncRoughnesses();

        clearInfillings();
        numRecords+=syncInfillings();

        clearWeatherings();
        numRecords+=syncWeatherings();

        clearDiscontinuityWaters();
        numRecords+=syncDiscontinuityWaters();

        clearStrengths();
        numRecords+=syncStrengths();

        clearGroudwaters();
        numRecords+=syncGroundwaters();

        clearOrientation();
        numRecords+=syncOrientation();

        clearJn();
        numRecords+=syncJn();

        clearJr();
        numRecords+=syncJr();

        clearJa();
        numRecords+=syncJa();

        clearJw();
        numRecords+=syncJw();

        clearSRF();
        numRecords+=syncSRF();

        clearFractureTypes();
        numRecords+=syncFractureTypes();

        clearBoltTypes();
        syncBoltTypes();

        clearShotcreteTypes();
        syncShotcreteTypes();

        clearMeshTypes();
        syncMeshTypes();

        clearCoverages();
        syncCoverages();

        clearArchTypes();
        syncArchTypes();

        clearESRs();
        syncESRs();

        clearSupportPatternTypes();
        syncSupportPatternTypes();

        clearRockQualities();
        syncRockQualities();


        return numRecords;

    }


    public Long downloadUserRelatedData() throws DAOException {
        Long numRecords = 0L;

        clearClients();
        numRecords+= syncClients();

        clearProjects();
        numRecords+= syncProjects();

        clearTunnels();
        numRecords+= syncTunnels();

        //Support Requirements depends on Tunnel
        clearSupportRequirements();
        numRecords+= syncSupportRequirements();

        clearFaces();
        numRecords+= syncFaces();
        //HEADS UP: on this strategy users shuold be loaded last coz the assembling will look up some roles and faces DAOs
        //This is not exactly th best option as it will load the entire data and not just what this user can see
        //TODO evaluate and implement the userSpecifidData
        clearUsers();
        numRecords+= syncUsers();


        return numRecords;
    }

    //TODO Use the user information to pull just the faces, tunnels, projects anc clients for that user
    public void downloadUserRelatedDataFilteredByUser(User user) throws DAOException {
        syncFacesCascade(user);
    }

    private void clearExcavationSections() throws DAOException {
        LocalExcavationSectionDAO sqlLiteLocalSectionDAO = daoFactory.getLocalExcavationSectionDAO();
        sqlLiteLocalSectionDAO.deleteAllExcavationSections();
    }

    private Long syncExcavationSections() throws DAOException {
        //Read from DropBox
        RemoteExcavationSectionDAO dropBoxSectionDAO = daoFactory.getRemoteExcavationSectionDAO(DAOFactory.Flavour.DROPBOX);
        List<ExcavationSection> downloadedSections = dropBoxSectionDAO.getAllExcavationSections();
        //Write into the SQLLite
        LocalExcavationSectionDAO sqlLiteSectionDAO = daoFactory.getLocalExcavationSectionDAO();
        for (ExcavationSection downloadedSection : downloadedSections) {
            sqlLiteSectionDAO.saveExcavationSection(downloadedSection);
        }
        return Long.valueOf(downloadedSections.size());
    }

    private void clearExcavationMethods() throws DAOException {
        LocalExcavationMethodDAO sqlLiteMethodDAO = daoFactory.getLocalExcavationMethodDAO();
        sqlLiteMethodDAO.deleteAllExcavationMethods();
    }

    private Long syncExcavationMethods() throws DAOException {
        /**Update the excavation methods data*/
        RemoteExcavationMethodDAO dropBoxMethodDAO = daoFactory.getRemoteExcavationMethodDAO(DAOFactory.Flavour.DROPBOX);
        LocalExcavationMethodDAO sqlLiteMethodDAO = daoFactory.getLocalExcavationMethodDAO();
        //Read from DropBox
        List<ExcavationMethod> downloadedMethods = dropBoxMethodDAO.getAllExcavationMethods();
        for (ExcavationMethod downloadedMethod : downloadedMethods) {
            //Write into the SQLLite
            sqlLiteMethodDAO.saveExcavationMethod(downloadedMethod);
        }
        return Long.valueOf(downloadedMethods.size());
    }

    private void clearRoles() throws DAOException {
        LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO();
        sqlLiteLocalRoleDAO.deleteAllRoles();
    }


    private Long syncRoles() throws DAOException {
        //Read from DropBox
        RemoteRoleDAO dropBoxRemoteRoleDAO = daoFactory.getRemoteRoleDAO(DAOFactory.Flavour.DROPBOX);
        List<Role> downloadedMethods = dropBoxRemoteRoleDAO.getAllRoles();
        //Write into the SQLLite
        LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO();
        for (Role downloadedMethod : downloadedMethods) {
            sqlLiteLocalRoleDAO.saveRole(downloadedMethod);
        }
        return Long.valueOf(downloadedMethods.size());
    }

    private void clearUsers() throws DAOException {
        LocalUserDAO userDAO = daoFactory.getLocalUserDAO();
        userDAO.deleteAllUsers();

        LocalPermissionDAO permissionsDAO = daoFactory.getLocalPermissionDAO();
        permissionsDAO.deleteAllPermissions();
    }

    private Long syncUsers() throws DAOException {
        //Read from DropBox
        RemoteUserDAO dropBoxUserDAO = daoFactory.getRemoteUserDAO(DAOFactory.Flavour.DROPBOX);
        List<User> downloadedUsers = dropBoxUserDAO.getAllUsers();
        //Write into the SQLLite
        LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO();
        for (User downloadedUser : downloadedUsers) {
            sqlLiteLocalUserDAO.saveUser(downloadedUser);
        }
        return Long.valueOf(downloadedUsers.size());
    }


    private void clearTunnels() throws DAOException {
        LocalTunnelDAO sqlLiteLocalTunnelDAO = daoFactory.getLocalTunnelDAO();
        sqlLiteLocalTunnelDAO.deleteAllTunnels();
        //As the tunnel was deleted, delete on cascade
        clearExcavationFactors();
    }

    private Long syncTunnels() throws DAOException {
        //Read from DropBox
        RemoteTunnelDAO dropBoxTunnelDAO = daoFactory.getRemoteTunnelDAO(DAOFactory.Flavour.DROPBOX);
        List<Tunnel> downloadedTunnels = dropBoxTunnelDAO.getAllTunnels();
        //Write into the SQLLite
        LocalTunnelDAO sqlLiteLocalTunnelDAO = daoFactory.getLocalTunnelDAO();
        for (Tunnel downloadedTunnel : downloadedTunnels) {
            //The saveTunnel() inserts automatically on the ExcavationFactors table
            sqlLiteLocalTunnelDAO.saveTunnel(downloadedTunnel);
        }
        return Long.valueOf(downloadedTunnels.size());
    }

    private void clearProjects() throws DAOException {
        LocalExcavationProjectDAO sqlLiteProjectDAO = daoFactory.getLocalExcavationProjectDAO();
        sqlLiteProjectDAO.deleteAllExcavationProjects();
    }

    private Long syncProjects() throws DAOException {
        //Read from DropBox
        RemoteExcavationProjectDAO dropBoxProjectDAO = daoFactory.getRemoteExcavationProjectDAO(DAOFactory.Flavour.DROPBOX);
        List<ExcavationProject> downloadedProjects = dropBoxProjectDAO.getAllExcavationProjects();
        //Write into the SQLLite
        LocalExcavationProjectDAO sqlLiteProjectDAO = daoFactory.getLocalExcavationProjectDAO();
        for (ExcavationProject downloadedProject : downloadedProjects) {
            sqlLiteProjectDAO.saveExcavationProject(downloadedProject);
        }
        return Long.valueOf(downloadedProjects.size());
    }


    private void clearFaces() throws DAOException {
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        sqlLiteFaceDAO.deleteAllTunnelFaces();
    }

    private Long syncFaces() throws DAOException {
        //Read from DropBox
        RemoteTunnelFaceDAO dropBoxFaceDAO = daoFactory.getRemoteTunnelFaceDAO(DAOFactory.Flavour.DROPBOX);
        List<TunnelFace> downloadedFaces = dropBoxFaceDAO.getAllTunnelFaces();
        //Write into the SQLLite
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        for (TunnelFace downloadedFace : downloadedFaces) {
            sqlLiteFaceDAO.saveTunnelFace(downloadedFace);
        }
        return Long.valueOf(downloadedFaces.size());
    }

    private Long syncFacesCascade(User user) throws DAOException {
        //Read from DropBox
        RemoteTunnelFaceDAO dropBoxFaceDAO = daoFactory.getRemoteTunnelFaceDAO(DAOFactory.Flavour.DROPBOX);
        List<TunnelFace> downloadedFaces = dropBoxFaceDAO.getTunnelFacesByUser(user);
        //Write into the SQLLite
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        for (TunnelFace downloadedFace : downloadedFaces) {
            sqlLiteFaceDAO.saveTunnelFace(downloadedFace);
        }
        return Long.valueOf(downloadedFaces.size());
    }

    private void clearClients() throws DAOException {
        LocalClientDAO sqlLiteLocalClientDAO = daoFactory.getLocalClientDAO();
        sqlLiteLocalClientDAO.deleteAllClients();
    }

    private Long syncClients() throws DAOException {
        //Read from DropBox
        RemoteClientDAO dropBoxClientDAO = daoFactory.getRemoteClientDAO(DAOFactory.Flavour.DROPBOX);
        List<Client> downloadedClients = dropBoxClientDAO.getAllClients();
        //Write into the SQLLite
        LocalClientDAO sqlLiteLocalClientDAO = daoFactory.getLocalClientDAO();
        for (Client downloadedClient : downloadedClients) {
            sqlLiteLocalClientDAO.saveClient(downloadedClient);
        }
        return Long.valueOf(downloadedClients.size());
    }

    private void clearDiscontinuityTypes() throws DAOException {
        LocalDiscontinuityTypeDAO sqlLiteDiscontinuityTypeDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        sqlLiteDiscontinuityTypeDAO.deleteAllDiscontinuityTypes();
    }

    private Long syncDiscontinuityTypes() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityTypeDAO dropBoxDiscontinuityTypeDAO = daoFactory.getRemoteDiscontinuityTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<DiscontinuityType> downloadedDiscontinuityTypes = dropBoxDiscontinuityTypeDAO.getAllDiscontinuityTypes();
        //Write into the SQLLite
        LocalDiscontinuityTypeDAO sqlLiteDiscontinuityTypeDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        for (DiscontinuityType downloadedDiscontinuityType : downloadedDiscontinuityTypes) {
            sqlLiteDiscontinuityTypeDAO.saveDiscontinuityType(downloadedDiscontinuityType);
        }
        return Long.valueOf(downloadedDiscontinuityTypes.size());
    }


    private void clearDiscontinuityRelevances() throws DAOException {
        LocalDiscontinuityRelevanceDAO sqlLiteDiscontinuityRelevanceDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        sqlLiteDiscontinuityRelevanceDAO.deleteAllDiscontinuityRelevances();
    }

    private Long syncDiscontinuityRelevances() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityRelevanceDAO dropBoxDiscontinuityRelevanceDAO = daoFactory.getRemoteDiscontinuityRelevanceDAO(DAOFactory.Flavour.DROPBOX);
        List<DiscontinuityRelevance> downloadedDiscontinuityRelevances = dropBoxDiscontinuityRelevanceDAO.getAllDiscontinuityRelevances();
        //Write into the SQLLite
        LocalDiscontinuityRelevanceDAO sqlLiteDiscontinuityRelevanceDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        for (DiscontinuityRelevance downloadedDiscontinuityRelevance : downloadedDiscontinuityRelevances) {
            sqlLiteDiscontinuityRelevanceDAO.saveDiscontinuityRelevance(downloadedDiscontinuityRelevance);
        }
        return Long.valueOf(downloadedDiscontinuityRelevances.size());
    }


    private void clearIndexes() throws DAOException {
        LocalIndexDAO sqlLiteIndexDAO = daoFactory.getLocalIndexDAO();
        sqlLiteIndexDAO.deleteAllIndexes();
    }

    private Long syncIndexes() throws DAOException {
        //Read from DropBox
        RemoteIndexDAO dropBoxIndexDAO = daoFactory.getRemoteIndexDAO(DAOFactory.Flavour.DROPBOX);
        List<Index> downloadedIndexes = dropBoxIndexDAO.getAllIndexes();
        //Write into the SQLLite
        LocalIndexDAO sqlLiteIndexDAO = daoFactory.getLocalIndexDAO();
        for (Index downloadedIndex : downloadedIndexes) {
            sqlLiteIndexDAO.saveIndex(downloadedIndex);
        }
        return Long.valueOf(downloadedIndexes.size());
    }

    private void clearGroups() throws DAOException {
        LocalGroupDAO sqlLiteStrengthDAO = daoFactory.getLocalGroupDAO();
        sqlLiteStrengthDAO.deleteAllGroups();
    }

    private Long syncGroups() throws DAOException {
        //Read from DropBox
        RemoteGroupDAO dropBoxRemoteGroupDAO = daoFactory.getRemoteGroupDAO(DAOFactory.Flavour.DROPBOX);
        List<Group> downloadedGroups = dropBoxRemoteGroupDAO.getAllGroups();
        //Write into the SQLLite
        LocalGroupDAO sqlLiteLocalGroupDAO = daoFactory.getLocalGroupDAO();
        for (Group downloadedGroup : downloadedGroups) {
            sqlLiteLocalGroupDAO.saveGroup(downloadedGroup);
        }
        return Long.valueOf(downloadedGroups.size());
    }

    private void clearJn() throws DAOException {
        LocalJnDAO sqlLiteJnDAO = daoFactory.getLocalJnDAO();
        sqlLiteJnDAO.deleteAllJns();
    }

    private Long syncJn() throws DAOException {
        //Read from DropBox
        RemoteJnDAO dropBoxJnDAO = daoFactory.getRemoteJnDAO(DAOFactory.Flavour.DROPBOX);
        List<Jn> dowloadedJns = dropBoxJnDAO.getAllJns();
        //Write into the SQLLite
        LocalJnDAO sqlLiteJnDAO = daoFactory.getLocalJnDAO();
        for (Jn dowloadedJn : dowloadedJns) {
            sqlLiteJnDAO.saveJn(dowloadedJn);
        }
        return Long.valueOf(dowloadedJns.size());
    }

    private void clearJr() throws DAOException {
        LocalJrDAO sqlLiteJrDAO = daoFactory.getLocalJrDAO();
        sqlLiteJrDAO.deleteAllJrs();
    }

    private Long syncJr() throws DAOException {
        //Read from DropBox
        RemoteJrDAO dropBoxJrDAO = daoFactory.getRemoteJrDAO(DAOFactory.Flavour.DROPBOX);
        List<Jr> dowloadedJrs = dropBoxJrDAO.getAllJrs();
        //Write into the SQLLite
        LocalJrDAO sqlLiteJrDAO = daoFactory.getLocalJrDAO();
        for (Jr dowloadedJr : dowloadedJrs) {
            sqlLiteJrDAO.saveJr(dowloadedJr);
        }
        return Long.valueOf(dowloadedJrs.size());
    }

    private void clearJa() throws DAOException {
        LocalJaDAO sqlLiteJaDAO = daoFactory.getLocalJaDAO();
        sqlLiteJaDAO.deleteAllJas();
    }

    private Long syncJa() throws DAOException {
        //Read from DropBox
        RemoteJaDAO dropBoxJaDAO = daoFactory.getRemoteJaDAO(DAOFactory.Flavour.DROPBOX);
        List<Ja> dowloadedJas = dropBoxJaDAO.getAllJas();
        //Write into the SQLLite
        LocalJaDAO sqlLiteJaDAO = daoFactory.getLocalJaDAO();
        for (Ja dowloadedJa : dowloadedJas) {
            sqlLiteJaDAO.saveJa(dowloadedJa);
        }
        return Long.valueOf(dowloadedJas.size());
    }

    private void clearJw() throws DAOException {
        LocalJwDAO sqlLiteJwDAO = daoFactory.getLocalJwDAO();
        sqlLiteJwDAO.deleteAllJws();
    }

    private Long syncJw() throws DAOException {
        //Read from DropBox
        RemoteJwDAO dropBoxJwDAO = daoFactory.getRemoteJwDAO(DAOFactory.Flavour.DROPBOX);
        List<Jw> dowloadedJws = dropBoxJwDAO.getAllJws();
        //Write into the SQLLite
        LocalJwDAO sqlLiteJwDAO = daoFactory.getLocalJwDAO();
        for (Jw dowloadedJw : dowloadedJws) {
            sqlLiteJwDAO.saveJw(dowloadedJw);
        }
        return Long.valueOf(dowloadedJws.size());
    }

    private void clearSRF() throws DAOException {
        LocalSrfDAO sqlLiteSrfDAO = daoFactory.getLocalSrfDAO();
        sqlLiteSrfDAO.deleteAllSrfs();
    }

    private Long syncSRF() throws DAOException {
        //Read from DropBox
        RemoteSrfDAO dropBoxSrfDAO = daoFactory.getRemoteSrfDAO(DAOFactory.Flavour.DROPBOX);
        List<SRF> dowloadedSrfs = dropBoxSrfDAO.getAllSrfs();
        //Write into the SQLLite
        LocalSrfDAO sqlLiteSrfDAO = daoFactory.getLocalSrfDAO();
        for (SRF dowloadedSrf : dowloadedSrfs) {
            sqlLiteSrfDAO.saveSrf(dowloadedSrf);
        }
        return Long.valueOf(dowloadedSrfs.size());
    }

    private void clearStrengths() throws DAOException {
        LocalStrengthDAO sqlLiteStrengthDAO = daoFactory.getLocalStrengthDAO();
        sqlLiteStrengthDAO.deleteAllStrengths();
    }

    private Long syncStrengths() throws DAOException {
        //Read from DropBox
        RemoteStrengthDAO dropBoxStrengthDAO = daoFactory.getRemoteStrengthDAO(DAOFactory.Flavour.DROPBOX);
        List<StrengthOfRock> dowloadedStrengths = dropBoxStrengthDAO.getAllStrengths();
        //Write into the SQLLite
        LocalStrengthDAO sqlLiteStrengthDAO = daoFactory.getLocalStrengthDAO();
        for (StrengthOfRock dowloadedStrength : dowloadedStrengths) {
            sqlLiteStrengthDAO.saveStrength(dowloadedStrength);
        }
        return Long.valueOf(dowloadedStrengths.size());
    }

    private void clearGroudwaters() throws DAOException {
        LocalGroundwaterDAO sqlLiteGroudwaterDAO = daoFactory.getLocalGroundwaterDAO();
        sqlLiteGroudwaterDAO.deleteAllGroundwaters();
    }

    private Long syncGroundwaters() throws DAOException {
        //Read from DropBox
        RemoteGroundwaterDAO dropBoxGroundwaterDAO = daoFactory.getRemoteGroundwaterDAO(DAOFactory.Flavour.DROPBOX);
        List<Groundwater> dowloadedGroundwaters = dropBoxGroundwaterDAO.getAllGroundwaters();
        //Write into the SQLLite
        LocalGroundwaterDAO sqlLiteGroundwaterDAO = daoFactory.getLocalGroundwaterDAO();
        for (Groundwater dowloadedGroundwater : dowloadedGroundwaters) {
            sqlLiteGroundwaterDAO.saveGroundwater(dowloadedGroundwater);
        }
        return Long.valueOf(dowloadedGroundwaters.size());
    }

    private void clearOrientation() throws DAOException {
        LocalOrientationDiscontinuitiesDAO sqlLiteGroudwaterDAO = daoFactory.getLocalOrientationDiscontinuitiesDAO();
        sqlLiteGroudwaterDAO.deleteAllOrientationDiscontinuities();
    }

    private Long syncOrientation() throws DAOException {
        //Read from DropBox
        RemoteOrientationDiscontinuitiesDAO dropBoxOrientationDiscontinuitiesDAO = daoFactory.getRemoteOrientationDiscontinuitiesDAO(DAOFactory.Flavour.DROPBOX);
        List<OrientationDiscontinuities> dowloadedOrientationsDiscontinuities = dropBoxOrientationDiscontinuitiesDAO.getAllOrientationsDiscontinuities();
        //Write into the SQLLite
        LocalOrientationDiscontinuitiesDAO sqlLiteOrientationDiscontinuitiesDAO = daoFactory.getLocalOrientationDiscontinuitiesDAO();
        for (OrientationDiscontinuities dowloadedOrientationDiscontinuities : dowloadedOrientationsDiscontinuities) {
            sqlLiteOrientationDiscontinuitiesDAO.saveOrientationDiscontinuities(dowloadedOrientationDiscontinuities);
        }
        return Long.valueOf(dowloadedOrientationsDiscontinuities.size());
    }


    private void clearSpacings() throws DAOException {
        LocalSpacingDAO sqlLiteSpacingDAO = daoFactory.getLocalSpacingDAO();
        sqlLiteSpacingDAO.deleteAllSpacings();

    }

    private Long syncSpacings() throws DAOException {
        //Read from DropBox
        RemoteSpacingDAO dropBoxSpacingDAO = daoFactory.getRemoteSpacingDAO(DAOFactory.Flavour.DROPBOX);
        List<Spacing> dowloadedSpacings = dropBoxSpacingDAO.getAllSpacings();
        //Write into the SQLLite
        LocalSpacingDAO sqlLiteSpacingDAO = daoFactory.getLocalSpacingDAO();
        for (Spacing dowloadedSpacing : dowloadedSpacings) {
            sqlLiteSpacingDAO.saveSpacing(dowloadedSpacing);
        }
        return Long.valueOf(dowloadedSpacings.size());
    }

    private void clearPersistences() throws DAOException {
        LocalPersistenceDAO sqlLitePersistenceDAO = daoFactory.getLocalPersistenceDAO();
        sqlLitePersistenceDAO.deleteAllPersistences();
    }

    private Long syncPersistences() throws DAOException {
        //Read from DropBox
        RemotePersistenceDAO dropBoxPersistenceDAO = daoFactory.getRemotePersistenceDAO(DAOFactory.Flavour.DROPBOX);
        List<Persistence> downloadedPersistences = dropBoxPersistenceDAO.getAllPersistences();
        //Write into the SQLLite
        LocalPersistenceDAO sqlLitePersistenceDAO = daoFactory.getLocalPersistenceDAO();
        for (Persistence downloadedPersistence : downloadedPersistences) {
            sqlLitePersistenceDAO.savePersistence(downloadedPersistence);
        }
        return Long.valueOf(downloadedPersistences.size());
    }

    private void clearApertures() throws DAOException {
        LocalApertureDAO sqlLiteApertureDAO = daoFactory.getLocalApertureDAO();
        sqlLiteApertureDAO.deleteAllApertures();
    }

    private Long syncApertures() throws DAOException {
        //Read from DropBox
        RemoteApertureDAO dropBoxApertureDAO = daoFactory.getRemoteApertureDAO(DAOFactory.Flavour.DROPBOX);
        List<Aperture> downloadedApertures = dropBoxApertureDAO.getAllApertures();
        //Write into the SQLLite
        LocalApertureDAO sqlLiteApertureDAO = daoFactory.getLocalApertureDAO();
        for (Aperture downloadedAperture : downloadedApertures) {
            sqlLiteApertureDAO.saveAperture(downloadedAperture);
        }
        return Long.valueOf(downloadedApertures.size());
    }


    private void clearDiscontinuityShapes() throws DAOException {
        LocalDiscontinuityShapeDAO sqlLiteDiscontinuityShapeDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        sqlLiteDiscontinuityShapeDAO.deleteAllDiscontinuityShapes();
    }

    private Long syncDiscontinuityShapes() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityShapeDAO dropBoxDiscontinuityShapeDAO = daoFactory.getRemoteDiscontinuityShapeDAO(DAOFactory.Flavour.DROPBOX);
        List<DiscontinuityShape> downloadedDiscontinuityShapes = dropBoxDiscontinuityShapeDAO.getAllDiscontinuityShapes();
        //Write into the SQLLite
        LocalDiscontinuityShapeDAO sqlLiteDiscontinuityShapeDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        for (DiscontinuityShape downloadedDiscontinuityShape : downloadedDiscontinuityShapes) {
            sqlLiteDiscontinuityShapeDAO.saveDiscontinuityShape(downloadedDiscontinuityShape);
        }
        return Long.valueOf(downloadedDiscontinuityShapes.size());
    }

    private void clearRoughnesses() throws DAOException {
        LocalRoughnessDAO sqlLiteRoughnessDAO = daoFactory.getLocalRoughnessDAO();
        sqlLiteRoughnessDAO.deleteAllRoughnesses();
    }


    private Long syncRoughnesses() throws DAOException {
        //Read from DropBox
        RemoteRoughnessDAO dropBoxRoughnessDAO = daoFactory.getRemoteRoughnessDAO(DAOFactory.Flavour.DROPBOX);
        List<Roughness> downloadedRoughnesses = dropBoxRoughnessDAO.getAllRoughnesses();
        //Write into the SQLLite
        LocalRoughnessDAO sqlLiteRoughnessDAO = daoFactory.getLocalRoughnessDAO();
        for (Roughness downloadedRoughness : downloadedRoughnesses) {
            sqlLiteRoughnessDAO.saveRoughness(downloadedRoughness);
        }
        return Long.valueOf(downloadedRoughnesses.size());
    }

    private void clearInfillings() throws DAOException {
        LocalInfillingDAO sqlLiteInfillingDAO = daoFactory.getLocalInfillingDAO();
        sqlLiteInfillingDAO.deleteAllInfillings();
    }

    private Long syncInfillings() throws DAOException {
        //Read from DropBox
        RemoteInfillingDAO dropBoxInfillingDAO = daoFactory.getRemoteInfillingDAO(DAOFactory.Flavour.DROPBOX);
        List<Infilling> downloadedInfillings = dropBoxInfillingDAO.getAllInfillings();
        //Write into the SQLLite
        LocalInfillingDAO sqlLiteInfillingDAO = daoFactory.getLocalInfillingDAO();
        for (Infilling downloadedInfilling : downloadedInfillings) {
            sqlLiteInfillingDAO.saveInfilling(downloadedInfilling);
        }
        return Long.valueOf(downloadedInfillings.size());
    }

    private void clearWeatherings() throws DAOException {
        LocalWeatheringDAO sqlLiteWeatheringDAO = daoFactory.getLocalWeatheringDAO();
        sqlLiteWeatheringDAO.deleteAllWeatherings();
    }

    private Long syncWeatherings() throws DAOException {
        //Read from DropBox
        RemoteWeatheringDAO dropBoxWeatheringDAO = daoFactory.getRemoteWeatheringDAO(DAOFactory.Flavour.DROPBOX);
        List<Weathering> downloadedWeatherings = dropBoxWeatheringDAO.getAllWeatherings();
        //Write into the SQLLite
        LocalWeatheringDAO sqlLiteWeatheringDAO = daoFactory.getLocalWeatheringDAO();
        for (Weathering downloadedWeathering : downloadedWeatherings) {
            sqlLiteWeatheringDAO.saveWeathering(downloadedWeathering);
        }
        return Long.valueOf(downloadedWeatherings.size());
    }

    private void clearDiscontinuityWaters() throws DAOException {
        LocalDiscontinuityWaterDAO sqlLiteDiscontinuityWaterDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        sqlLiteDiscontinuityWaterDAO.deleteAllDiscontinuityWaters();
    }

    private Long syncDiscontinuityWaters() throws DAOException {
        //Read from DropBox
        RemoteDiscontinuityWaterDAO dropBoxDiscontinuityWaterDAO = daoFactory.getRemoteDiscontinuityWaterDAO(DAOFactory.Flavour.DROPBOX);
        List<DiscontinuityWater> downloadedDiscontinuityWaters = dropBoxDiscontinuityWaterDAO.getAllDiscontinuityWaters();
        //Write into the SQLLite
        LocalDiscontinuityWaterDAO sqlLiteDiscontinuityWaterDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        for (DiscontinuityWater downloadedDiscontinuityWater : downloadedDiscontinuityWaters) {
            sqlLiteDiscontinuityWaterDAO.saveDiscontinuityWater(downloadedDiscontinuityWater);
        }
        return Long.valueOf(downloadedDiscontinuityWaters.size());
    }

    private void clearFractureTypes() throws DAOException {
        LocalFractureTypeDAO sqlLiteFractureDAO = daoFactory.getLocalFractureTypeDAO();
        sqlLiteFractureDAO.deleteAllFractureTypes();
    }

    private Long syncFractureTypes() throws DAOException {
        //Read from DropBox
        RemoteFractureTypeDAO dropBoxFractureTypeDAO = daoFactory.getRemoteFractureTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<FractureType> downloadedFractureTypes = dropBoxFractureTypeDAO.getAllFractureTypes();
        //Write into the SQLLite
        LocalFractureTypeDAO sqlLiteFractureTypeDAO = daoFactory.getLocalFractureTypeDAO();
        for (FractureType downloadedFractureType : downloadedFractureTypes) {
            sqlLiteFractureTypeDAO.saveFractureType(downloadedFractureType);
        }
        return Long.valueOf(downloadedFractureTypes.size());
    }

    private void clearBoltTypes() {
        LocalBoltTypeDAO sqlLiteBoltTypesDAO = daoFactory.getLocalBoltTypeDAO();
        sqlLiteBoltTypesDAO.deleteAllBoltTypes();
    }

    private void syncBoltTypes() throws DAOException {
        //Read from DropBox
        RemoteBoltTypeDAO dropBoxBoltTypeDAO = daoFactory.getRemoteBoltTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<BoltType> downloadedBoltTypes = dropBoxBoltTypeDAO.getAllBoltTypes();
        //Write into the SQLLite
        LocalBoltTypeDAO sqlLiteBoltTypeDAO = daoFactory.getLocalBoltTypeDAO();
        for (BoltType downloadedBoltType : downloadedBoltTypes) {
            sqlLiteBoltTypeDAO.saveBoltType(downloadedBoltType);
        }
    }

    private void clearShotcreteTypes() {
        LocalShotcreteTypeDAO sqlLiteShotcreteDAO = daoFactory.getLocalShotcreteTypeDAO();
        sqlLiteShotcreteDAO.deleteAllShotcreteTypes();
    }

    private void syncShotcreteTypes() throws DAOException {
        //Read from DropBox
        RemoteShotcreteTypeDAO dropBoxShotcreteTypeDAO = daoFactory.getRemoteShotcreteTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<ShotcreteType> downloadedShotcreteTypes = dropBoxShotcreteTypeDAO.getAllShotcreteTypes();
        //Write into the SQLLite
        LocalShotcreteTypeDAO sqlLiteShotcreteTypeDAO = daoFactory.getLocalShotcreteTypeDAO();
        for (ShotcreteType downloadedShotcreteType : downloadedShotcreteTypes) {
            sqlLiteShotcreteTypeDAO.saveShotcreteType(downloadedShotcreteType);
        }
    }


    private void clearMeshTypes() {
        LocalMeshTypeDAO sqlLiteMeshTypeDAO = daoFactory.getLocalMeshTypeDAO();
        sqlLiteMeshTypeDAO.deleteAllMeshTypes();
    }

    private void syncMeshTypes() throws DAOException {
        //Read from DropBox
        RemoteMeshTypeDAO dropBoxMeshTypeDAO = daoFactory.getRemoteMeshTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<MeshType> downloadedMeshTypes = dropBoxMeshTypeDAO.getAllMeshTypes();
        //Write into the SQLLite
        LocalMeshTypeDAO sqlLiteMeshTypeDAO = daoFactory.getLocalMeshTypeDAO();
        for (MeshType downloadedMeshType : downloadedMeshTypes) {
            sqlLiteMeshTypeDAO.saveMeshType(downloadedMeshType);
        }
    }


    private void clearCoverages() {
        LocalCoverageDAO sqlLiteCoverageDAO = daoFactory.getLocalCoverageDAO();
        sqlLiteCoverageDAO.deleteAllCoverages();
    }

    private void syncCoverages() throws DAOException {
        //Read from DropBox
        RemoteCoverageDAO dropBoxCoverageDAO = daoFactory.getRemoteCoverageDAO(DAOFactory.Flavour.DROPBOX);
        List<Coverage> downloadedCoverages = dropBoxCoverageDAO.getAllCoverages();
        //Write into the SQLLite
        LocalCoverageDAO sqlLiteCoverageDAO = daoFactory.getLocalCoverageDAO();
        for (Coverage downloadedCoverage : downloadedCoverages) {
            sqlLiteCoverageDAO.saveCoverage(downloadedCoverage);
        }
    }

    private void clearArchTypes() {
        LocalArchTypeDAO sqlLiteArchDAO = daoFactory.getLocalArchTypeDAO();
        sqlLiteArchDAO.deleteAllArchTypes();
    }

    private void syncArchTypes() throws DAOException {
        //Read from DropBox
        RemoteArchTypeDAO dropBoxArchTypeDAO = daoFactory.getRemoteArchTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<ArchType> downloadedArchTypes = dropBoxArchTypeDAO.getAllArchTypes();
        //Write into the SQLLite
        LocalArchTypeDAO sqlLiteArchTypeDAO = daoFactory.getLocalArchTypeDAO();
        for (ArchType downloadedArchType : downloadedArchTypes) {
            sqlLiteArchTypeDAO.saveArchType(downloadedArchType);
        }
    }

    private void clearSupportPatternTypes() {
        LocalSupportPatternTypeDAO sqlLitePatternDAO = daoFactory.getLocalSupportPatternTypeDAO();
        sqlLitePatternDAO.deleteAllSupportPatternTypes();
    }

    private void syncSupportPatternTypes() throws DAOException {
        //Read from DropBox
        RemoteSupportPatternTypeDAO dropBoxSupportPatternTypeDAO = daoFactory.getRemoteSupportPatternTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<SupportPatternType> downloadedSupportPatternTypes = dropBoxSupportPatternTypeDAO.getAllSupportPatternTypes();
        //Write into the SQLLite
        LocalSupportPatternTypeDAO sqlLiteSupportPatternTypeDAO = daoFactory.getLocalSupportPatternTypeDAO();
        for (SupportPatternType downloadedSupportPatternType : downloadedSupportPatternTypes) {
            sqlLiteSupportPatternTypeDAO.saveSupportPatternType(downloadedSupportPatternType);
        }
    }

    private void clearESRs() throws DAOException {
        LocalEsrDAO sqlLiteESRDAO = daoFactory.getLocalEsrDAO();
        sqlLiteESRDAO.deleteAllESRs();
    }

    private void syncESRs() throws DAOException {
        //Read from DropBox
        RemoteEsrDAO dropBoxESRDAO = daoFactory.getRemoteEsrDAO(DAOFactory.Flavour.DROPBOX);
        List<ESR> dowloadedESRs = dropBoxESRDAO.getAllESRs();
        //Write into the SQLLite
        LocalEsrDAO sqlLiteESRDAO = daoFactory.getLocalEsrDAO();
        for (ESR dowloadedESR : dowloadedESRs) {
            sqlLiteESRDAO.saveESR(dowloadedESR);
        }
    }

    private void clearRockQualities() throws DAOException {
        LocalRockQualityDAO sqlLiteRockQualityDAO = daoFactory.getLocalRockQualityDAO();
        sqlLiteRockQualityDAO.deleteAllRockQualities();
    }

    private void syncRockQualities() throws DAOException {
        //Read from DropBox
        RemoteRockQualityDAO dropBoxRockQualityDAO = daoFactory.getRemoteRockQualityDAO(DAOFactory.Flavour.DROPBOX);
        List<RockQuality> dowloadedRockQualitys = dropBoxRockQualityDAO.getAllRockQualities();
        //Write into the SQLLite
        LocalRockQualityDAO sqlLiteRockQualityDAO = daoFactory.getLocalRockQualityDAO();
        for (RockQuality dowloadedRockQuality : dowloadedRockQualitys) {
            sqlLiteRockQualityDAO.saveRockQuality(dowloadedRockQuality);
        }
    }

    private void clearExcavationFactors() throws DAOException {
        LocalExcavationFactorDAO factorsDAO = daoFactory.getLocalExcavationFactorsDAO();
        factorsDAO.deleteAllExcavationFactors();
    }


    private void clearSupportRequirements() {
        LocalSupportRequirementDAO sqlLiteSupportRequirementsDAO = daoFactory.getLocalSupportRequirementDAO();
        sqlLiteSupportRequirementsDAO.deleteAllSupportRequirements();
    }

    private Long syncSupportRequirements() throws DAOException {
        //Read from DropBox
        RemoteSupportRequirementDAO supportRequirementsDAO = daoFactory.getRemoteSupportRequirementDAO(DAOFactory.Flavour.DROPBOX);
        List<SupportRequirement> dowloadedRequirements = supportRequirementsDAO.getAllSupportRequirements();
        //Write into the SQLLite
        LocalSupportRequirementDAO sqlLiteSupportRequirementsDAO = daoFactory.getLocalSupportRequirementDAO();
        for (SupportRequirement dowloadedRequirement : dowloadedRequirements) {
            sqlLiteSupportRequirementsDAO.saveSupportRequirement(dowloadedRequirement);
        }
        return Long.valueOf(dowloadedRequirements.size());
    }

}
