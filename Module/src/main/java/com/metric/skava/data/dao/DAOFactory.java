package com.metric.skava.data.dao;

import android.content.Context;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.ApertureDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ClientDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityRelevanceDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityShapeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityWaterDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationMethodDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationProjectDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationSectionDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.GroundwaterDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.InfillingDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.PersistenceDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.RoleDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.RoughnessDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.SpacingDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.StrengthDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.TunnelDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.TunnelFaceDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.UserDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.WeatheringDAODropboxImpl;
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
import com.metric.skava.data.dao.impl.sqllite.GroupDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.IndexDAOsqlLiteImpl;
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

    //Placeholder enumeration for future remote implementations
    public static enum Flavour {
        DROPBOX;
    }

    private Context mContext;
    private SkavaContext mSkavaContext;

    private static DAOFactory instance;

    public static DAOFactory getInstance(Context context, SkavaContext skavaContext) {
        if (instance == null) {
            instance = new DAOFactory(context, skavaContext);
        }
        return instance;
    }


    private DAOFactory(Context context, SkavaContext skavaContext) {
        this.mContext = context;
        this.mSkavaContext = skavaContext;
    }

    public void setSkavaContext(SkavaContext mSkavaContext) {
        this.mSkavaContext = mSkavaContext;
    }

    public LocalUserDAO getLocalUserDAO() throws DAOException {
        LocalUserDAO localUserDAO = new UserDAOsqlLiteImpl(mContext, mSkavaContext);
        return localUserDAO;
    }

    public RemoteUserDAO getRemoteUserDAO(Flavour daoFlavour) throws DAOException {
        RemoteUserDAO remoteUserDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteUserDAO = new UserDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return remoteUserDAO;
    }

    public LocalRoleDAO getLocalRoleDAO() throws DAOException {
        LocalRoleDAO localRoleDAO = new RoleDAOsqlLiteImpl(mContext, mSkavaContext);
        return localRoleDAO;
    }

    public RemoteRoleDAO getRemoteRoleDAO(Flavour daoFlavour) throws DAOException {
        RemoteRoleDAO remoteRoleDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteRoleDAO = new RoleDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return remoteRoleDAO;
    }

    public LocalClientDAO getLocalClientDAO() throws DAOException {
        LocalClientDAO localClientDAO = new ClientDAOsqlLiteImpl(mContext, mSkavaContext);
        return localClientDAO;
    }

    public RemoteClientDAO getRemoteClientDAO(Flavour daoFlavour) throws DAOException {
        RemoteClientDAO remoteClientDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteClientDAO = new ClientDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return remoteClientDAO;
    }


    public LocalPermissionDAO getLocalPermissionDAO() throws DAOException {
        LocalPermissionDAO localPermissionDAO = new PermissionDAOsqlLiteImpl(mContext, mSkavaContext);
        return localPermissionDAO;
    }


    public LocalExcavationProjectDAO getLocalExcavationProjectDAO() throws DAOException {
        LocalExcavationProjectDAO projectDAO = new ExcavationProjectDAOsqlLiteImpl(mContext, mSkavaContext);
        return projectDAO;
    }

    public RemoteExcavationProjectDAO getRemoteExcavationProjectDAO(Flavour daoFlavour) throws DAOException {
        RemoteExcavationProjectDAO projectDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                projectDAO = new ExcavationProjectDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return projectDAO;
    }


    public LocalTunnelDAO getLocalTunnelDAO() throws DAOException {
        LocalTunnelDAO tunnelDAO = new TunnelDAOsqlLiteImpl(mContext, mSkavaContext);
        return tunnelDAO;
    }

    public RemoteTunnelDAO getRemoteTunnelDAO(Flavour daoFlavour) throws DAOException {
        RemoteTunnelDAO tunnelDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                tunnelDAO = new TunnelDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return tunnelDAO;
    }

    public LocalTunnelFaceDAO getLocalTunnelFaceDAO() throws DAOException {
        LocalTunnelFaceDAO tunnelFaceDAO = new TunnelFaceDAOsqlLiteImpl(mContext, mSkavaContext);
        return tunnelFaceDAO;
    }

    public RemoteTunnelFaceDAO getRemoteTunnelFaceDAO(Flavour daoFlavour) throws DAOException {
        RemoteTunnelFaceDAO tunnelFaceDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                tunnelFaceDAO = new TunnelFaceDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return tunnelFaceDAO;
    }

    public LocalExcavationSectionDAO getLocalExcavationSectionDAO() throws DAOException {
        LocalExcavationSectionDAO localExcavationSectionDAO = new ExcavationSectionDAOsqlLiteImpl(mContext, mSkavaContext);
        return localExcavationSectionDAO;
    }

    public RemoteExcavationSectionDAO getRemoteExcavationSectionDAO(Flavour daoFlavour) throws DAOException {
        RemoteExcavationSectionDAO remoteExcavationSectionDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteExcavationSectionDAO = new ExcavationSectionDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return remoteExcavationSectionDAO;
    }

    public LocalEsrDAO getLocalEsrDAO() throws DAOException {
        LocalEsrDAO localEsrDAO = new EsrDAOsqlLiteImpl(mContext, mSkavaContext);
        return localEsrDAO;
    }


    public LocalExcavationMethodDAO getLocalExcavationMethodDAO() throws DAOException {
        LocalExcavationMethodDAO localExcavationMethodDAO = new ExcavationMethodDAOsqlLiteImpl(mContext, mSkavaContext);
        return localExcavationMethodDAO;
    }

    public RemoteExcavationMethodDAO getRemoteExcavationMethodDAO(Flavour daoFlavour) throws DAOException {
        RemoteExcavationMethodDAO remoteExcavationMethodDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteExcavationMethodDAO = new ExcavationMethodDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return remoteExcavationMethodDAO;
    }


    public LocalIndexDAO getLocalIndexDAO() throws DAOException {
        LocalIndexDAO sqlLiteIndexDAO = new IndexDAOsqlLiteImpl(mContext, mSkavaContext);
        return sqlLiteIndexDAO;
    }

    public LocalGroupDAO getLocalGroupDAO() throws DAOException {
        LocalGroupDAO sqlLiteIndexDAO = new GroupDAOsqlLiteImpl(mContext, mSkavaContext);
        return sqlLiteIndexDAO;
    }

    public LocalDiscontinuityTypeDAO getLocalDiscontinuityTypeDAO() {
        LocalDiscontinuityTypeDAO localDiscontinuityTypeDAO = new DiscontinuityTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return localDiscontinuityTypeDAO;
    }

    public RemoteDiscontinuityTypeDAO getRemoteDiscontinuityTypeDAO(Flavour daoFlavour) throws DAOException {
        RemoteDiscontinuityTypeDAO remoteDiscontinuityTypeDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteDiscontinuityTypeDAO = new DiscontinuityTypeDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteDiscontinuityTypeDAO;
    }


    public LocalDiscontinuityRelevanceDAO getLocalDiscontinuityRelevanceDAO() {
        LocalDiscontinuityRelevanceDAO localDiscontinuityRelevanceDAO = new DiscontinuityRelevanceDAOsqlLiteImpl(mContext, mSkavaContext);
        return localDiscontinuityRelevanceDAO;
    }

    public RemoteDiscontinuityRelevanceDAO getRemoteDiscontinuityRelevanceDAO(Flavour daoFlavour) throws DAOException {
        RemoteDiscontinuityRelevanceDAO remoteDiscontinuityRelevanceDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteDiscontinuityRelevanceDAO = new DiscontinuityRelevanceDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteDiscontinuityRelevanceDAO;
    }

    public LocalDiscontinuityWaterDAO getLocalDiscontinuityWaterDAO() throws DAOException {
        LocalDiscontinuityWaterDAO waterDAO = new DiscontinuityWaterDAOsqlLiteImpl(mContext, mSkavaContext);
        return waterDAO;
    }

    public RemoteDiscontinuityWaterDAO getRemoteDiscontinuityWaterDAO(Flavour daoFlavour) throws DAOException {
        RemoteDiscontinuityWaterDAO remoteDiscontinuityWaterDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteDiscontinuityWaterDAO = new DiscontinuityWaterDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteDiscontinuityWaterDAO;
    }

    public LocalDiscontinuityShapeDAO getLocalDiscontinuityShapeDAO() throws DAOException {
        LocalDiscontinuityShapeDAO shapeDAO = new DiscontinuityShapeDAOsqlLiteImpl(mContext, mSkavaContext);
        return shapeDAO;
    }

    public RemoteDiscontinuityShapeDAO getRemoteDiscontinuityShapeDAO(Flavour daoFlavour) throws DAOException {
        RemoteDiscontinuityShapeDAO remoteDiscontinuityShapeDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteDiscontinuityShapeDAO = new DiscontinuityShapeDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteDiscontinuityShapeDAO;
    }

    public LocalFractureTypeDAO getLocalFractureTypeDAO() throws DAOException {
        LocalFractureTypeDAO fractureTypeDAO = new FractureTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return fractureTypeDAO;
    }


    public LocalBoltTypeDAO getLocalBoltTypeDAO() {
        LocalBoltTypeDAO localBoltTypeDAO = new BoltTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return localBoltTypeDAO;
    }

    public LocalShotcreteTypeDAO getLocalShotcreteTypeDAO() {
        LocalShotcreteTypeDAO shotcreteTypeDAO = new ShotcreteTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return shotcreteTypeDAO;
    }

    public LocalMeshTypeDAO getLocalMeshTypeDAO() {
        LocalMeshTypeDAO localMeshTypeDAO = new MeshTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return localMeshTypeDAO;
    }

    public LocalCoverageDAO getLocalCoverageDAO() {
        LocalCoverageDAO localCoverageDAO = new CoverageDAOsqlLiteImpl(mContext, mSkavaContext);
        return localCoverageDAO;
    }

    public LocalArchTypeDAO getLocalArchTypeDAO() {
        LocalArchTypeDAO localArchTypeDAO = new ArchTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return localArchTypeDAO;
    }

    public SupportRequirementDAO getSupportRequirementDAO() {
        SupportRequirementDAO supportRequirementDAO = new SupportRequirementDAOsqlLiteImpl(mContext, mSkavaContext);
        return supportRequirementDAO;
    }


    public LocalAssessmentDAO getLocalAssessmentDAO() throws DAOException {
        LocalAssessmentDAO localAssessmentDAO = new AssessmentDAOsqlLiteImpl(mContext, mSkavaContext);
        return localAssessmentDAO;
    }


    public LocalJnDAO getLocalJnDAO() throws DAOException {
        LocalJnDAO localJnDAO = new JnDAOsqlLiteImpl(mContext, mSkavaContext);
        return localJnDAO;
    }

    public LocalJaDAO getLocalJaDAO() throws DAOException {
        LocalJaDAO localJaDAO = new JaDAOsqlLiteImpl(mContext, mSkavaContext);
        return localJaDAO;
    }

    public LocalJrDAO getLocalJrDAO() throws DAOException {
        LocalJrDAO localJrDAO = new JrDAOsqlLiteImpl(mContext, mSkavaContext);
        return localJrDAO;
    }

    public LocalJwDAO getLocalJwDAO() throws DAOException {
        LocalJwDAO localJwDAO = new JwDAOsqlLiteImpl(mContext, mSkavaContext);
        return localJwDAO;
    }

    public LocalSrfDAO getLocalSrfDAO() throws DAOException {
        LocalSrfDAO localSrfDAO = new SrfDAOsqlLiteImpl(mContext, mSkavaContext);
        return localSrfDAO;
    }


    public LocalOrientationDiscontinuitiesDAO getLocalOrientationDiscontinuitiesDAO() throws DAOException {
        LocalOrientationDiscontinuitiesDAO orientationDAO = new OrientationDiscontinuitiesDAOsqlLiteImpl(mContext, mSkavaContext);
        return orientationDAO;
    }


    public LocalSpacingDAO getLocalSpacingDAO() throws DAOException {
        LocalSpacingDAO spacingDAO = new SpacingDAOsqlLiteImpl(mContext, mSkavaContext);
        return spacingDAO;
    }

    public RemoteSpacingDAO getRemoteSpacingDAO(Flavour daoFlavour) throws DAOException {
        RemoteSpacingDAO spacingDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                spacingDAO = new SpacingDAODropboxImpl(mContext, mSkavaContext);
        }
        return spacingDAO;
    }

    public LocalPersistenceDAO getLocalPersistenceDAO() throws DAOException {
        LocalPersistenceDAO persistenceDAO = new PersistenceDAOsqlLiteImpl(mContext, mSkavaContext);
        return persistenceDAO;
    }

    public RemotePersistenceDAO getRemotePersistenceDAO(Flavour daoFlavour) throws DAOException {
        RemotePersistenceDAO persistenceDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                persistenceDAO = new PersistenceDAODropboxImpl(mContext, mSkavaContext);
        }
        return persistenceDAO;
    }

    public LocalApertureDAO getLocalApertureDAO() throws DAOException {
        LocalApertureDAO apertureDAO = new ApertureDAOsqlLiteImpl(mContext, mSkavaContext);
        return apertureDAO;
    }

    public RemoteApertureDAO getRemoteApertureDAO(Flavour daoFlavour) throws DAOException {
        RemoteApertureDAO apertureDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                apertureDAO = new ApertureDAODropboxImpl(mContext, mSkavaContext);
        }
        return apertureDAO;
    }

    public LocalInfillingDAO getLocalInfillingDAO() throws DAOException {
        LocalInfillingDAO infillingDAO = new InfillingDAOsqlLiteImpl(mContext, mSkavaContext);
        return infillingDAO;
    }

    public RemoteInfillingDAO getRemoteInfillingDAO(Flavour daoFlavour) throws DAOException {
        RemoteInfillingDAO infillingDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                infillingDAO = new InfillingDAODropboxImpl(mContext, mSkavaContext);
        }
        return infillingDAO;
    }

    public LocalWeatheringDAO getLocalWeatheringDAO() throws DAOException {
        LocalWeatheringDAO weatheringDAO = new WeatheringDAOsqlLiteImpl(mContext, mSkavaContext);
        return weatheringDAO;
    }

    public RemoteWeatheringDAO getRemoteWeatheringDAO(Flavour daoFlavour) throws DAOException {
        RemoteWeatheringDAO weatheringDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                weatheringDAO = new WeatheringDAODropboxImpl(mContext, mSkavaContext);
        }
        return weatheringDAO;
    }

    public LocalRoughnessDAO getLocalRoughnessDAO() throws DAOException {
        LocalRoughnessDAO roughnessDAO = new RoughnessDAOsqlLiteImpl(mContext, mSkavaContext);
        return roughnessDAO;
    }

    public RemoteRoughnessDAO getRemoteRoughnessDAO(Flavour daoFlavour) throws DAOException {
        RemoteRoughnessDAO roughnessDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                roughnessDAO = new RoughnessDAODropboxImpl(mContext, mSkavaContext);
        }
        return roughnessDAO;
    }

    public LocalGroundwaterDAO getLocalGroundwaterDAO() throws DAOException {
        LocalGroundwaterDAO groundwaterDAO = new GroundwaterDAOsqlLiteImpl(mContext, mSkavaContext);
        return groundwaterDAO;
    }

    public RemoteGroundwaterDAO getRemoteGroundwaterDAO(Flavour daoFlavour) throws DAOException {
        RemoteGroundwaterDAO groundwaterDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                groundwaterDAO = new GroundwaterDAODropboxImpl(mContext, mSkavaContext);
        }
        return groundwaterDAO;
    }

    public LocalStrengthDAO getLocalStrengthDAO() throws DAOException {
        LocalStrengthDAO strengthDAO = new StrengthDAOsqlLiteImpl(mContext, mSkavaContext);
        return strengthDAO;
    }

    public RemoteStrengthDAO getRemoteStrengthDAO(Flavour daoFlavour) throws DAOException {
        RemoteStrengthDAO roughnessDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                roughnessDAO = new StrengthDAODropboxImpl(mContext, mSkavaContext);
        }
        return roughnessDAO;
    }


    public SyncLoggingDAO getSyncLoggingDAO() throws DAOException {
        return new SyncLoggingDAOsqlLiteImpl(mContext, mSkavaContext);
    }
}
