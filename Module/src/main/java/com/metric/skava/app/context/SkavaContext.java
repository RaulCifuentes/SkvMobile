package com.metric.skava.app.context;

import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;
import com.metric.skava.sync.model.SyncLogEntry;

/**
 * Created by metricboy on 2/23/14.
 */
public class SkavaContext {

    private User loggedUser;
    private Assessment assessment;
    private SyncLogEntry syncMetadata;
    private DatastoreHelper mDatastoreHelper;

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

    public SyncLogEntry getSyncMetadata() {
        return syncMetadata;
    }

    public void setSyncMetadata(SyncLogEntry syncMetadata) {
        this.syncMetadata = syncMetadata;
    }

    public DatastoreHelper getDatastoreHelper() {
        return mDatastoreHelper;
    }

    public void setDatastoreHelper(DatastoreHelper mDatastoreHelper) {
        this.mDatastoreHelper = mDatastoreHelper;
    }
}
