package com.metric.skava.sync.model;

import java.util.Date;

/**
 * Created by metricboy on 4/23/14.
 */
public class SyncStatus {

    private boolean success;
    private Date lastExecution;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

//    private SyncLogEntry globalData;
//
//    public SyncLogEntry getGlobalData() {
//        return globalData;
//    }
//
//    public void setGlobalData(SyncLogEntry globalData) {
//        this.globalData = globalData;
//    }




}
