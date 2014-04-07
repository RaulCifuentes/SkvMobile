package com.metric.skava.data.dao;

import android.content.Context;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.ClientDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationMethodDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationProjectDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.ExcavationSectionDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.RoleDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.TunnelDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.TunnelFaceDAODropboxImpl;
import com.metric.skava.data.dao.impl.dropbox.UserDAODropboxImpl;
import com.metric.skava.data.dao.impl.sqllite.ArchTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.AssessmentDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.BoltTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ClientDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.CoverageDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityRelevanceDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.DiscontinuityTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.EsrDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ExcavationMethodDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ExcavationProjectDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ExcavationSectionDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.FractureTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.JrDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.MeshTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.PermissionDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.RoleDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.ShotcreteTypeDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.SpacingDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.SupportRequirementDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.TunnelDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.TunnelFaceDAOsqlLiteImpl;
import com.metric.skava.data.dao.impl.sqllite.UserDAOsqlLiteImpl;
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

    public LocalBoltTypeDAO getBoltTypeDAO() {
        LocalBoltTypeDAO localBoltTypeDAO = new BoltTypeDAOsqlLiteImpl(mContext);
        return localBoltTypeDAO;
    }

    public LocalShotcreteTypeDAO getShotcreteTypeDAO() {
        LocalShotcreteTypeDAO shotcreteTypeDAO = new ShotcreteTypeDAOsqlLiteImpl(mContext);
        return shotcreteTypeDAO;
    }

    public LocalMeshTypeDAO getMeshTypeDAO() {
        LocalMeshTypeDAO localMeshTypeDAO = new MeshTypeDAOsqlLiteImpl(mContext);
        return localMeshTypeDAO;
    }

    public LocalCoverageDAO getCoverageDAO() {
        LocalCoverageDAO localCoverageDAO = new CoverageDAOsqlLiteImpl(mContext);
        return localCoverageDAO;
    }

    public LocalArchTypeDAO getArchTypeDAO() {
        LocalArchTypeDAO localArchTypeDAO = new ArchTypeDAOsqlLiteImpl(mContext);
        return localArchTypeDAO;
    }

    public SupportRequirementDAO getSupportRequirementDAO() {
        SupportRequirementDAO supportRequirementDAO = new SupportRequirementDAOsqlLiteImpl(mContext);
        return supportRequirementDAO;
    }

    public LocalFractureTypeDAO getFractureTypeDAO() {
        LocalFractureTypeDAO localFractureTypeDAO = new FractureTypeDAOsqlLiteImpl(mContext);
        return localFractureTypeDAO;
    }


    public LocalAssessmentDAO getAssessmentDAO(Flavour daoFlavour) throws DAOException {
        LocalAssessmentDAO localAssessmentDAO = new AssessmentDAOsqlLiteImpl(mContext);
        return localAssessmentDAO;
    }

    public LocalJrDAO getJrDAO() throws DAOException {
        LocalJrDAO localJrDAO = new JrDAOsqlLiteImpl(mContext);
        return localJrDAO;
    }

    public LocalDiscontinuityTypeDAO getDiscontinuityTypeDAO() {
        LocalDiscontinuityTypeDAO localDiscontinuityTypeDAO = new DiscontinuityTypeDAOsqlLiteImpl(mContext);
        return localDiscontinuityTypeDAO;
    }

    public LocalDiscontinuityRelevanceDAO getDiscontinuityRelevanceDAO() {
        LocalDiscontinuityRelevanceDAO localDiscontinuityRelevanceDAO = new DiscontinuityRelevanceDAOsqlLiteImpl(mContext);
        return localDiscontinuityRelevanceDAO;
    }

    public LocalSpacingDAO getSpacingDAO() throws DAOException {
        LocalSpacingDAO spacingDAO = new SpacingDAOsqlLiteImpl(mContext);
        return spacingDAO;
    }

    public SyncLoggingDAO getSyncLoggingDAO() throws DAOException {
        return new SyncLoggingDAOsqlLiteImpl(mContext);
    }
}
