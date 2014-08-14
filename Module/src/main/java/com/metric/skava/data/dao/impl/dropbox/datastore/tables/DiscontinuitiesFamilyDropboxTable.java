package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 3/6/14.
 */
public class DiscontinuitiesFamilyDropboxTable extends DropboxBaseTable implements DropboxTable {

    public static final String DISCONTINUITIES_TABLE = "DiscontinuitiesFamily";

    public DiscontinuitiesFamilyDropboxTable(DbxDatastore datastore) {
        super(datastore);
    }

    @Override
    public DbxTable getBaseDropboxTable() {
        return mDatastore.getTable(DISCONTINUITIES_TABLE);
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
