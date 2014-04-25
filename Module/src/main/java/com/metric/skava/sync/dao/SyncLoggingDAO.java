package com.metric.skava.sync.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.sync.model.SyncLogEntry;

/**
 * Created by metricboy on 4/3/14.
 */
public interface SyncLoggingDAO {


    public SyncLogEntry getLastSyncByState(SyncLogEntry.Domain domain, SyncLogEntry.Status state) throws DAOException;

    public void saveSyncLogEntry(SyncLogEntry newEntity) throws DAOException;

    public int deleteAllSyncLogs();

}
