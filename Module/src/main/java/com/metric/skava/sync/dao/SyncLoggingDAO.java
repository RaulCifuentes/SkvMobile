package com.metric.skava.sync.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.sync.model.AssessmentSyncTrace;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncQueue;
import com.metric.skava.sync.model.SyncTask;

/**
 * Created by metricboy on 4/3/14.
 */
public interface SyncLoggingDAO {


    public SyncLogEntry getLastSyncByState(SyncTask.Domain domain, SyncTask.Status state) throws DAOException;

    public void saveSyncLogEntry(SyncLogEntry newEntity) throws DAOException;

    public int deleteAllSyncLogs();

    public int deleteAllAssessmentSyncTraces();

    public SyncQueue getSyncQueue();

//    public void saveSyncQueue(SyncQueue newEntity);

    public AssessmentSyncTrace getAssessmentSyncTrace(String assessmentCode);

    public void saveAssessmentSyncTrace(AssessmentSyncTrace syncQueue) throws DAOException;

    public void updateAssessmentSyncTrace(AssessmentSyncTrace assessmentSyncTrace) throws DAOException;

    public boolean existsOnSyncTraces(String acknowledgedAssessmentCode) throws DAOException;
}
