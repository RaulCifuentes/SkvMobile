package com.metric.skava.sync.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.sync.model.AssessmentSyncTrace;
import com.metric.skava.sync.model.DataToSync;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncQueue;

/**
 * Created by metricboy on 4/3/14.
 */
public interface SyncLoggingDAO {

    //    public int deleteAllSyncLogs();
    //    public SyncLogEntry getLastSyncByState(SyncTask.Domain domain, SyncTask.Status state) throws DAOException;
    //    public void saveSyncQueue(SyncQueue newEntity);

    public void saveSyncLogEntry(SyncLogEntry newEntity) throws DAOException;

    public int deleteAllAssessmentSyncTraces();

    public SyncQueue getSyncQueue();

    public AssessmentSyncTrace getAssessmentSyncTrace(String environment, String assessmentCode, DataToSync.Operation operation);

    public void saveAssessmentSyncTrace(AssessmentSyncTrace syncQueue) throws DAOException;

    public void updateAssessmentSyncTrace(AssessmentSyncTrace assessmentSyncTrace) throws DAOException;

    public boolean existsOnSyncTraces(String acknowledgedAssessmentCode) throws DAOException;
}
