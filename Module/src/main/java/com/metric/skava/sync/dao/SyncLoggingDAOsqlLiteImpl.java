package com.metric.skava.sync.dao;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.util.DateDataFormat;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.SqlLiteBasePersistentEntityDAO;
import com.metric.skava.data.dao.impl.sqllite.table.SyncLoggingTable;
import com.metric.skava.sync.model.SyncLogEntry;
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
            SyncLogEntry newInstance = new SyncLogEntry(date, domain, source , status, numRecords);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public SyncLogEntry getLastSyncByState(SyncTask.Domain domain, SyncTask.Status state) throws DAOException {
        String[] columns = new String[]{SyncLoggingTable.DOMAIN_COLUMN, SyncLoggingTable.STATUS_COLUMN};
        String[] values =  new String[]{domain.name(), state.name()};
        Cursor cursor = getRecordsFilteredByColumns(SyncLoggingTable.SYNC_LOGGING_TABLE, columns, values, SyncLoggingTable.DATE_COLUMN);
        List<SyncLogEntry> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void saveSyncLogEntry(SyncLogEntry newEntity) throws DAOException {
        savePersistentEntity(SyncLoggingTable.SYNC_LOGGING_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, SyncLogEntry newSkavaEntity) throws DAOException {
        String[] columnNames = new String[]{SyncLoggingTable.DATE_COLUMN, SyncLoggingTable.DOMAIN_COLUMN, SyncLoggingTable.SOURCE_COLUMN, SyncLoggingTable.STATUS_COLUMN, SyncLoggingTable.NUMRECORDS_COLUMN};
        Object[] values = new Object[]{DateDataFormat.formatDateAsLong(newSkavaEntity.getSyncDate()), newSkavaEntity.getDomain().name(),  newSkavaEntity.getSource().name(), newSkavaEntity.getStatus().name(), newSkavaEntity.getNumRecordsSynced()};
        saveRecord(tableName, columnNames, values);
    }

    @Override
    public int deleteAllSyncLogs() {
        return deleteAllPersistentEntities(SyncLoggingTable.SYNC_LOGGING_TABLE);
    }
}
