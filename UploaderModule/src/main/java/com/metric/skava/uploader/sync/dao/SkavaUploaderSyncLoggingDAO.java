package com.metric.skava.uploader.sync.dao;

import com.metric.skava.uploader.data.dao.exception.SkavaUploaderDAOException;
import com.metric.skava.uploader.sync.model.SkavaUploaderSyncTrace;

/**
 * Created by metricboy on 4/3/14.
 */
public interface SkavaUploaderSyncLoggingDAO {

//    public int deleteAllAssessmentSyncTraces();
//
//    public SkavaUploaderSyncTrace getAssessmentSyncTrace(String environment, String assessmentCode, SkavaUploaderDataToSync.Operation operation);

    public void saveUploaderSyncTrace(SkavaUploaderSyncTrace syncQueue) throws SkavaUploaderDAOException;

//    public void updateAssessmentSyncTrace(SkavaUploaderSyncTrace assessmentSyncTrace) throws SkavaUploaderDAOException;
//
//    public boolean existsOnSyncTraces(String acknowledgedAssessmentCode) throws SkavaUploaderDAOException;
}
