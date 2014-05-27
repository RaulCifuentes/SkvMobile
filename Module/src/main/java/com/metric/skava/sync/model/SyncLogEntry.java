package com.metric.skava.sync.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by metricboy on 4/3/14.
 */
public class SyncLogEntry implements Serializable {

    public enum Domain {
        GLOBAL_DATA,
        USER_RELATED_DATA,
        PICTURES,
        ROLES,
        EXCAVATIONMETHODS,
        EXCAVATIONSECTIONS,
        DISCONTINUITYTYPES,
        DISCONTINUITYRELEVANCES,
        INDEXES,
        GROUPS,
        SPACINGS,
        PERSISTENCES,
        APERTURES,
        DISCONTINUITYSHAPES,
        ROUGHNESSES,
        INFILLINGS,
        WEATHERINGS,
        DISCONTINUITYWATERS,
        STRENGTHS,
        GROUDWATERS,
        ORIENTATION,
        JN,
        JR,
        JA,
        JW,
        SRF,
        FRACTURETYPES,
        BOLTTYPES,
        SHOTCRETETYPES,
        MESHTYPES,
        COVERAGES,
        ARCHTYPES,
        ESRS,
        SUPPORTPATTERNTYPES,
        CLIENTS,
        EXCAVATIONPROJECTS,
        TUNNELS,
        SUPPORTREQUIREMENTS,
        TUNNELFACES, USERS, ROCKQUALITIES
    }

    public enum Status {SUCCESS, FAIL}

    public enum Source {DROPBOX, DEFAULT}

    private Date syncDate;
    private Status status;
    private Source source;
    private Domain domain;
    private Long numRecordsSynced;
    private String message;

    public SyncLogEntry(Date syncDate, Domain domain, Source source, Status status, Long numRecordsSynced) {
        this.syncDate = syncDate;
        this.source = source;
        this.status = status;
        this.domain = domain;
        this.numRecordsSynced = numRecordsSynced;
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
