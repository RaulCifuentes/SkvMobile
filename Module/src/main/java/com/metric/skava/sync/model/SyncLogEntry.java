package com.metric.skava.sync.model;

import java.util.Date;

/**
 * Created by metricboy on 4/3/14.
 */
public class SyncLogEntry {

    public enum Domain{GLOBAL, USER_SPECIFIC, NON_USER_SPECIFIC}
    public enum Status{SUCCESS, FAIL}
    public enum Source{DROPBOX, DEFAULT}

    private Date syncDate;
    private Status status;
    private Source source;
    private Domain domain;

    public SyncLogEntry(Date syncDate, Domain domain, Source source, Status status) {
        this.syncDate = syncDate;
        this.source = source;
        this.status = status;
        this.domain = domain;
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

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}
