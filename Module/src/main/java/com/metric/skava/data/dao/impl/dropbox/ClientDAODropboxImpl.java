package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Client;
import com.metric.skava.data.dao.RemoteClientDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ClientDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/6/14.
 */
public class ClientDAODropboxImpl extends DropBoxBaseDAO implements RemoteClientDAO {

    private ClientDropboxTable mClientsTable;


    public ClientDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mClientsTable = new ClientDropboxTable(getDatastore());
    }



    @Override
    public List<Client> getAllClients() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            List<Client> listProjects = new ArrayList<Client>();
            List<DbxRecord> recordList = mClientsTable.findAll();
            for (DbxRecord currentDbxRecord : recordList) {
                String codigo = currentDbxRecord.getString("ClientId");
                String nombre = currentDbxRecord.getString("ClientName");
//                String urlLogo = currentDbxRecord.getString("ClientLogo");
                Client newClient = new Client(codigo, nombre);
//                if (urlLogo != null){
//                    Uri uri = Uri.parse(urlLogo);
//                    newClient.setClientLogo(uri);
//                }
                listProjects.add(newClient);
            }
            return listProjects;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }



}
