package com.metric.skava.sync.dao;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.util.DateDataFormat;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.SqlLiteBasePersistentEntityDAO;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentSyncTraceFilesTable;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentSyncTraceRecordsTable;
import com.metric.skava.data.dao.impl.sqllite.table.SyncLoggingTable;
import com.metric.skava.sync.model.AssessmentSyncTrace;
import com.metric.skava.sync.model.DataToSync;
import com.metric.skava.sync.model.FileToSync;
import com.metric.skava.sync.model.RecordToSync;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncQueue;
import com.metric.skava.sync.model.SyncTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class SyncLoggingDAOsqlLiteImpl extends SqlLiteBasePersistentEntityDAO<SyncLogEntry> implements SyncLoggingDAO {


    public SyncLoggingDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
    }

    @Override
    protected List<SyncLogEntry> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<SyncLogEntry> list = new ArrayList<SyncLogEntry>();
        while (cursor.moveToNext()) {
            Long dateAsLong = CursorUtils.getLong(SyncLoggingTable.DATE_COLUMN, cursor);
            String domainAsString = CursorUtils.getString(SyncLoggingTable.DOMAIN_COLUMN, cursor);
            String sourceAsString = CursorUtils.getString(SyncLoggingTable.SOURCE_COLUMN, cursor);
            String statusAsString = CursorUtils.getString(SyncLoggingTable.STATUS_COLUMN, cursor);
            Long numRecords = CursorUtils.getLong(SyncLoggingTable.NUMRECORDS_COLUMN, cursor);

            SyncTask.Domain domain = SyncTask.Domain.valueOf(domainAsString);
            SyncTask.Status status = SyncTask.Status.valueOf(statusAsString);
            SyncTask.Source source = SyncTask.Source.valueOf(sourceAsString);

            Date date = DateDataFormat.getDateFromFormattedLong(dateAsLong);
            SyncLogEntry newInstance = new SyncLogEntry(date, domain, source, status, numRecords);
            list.add(newInstance);
        }
        return list;
    }

    private List<RecordToSync> assembleSyncTraceRecordPersistentEntities(Cursor cursorRecords) {
        List<RecordToSync> list = new ArrayList<RecordToSync>();
        while (cursorRecords.moveToNext()) {
            String environment = CursorUtils.getString(AssessmentSyncTraceRecordsTable.ENVIRONMENT_COLUMN, cursorRecords);
            String assessmentCode = CursorUtils.getString(AssessmentSyncTraceRecordsTable.ASSESSMENT_COLUMN, cursorRecords);
            Long dateAsLong = CursorUtils.getLong(AssessmentSyncTraceRecordsTable.DATE_COLUMN, cursorRecords);
            String operationAsString = CursorUtils.getString(AssessmentSyncTraceRecordsTable.OPERATION_COLUMN, cursorRecords);
            String statusAsString = CursorUtils.getString(AssessmentSyncTraceRecordsTable.STATUS_COLUMN, cursorRecords);
            String recordID = CursorUtils.getString(AssessmentSyncTraceRecordsTable.RECORD_ID_COLUMN, cursorRecords);
            String skavaEntityCode = CursorUtils.getString(AssessmentSyncTraceRecordsTable.ENTITY_CODE_COLUMN, cursorRecords);

            Date date = DateDataFormat.getDateFromFormattedLong(dateAsLong);
            DataToSync.Operation operation = DataToSync.Operation.valueOf(operationAsString);
            DataToSync.Status status = DataToSync.Status.valueOf(statusAsString);

            RecordToSync newInstance = new RecordToSync(environment, assessmentCode);
            newInstance.setDate(date);
            newInstance.setOperation(operation);
            newInstance.setRecordID(recordID);
            newInstance.setSkavaEntityCode(skavaEntityCode);
            newInstance.setStatus(status);
            list.add(newInstance);
        }
        return list;
    }

    private List<FileToSync> assembleSyncTraceFilePersistentEntities(Cursor cursorFiles) {
        List<FileToSync> list = new ArrayList<FileToSync>();
        while (cursorFiles.moveToNext()) {
            String environment = CursorUtils.getString(AssessmentSyncTraceRecordsTable.ENVIRONMENT_COLUMN, cursorFiles);
            String assessmentCode = CursorUtils.getString(AssessmentSyncTraceFilesTable.ASSESSMENT_COLUMN, cursorFiles);
            Long dateAsLong = CursorUtils.getLong(AssessmentSyncTraceFilesTable.DATE_COLUMN, cursorFiles);
            String operationAsString = CursorUtils.getString(AssessmentSyncTraceFilesTable.OPERATION_COLUMN, cursorFiles);
            String statusAsString = CursorUtils.getString(AssessmentSyncTraceFilesTable.STATUS_COLUMN, cursorFiles);
            String fileName = CursorUtils.getString(AssessmentSyncTraceFilesTable.FILE_NAME_COLUMN, cursorFiles);

            Date date = DateDataFormat.getDateFromFormattedLong(dateAsLong);
            DataToSync.Operation operation = DataToSync.Operation.valueOf(operationAsString);
            DataToSync.Status status = DataToSync.Status.valueOf(statusAsString);

            FileToSync newInstance = new FileToSync(environment, assessmentCode);
            newInstance.setDate(date);
            newInstance.setOperation(operation);
            newInstance.setFileName(fileName);
            newInstance.setStatus(status);
            list.add(newInstance);
        }
        return list;

    }

    @Override
    public SyncQueue getSyncQueue() {
        SyncQueue theQueue = new SyncQueue();
        Cursor cursorFiles = getRecordsFilteredByColumn(AssessmentSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE, AssessmentSyncTraceFilesTable.STATUS_COLUMN, DataToSync.Status.QUEUED.name(), AssessmentSyncTraceFilesTable.DATE_COLUMN);
        List<FileToSync> listFiles = assembleSyncTraceFilePersistentEntities(cursorFiles);
        for (FileToSync fileToSync : listFiles) {
            theQueue.addFile(fileToSync);
        }
        Cursor cursorRecords = getRecordsFilteredByColumn(AssessmentSyncTraceRecordsTable.SYNC_TRACE_RECORDS_TABLE, AssessmentSyncTraceRecordsTable.STATUS_COLUMN, DataToSync.Status.QUEUED.name(), AssessmentSyncTraceRecordsTable.DATE_COLUMN);
        List<RecordToSync> listRecords = assembleSyncTraceRecordPersistentEntities(cursorRecords);
        for (RecordToSync recordToSync : listRecords) {
            theQueue.addRecord(recordToSync);
        }
        return theQueue;
    }


//    @Override
//    public SyncLogEntry getLastSyncByState(SyncTask.Domain domain, SyncTask.Status state) throws DAOException {
//        String[] columns = new String[]{SyncLoggingTable.DOMAIN_COLUMN, SyncLoggingTable.STATUS_COLUMN};
//        String[] values = new String[]{domain.name(), state.name()};
//        Cursor cursor = getRecordsFilteredByColumns(SyncLoggingTable.SYNC_LOGGING_TABLE, columns, values, SyncLoggingTable.DATE_COLUMN);
//        List<SyncLogEntry> list = assemblePersistentEntities(cursor);
//        if (list.isEmpty()) {
//            return null;
//        }
//        return list.get(0);
//    }

    @Override
    public void saveSyncLogEntry(SyncLogEntry newEntity) throws DAOException {
        savePersistentEntity(SyncLoggingTable.SYNC_LOGGING_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, SyncLogEntry newSkavaEntity) throws DAOException {
        String[] columnNames = new String[]{SyncLoggingTable.DATE_COLUMN, SyncLoggingTable.DOMAIN_COLUMN, SyncLoggingTable.SOURCE_COLUMN, SyncLoggingTable.STATUS_COLUMN, SyncLoggingTable.NUMRECORDS_COLUMN};
        Object[] values = new Object[]{DateDataFormat.formatDateAsLong(newSkavaEntity.getSyncDate()), newSkavaEntity.getDomain().name(), newSkavaEntity.getSource().name(), newSkavaEntity.getStatus().name(), newSkavaEntity.getNumRecordsSynced()};
        saveRecord(tableName, columnNames, values);
    }

//    @Override
//    public int deleteAllSyncLogs() {
//        return deleteAllPersistentEntities(SyncLoggingTable.SYNC_LOGGING_TABLE);
//    }

    @Override
    public int deleteAllAssessmentSyncTraces() {
        int numDeleted = deleteAllPersistentEntities(AssessmentSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE);
        numDeleted+= deleteAllPersistentEntities(AssessmentSyncTraceRecordsTable.SYNC_TRACE_RECORDS_TABLE);
        return numDeleted;
    }


    @Override
    public AssessmentSyncTrace getAssessmentSyncTrace(String environment, String assessmentCode, DataToSync.Operation operation) {

        String[] names = new String[] {AssessmentSyncTraceFilesTable.ENVIRONMENT_COLUMN, AssessmentSyncTraceFilesTable.ASSESSMENT_COLUMN, AssessmentSyncTraceFilesTable.OPERATION_COLUMN};
        String[] values = new String[] {environment, assessmentCode, operation.name()};

        Cursor cursorFiles = getRecordsFilteredByColumns(AssessmentSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE, names, values, AssessmentSyncTraceFilesTable.DATE_COLUMN);
        List<FileToSync> listFiles = assembleSyncTraceFilePersistentEntities(cursorFiles);

        names = new String[] {AssessmentSyncTraceRecordsTable.ENVIRONMENT_COLUMN, AssessmentSyncTraceRecordsTable.ASSESSMENT_COLUMN};
        values = new String[] {environment, assessmentCode};

        Cursor cursorRecords = getRecordsFilteredByColumns(AssessmentSyncTraceRecordsTable.SYNC_TRACE_RECORDS_TABLE, names, values, AssessmentSyncTraceRecordsTable.DATE_COLUMN);
        List<RecordToSync> listRecords = assembleSyncTraceRecordPersistentEntities(cursorRecords);

        AssessmentSyncTrace assessmentSyncTrace = new AssessmentSyncTrace(environment, assessmentCode);
        assessmentSyncTrace.setFiles(listFiles);
        assessmentSyncTrace.setRecords(listRecords);
        return assessmentSyncTrace;
    }


    public void updateAssessmentSyncTrace(AssessmentSyncTrace syncTrace) throws DAOException {
        saveAssessmentSyncTrace(syncTrace);
    }

    public boolean existsOnSyncTraces(String assessmentCode) throws DAOException{
        Cursor cursorFiles = getRecordsFilteredByColumn(AssessmentSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE, AssessmentSyncTraceFilesTable.ASSESSMENT_COLUMN, assessmentCode, AssessmentSyncTraceFilesTable.DATE_COLUMN);
        int files = cursorFiles.getCount();

        Cursor cursorRecords = getRecordsFilteredByColumn(AssessmentSyncTraceRecordsTable.SYNC_TRACE_RECORDS_TABLE, AssessmentSyncTraceRecordsTable.ASSESSMENT_COLUMN, assessmentCode, AssessmentSyncTraceRecordsTable.DATE_COLUMN);
        int records = cursorRecords.getCount();

        return (files > 0 || records > 0);
    }


    public void saveAssessmentSyncTrace(AssessmentSyncTrace syncTrace) throws DAOException {
        List<FileToSync> filesForThisAssessment = syncTrace.getFiles();
        for (FileToSync fileToSync : filesForThisAssessment) {
            String[] columnNames = new String[]{
                    AssessmentSyncTraceFilesTable.ENVIRONMENT_COLUMN,
                    AssessmentSyncTraceFilesTable.ASSESSMENT_COLUMN,
                    AssessmentSyncTraceFilesTable.OPERATION_COLUMN,
                    AssessmentSyncTraceFilesTable.FILE_NAME_COLUMN,
                    AssessmentSyncTraceFilesTable.DATE_COLUMN,
                    AssessmentSyncTraceFilesTable.STATUS_COLUMN};
            Object[] values = new Object[]{
                    syncTrace.getEnvironment(),
                    syncTrace.getAssessmentCode(),
                    fileToSync.getOperation(),
                    fileToSync.getFileName(),
                    DateDataFormat.formatDateAsLong(fileToSync.getDate()),
                    fileToSync.getStatus().name()};
            saveRecord(AssessmentSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE, columnNames, values);
        }


        List<RecordToSync> recordsForThisAssessment = syncTrace.getRecords();
        for (RecordToSync recordToSync : recordsForThisAssessment) {
            String[] columnNames = new String[]{
                    AssessmentSyncTraceRecordsTable.ENVIRONMENT_COLUMN,
                    AssessmentSyncTraceRecordsTable.ASSESSMENT_COLUMN,
                    AssessmentSyncTraceRecordsTable.OPERATION_COLUMN,
                    AssessmentSyncTraceRecordsTable.ENTITY_CODE_COLUMN,
                    AssessmentSyncTraceRecordsTable.RECORD_ID_COLUMN,
                    AssessmentSyncTraceRecordsTable.DATE_COLUMN,
                    AssessmentSyncTraceRecordsTable.STATUS_COLUMN};
            Object[] values = new Object[]{
                    syncTrace.getEnvironment(),
                    syncTrace.getAssessmentCode(),
                    recordToSync.getOperation(),
                    recordToSync.getSkavaEntityCode(),
                    recordToSync.getRecordID(),
                    DateDataFormat.formatDateAsLong(recordToSync.getDate()),
                    recordToSync.getStatus().name()};
            saveRecord(AssessmentSyncTraceRecordsTable.SYNC_TRACE_RECORDS_TABLE, columnNames, values);
        }
    }

}
