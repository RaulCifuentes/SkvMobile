package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteSyncAcknowlegeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.DataSyncDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.FilesSyncDropboxTable;

import java.util.List;

/**
 * Created by metricboy on 6/20/14.
 */
public class SyncAcknowledgeDAODropboxImpl extends DropBoxBaseDAO implements RemoteSyncAcknowlegeDAO {

    private DataSyncDropboxTable mDataSyncAcknowledgesTable;
    private FilesSyncDropboxTable mFilesSyncAcknowledgesTable;

    public SyncAcknowledgeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mDataSyncAcknowledgesTable = new DataSyncDropboxTable(getDatastore());
        this.mFilesSyncAcknowledgesTable = new FilesSyncDropboxTable(getDatastore());
    }

    @Override
    public void deleteAcknowledges(String assessmentCode) throws DAOException {
        DbxRecord assessmentRecordToDelete = mDataSyncAcknowledgesTable.findRecordByCode(assessmentCode);
        assessmentRecordToDelete.deleteRecord();

        String[] names = new String[]{"assessmentCode"};
        Object[] values = new String[]{assessmentCode};
        List<DbxRecord> recordsToDelete = mFilesSyncAcknowledgesTable.findRecordsByCriteria(names, values);
        for (DbxRecord dbxRecord : recordsToDelete) {
            dbxRecord.deleteRecord();
        }

    }

    @Override
    public void deleteAllAcknowledges() throws DAOException {

        List<DbxRecord> dataRecordList = mDataSyncAcknowledgesTable.findAll();
        for (DbxRecord dbxRecord : dataRecordList) {
            dbxRecord.deleteRecord();
        }

        List<DbxRecord> filesRecordList = mFilesSyncAcknowledgesTable.findAll();
        for (DbxRecord dbxRecord : filesRecordList) {
            dbxRecord.deleteRecord();
        }
    }
}
