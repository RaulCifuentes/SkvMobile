package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.model.Index;
import com.metric.skava.data.dao.RemoteIndexDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class IndexDAODropboxImpl extends DropBoxBaseDAO implements RemoteIndexDAO {

    private RmrParametersDropboxTable mIndexesTable;


    public IndexDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mIndexesTable = new RmrParametersDropboxTable(getDatastore());

    }


    @Override
    public List<Index> getAllIndexes() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            List<Index> listIndexes = new ArrayList<Index>();
            List<DbxRecord> tunnelList = mIndexesTable.findAll();
            for (DbxRecord currentIndexRecord : tunnelList) {
                String codigo = currentIndexRecord.getString("ParameterId");
                String key = currentIndexRecord.getString("ParameterFriendlyName");
                String nombre = currentIndexRecord.getString("ParameterName");
                Index newIndex = new Index(codigo, key, nombre);
                listIndexes.add(newIndex);
            }
            Collections.sort(listIndexes, new Comparator<Index>() {
                @Override
                public int compare(Index lhs, Index rhs) {
                    return lhs.getKey().compareTo(rhs.getKey());
                }
            });
            return listIndexes;

        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }




}
