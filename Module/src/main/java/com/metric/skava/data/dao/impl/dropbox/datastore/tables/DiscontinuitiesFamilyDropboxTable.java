package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 3/6/14.
 */
public class DiscontinuitiesFamilyDropboxTable {

    private DbxDatastore mDatastore;
    private DbxTable mDiscontinuitiesFamilyTable;

    public DiscontinuitiesFamilyDropboxTable(DbxDatastore datastore) {
        mDatastore = datastore;
        mDiscontinuitiesFamilyTable = datastore.getTable("DiscontinuitiesFamily");
    }

    public DbxRecord persist(DbxFields discontinuityFamilyFields) {
        return mDiscontinuitiesFamilyTable.insert(discontinuityFamilyFields);
    }
}
