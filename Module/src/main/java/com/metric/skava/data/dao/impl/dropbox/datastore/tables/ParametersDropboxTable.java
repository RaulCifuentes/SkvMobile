package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 3/24/14.
 */
public class ParametersDropboxTable extends DropboxBaseTable implements DropboxTable {

    public static final String PARAMETERS_DROPBOX_TABLE = "Parameters";

    public ParametersDropboxTable(DbxDatastore datastore) {
        super(datastore);
    }

    @Override
    public DbxTable getBaseDropboxTable() {
        return mDatastore.getTable(PARAMETERS_DROPBOX_TABLE);
    }

    @Override
    public boolean shouldSortByOrdinalColumn() {
        return true;
    }

    @Override
    public boolean shouldSortByKeyColumn() {
        return false;
    }


}
