package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 3/6/14.
 */
public class QBartonCalculationDropboxTable {

    private DbxDatastore mDatastore;
    private DbxTable mQBartonCalculationTable;

    public QBartonCalculationDropboxTable(DbxDatastore datastore) {
        mDatastore = datastore;
        mQBartonCalculationTable = datastore.getTable("QBartonCalculation");
    }

    public DbxRecord persist(DbxFields fields) {
        return mQBartonCalculationTable.insert(fields);
    }
}
