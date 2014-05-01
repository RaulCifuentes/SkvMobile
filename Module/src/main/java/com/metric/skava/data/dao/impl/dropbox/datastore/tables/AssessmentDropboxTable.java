package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;
import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 3/5/14.
 */
public class AssessmentDropboxTable extends DropboxBaseTable implements DropboxTable {


    public AssessmentDropboxTable(DbxDatastore datastore) {
        super(datastore);
    }

    @Override
    public DbxTable getBaseDropboxTable() {
        return mDatastore.getTable("Assessment");
    }

    @Override
    public boolean shouldSortByOrdinalColumn() {
        return false;
    }

    @Override
    public boolean shouldSortByKeyColumn() {
        return false;
    }


    public DbxRecord findRecordByInternalCode(String internalCode) throws DAOException {
        DbxRecord assessmentRecord = null;
        try {
            DbxFields criteria = new DbxFields();
            criteria.set("internalCode", internalCode);
            DbxTable.QueryResult results = getBaseDropboxTable().query(criteria);
            if (results.hasResults()) {
                if (results.count() == 1) {
                    assessmentRecord = results.iterator().next();
                } else {
                    throw new DAOException("Multiple Assessment instances for same internal code: " + internalCode);
                }
            }
        } catch (DbxException e) {
            throw new DAOException(e);
        }
        return assessmentRecord;
    }




}
