package com.metric.skava.sync.model;

import java.util.Date;

/**
 * Created by metricboy on 4/3/14.
 */
public class SyncLogEntry {

    public enum Status{SUCCESS, FAIL}
    public enum Source{DROPBOX, DEFAULT}

    private Date syncDate;
    private Status status;
    private Source source;

    public SyncLogEntry(Date syncDate, Source source, Status status) {
        this.syncDate = syncDate;
        this.source = source;
        this.status = status;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
