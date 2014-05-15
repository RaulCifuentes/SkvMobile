package com.metric.skava.sync.exception;

import com.metric.skava.sync.model.SyncLogEntry;

/**
 * Created by metricboy on 5/14/14.
 */
public class SyncDataFailedException extends Exception {

    private SyncLogEntry entry;

    public SyncDataFailedException(SyncLogEntry entry, String message) {
        super(message);
        this.entry = entry;
    }

    public SyncLogEntry getEntry() {
        return entry;
    }

    public void setEntry(SyncLogEntry entry) {
        this.entry = entry;
    }
}
