package com.metric.skava.sync.helper;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.Client;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaUtils;
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
import com.metric.skava.data.dao.LocalAssessmentDAO;
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
import com.metric.skava.data.dao.RemoteAssessmentDAO;
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
import com.metric.skava.data.dao.RemoteMetadataDAO;
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
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ClientDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ExcavationProjectDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrCategoriesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RoleDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRequirementDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelFaceDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.UserDropboxTable;
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
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.exception.SyncDataFailedException;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncTask;

import java.util.ArrayList;
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


    public boolean areAppDataTablesEmpty() throws DAOException {

        LocalApertureDAO sqlLiteAperturesDAO = daoFactory.getLocalApertureDAO();
        LocalArchTypeDAO sqlLiteArchTypesDAO = daoFactory.getLocalArchTypeDAO();
        LocalBoltTypeDAO sqlLiteBoltTypesDAO = daoFactory.getLocalBoltTypeDAO();
        LocalCoverageDAO sqlLiteCoveragesDAO = daoFactory.getLocalCoverageDAO();
        LocalDiscontinuityTypeDAO sqlLiteDiscontinuityTypesDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        LocalDiscontinuityRelevanceDAO sqlLiteDiscontinuityRelevancesDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        LocalDiscontinuityShapeDAO sqlLiteDiscontinuityShapesDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        LocalDiscontinuityWaterDAO sqlLiteDiscontinuityWatersDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        LocalEsrDAO sqlLiteESRsDAO = daoFactory.getLocalEsrDAO();
        LocalFractureTypeDAO sqlLiteFractureTypesDAO = daoFactory.getLocalFractureTypeDAO();
        LocalGroundwaterDAO sqlLiteGroudwatersDAO = daoFactory.getLocalGroundwaterDAO();
        LocalGroupDAO sqlLiteGroupsDAO = daoFactory.getLocalGroupDAO();
        LocalIndexDAO sqlLiteIndexesDAO = daoFactory.getLocalIndexDAO();
        LocalInfillingDAO sqlLiteInfillingsDAO = daoFactory.getLocalInfillingDAO();
        LocalJaDAO sqlLiteJaDAO = daoFactory.getLocalJaDAO();
        LocalJnDAO sqlLiteJnDAO = daoFactory.getLocalJnDAO();
        LocalJrDAO sqlLiteJrDAO = daoFactory.getLocalJrDAO();
        LocalJwDAO sqlLiteJwDAO = daoFactory.getLocalJwDAO();
        LocalMeshTypeDAO sqlLiteMeshTypesDAO = daoFactory.getLocalMeshTypeDAO();
        LocalExcavationMethodDAO sqlLiteExcavationMethodsDAO = daoFactory.getLocalExcavationMethodDAO();
        LocalOrientationDiscontinuitiesDAO sqlLiteOrientationDAO = daoFactory.getLocalOrientationDiscontinuitiesDAO();
        LocalSupportPatternTypeDAO sqlLiteSupportPatternTypesDAO = daoFactory.getLocalSupportPatternTypeDAO();
        LocalPersistenceDAO sqlLitePersistencesDAO = daoFactory.getLocalPersistenceDAO();
        LocalRockQualityDAO sqlLiteRockQualitiesDAO = daoFactory.getLocalRockQualityDAO();
        LocalRoughnessDAO sqlLiteRoughnessesDAO = daoFactory.getLocalRoughnessDAO();
        LocalSrfDAO sqlLiteSRFDAO = daoFactory.getLocalSrfDAO();
        LocalExcavationSectionDAO sqlLiteExcavationSectionsDAO = daoFactory.getLocalExcavationSectionDAO();
        LocalShotcreteTypeDAO sqlLiteShotcreteTypesDAO = daoFactory.getLocalShotcreteTypeDAO();
        LocalSpacingDAO sqlLiteSpacingsDAO = daoFactory.getLocalSpacingDAO();
        LocalStrengthDAO sqlLiteStrengthsDAO = daoFactory.getLocalStrengthDAO();
        LocalWeatheringDAO sqlLiteWeatheringsDAO = daoFactory.getLocalWeatheringDAO();

        boolean aperturesEmpty = sqlLiteAperturesDAO.countApertures() == 0;
        boolean archTypesEmpty = sqlLiteArchTypesDAO.countArchTypes() == 0;
        boolean boltTypesEmpty = sqlLiteBoltTypesDAO.countBoltTypes() == 0;
        boolean coveragesEmpty = sqlLiteCoveragesDAO.countCoverages() == 0;
        boolean discontinuityTypesEmpty = sqlLiteDiscontinuityTypesDAO.countDiscontinuityTypes() == 0;
        boolean discontinuityRelevancesEmpty = sqlLiteDiscontinuityRelevancesDAO.countDiscontinuityRelevances() == 0;
        boolean discontinuityShapesEmpty = sqlLiteDiscontinuityShapesDAO.countDiscontinuityShapes() == 0;
        boolean discontinuityWatersEmpty = sqlLiteDiscontinuityWatersDAO.countDiscontinuityWaters() == 0;
        boolean esrEmpty = sqlLiteESRsDAO.countESRs() == 0;
        boolean fractureTypesEmpty = sqlLiteFractureTypesDAO.countFractureTypes() == 0;
        boolean groudwatersEmpty = sqlLiteGroudwatersDAO.countGroudwaters() == 0;
        boolean groupsEmpty = sqlLiteGroupsDAO.countGroups() == 0;
        boolean indexesEmpty = sqlLiteIndexesDAO.countIndexes() == 0;
        boolean infillingsEmpty = sqlLiteInfillingsDAO.countInfillings() == 0;
        boolean jaEmpty = sqlLiteJaDAO.countJa() == 0;
        boolean jnEmpty = sqlLiteJnDAO.countJn() == 0;
        boolean jrEmpty = sqlLiteJrDAO.countJr() == 0;
        boolean jwEmpty = sqlLiteJwDAO.countJw() == 0;
        boolean meshTypesEmpty = sqlLiteMeshTypesDAO.countMeshTypes() == 0;
        boolean excavationMethodsEmpty = sqlLiteExcavationMethodsDAO.countExcavationMethods() == 0;
        boolean orientationEmpty = sqlLiteOrientationDAO.countOrientation() == 0;
        boolean supportPatternTypesEmpty = sqlLiteSupportPatternTypesDAO.countSupportPatternTypes() == 0;
        boolean persistencesEmpty = sqlLitePersistencesDAO.countPersistences() == 0;
        boolean rockQualitiesEmpty = sqlLiteRockQualitiesDAO.countRockQualities() == 0;
        boolean roughnessesEmpty = sqlLiteRoughnessesDAO.countRoughnesses() == 0;
        boolean srfEmpty = sqlLiteSRFDAO.countSRF() == 0;
        boolean excavationSectionsEmpty = sqlLiteExcavationSectionsDAO.countExcavationSections() == 0;
        boolean shotcreteTypesEmpty = sqlLiteShotcreteTypesDAO.countShotcreteTypes() == 0;
        boolean spacingsEmpty = sqlLiteSpacingsDAO.countSpacings() == 0;
        boolean strengthsEmpty = sqlLiteStrengthsDAO.countStrengths() == 0;
        boolean weatheringsEmpty = sqlLiteWeatheringsDAO.countWeatherings() == 0;

        return (aperturesEmpty ||
                archTypesEmpty ||
                boltTypesEmpty ||
                coveragesEmpty ||
                discontinuityTypesEmpty ||
                discontinuityRelevancesEmpty ||
                discontinuityShapesEmpty ||
                discontinuityWatersEmpty ||
                esrEmpty ||
                fractureTypesEmpty ||
                groudwatersEmpty ||
                groupsEmpty ||
                indexesEmpty ||
                infillingsEmpty ||
                jaEmpty ||
                jnEmpty ||
                jrEmpty ||
                jwEmpty ||
                meshTypesEmpty ||
                excavationMethodsEmpty ||
                orientationEmpty ||
                supportPatternTypesEmpty ||
                persistencesEmpty ||
                rockQualitiesEmpty ||
                roughnessesEmpty ||
                srfEmpty ||
                excavationSectionsEmpty ||
                shotcreteTypesEmpty ||
                spacingsEmpty ||
                strengthsEmpty ||
                weatheringsEmpty );
    }

    public Long getAllAppDataRecordCount() throws DAOException {
        /**Update the excavation methods data*/
        RemoteMetadataDAO dropBoxMetadataDAO = daoFactory.getRemoteMetadataDAO(DAOFactory.Flavour.DROPBOX);
        //Read from DropBox
        Long totalRecords = dropBoxMetadataDAO.getAllAppDataRecordsCount();
        return totalRecords;
    }


    public boolean areUserDataTablesEmpty() throws DAOException {

        LocalRoleDAO sqlLiteRolesDAO = daoFactory.getLocalRoleDAO();
        LocalClientDAO sqlLiteClientsDAO = daoFactory.getLocalClientDAO();
        LocalExcavationProjectDAO sqlLiteProjectsDAO = daoFactory.getLocalExcavationProjectDAO();
        LocalSupportRequirementDAO sqlLiteSupportRequirementsDAO = daoFactory.getLocalSupportRequirementDAO();
        LocalTunnelDAO sqlLiteTunnelsDAO = daoFactory.getLocalTunnelDAO();
        LocalTunnelFaceDAO sqlLiteFacesDAO = daoFactory.getLocalTunnelFaceDAO();
        LocalUserDAO sqlLiteUsersDAO = daoFactory.getLocalUserDAO();

        boolean rolesEmpty = sqlLiteRolesDAO.countRoles() == 0;
        boolean clientsEmpty = sqlLiteClientsDAO.countClients() == 0;
        boolean projectsEmpty = sqlLiteProjectsDAO.countProjects() == 0;
        boolean supportRequirementsEmpty = sqlLiteSupportRequirementsDAO.countRequirements() == 0;
        boolean tunnelsEmpty = sqlLiteTunnelsDAO.countTunnels() == 0;
        boolean facesEmpty = sqlLiteFacesDAO.countFaces() == 0;
        boolean usersEmpty = sqlLiteUsersDAO.countUsers() == 0;

        return usersEmpty || facesEmpty || tunnelsEmpty || supportRequirementsEmpty || projectsEmpty || clientsEmpty || rolesEmpty;
    }

    public Long getAllUserDataRecordCount() throws DAOException {
        /**Update the excavation methods data*/
        RemoteMetadataDAO dropBoxMetadataDAO = daoFactory.getRemoteMetadataDAO(DAOFactory.Flavour.DROPBOX);
        //Read from DropBox
        Long totalRecords = dropBoxMetadataDAO.getAllUserDataRecordsCount();
        return totalRecords;
    }

    public Long findOutNumberOfRecordsToImportForAssessment(String assessmentCode) throws DAOException {
        RemoteMetadataDAO dropBoxMetadataDAO = daoFactory.getRemoteMetadataDAO(DAOFactory.Flavour.DROPBOX);
        //Read from DropBox
        return dropBoxMetadataDAO.getAssessmentRelatedRecordsCount(assessmentCode);
    }

    public Long findOutNumberOfRecordsToImport(SyncTask.Domain[] domains) throws DAOException {
        /**Update the excavation methods data*/
        RemoteMetadataDAO dropBoxMetadataDAO = daoFactory.getRemoteMetadataDAO(DAOFactory.Flavour.DROPBOX);
        //Read from DropBox
        List<String> dropboxTables = new ArrayList<String>();
        for (SyncTask.Domain currentDomain : domains) {
            switch (currentDomain) {
                case ASSESSMENTS:
                    return dropBoxMetadataDAO.getAllAssessmentRelatedRecordsCount();
                case ALL_APP_DATA_TABLES:
                    return dropBoxMetadataDAO.getAllAppDataRecordsCount();
                case ALL_USER_DATA_TABLES:
                    return dropBoxMetadataDAO.getAllUserDataRecordsCount();
                case ROLES:
                    dropboxTables.add(RoleDropboxTable.ROLES_DROPBOX_TABLE);
                    break;
                case CLIENTS:
                    dropboxTables.add(ClientDropboxTable.CLIENTS_DROPBOX_TABLE);
                    break;
                case EXCAVATION_PROJECTS:
                    dropboxTables.add(ExcavationProjectDropboxTable.PROJECTS_DROPBOX_TABLE);
                    break;
                case TUNNELS:
                    dropboxTables.add(TunnelDropboxTable.TUNNELS_DROPBOX_TABLE);
                    break;
                case SUPPORT_REQUIREMENTS:
                    dropboxTables.add(SupportRequirementDropboxTable.SUPPORT_REQUIREMENTS_DROPBOX_TABLE);
                    break;
                case TUNNEL_FACES:
                    dropboxTables.add(TunnelFaceDropboxTable.FACES_DROPBOX_TABLE);
                    break;
                case USERS:
                    dropboxTables.add(UserDropboxTable.USERS_DROPBOX_TABLE);
                    break;
                default:
                    dropboxTables.add(ParametersDropboxTable.PARAMETERS_DROPBOX_TABLE);
                    dropboxTables.add(RmrParametersDropboxTable.RMR_PARAMETERS_TABLE);
                    dropboxTables.add(RmrIndexesDropboxTable.RMR_INDEXES_TABLE);
                    dropboxTables.add(RmrCategoriesDropboxTable.RMR_CATEGORIES_TABLE);
                    break;
            }
        }
        Long totalRecords = dropBoxMetadataDAO.getRecordsCount(dropboxTables.toArray(new String[]{}));
        return totalRecords;
    }

    public Long clearAllUserData() throws DAOException {
        Long numRecordsDeleted = 0L;
        numRecordsDeleted+=clearRoles();
        numRecordsDeleted+=clearClients();
        numRecordsDeleted+=clearProjects();
        numRecordsDeleted+=clearTunnels();
        numRecordsDeleted+=clearSupportRequirements();
        numRecordsDeleted+=clearFaces();
        numRecordsDeleted+=clearUsers();
        return numRecordsDeleted;
    }

    public Long clearAllAppData() throws DAOException {
        Long numRecordsDeleted = 0L;
        numRecordsDeleted+=clearApertures();
        numRecordsDeleted+=clearArchTypes();
        numRecordsDeleted+=clearBoltTypes();
        numRecordsDeleted+=clearCoverages();
        numRecordsDeleted+=clearDiscontinuityTypes();
        numRecordsDeleted+=clearDiscontinuityRelevances();
        numRecordsDeleted+=clearDiscontinuityShapes();
        numRecordsDeleted+=clearDiscontinuityWaters();
        numRecordsDeleted+=clearESRs();
        numRecordsDeleted+=clearFractureTypes();
        numRecordsDeleted+=clearGroudwaters();
        numRecordsDeleted+=clearGroups();
        numRecordsDeleted+=clearIndexes();
        numRecordsDeleted+=clearInfillings();
        numRecordsDeleted+=clearJa();
        numRecordsDeleted+=clearJn();
        numRecordsDeleted+=clearJr();
        numRecordsDeleted+=clearJw();
        numRecordsDeleted+=clearMeshTypes();
        numRecordsDeleted+=clearExcavationMethods();
        numRecordsDeleted+=clearOrientation();
        numRecordsDeleted+=clearSupportPatternTypes();
        numRecordsDeleted+=clearPersistences();
        numRecordsDeleted+=clearRockQualities();
        numRecordsDeleted+=clearRoughnesses();
        numRecordsDeleted+=clearSRF();
        numRecordsDeleted+=clearExcavationSections();
        numRecordsDeleted+=clearShotcreteTypes();
        numRecordsDeleted+=clearSpacings();
        numRecordsDeleted+=clearStrengths();
        numRecordsDeleted+=clearWeatherings();
        return numRecordsDeleted;
    }


    public Long downloadDiscontinuities(String assessmentCode) throws DAOException, SyncDataFailedException {
        return 1L;
    }

    public Long downloadRMRCalculation(String assessmentCode) throws DAOException, SyncDataFailedException {
        return 1L;
    }


    public Long downloadQCalculation(String assessmentCode) throws DAOException, SyncDataFailedException {
        return 1L;
    }


    public Long downloadSupportRecommendation(String assessmentCode) throws DAOException, SyncDataFailedException {
        return 1L;
    }



    public Long downloadAssessment(String assessmentCode) throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ASSESSMENTS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearAssessment(assessmentCode);
            numRecords = syncAssessment(assessmentCode);
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadRoles() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ROLES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearRoles();
            numRecords = syncRoles();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadExcavationMethods() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.EXCAVATION_METHODS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearExcavationMethods();
            numRecords = syncExcavationMethods();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadExcavationSections() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.EXCAVATION_SECTIONS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearExcavationSections();
            numRecords = syncExcavationSections();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadDiscontinuityTypes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.DISCONTINUITY_TYPES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearDiscontinuityTypes();
            numRecords = syncDiscontinuityTypes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadDiscontinuityRelevances() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.DISCONTINUITY_RELEVANCES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearDiscontinuityRelevances();
            numRecords = syncDiscontinuityRelevances();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadIndexes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.INDEXES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearIndexes();
            numRecords = syncIndexes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadGroups() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.GROUPS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearGroups();
            numRecords = syncGroups();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadSpacings() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.SPACINGS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearSpacings();
            numRecords = syncSpacings();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadPersistences() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.PERSISTENCES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearPersistences();
            numRecords = syncPersistences();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadApertures() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.APERTURES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearApertures();
            numRecords = syncApertures();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadShapes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.DISCONTINUITY_SHAPES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearDiscontinuityShapes();
            numRecords = syncDiscontinuityShapes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadRoughnesses() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ROUGHNESSES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearRoughnesses();
            numRecords = syncRoughnesses();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadInfillings() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.INFILLINGS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearInfillings();
            numRecords = syncInfillings();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadWeatherings() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.WEATHERINGS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearWeatherings();
            numRecords = syncWeatherings();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadWaters() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.DISCONTINUITY_WATERS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearDiscontinuityWaters();
            numRecords = syncDiscontinuityWaters();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadStrengths() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.STRENGTHS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearStrengths();
            numRecords = syncStrengths();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadGroundwaters() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.GROUDWATERS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearGroudwaters();
            numRecords = syncGroundwaters();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadOrientations() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ORIENTATION, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearOrientation();
            numRecords = syncOrientation();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadJns() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.JN, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearJn();
            numRecords = syncJn();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadJrs() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.JR, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearJr();
            numRecords = syncJr();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadJas() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.JA, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearJa();
            numRecords = syncJa();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadJws() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.JW, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearJw();
            numRecords = syncJw();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadSRFs() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.SRF, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearSRF();
            numRecords = syncSRF();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadFractureTypes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.FRACTURE_TYPES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearFractureTypes();
            numRecords = syncFractureTypes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadBoltTypes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.BOLT_TYPES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearBoltTypes();
            numRecords = syncBoltTypes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadShotcreteTypes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.SHOTCRETE_TYPES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearShotcreteTypes();
            numRecords = syncShotcreteTypes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadMeshTypes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.MESHTYPES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearMeshTypes();
            numRecords = syncMeshTypes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadCoverages() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.COVERAGES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearCoverages();
            numRecords = syncCoverages();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadArchTypes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ARCHTYPES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearArchTypes();
            numRecords = syncArchTypes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadESRs() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ESRS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearESRs();
            numRecords = syncESRs();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadSupportPatternTypes() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.SUPPORT_PATTERN_TYPES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearSupportPatternTypes();
            numRecords = syncSupportPatternTypes();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadRockQualities() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ROCK_QUALITIES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearRockQualities();
            numRecords = syncRockQualities();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    //**** USER RELATED DATA ***//
    public Long downloadClients() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.CLIENTS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearClients();
            numRecords = syncClients();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadProjects() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.EXCAVATION_PROJECTS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearProjects();
            numRecords = syncProjects();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }


    public Long downloadTunnels() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.TUNNELS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            clearTunnels();
            numRecords = syncTunnels();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadSupportRequirements() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.SUPPORT_REQUIREMENTS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            //Support Requirements depends on Tunnel
            clearSupportRequirements();
            numRecords = syncSupportRequirements();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadTunnelFaces() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.TUNNEL_FACES, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            //Support Requirements depends on Tunnel
            clearFaces();
            numRecords = syncFaces();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);
        return numRecords;
    }

    public Long downloadUsers() throws DAOException, SyncDataFailedException {
        Long numRecords = 0L;
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        DbxDatastoreStatus status = skavaContext.getDatastore().getSyncStatus();
        SyncTask.Source source = status.isConnected ? SyncTask.Source.DROPBOX_REMOTE_DATASTORE : SyncTask.Source.DROPBOX_LOCAL_DATASTORE;
        SyncLogEntry syncLogEntry = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.USERS, source, SyncTask.Status.SUCCESS, numRecords);
        try {
            //HEADS UP: on this strategy users shuold be loaded last coz the assembling will look up some roles and faces DAOs
            //This is not exactly th best option as it will load the entire dataset and not just what this user can see
            //TODO evaluate and implement the userSpecificData
            clearUsers();
            numRecords = syncUsers();
            syncLogEntry.setNumRecordsSynced(numRecords);
        } catch (Exception e) {
            syncLogEntry.setStatus(SyncTask.Status.FAIL);
            syncLogEntry.setMessage(e.getMessage());
            throw new SyncDataFailedException(syncLogEntry, e.getMessage());
        }
        syncLoggingDAO.saveSyncLogEntry(syncLogEntry);

        return numRecords;
    }



    //TODO Use the user information to pull just the faces, tunnels, projects anc clients for that user
//    public void downloadUserRelatedDataFilteredByUser(User user) throws DAOException {
//        syncFacesCascade(user);
//    }

    private void clearAssessment(String assessmentCode) throws DAOException {
        LocalAssessmentDAO sqlLiteAssessmentDAO = daoFactory.getLocalAssessmentDAO();
        sqlLiteAssessmentDAO.deleteAssessment(assessmentCode);
    }

    private Long syncAssessment(String assessmentCode) throws DAOException {
        //Read from DropBox
        RemoteAssessmentDAO remoteAssessmentDAO = daoFactory.getRemoteAssessmentDAO(DAOFactory.Flavour.DROPBOX);
        Assessment downloadedAssessment = remoteAssessmentDAO.getAssessment(assessmentCode);
        //Write into the SQLLite
        LocalAssessmentDAO sqlLiteAssessmentDAO = daoFactory.getLocalAssessmentDAO();
        sqlLiteAssessmentDAO.saveAssessment(downloadedAssessment);
        return 1L;
    }

    private int clearExcavationSections() throws DAOException {
        LocalExcavationSectionDAO sqlLiteLocalSectionDAO = daoFactory.getLocalExcavationSectionDAO();
        return sqlLiteLocalSectionDAO.deleteAllExcavationSections();
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

    private int clearExcavationMethods() throws DAOException {
        LocalExcavationMethodDAO sqlLiteMethodDAO = daoFactory.getLocalExcavationMethodDAO();
        return sqlLiteMethodDAO.deleteAllExcavationMethods();
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

    private int clearRoles() throws DAOException {
        LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO();
        return sqlLiteLocalRoleDAO.deleteAllRoles();
    }


    private Long syncRoles() throws DAOException {
        //Read from DropBox
        RemoteRoleDAO dropBoxRemoteRoleDAO = daoFactory.getRemoteRoleDAO(DAOFactory.Flavour.DROPBOX);
        List<Role> downloadedRoles = dropBoxRemoteRoleDAO.getAllRoles();
        //Write into the SQLLite
        LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO();
        for (Role downloadedMethod : downloadedRoles) {
            sqlLiteLocalRoleDAO.saveRole(downloadedMethod);
        }
        return Long.valueOf(downloadedRoles.size());
    }

    private int clearUsers() throws DAOException {
        LocalPermissionDAO permissionsDAO = daoFactory.getLocalPermissionDAO();
        permissionsDAO.deleteAllPermissions();
        LocalUserDAO userDAO = daoFactory.getLocalUserDAO();
        int deletedRecords = userDAO.deleteAllUsers();
        return deletedRecords;
    }

    private Long syncUsers() throws DAOException {
        //Read from DropBox
        RemoteUserDAO dropBoxUserDAO = daoFactory.getRemoteUserDAO(DAOFactory.Flavour.DROPBOX);
        String targetEnvironment = skavaContext.getTargetEnvironment();
        List<User> downloadedUsers = dropBoxUserDAO.getAllUsers(targetEnvironment);
        //Write into the SQLLite
        LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO();
        for (User downloadedUser : downloadedUsers) {
            sqlLiteLocalUserDAO.saveUser(downloadedUser);
        }
        return Long.valueOf(downloadedUsers.size());
    }


    private int clearTunnels() throws DAOException {
        LocalTunnelDAO sqlLiteLocalTunnelDAO = daoFactory.getLocalTunnelDAO();
        //Deleted the helper table wich associates tunnels with ESR and Span. This table was
        //created originally to define SupportRequirements but currently is not used
        clearExcavationFactors();
        return sqlLiteLocalTunnelDAO.deleteAllTunnels();


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

    private int clearProjects() throws DAOException {
        LocalExcavationProjectDAO sqlLiteProjectDAO = daoFactory.getLocalExcavationProjectDAO();
        return sqlLiteProjectDAO.deleteAllExcavationProjects();
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


    private int clearFaces() throws DAOException {
        LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        return sqlLiteFaceDAO.deleteAllTunnelFaces();
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

    private int clearClients() throws DAOException {
        LocalClientDAO sqlLiteLocalClientDAO = daoFactory.getLocalClientDAO();
        return sqlLiteLocalClientDAO.deleteAllClients();
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

    private int clearDiscontinuityTypes() throws DAOException {
        LocalDiscontinuityTypeDAO sqlLiteDiscontinuityTypeDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        return sqlLiteDiscontinuityTypeDAO.deleteAllDiscontinuityTypes();
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


    private int clearDiscontinuityRelevances() throws DAOException {
        LocalDiscontinuityRelevanceDAO sqlLiteDiscontinuityRelevanceDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        return sqlLiteDiscontinuityRelevanceDAO.deleteAllDiscontinuityRelevances();
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


    private int clearIndexes() throws DAOException {
        LocalIndexDAO sqlLiteIndexDAO = daoFactory.getLocalIndexDAO();
        return sqlLiteIndexDAO.deleteAllIndexes();
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

    private int clearGroups() throws DAOException {
        LocalGroupDAO sqlLiteStrengthDAO = daoFactory.getLocalGroupDAO();
        return sqlLiteStrengthDAO.deleteAllGroups();
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

    private int clearJn() throws DAOException {
        LocalJnDAO sqlLiteJnDAO = daoFactory.getLocalJnDAO();
        return sqlLiteJnDAO.deleteAllJns();
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

    private int clearJr() throws DAOException {
        LocalJrDAO sqlLiteJrDAO = daoFactory.getLocalJrDAO();
        return sqlLiteJrDAO.deleteAllJrs();
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

    private int clearJa() throws DAOException {
        LocalJaDAO sqlLiteJaDAO = daoFactory.getLocalJaDAO();
        return sqlLiteJaDAO.deleteAllJas();
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

    private int clearJw() throws DAOException {
        LocalJwDAO sqlLiteJwDAO = daoFactory.getLocalJwDAO();
        return sqlLiteJwDAO.deleteAllJws();
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

    private int clearSRF() throws DAOException {
        LocalSrfDAO sqlLiteSrfDAO = daoFactory.getLocalSrfDAO();
        return sqlLiteSrfDAO.deleteAllSrfs();
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

    private int clearStrengths() throws DAOException {
        LocalStrengthDAO sqlLiteStrengthDAO = daoFactory.getLocalStrengthDAO();
        return sqlLiteStrengthDAO.deleteAllStrengths();
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

    private int clearGroudwaters() throws DAOException {
        LocalGroundwaterDAO sqlLiteGroudwaterDAO = daoFactory.getLocalGroundwaterDAO();
        return sqlLiteGroudwaterDAO.deleteAllGroundwaters();
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

    private int clearOrientation() throws DAOException {
        LocalOrientationDiscontinuitiesDAO sqlLiteGroudwaterDAO = daoFactory.getLocalOrientationDiscontinuitiesDAO();
        return sqlLiteGroudwaterDAO.deleteAllOrientationDiscontinuities();
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


    private int clearSpacings() throws DAOException {
        LocalSpacingDAO sqlLiteSpacingDAO = daoFactory.getLocalSpacingDAO();
        return sqlLiteSpacingDAO.deleteAllSpacings();
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

    private int clearPersistences() throws DAOException {
        LocalPersistenceDAO sqlLitePersistenceDAO = daoFactory.getLocalPersistenceDAO();
        return sqlLitePersistenceDAO.deleteAllPersistences();
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

    private int clearApertures() throws DAOException {
        LocalApertureDAO sqlLiteApertureDAO = daoFactory.getLocalApertureDAO();
        return sqlLiteApertureDAO.deleteAllApertures();
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


    private int clearDiscontinuityShapes() throws DAOException {
        LocalDiscontinuityShapeDAO sqlLiteDiscontinuityShapeDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        return sqlLiteDiscontinuityShapeDAO.deleteAllDiscontinuityShapes();
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

    private int clearRoughnesses() throws DAOException {
        LocalRoughnessDAO sqlLiteRoughnessDAO = daoFactory.getLocalRoughnessDAO();
        return sqlLiteRoughnessDAO.deleteAllRoughnesses();
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

    private int clearInfillings() throws DAOException {
        LocalInfillingDAO sqlLiteInfillingDAO = daoFactory.getLocalInfillingDAO();
        return sqlLiteInfillingDAO.deleteAllInfillings();
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

    private int clearWeatherings() throws DAOException {
        LocalWeatheringDAO sqlLiteWeatheringDAO = daoFactory.getLocalWeatheringDAO();
        return sqlLiteWeatheringDAO.deleteAllWeatherings();
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

    private int clearDiscontinuityWaters() throws DAOException {
        LocalDiscontinuityWaterDAO sqlLiteDiscontinuityWaterDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        return sqlLiteDiscontinuityWaterDAO.deleteAllDiscontinuityWaters();
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

    private int clearFractureTypes() throws DAOException {
        LocalFractureTypeDAO sqlLiteFractureDAO = daoFactory.getLocalFractureTypeDAO();
        return sqlLiteFractureDAO.deleteAllFractureTypes();
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

    private int clearBoltTypes() {
        LocalBoltTypeDAO sqlLiteBoltTypesDAO = daoFactory.getLocalBoltTypeDAO();
        return sqlLiteBoltTypesDAO.deleteAllBoltTypes();
    }

    private Long syncBoltTypes() throws DAOException {
        //Read from DropBox
        RemoteBoltTypeDAO dropBoxBoltTypeDAO = daoFactory.getRemoteBoltTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<BoltType> downloadedBoltTypes = dropBoxBoltTypeDAO.getAllBoltTypes();
        //Write into the SQLLite
        LocalBoltTypeDAO sqlLiteBoltTypeDAO = daoFactory.getLocalBoltTypeDAO();
        for (BoltType downloadedBoltType : downloadedBoltTypes) {
            sqlLiteBoltTypeDAO.saveBoltType(downloadedBoltType);
        }
        return Long.valueOf(downloadedBoltTypes.size());
    }

    private int clearShotcreteTypes() {
        LocalShotcreteTypeDAO sqlLiteShotcreteDAO = daoFactory.getLocalShotcreteTypeDAO();
        return sqlLiteShotcreteDAO.deleteAllShotcreteTypes();
    }

    private Long syncShotcreteTypes() throws DAOException {
        //Read from DropBox
        RemoteShotcreteTypeDAO dropBoxShotcreteTypeDAO = daoFactory.getRemoteShotcreteTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<ShotcreteType> downloadedShotcreteTypes = dropBoxShotcreteTypeDAO.getAllShotcreteTypes();
        //Write into the SQLLite
        LocalShotcreteTypeDAO sqlLiteShotcreteTypeDAO = daoFactory.getLocalShotcreteTypeDAO();
        for (ShotcreteType downloadedShotcreteType : downloadedShotcreteTypes) {
            sqlLiteShotcreteTypeDAO.saveShotcreteType(downloadedShotcreteType);
        }
        return Long.valueOf(downloadedShotcreteTypes.size());
    }


    private int clearMeshTypes() {
        LocalMeshTypeDAO sqlLiteMeshTypeDAO = daoFactory.getLocalMeshTypeDAO();
        return sqlLiteMeshTypeDAO.deleteAllMeshTypes();
    }

    private Long syncMeshTypes() throws DAOException {
        //Read from DropBox
        RemoteMeshTypeDAO dropBoxMeshTypeDAO = daoFactory.getRemoteMeshTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<MeshType> downloadedMeshTypes = dropBoxMeshTypeDAO.getAllMeshTypes();
        //Write into the SQLLite
        LocalMeshTypeDAO sqlLiteMeshTypeDAO = daoFactory.getLocalMeshTypeDAO();
        for (MeshType downloadedMeshType : downloadedMeshTypes) {
            sqlLiteMeshTypeDAO.saveMeshType(downloadedMeshType);
        }
        return Long.valueOf(downloadedMeshTypes.size());
    }


    private int clearCoverages() {
        LocalCoverageDAO sqlLiteCoverageDAO = daoFactory.getLocalCoverageDAO();
        return sqlLiteCoverageDAO.deleteAllCoverages();
    }

    private Long syncCoverages() throws DAOException {
        //Read from DropBox
        RemoteCoverageDAO dropBoxCoverageDAO = daoFactory.getRemoteCoverageDAO(DAOFactory.Flavour.DROPBOX);
        List<Coverage> downloadedCoverages = dropBoxCoverageDAO.getAllCoverages();
        //Write into the SQLLite
        LocalCoverageDAO sqlLiteCoverageDAO = daoFactory.getLocalCoverageDAO();
        for (Coverage downloadedCoverage : downloadedCoverages) {
            sqlLiteCoverageDAO.saveCoverage(downloadedCoverage);
        }
        return Long.valueOf(downloadedCoverages.size());
    }

    private int clearArchTypes() {
        LocalArchTypeDAO sqlLiteArchDAO = daoFactory.getLocalArchTypeDAO();
        return sqlLiteArchDAO.deleteAllArchTypes();
    }

    private Long syncArchTypes() throws DAOException {
        //Read from DropBox
        RemoteArchTypeDAO dropBoxArchTypeDAO = daoFactory.getRemoteArchTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<ArchType> downloadedArchTypes = dropBoxArchTypeDAO.getAllArchTypes();
        //Write into the SQLLite
        LocalArchTypeDAO sqlLiteArchTypeDAO = daoFactory.getLocalArchTypeDAO();
        for (ArchType downloadedArchType : downloadedArchTypes) {
            sqlLiteArchTypeDAO.saveArchType(downloadedArchType);
        }
        return Long.valueOf(downloadedArchTypes.size());
    }

    private int clearSupportPatternTypes() {
        LocalSupportPatternTypeDAO sqlLitePatternDAO = daoFactory.getLocalSupportPatternTypeDAO();
        return sqlLitePatternDAO.deleteAllSupportPatternTypes();
    }

    private Long syncSupportPatternTypes() throws DAOException {
        //Read from DropBox
        RemoteSupportPatternTypeDAO dropBoxSupportPatternTypeDAO = daoFactory.getRemoteSupportPatternTypeDAO(DAOFactory.Flavour.DROPBOX);
        List<SupportPatternType> downloadedSupportPatternTypes = dropBoxSupportPatternTypeDAO.getAllSupportPatternTypes();
        //Write into the SQLLite
        LocalSupportPatternTypeDAO sqlLiteSupportPatternTypeDAO = daoFactory.getLocalSupportPatternTypeDAO();
        for (SupportPatternType downloadedSupportPatternType : downloadedSupportPatternTypes) {
            sqlLiteSupportPatternTypeDAO.saveSupportPatternType(downloadedSupportPatternType);
        }
        return Long.valueOf(downloadedSupportPatternTypes.size());
    }

    private int clearESRs() throws DAOException {
        LocalEsrDAO sqlLiteESRDAO = daoFactory.getLocalEsrDAO();
        return sqlLiteESRDAO.deleteAllESRs();
    }

    private Long syncESRs() throws DAOException {
        //Read from DropBox
        RemoteEsrDAO dropBoxESRDAO = daoFactory.getRemoteEsrDAO(DAOFactory.Flavour.DROPBOX);
        List<ESR> dowloadedESRs = dropBoxESRDAO.getAllESRs();
        //Write into the SQLLite
        LocalEsrDAO sqlLiteESRDAO = daoFactory.getLocalEsrDAO();
        for (ESR dowloadedESR : dowloadedESRs) {
            sqlLiteESRDAO.saveESR(dowloadedESR);
        }
        return Long.valueOf(dowloadedESRs.size());
    }

    private int clearRockQualities() throws DAOException {
        LocalRockQualityDAO sqlLiteRockQualityDAO = daoFactory.getLocalRockQualityDAO();
        return sqlLiteRockQualityDAO.deleteAllRockQualities();
    }

    private Long syncRockQualities() throws DAOException {
        //Read from DropBox
        RemoteRockQualityDAO dropBoxRockQualityDAO = daoFactory.getRemoteRockQualityDAO(DAOFactory.Flavour.DROPBOX);
        List<RockQuality> dowloadedRockQualitys = dropBoxRockQualityDAO.getAllRockQualities();
        //Write into the SQLLite
        LocalRockQualityDAO sqlLiteRockQualityDAO = daoFactory.getLocalRockQualityDAO();
        for (RockQuality dowloadedRockQuality : dowloadedRockQualitys) {
            sqlLiteRockQualityDAO.saveRockQuality(dowloadedRockQuality);
        }
        return Long.valueOf(dowloadedRockQualitys.size());
    }

    private int clearExcavationFactors() throws DAOException {
        LocalExcavationFactorDAO factorsDAO = daoFactory.getLocalExcavationFactorsDAO();
        return factorsDAO.deleteAllExcavationFactors();
    }


    private int clearSupportRequirements() {
        LocalSupportRequirementDAO sqlLiteSupportRequirementsDAO = daoFactory.getLocalSupportRequirementDAO();
        return sqlLiteSupportRequirementsDAO.deleteAllSupportRequirements();
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
