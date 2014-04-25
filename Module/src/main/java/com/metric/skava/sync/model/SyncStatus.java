package com.metric.skava.sync.model;

/**
 * Created by metricboy on 4/23/14.
 */
public class SyncStatus {

    private SyncLogEntry global;
    private SyncLogEntry userSpecific;
    private SyncLogEntry nonUserSpecific;

    public SyncLogEntry getNonUserSpecific() {
        return nonUserSpecific;
    }

    public void setNonUserSpecific(SyncLogEntry nonUserSpecific) {
        this.nonUserSpecific = nonUserSpecific;
    }

    public SyncLogEntry getUserSpecific() {
        return userSpecific;
    }

    public void setUserSpecific(SyncLogEntry userSpecific) {
        this.userSpecific = userSpecific;
    }

    public SyncLogEntry getGlobal() {
        return global;
    }

    public void setGlobal(SyncLogEntry global) {
        this.global = global;
    }


}
