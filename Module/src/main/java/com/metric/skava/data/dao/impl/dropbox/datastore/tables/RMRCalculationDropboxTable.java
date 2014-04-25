package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 3/6/14.
 */
public class RMRCalculationDropboxTable {

    private DbxDatastore mDatastore;
    private DbxTable mRMRCalculationTable;

    public RMRCalculationDropboxTable(DbxDatastore datastore) {
        mDatastore = datastore;
        mRMRCalculationTable = datastore.getTable("RMRCalculation");
    }


    public DbxRecord  persist(DbxFields fields) {
        return mRMRCalculationTable.insert(fields);
    }
}
