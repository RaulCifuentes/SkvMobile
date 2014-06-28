package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 3/24/14.
 */
public class RmrCategoriesDropboxTable extends DropboxBaseTable implements DropboxTable {


    public static final String RMR_CATEGORIES_TABLE = "RmrCategories";

    public RmrCategoriesDropboxTable(DbxDatastore datastore) {
        super(datastore);
    }

    @Override
    public DbxTable getBaseDropboxTable() {
        return mDatastore.getTable(RMR_CATEGORIES_TABLE);
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
