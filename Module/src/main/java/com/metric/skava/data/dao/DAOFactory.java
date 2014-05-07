package com.metric.skava.data.dao;

import android.content.Context;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.ApertureDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ArchTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.AssessmentDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.BoltTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ClientDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.CoverageDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityRelevanceDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityShapeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.DiscontinuityWaterDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.EsrDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationMethodDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationProjectDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationSectionDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.FractureTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.GroundwaterDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.GroupDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.IndexDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.InfillingDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.JaDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.JnDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.JrDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.JwDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.MeshTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.OrientationDiscontinuitiesDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.PersistenceDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.RockQualityDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.RoleDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.RoughnessDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ShotcreteTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.SpacingDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.SrfDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.StrengthDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.SupportPatternTypeDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.SupportRequirementDAODropboxImpl;
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
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityFamilyDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityRelevanceDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityShapeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityWaterDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.EsrDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ExcavationFactorsDAOsqlLiteImpl;
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
import com.metric.skava.data.dao.impl.sqllite.QCalculationDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.RMRCalculationDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.RockQualityDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.RoleDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.RoughnessDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ShotcreteTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.SpacingDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.SrfDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.StrengthDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.SupportPatternTypeDAOsqlLiteImpl;
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

    public LocalDiscontinuityFamilyDAO getLocalDiscontinuityFamilyDAO() throws DAOException {
        LocalDiscontinuityFamilyDAO discontinuityFamilyDAO = new DiscontinuityFamilyDAOsqlLiteImpl(mContext, mSkavaContext);
        return discontinuityFamilyDAO;
    }

    public LocalQCalculationDAO getLocalQCalculationDAO() throws DAOException {
        LocalQCalculationDAO qCalculationDAO = new QCalculationDAOsqlLiteImpl(mContext, mSkavaContext);
        return qCalculationDAO;
    }

    public LocalRMRCalculationDAO getLocalRMRCalculationDAO() throws DAOException {
        LocalRMRCalculationDAO rmrCalculationDAO = new RMRCalculationDAOsqlLiteImpl(mContext, mSkavaContext);
        return rmrCalculationDAO;
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

    public LocalExcavationFactorDAO getLocalExcavationFactorsDAO() throws DAOException {
        LocalExcavationFactorDAO factorsDAO = new ExcavationFactorsDAOsqlLiteImpl(mContext, mSkavaContext);
        return factorsDAO;
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

    public LocalSupportPatternTypeDAO getLocalSupportPatternTypeDAO() {
        LocalSupportPatternTypeDAO localSupportPatternTypeDAO = new SupportPatternTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return localSupportPatternTypeDAO;
    }

    public RemoteSupportPatternTypeDAO getRemoteSupportPatternTypeDAO(Flavour daoFlavour) throws DAOException {
        RemoteSupportPatternTypeDAO remoteSupportPatternTypeDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteSupportPatternTypeDAO = new SupportPatternTypeDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return remoteSupportPatternTypeDAO;
    }

    public LocalEsrDAO getLocalEsrDAO() throws DAOException {
        LocalEsrDAO localEsrDAO = new EsrDAOsqlLiteImpl(mContext, mSkavaContext);
        return localEsrDAO;
    }

    public RemoteEsrDAO getRemoteEsrDAO(Flavour daoFlavour) throws DAOException {
        RemoteEsrDAO remoteEsrDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteEsrDAO = new EsrDAODropboxImpl(mContext, mSkavaContext);
                break;
        }
        return remoteEsrDAO;
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

    public RemoteIndexDAO getRemoteIndexDAO(Flavour daoFlavour) throws DAOException {
        RemoteIndexDAO remoteIndexDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteIndexDAO = new IndexDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteIndexDAO;
    }

    public LocalGroupDAO getLocalGroupDAO() throws DAOException {
        LocalGroupDAO sqlLiteIndexDAO = new GroupDAOsqlLiteImpl(mContext, mSkavaContext);
        return sqlLiteIndexDAO;
    }

    public RemoteGroupDAO getRemoteGroupDAO(Flavour daoFlavour) throws DAOException {
        RemoteGroupDAO remoteGroupDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteGroupDAO = new GroupDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteGroupDAO;
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

    public RemoteFractureTypeDAO getRemoteFractureTypeDAO(Flavour daoFlavour) throws DAOException {
        RemoteFractureTypeDAO remoteFractureTypeDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteFractureTypeDAO = new FractureTypeDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteFractureTypeDAO;
    }

    public LocalBoltTypeDAO getLocalBoltTypeDAO() {
        LocalBoltTypeDAO localBoltTypeDAO = new BoltTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return localBoltTypeDAO;
    }

    public RemoteBoltTypeDAO getRemoteBoltTypeDAO(Flavour daoFlavour) throws DAOException {
        RemoteBoltTypeDAO remoteBoltTypeDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteBoltTypeDAO = new BoltTypeDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteBoltTypeDAO;
    }

    public LocalRockQualityDAO getLocalRockQualityDAO() {
        LocalRockQualityDAO shotcreteTypeDAO = new RockQualityDAOsqlLiteImpl(mContext, mSkavaContext);
        return shotcreteTypeDAO;
    }

    public RemoteRockQualityDAO getRemoteRockQualityDAO(Flavour daoFlavour) throws DAOException {
        RemoteRockQualityDAO remoteRockQualityDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteRockQualityDAO = new RockQualityDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteRockQualityDAO;
    }

    public LocalShotcreteTypeDAO getLocalShotcreteTypeDAO() {
        LocalShotcreteTypeDAO shotcreteTypeDAO = new ShotcreteTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return shotcreteTypeDAO;
    }

    public RemoteShotcreteTypeDAO getRemoteShotcreteTypeDAO(Flavour daoFlavour) throws DAOException {
        RemoteShotcreteTypeDAO remoteShotcreteTypeDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteShotcreteTypeDAO = new ShotcreteTypeDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteShotcreteTypeDAO;
    }

    public LocalMeshTypeDAO getLocalMeshTypeDAO() {
        LocalMeshTypeDAO localMeshTypeDAO = new MeshTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return localMeshTypeDAO;
    }

    public RemoteMeshTypeDAO getRemoteMeshTypeDAO(Flavour daoFlavour) throws DAOException {
        RemoteMeshTypeDAO remoteMeshTypeDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteMeshTypeDAO = new MeshTypeDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteMeshTypeDAO;
    }


    public LocalCoverageDAO getLocalCoverageDAO() {
        LocalCoverageDAO localCoverageDAO = new CoverageDAOsqlLiteImpl(mContext, mSkavaContext);
        return localCoverageDAO;
    }

    public RemoteCoverageDAO getRemoteCoverageDAO(Flavour daoFlavour) throws DAOException {
        RemoteCoverageDAO remoteCoverageDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteCoverageDAO = new CoverageDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteCoverageDAO;
    }

    public LocalArchTypeDAO getLocalArchTypeDAO() {
        LocalArchTypeDAO localArchTypeDAO = new ArchTypeDAOsqlLiteImpl(mContext, mSkavaContext);
        return localArchTypeDAO;
    }

    public RemoteArchTypeDAO getRemoteArchTypeDAO(Flavour daoFlavour) throws DAOException {
        RemoteArchTypeDAO remoteArchTypeDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteArchTypeDAO = new ArchTypeDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteArchTypeDAO;
    }

    public LocalSupportRequirementDAO getLocalSupportRequirementDAO() {
        LocalSupportRequirementDAO supportRequirementDAO = new SupportRequirementDAOsqlLiteImpl(mContext, mSkavaContext);
        return supportRequirementDAO;
    }

    public RemoteSupportRequirementDAO getRemoteSupportRequirementDAO(Flavour daoFlavour) throws DAOException {
        RemoteSupportRequirementDAO supportRequirementDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                supportRequirementDAO = new SupportRequirementDAODropboxImpl(mContext, mSkavaContext);
        }
        return supportRequirementDAO;
    }

    public LocalAssessmentDAO getLocalAssessmentDAO() throws DAOException {
        LocalAssessmentDAO localAssessmentDAO = new AssessmentDAOsqlLiteImpl(mContext, mSkavaContext);
        return localAssessmentDAO;
    }

    public RemoteAssessmentDAO getRemoteAssessmentDAO(Flavour daoFlavour) throws DAOException {
        RemoteAssessmentDAO remoteAssessmentDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteAssessmentDAO = new AssessmentDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteAssessmentDAO;
    }

    public LocalJnDAO getLocalJnDAO() throws DAOException {
        LocalJnDAO localJnDAO = new JnDAOsqlLiteImpl(mContext, mSkavaContext);
        return localJnDAO;
    }

    public RemoteJnDAO getRemoteJnDAO(Flavour daoFlavour) throws DAOException {
        RemoteJnDAO remoteJnDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteJnDAO = new JnDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteJnDAO;
    }


    public LocalJaDAO getLocalJaDAO() throws DAOException {
        LocalJaDAO localJaDAO = new JaDAOsqlLiteImpl(mContext, mSkavaContext);
        return localJaDAO;
    }

    public RemoteJaDAO getRemoteJaDAO(Flavour daoFlavour) throws DAOException {
        RemoteJaDAO remoteJaDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteJaDAO = new JaDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteJaDAO;
    }

    public LocalJrDAO getLocalJrDAO() throws DAOException {
        LocalJrDAO localJrDAO = new JrDAOsqlLiteImpl(mContext, mSkavaContext);
        return localJrDAO;
    }

    public RemoteJrDAO getRemoteJrDAO(Flavour daoFlavour) throws DAOException {
        RemoteJrDAO remoteJrDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteJrDAO = new JrDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteJrDAO;
    }

    public LocalJwDAO getLocalJwDAO() throws DAOException {
        LocalJwDAO localJwDAO = new JwDAOsqlLiteImpl(mContext, mSkavaContext);
        return localJwDAO;
    }

    public RemoteJwDAO getRemoteJwDAO(Flavour daoFlavour) throws DAOException {
        RemoteJwDAO remoteJwDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteJwDAO = new JwDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteJwDAO;
    }

    public LocalSrfDAO getLocalSrfDAO() throws DAOException {
        LocalSrfDAO localSrfDAO = new SrfDAOsqlLiteImpl(mContext, mSkavaContext);
        return localSrfDAO;
    }

    public RemoteSrfDAO getRemoteSrfDAO(Flavour daoFlavour) throws DAOException {
        RemoteSrfDAO remoteSrfDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                remoteSrfDAO = new SrfDAODropboxImpl(mContext, mSkavaContext);
        }
        return remoteSrfDAO;
    }

    public LocalOrientationDiscontinuitiesDAO getLocalOrientationDiscontinuitiesDAO() throws DAOException {
        LocalOrientationDiscontinuitiesDAO orientationDAO = new OrientationDiscontinuitiesDAOsqlLiteImpl(mContext, mSkavaContext);
        return orientationDAO;
    }

    public RemoteOrientationDiscontinuitiesDAO getRemoteOrientationDiscontinuitiesDAO(Flavour daoFlavour) throws DAOException {
        RemoteOrientationDiscontinuitiesDAO orientationDAO = null;
        switch (daoFlavour) {
            case DROPBOX:
                orientationDAO = new OrientationDiscontinuitiesDAODropboxImpl(mContext, mSkavaContext);
        }
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
