package com.metric.skava.app.context;

import com.dropbox.sync.android.DbxDatastore;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncStatus;

/**
 * Created by metricboy on 2/23/14.
 */
public class SkavaContext {

    private User loggedUser;
    private Assessment assessment;
    private SyncStatus syncMetadata;
    private DatastoreHelper mDatastoreHelper;
    private DbxDatastore mDatastore;
    private DAOFactory daoFactory;
    private SyncHelper syncHelper;

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

    public SyncStatus getSyncMetadata() {
        return syncMetadata;
    }

    public void setSyncMetadata(SyncStatus syncMetadata) {
        this.syncMetadata = syncMetadata;
    }

    public DatastoreHelper getDatastoreHelper() {
        return mDatastoreHelper;
    }

    public void setDatastoreHelper(DatastoreHelper mDatastoreHelper) {
        this.mDatastoreHelper = mDatastoreHelper;
    }

    public void setDatastore(DbxDatastore myDatastore) {
        this.mDatastore = myDatastore;
    }

    public DbxDatastore getDatastore() {
        return mDatastore;
    }
}
