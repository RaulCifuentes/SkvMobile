package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxTable;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteMetadataDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ClientDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ExcavationProjectDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RoleDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRequirementDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelFaceDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.UserDropboxTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by metricboy on 3/6/14.
 */
public class MetadataDAODropboxImpl extends DropBoxBaseDAO implements RemoteMetadataDAO {




    public MetadataDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
    }

    @Override
    public Long getAppDataRecordsCount() throws DAOException {
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
            totalRecords -= getUserDataRecordsCount();
            return totalRecords;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }


    public Long getUserDataRecordsCount() throws DAOException {
        try {
            Long totalRecords = 0L;
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }

            List<DbxTable> allTables = new ArrayList<DbxTable>();
            allTables.add(getDatastore().getTable(RoleDropboxTable.ROLES_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(ClientDropboxTable.CLIENTS_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(ExcavationProjectDropboxTable.PROJECTS_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(TunnelDropboxTable.TUNNELS_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(SupportRequirementDropboxTable.SUPPORT_REQUIREMENTS_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(TunnelFaceDropboxTable.FACES_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(UserDropboxTable.USERS_DROPBOX_TABLE));

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
