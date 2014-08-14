package com.metric.skava.uploader.sync.dao;

import android.content.Context;

import com.metric.skava.uploader.app.util.SkavaUploaderDateDataFormat;
import com.metric.skava.uploader.data.dao.SkavaUploaderSqlLiteBaseDAO;
import com.metric.skava.uploader.data.dao.exception.SkavaUploaderDAOException;
import com.metric.skava.uploader.data.dao.impl.sqllite.table.SkavaUploaderSyncTraceFilesTable;
import com.metric.skava.uploader.sync.model.SkavaUploaderFileToSync;
import com.metric.skava.uploader.sync.model.SkavaUploaderSyncTrace;

import java.util.List;

/**
 * Created by metricboy on 8/11/14.
 */
public class SkavaUploaderSyncLoggingDAOsqlLiteImpl extends SkavaUploaderSqlLiteBaseDAO implements SkavaUploaderSyncLoggingDAO {


    public SkavaUploaderSyncLoggingDAOsqlLiteImpl(Context context) throws SkavaUploaderDAOException {
        super(context);
    }



//    private List<SkavaUploaderFileToSync> assembleSyncTraceFilePersistentEntities(Cursor cursorFiles) {
//        List<SkavaUploaderFileToSync> list = new ArrayList<SkavaUploaderFileToSync>();
//        while (cursorFiles.moveToNext()) {
//            String environment = SkavaUploaderCursorUtils.getString(SkavaUploaderSyncTraceFilesTable.ENVIRONMENT_COLUMN, cursorFiles);
//            String assessmentCode = SkavaUploaderCursorUtils.getString(SkavaUploaderSyncTraceFilesTable.ASSESSMENT_COLUMN, cursorFiles);
//            Long dateAsLong = SkavaUploaderCursorUtils.getLong(SkavaUploaderSyncTraceFilesTable.DATE_COLUMN, cursorFiles);
//            String operationAsString = SkavaUploaderCursorUtils.getString(SkavaUploaderSyncTraceFilesTable.OPERATION_COLUMN, cursorFiles);
//            String statusAsString = SkavaUploaderCursorUtils.getString(SkavaUploaderSyncTraceFilesTable.STATUS_COLUMN, cursorFiles);
//            String fileName = SkavaUploaderCursorUtils.getString(SkavaUploaderSyncTraceFilesTable.FILE_NAME_COLUMN, cursorFiles);
//
//            Date date = SkavaUploaderDateDataFormat.getDateFromFormattedLong(dateAsLong);
//            SkavaUploaderFileToSync.Operation operation = SkavaUploaderFileToSync.Operation.valueOf(operationAsString);
//            SkavaUploaderDataToSync.Status status = SkavaUploaderFileToSync.Status.valueOf(statusAsString);
//
//            SkavaUploaderFileToSync newInstance = new SkavaUploaderFileToSync(environment, assessmentCode);
//            newInstance.setDate(date);
//            newInstance.setOperation(operation);
//            newInstance.setFileName(fileName);
//            newInstance.setStatus(status);
//            list.add(newInstance);
//        }
//        return list;
//
//    }
//
//
//    public SkavaUploaderSyncQueue getSyncQueue() {
//        SkavaUploaderSyncQueue theQueue = new SkavaUploaderSyncQueue();
//        Cursor cursorFiles = getRecordsFilteredByColumn(SkavaUploaderSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE, SkavaUploaderSyncTraceFilesTable.STATUS_COLUMN, SkavaUploaderDataToSync.Status.QUEUED.name(), SkavaUploaderSyncTraceFilesTable.DATE_COLUMN);
//        List<SkavaUploaderFileToSync> listFiles = assembleSyncTraceFilePersistentEntities(cursorFiles);
//        for (SkavaUploaderFileToSync fileToSync : listFiles) {
//            theQueue.addFile(fileToSync);
//        }
//        return theQueue;
//    }
//
//
//
//    @Override
//    public int deleteAllAssessmentSyncTraces() {
//        int numDeleted = deleteAllPersistentEntities(SkavaUploaderSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE);
//        return numDeleted;
//    }
//
//
//    @Override
//    public SkavaUploaderSyncTrace getAssessmentSyncTrace(String environment, String assessmentCode, SkavaUploaderDataToSync.Operation operation) {
//        String[] names = new String[] {SkavaUploaderSyncTraceFilesTable.ENVIRONMENT_COLUMN, SkavaUploaderSyncTraceFilesTable.ASSESSMENT_COLUMN, SkavaUploaderSyncTraceFilesTable.OPERATION_COLUMN};
//        String[] values = new String[] {environment, assessmentCode, operation.name()};
//
//        Cursor cursorFiles = getRecordsFilteredByColumns(SkavaUploaderSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE, names, values, SkavaUploaderSyncTraceFilesTable.DATE_COLUMN);
//        List<SkavaUploaderFileToSync> listFiles = assembleSyncTraceFilePersistentEntities(cursorFiles);
//
//        SkavaUploaderSyncTrace assessmentSyncTrace = new SkavaUploaderSyncTrace(environment, assessmentCode);
//        assessmentSyncTrace.setFiles(listFiles);
//        return assessmentSyncTrace;
//    }
//
//
//    public void updateAssessmentSyncTrace(SkavaUploaderSyncTrace syncTrace) throws SkavaUploaderDAOException {
//        saveAssessmentSyncTrace(syncTrace);
//    }
//
//    public boolean existsOnSyncTraces(String assessmentCode) throws SkavaUploaderDAOException {
//        Cursor cursorFiles = getRecordsFilteredByColumn(SkavaUploaderSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE, SkavaUploaderSyncTraceFilesTable.ASSESSMENT_COLUMN, assessmentCode, SkavaUploaderSyncTraceFilesTable.DATE_COLUMN);
//        int files = cursorFiles.getCount();
//        return (files > 0);
//    }
//

    public void saveUploaderSyncTrace(SkavaUploaderSyncTrace syncTrace) throws SkavaUploaderDAOException {
        List<SkavaUploaderFileToSync> filesForThisAssessment = syncTrace.getFiles();
        for (SkavaUploaderFileToSync fileToSync : filesForThisAssessment) {
            String[] columnNames = new String[]{
                    SkavaUploaderSyncTraceFilesTable.ENVIRONMENT_COLUMN,
                    SkavaUploaderSyncTraceFilesTable.ASSESSMENT_COLUMN,
                    SkavaUploaderSyncTraceFilesTable.OPERATION_COLUMN,
                    SkavaUploaderSyncTraceFilesTable.FILE_NAME_COLUMN,
                    SkavaUploaderSyncTraceFilesTable.DATE_COLUMN,
                    SkavaUploaderSyncTraceFilesTable.STATUS_COLUMN};
            Object[] values = new Object[]{
                    syncTrace.getEnvironment(),
                    syncTrace.getAssessmentCode(),
                    fileToSync.getOperation(),
                    fileToSync.getFileName(),
                    SkavaUploaderDateDataFormat.formatDateAsLong(fileToSync.getDate()),
                    fileToSync.getStatus().name()};
            saveRecord(SkavaUploaderSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE, columnNames, values);
        }

    }

}