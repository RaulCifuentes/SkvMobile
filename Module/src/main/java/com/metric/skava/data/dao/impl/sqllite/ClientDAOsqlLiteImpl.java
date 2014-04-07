package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Client;
import com.metric.skava.data.dao.LocalClientDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ClientTable;
import com.metric.skava.data.dao.impl.sqllite.table.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class ClientDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Client> implements LocalClientDAO {


    public ClientDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
    }



    @Override
    protected List<Client> assamblePersistentEntities(Cursor cursor) throws DAOException {
        List<Client> list = new ArrayList<Client>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(ClientTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(ClientTable.NAME_COLUMN, cursor);
            Client newInstance = new Client(code, name);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public Client getClientByCode(String code) throws DAOException {
        Client entity = getIdentifiableEntityByCode(ClientTable.CLIENT_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<Client> getAllClients() throws DAOException {
        List<Client> list = getAllPersistentEntities(ClientTable.CLIENT_DATABASE_TABLE);
        return list;
    }


    @Override
    public void saveClient(Client newEntity) throws DAOException {
        savePersistentEntity(ClientTable.CLIENT_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, Client newSkavaEntity) throws DAOException {
        String[] colNames = {ClientTable.CODE_COLUMN, UserTable.NAME_COLUMN};
        String[] colValues = {newSkavaEntity.getCode(), newSkavaEntity.getName()};
        saveRecord(tableName, colNames, colValues);
    }

    @Override
    public boolean deleteClient(String code) {
        return false;
    }

    @Override
    public int deleteAllClients() {
        return deleteAllPersistentEntities(ClientTable.CLIENT_DATABASE_TABLE);
    }
}
