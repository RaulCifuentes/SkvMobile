package com.metric.skava.sync.model;

/**
 * Created by metricboy on 4/23/14.
 */
public class SyncStatus {

    private SyncLogEntry globalData;
    private SyncLogEntry userRelatedData;

    public SyncLogEntry getGlobalData() {
        return globalData;
    }

    public void setGlobalData(SyncLogEntry globalData) {
        this.globalData = globalData;
    }

    public SyncLogEntry getUserRelatedData() {
        return userRelatedData;
    }

    public void setUserRelatedData(SyncLogEntry userRelatedData) {
        this.userRelatedData = userRelatedData;
    }


}
