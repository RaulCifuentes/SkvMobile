package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxTable;

/**
 * Created by metricboy on 3/24/14.
 */
public class RmrParametersDropboxTable extends DropboxBaseTable implements DropboxTable {

    //This is the counterintuitive name that Skava Web App is using for all index parameters
    //An index parameters is any including a code, name, a key and a score associated
    public static final String RMR_PARAMETERS_TABLE = "RmrParameters";

    public RmrParametersDropboxTable(DbxDatastore datastore) {
        super(datastore);
    }

    @Override
    public DbxTable getBaseDropboxTable() {
        return mDatastore.getTable(RMR_PARAMETERS_TABLE);
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
