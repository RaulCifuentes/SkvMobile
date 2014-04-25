package com.metric.skava.data.dao;

import android.content.Context;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.ClientDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityRelevanceDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityShapeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityWaterDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationMethodDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationProjectDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationSectionDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.RoleDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.SpacingDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.TunnelDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.TunnelFaceDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.UserDAODropboxImpl;
import com.metric.skava.data.dao.impl.sqllite.ApertureDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ArchTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.AssessmentDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.BoltTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ClientDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.CoverageDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityRelevanceDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityShapeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityWaterDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.EsrDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ExcavationMethodDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ExcavationProjectDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ExcavationSectionDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.FractureTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.GroundwaterDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.InfillingDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.JaDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.JnDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.JrDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.JwDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.MeshTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.OrientationDiscontinuitiesDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.PermissionDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.PersistenceDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.RoleDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.RoughnessDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ShotcreteTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.SpacingDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.SrfDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.StrengthDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.SupportRequirementDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.TunnelDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.TunnelFaceDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.UserDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.WeatheringDAOsqlLiteImpl;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.dao.SyncLoggingDAOsqlLiteImpl;

/**
 * Created by metricboy on 3/14/14.
 */
public class DAOFactory {




    public static enum Flavour {
        SQLLITE, DROPBOX;
    }

    private Context mContext;

    private static DAOFactory instance;
    public static DAOFactory getInstance(Context context) {
        if (instance == null) {
            instance = new DAOFactory(context);
        }
        return instance;
    }


    private DAOFactory(Context context) {
        this.mContext = context;
    }

    public LocalUserDAO getLocalUserDAO(Flavour daoFlavour) throws DAOException {
        LocalUserDAO localUserDAO = null;
        switch (daoFlavour){
            case SQLLITE:
                localUserDAO = new UserDAOsqlLiteImpl(mContext);
                break;
        }
        return localUserDAO;
    }

    public RemoteUserDAO getRemoteUserDAO(Flavour daoFlavour) throws DAOException {
        RemoteUserDAO remoteUserDAO = null;
        switch (daoFlavour){
            case DROPBOX:
                remoteUserDAO = new UserDAODropboxImpl(mContext, null);
                break;
        }
        return remoteUserDAO;
    }

    public LocalRoleDAO getLocalRoleDAO(Flavour daoFlavour) throws DAOException {
        LocalRoleDAO localRoleDAO = null;
        switch (daoFlavour){
            case SQLLITE:
                localRoleDAO = new RoleDAOsqlLiteImpl(mContext);
                break;
        }
        return localRoleDAO;
    }

    public RemoteRoleDAO getRemoteRoleDAO(Flavour daoFlavour) throws DAOException {
        RemoteRoleDAO remoteRoleDAO = null;
        switch (daoFlavour){
            case DROPBOX:
                remoteRoleDAO = new RoleDAODropboxImpl(mContext, null);
                break;
        }
        return remoteRoleDAO;
    }

    public LocalClientDAO getLocalClientDAO(Flavour daoFlavour) throws DAOException {
        LocalClientDAO localClientDAO = null;
        switch (daoFlavour){
            case SQLLITE:
                localClientDAO = new ClientDAOsqlLiteImpl(mContext);
                break;
        }
        return localClientDAO;
    }

    public RemoteClientDAO getRemoteClientDAO(Flavour daoFlavour) throws DAOException {
        RemoteClientDAO remoteClientDAO = null;
        switch (daoFlavour){
            case DROPBOX:
                remoteClientDAO = new ClientDAODropboxImpl(mContext, null);
                break;
        }
        return remoteClientDAO;
    }


    public LocalPermissionDAO getLocalPermissionDAO(Flavour daoFlavour) throws DAOException {
        LocalPermissionDAO localPermissionDAO = null;
        switch (daoFlavour){
            case SQLLITE:
                localPermissionDAO = new PermissionDAOsqlLiteImpl(mContext);
                break;
        }
        return localPermissionDAO;
    }



    public LocalExcavationProjectDAO getLocalExcavationProjectDAO(Flavour daoFlavour) throws DAOException {
        LocalExcavationProjectDAO projectDAO = null;
        switch (daoFlavour) {
            case SQLLITE:
                projectDAO = new ExcavationProjectDAOsqlLiteImpl(mContext);
                break;
        }
        return projectDAO;
    }

    public RemoteExcavationProjectDAO getRemoteExcavationProjectDAO(Flavour daoFlavour) throws DAOException {
        RemoteExcavationProjectDAO projectDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                projectDAO = new ExcavationProjectDAODropboxImpl(mContext, null);
                break;
        }
        return projectDAO;
    }


    public LocalTunnelDAO getLocalTunnelDAO(Flavour daoFlavour) throws DAOException {
        LocalTunnelDAO tunnelDAO = null;
        switch (daoFlavour) {
            case SQLLITE:
                tunnelDAO = new TunnelDAOsqlLiteImpl(mContext);
                break;
        }
        return tunnelDAO;
    }

    public RemoteTunnelDAO getRemoteTunnelDAO(Flavour daoFlavour) throws DAOException {
        RemoteTunnelDAO tunnelDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                tunnelDAO = new TunnelDAODropboxImpl(mContext, null);
                break;
        }
        return tunnelDAO;
    }

    public LocalTunnelFaceDAO getLocalTunnelFaceDAO(Flavour daoFlavour) throws DAOException {
        LocalTunnelFaceDAO tunnelFaceDAO = null;
        switch (daoFlavour) {
            case SQLLITE:
                tunnelFaceDAO = new TunnelFaceDAOsqlLiteImpl(mContext);
                break;
        }
        return tunnelFaceDAO;
    }

    public RemoteTunnelFaceDAO getRemoteTunnelFaceDAO(Flavour daoFlavour) throws DAOException {
        RemoteTunnelFaceDAO tunnelFaceDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                tunnelFaceDAO = new TunnelFaceDAODropboxImpl(mContext, null);
                break;
        }
        return tunnelFaceDAO;
    }

    public LocalExcavationSectionDAO getLocalExcavationSectionDAO(Flavour daoFlavour) throws DAOException {
        LocalExcavationSectionDAO localExcavationSectionDAO = null;
        switch (daoFlavour) {
            case SQLLITE:
                localExcavationSectionDAO = new ExcavationSectionDAOsqlLiteImpl(mContext);
                break;
        }
        return localExcavationSectionDAO;
    }

    public RemoteExcavationSectionDAO getRemoteExcavationSectionDAO(Flavour daoFlavour) throws DAOException {
        RemoteExcavationSectionDAO remoteExcavationSectionDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteExcavationSectionDAO = new ExcavationSectionDAODropboxImpl(mContext, null);
                break;
        }
        return remoteExcavationSectionDAO;
    }

    public LocalEsrDAO getLocalEsrDAO(Flavour daoFlavour) throws DAOException {
        LocalEsrDAO localEsrDAO = null;
        switch (daoFlavour) {
            case SQLLITE:
                localEsrDAO = new EsrDAOsqlLiteImpl(mContext);
                break;
        }
        return localEsrDAO;
    }


    public LocalExcavationMethodDAO getLocalExcavationMethodDAO(Flavour daoFlavour) throws DAOException {
        LocalExcavationMethodDAO localExcavationMethodDAO = null;
        switch (daoFlavour) {
            case SQLLITE:
                localExcavationMethodDAO = new ExcavationMethodDAOsqlLiteImpl(mContext);
                break;
        }
        return localExcavationMethodDAO;
    }

    public RemoteExcavationMethodDAO getRemoteExcavationMethodDAO(Flavour daoFlavour) throws DAOException {
        RemoteExcavationMethodDAO remoteExcavationMethodDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteExcavationMethodDAO = new ExcavationMethodDAODropboxImpl(mContext, null);
                break;
        }
        return remoteExcavationMethodDAO;
    }


    public LocalDiscontinuityTypeDAO getLocalDiscontinuityTypeDAO() {
        LocalDiscontinuityTypeDAO localDiscontinuityTypeDAO = new DiscontinuityTypeDAOsqlLiteImpl(mContext);
        return localDiscontinuityTypeDAO;
    }

    public RemoteDiscontinuityTypeDAO getRemoteDiscontinuityTypeDAO() throws DAOException {
        RemoteDiscontinuityTypeDAO remoteDiscontinuityTypeDAO = new DiscontinuityTypeDAODropboxImpl(mContext, null);
        return remoteDiscontinuityTypeDAO;
    }


    public LocalDiscontinuityRelevanceDAO getLocalDiscontinuityRelevanceDAO() {
        LocalDiscontinuityRelevanceDAO localDiscontinuityRelevanceDAO = new DiscontinuityRelevanceDAOsqlLiteImpl(mContext);
        return localDiscontinuityRelevanceDAO;
    }

    public RemoteDiscontinuityRelevanceDAO getRemoteDiscontinuityRelevanceDAO() throws DAOException {
        RemoteDiscontinuityRelevanceDAO remoteDiscontinuityRelevanceDAO = new DiscontinuityRelevanceDAODropboxImpl(mContext, null);
        return remoteDiscontinuityRelevanceDAO;
    }

    public LocalDiscontinuityWaterDAO getLocalDiscontinuityWaterDAO() throws DAOException {
        LocalDiscontinuityWaterDAO waterDAO = new DiscontinuityWaterDAOsqlLiteImpl(mContext);
        return waterDAO;
    }

    public RemoteDiscontinuityWaterDAO getRemoteDiscontinuityWaterDAO() throws DAOException {
        RemoteDiscontinuityWaterDAO remoteDiscontinuityWaterDAO = new DiscontinuityWaterDAODropboxImpl(mContext, null);
        return remoteDiscontinuityWaterDAO;
    }

    public LocalDiscontinuityShapeDAO getLocalDiscontinuityShapeDAO() throws DAOException {
        LocalDiscontinuityShapeDAO shapeDAO = new DiscontinuityShapeDAOsqlLiteImpl(mContext);
        return shapeDAO;
    }

    public RemoteDiscontinuityShapeDAO getRemoteDiscontinuityShapeDAO() throws DAOException {
        RemoteDiscontinuityShapeDAO remoteDiscontinuityShapeDAO = new DiscontinuityShapeDAODropboxImpl(mContext, null);
        return remoteDiscontinuityShapeDAO;
    }

    public LocalFractureTypeDAO getLocalFractureTypeDAO() throws DAOException {
        LocalFractureTypeDAO fractureTypeDAO = new FractureTypeDAOsqlLiteImpl(mContext);
        return fractureTypeDAO;
    }


    public LocalBoltTypeDAO getLocalBoltTypeDAO() {
        LocalBoltTypeDAO localBoltTypeDAO = new BoltTypeDAOsqlLiteImpl(mContext);
        return localBoltTypeDAO;
    }

    public LocalShotcreteTypeDAO getLocalShotcreteTypeDAO() {
        LocalShotcreteTypeDAO shotcreteTypeDAO = new ShotcreteTypeDAOsqlLiteImpl(mContext);
        return shotcreteTypeDAO;
    }

    public LocalMeshTypeDAO getLocalMeshTypeDAO() {
        LocalMeshTypeDAO localMeshTypeDAO = new MeshTypeDAOsqlLiteImpl(mContext);
        return localMeshTypeDAO;
    }

    public LocalCoverageDAO getLocalCoverageDAO() {
        LocalCoverageDAO localCoverageDAO = new CoverageDAOsqlLiteImpl(mContext);
        return localCoverageDAO;
    }

    public LocalArchTypeDAO getLocalArchTypeDAO() {
        LocalArchTypeDAO localArchTypeDAO = new ArchTypeDAOsqlLiteImpl(mContext);
        return localArchTypeDAO;
    }

    public SupportRequirementDAO getSupportRequirementDAO() {
        SupportRequirementDAO supportRequirementDAO = new SupportRequirementDAOsqlLiteImpl(mContext);
        return supportRequirementDAO;
    }


    public LocalAssessmentDAO getAssessmentDAO(Flavour daoFlavour) throws DAOException {
        LocalAssessmentDAO localAssessmentDAO = new AssessmentDAOsqlLiteImpl(mContext);
        return localAssessmentDAO;
    }


    public LocalJnDAO getLocalJnDAO() throws DAOException {
        LocalJnDAO localJnDAO = new JnDAOsqlLiteImpl(mContext);
        return localJnDAO;
    }

    public LocalJaDAO getLocalJaDAO() throws DAOException {
        LocalJaDAO localJaDAO = new JaDAOsqlLiteImpl(mContext);
        return localJaDAO;
    }

    public LocalJrDAO getLocalJrDAO() throws DAOException {
        LocalJrDAO localJrDAO = new JrDAOsqlLiteImpl(mContext);
        return localJrDAO;
    }

    public LocalJwDAO getLocalJwDAO() throws DAOException {
        LocalJwDAO localJwDAO = new JwDAOsqlLiteImpl(mContext);
        return localJwDAO;
    }

    public LocalSrfDAO getLocalSrfDAO() throws DAOException {
        LocalSrfDAO localSrfDAO = new SrfDAOsqlLiteImpl(mContext);
        return localSrfDAO;
    }


    public LocalOrientationDiscontinuitiesDAO getLocalOrientationDiscontinuitiesDAO() throws DAOException{
        LocalOrientationDiscontinuitiesDAO orientationDAO = new OrientationDiscontinuitiesDAOsqlLiteImpl(mContext);
        return orientationDAO;
    }


    public LocalSpacingDAO getLocalSpacingDAO() throws DAOException {
        LocalSpacingDAO spacingDAO = new SpacingDAOsqlLiteImpl(mContext);
        return spacingDAO;
    }

    public LocalPersistenceDAO getLocalPersistenceDAO() throws DAOException {
        LocalPersistenceDAO persistenceDAO = new PersistenceDAOsqlLiteImpl(mContext);
        return persistenceDAO;
    }

    public LocalApertureDAO getLocalApertureDAO() throws DAOException {
        LocalApertureDAO apertureDAO = new ApertureDAOsqlLiteImpl(mContext);
        return apertureDAO;
    }

    public LocalInfillingDAO getLocalInfillingDAO() throws DAOException {
        LocalInfillingDAO infillingDAO = new InfillingDAOsqlLiteImpl(mContext);
        return infillingDAO;
    }

    public LocalWeatheringDAO getLocalWeatheringDAO() throws DAOException {
        LocalWeatheringDAO weatheringDAO = new WeatheringDAOsqlLiteImpl(mContext);
        return weatheringDAO;
    }

    public LocalRoughnessDAO getLocalRoughnessDAO() throws DAOException {
        LocalRoughnessDAO roughnessDAO = new RoughnessDAOsqlLiteImpl(mContext);
        return roughnessDAO;
    }


    public LocalGroundwaterDAO getLocalGroundwaterDAO() throws DAOException {
        LocalGroundwaterDAO groundwaterDAO = new GroundwaterDAOsqlLiteImpl(mContext);
        return groundwaterDAO;
    }

    public LocalStrengthDAO getLocalStrengthDAO() throws DAOException {
        LocalStrengthDAO strengthDAO = new StrengthDAOsqlLiteImpl(mContext);
        return strengthDAO;
    }


    public RemoteSpacingDAO getRemoteSpacingDAO() throws DAOException {
        RemoteSpacingDAO spacingDAO = new SpacingDAODropboxImpl(mContext, null);
        return spacingDAO;
    }

    public SyncLoggingDAO getSyncLoggingDAO() throws DAOException {
        return new SyncLoggingDAOsqlLiteImpl(mContext);
    }
}
