package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 6/19/14.
 */
public class DataSyncDropboxTable extends DropboxBaseTable implements DropboxTable {

    public static final String DATA_SYNC_TABLE = "DataStoreSync";

    public DataSyncDropboxTable(DbxDatastore datastore) {
        super(datastore);
    }

    @Override
    public DbxTable getBaseDropboxTable() {
        return mDatastore.getTable(DATA_SYNC_TABLE);
    }

    @Override
    public boolean shouldSortByOrdinalColumn() {
        return false;
    }

    @Override
    public boolean shouldSortByKeyColumn() {
        return false;
    }
}
