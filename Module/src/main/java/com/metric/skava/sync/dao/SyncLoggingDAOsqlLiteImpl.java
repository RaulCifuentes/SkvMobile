package com.metric.skava.sync.dao;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.util.DateDataFormat;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.SqlLiteBasePersistentEntityDAO;
import com.metric.skava.data.dao.impl.sqllite.table.SyncLoggingTable;
import com.metric.skava.sync.model.SyncLogEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class SyncLoggingDAOsqlLiteImpl extends SqlLiteBasePersistentEntityDAO<SyncLogEntry> implements SyncLoggingDAO {


    public SyncLoggingDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
    }

    @Override
    protected List<SyncLogEntry> assamblePersistentEntities(Cursor cursor) throws DAOException {
        List<SyncLogEntry> list = new ArrayList<SyncLogEntry>();
        while (cursor.moveToNext()) {
            Long dateAsLong = CursorUtils.getLong(SyncLoggingTable.DATE_COLUMN, cursor);
            String sourceAsString = CursorUtils.getString(SyncLoggingTable.SOURCE_COLUMN, cursor);
            String statusAsString = CursorUtils.getString(SyncLoggingTable.STATUS_COLUMN, cursor);

            SyncLogEntry.Status status = SyncLogEntry.Status.valueOf(statusAsString);
            SyncLogEntry.Source source = SyncLogEntry.Source.valueOf(sourceAsString);

            Date date = DateDataFormat.getDateFromFormattedLong(dateAsLong);
            SyncLogEntry newInstance = new SyncLogEntry(date, source , status);
            newInstance.setSyncDate(date);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public SyncLogEntry getLastSyncByState(SyncLogEntry.Status state) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(SyncLoggingTable.SYNC_LOGGING_TABLE, SyncLoggingTable.STATUS_COLUMN, state.name(), SyncLoggingTable.DATE_COLUMN);
        List<SyncLogEntry> list = assamblePersistentEntities(cursor);
        if (list.isEmpty()){
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
        String[] columnNames = new String[]{SyncLoggingTable.DATE_COLUMN, SyncLoggingTable.SOURCE_COLUMN, SyncLoggingTable.STATUS_COLUMN};
        Object[] values = new Object[]{DateDataFormat.formatDateAsLong(newSkavaEntity.getSyncDate()), newSkavaEntity.getSource().name(), newSkavaEntity.getStatus().name()};
        saveRecord(tableName, columnNames, values);
    }

    @Override
    public int deleteAllSyncLogs() {
        return deleteAllPersistentEntities(SyncLoggingTable.SYNC_LOGGING_TABLE);
    }
}
