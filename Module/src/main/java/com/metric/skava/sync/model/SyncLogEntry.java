package com.metric.skava.sync.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by metricboy on 4/3/14.
 */
public class SyncLogEntry implements Serializable {

    private Date syncDate;
    private SyncTask.Status status;
    private SyncTask.Source source;
    private SyncTask.Domain domain;
    private Long numRecordsSynced;
    private String message;

    public SyncLogEntry(Date syncDate, SyncTask.Domain domain, SyncTask.Source source, SyncTask.Status status, Long numRecordsSynced) {
        this.syncDate = syncDate;
        this.source = source;
        this.status = status;
        this.domain = domain;
        this.numRecordsSynced = numRecordsSynced;
    }

    public SyncTask.Source getSource() {
        return source;
    }

    public void setSource(SyncTask.Source source) {
        this.source = source;
    }

    public SyncTask.Domain getDomain() {
        return domain;
    }

    public void setDomain(SyncTask.Domain domain) {
        this.domain = domain;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public SyncTask.Status getStatus() {
        return status;
    }

    public void setStatus(SyncTask.Status status) {
        this.status = status;
    }

    public Long getNumRecordsSynced() {
        return numRecordsSynced;
    }

    public void setNumRecordsSynced(Long numRecordsSynced) {
        this.numRecordsSynced = numRecordsSynced;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
