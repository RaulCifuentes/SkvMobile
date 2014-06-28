package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 3/24/14.
 */
public class RmrIndexesDropboxTable extends DropboxBaseTable implements DropboxTable {


    public static final String RMR_INDEXES_TABLE = "RmrIndexes";

    public RmrIndexesDropboxTable(DbxDatastore datastore) {
        super(datastore);
    }

    @Override
    public DbxTable getBaseDropboxTable() {
        return mDatastore.getTable(RMR_INDEXES_TABLE);
    }

    @Override
    public boolean shouldSortByOrdinalColumn() {
        return false;
    }

    @Override
    public boolean shouldSortByKeyColumn() {
        return true;
    }


}
