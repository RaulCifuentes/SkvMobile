package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxTable;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteMetadataDAO;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.Set;

/**
 * Created by metricboy on 3/6/14.
 */
public class MetadataDAODropboxImpl extends DropBoxBaseDAO implements RemoteMetadataDAO {


    public MetadataDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
    }

    @Override
    public Long getRecordsCount() throws DAOException {
        try {
            Long totalRecords = 0L;
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            Set<DbxTable> allTables = getDatastore().getTables();
            for (DbxTable aTable : allTables) {
                DbxTable.QueryResult justCountQuery = aTable.query();
                int numRecords = justCountQuery.count();
                totalRecords += numRecords;
            }
            return totalRecords;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }



}
