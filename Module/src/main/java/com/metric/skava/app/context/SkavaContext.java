package com.metric.skava.app.context;

import com.dropbox.sync.android.DbxDatastore;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncQueue;
import com.metric.skava.sync.model.SyncStatus;

/**
 * Created by metricboy on 2/23/14.
 */
public class SkavaContext {

    private User loggedUser;
    private Assessment assessment;
    private SyncStatus userDataSyncMetadata;
    private SyncStatus appDataSyncMetadata;
    private DbxDatastore mDatastore;
//    private DbxFileSystem mFileSystem;
    private DAOFactory daoFactory;
    private SyncHelper syncHelper;
    private String targetEnvironment;
    private SyncQueue middlemanInbox;


    public String getTargetEnvironment() {
        return targetEnvironment;
    }

    public void setTargetEnvironment(String targetEnvironment) {
        this.targetEnvironment = targetEnvironment;
    }

    public SyncHelper getSyncHelper() {
        return syncHelper;
    }

    public void setSyncHelper(SyncHelper syncHelper) {
        this.syncHelper = syncHelper;
    }

    public DAOFactory getDAOFactory() {
        return daoFactory;
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public SyncStatus getUserDataSyncMetadata() {
        return userDataSyncMetadata;
    }

    public void setUserDataSyncMetadata(SyncStatus userDataSyncMetadata) {
        this.userDataSyncMetadata = userDataSyncMetadata;
    }

    public SyncStatus getAppDataSyncMetadata() {
        return appDataSyncMetadata;
    }

    public void setAppDataSyncMetadata(SyncStatus appDataSyncMetadata) {
        this.appDataSyncMetadata = appDataSyncMetadata;
    }

//    public DbxFileSystem getFileSystem() {
//        return mFileSystem;
//    }
//
//    public void setFileSystem(DbxFileSystem fileSystem) {
//        this.mFileSystem = fileSystem;
//    }

    public void setDatastore(DbxDatastore myDatastore) {
        this.mDatastore = myDatastore;
    }

    public DbxDatastore getDatastore() {
        return mDatastore;
    }

    public SyncQueue getMiddlemanInbox() {
        return middlemanInbox;
    }

    public void setMiddlemanInbox(SyncQueue middlemanInbox) {
        this.middlemanInbox = middlemanInbox;
    }

}
