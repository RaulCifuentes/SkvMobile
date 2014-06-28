package com.metric.skava.sync.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncTask;

/**
 * Created by metricboy on 4/3/14.
 */
public interface SyncLoggingDAO {


    public SyncLogEntry getLastSyncByState(SyncTask.Domain domain, SyncTask.Status state) throws DAOException;

    public void saveSyncLogEntry(SyncLogEntry newEntity) throws DAOException;

    public int deleteAllSyncLogs();

}
