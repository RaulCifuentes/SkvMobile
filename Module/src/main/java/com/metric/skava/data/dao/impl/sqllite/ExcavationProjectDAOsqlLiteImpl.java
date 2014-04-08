package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.R;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Client;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.LocalClientDAO;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationProjectTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class ExcavationProjectDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<ExcavationProject> implements LocalExcavationProjectDAO {

    private LocalClientDAO localClientDAO;

    public ExcavationProjectDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        localClientDAO = DAOFactory.getInstance(context).getLocalClientDAO(DAOFactory.Flavour.SQLLITE);
    }

    @Override
    protected List<ExcavationProject> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<ExcavationProject> list = new ArrayList<ExcavationProject>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(ExcavationProjectTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(ExcavationProjectTable.NAME_COLUMN, cursor);
            String clientCode = CursorUtils.getString(ExcavationProjectTable.CLIENT_CODE_COLUMN, cursor);
            Client client = localClientDAO.getClientByCode(clientCode);
            //Client reference
            ExcavationProject newInstance = new ExcavationProject(code, name);
            newInstance.setClient(client);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public ExcavationProject getExcavationProjectByCode(String code) throws DAOException {
        ExcavationProject entity = getIdentifiableEntityByCode(ExcavationProjectTable.PROJECT_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<ExcavationProject> getAllExcavationProjects() throws DAOException {
        List<ExcavationProject> list = getAllPersistentEntities(ExcavationProjectTable.PROJECT_DATABASE_TABLE);
        return list;
    }

    @Override
    public List<ExcavationProject> getExcavationProjectsByUser(User user) throws DAOException {
        if (user == null) {
            throw new DAOException(mContext.getString(R.string.null_parameter, "getExcavationProjectsByUser", "user"));
        }
        LocalTunnelDAO localTunnelDAO = DAOFactory.getInstance(getContext()).getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE);
        List<ExcavationProject> projectsList = new ArrayList<ExcavationProject>();
        //find the faces of this user
        List<Tunnel> tunnels = localTunnelDAO.getTunnelsByUser(user);
        //find the correspondant tunnels
        for (Tunnel currentTunnel : tunnels) {
            ExcavationProject project = currentTunnel.getProject();
            projectsList.add(project);
        }
        return projectsList;
    }

    @Override
    public void saveExcavationProject(ExcavationProject newEntity) throws DAOException {
        savePersistentEntity(ExcavationProjectTable.PROJECT_DATABASE_TABLE, newEntity);
    }

    @Override
    protected void savePersistentEntity(String tableName, ExcavationProject newSkavaEntity) throws DAOException {
        String[] columnNames = new String[]{ExcavationProjectTable.CLIENT_CODE_COLUMN, ExcavationProjectTable.CODE_COLUMN, ExcavationProjectTable.NAME_COLUMN};
        String[] values = new String[]{newSkavaEntity.getClient().getCode(), newSkavaEntity.getCode(), newSkavaEntity.getName()};
        saveRecord(tableName, columnNames, values);
    }

    @Override
    public boolean deleteExcavationProject(String code) {
        return false;
    }

    @Override
    public int deleteAllExcavationProjects() {
        return deleteAllPersistentEntities(ExcavationProjectTable.PROJECT_DATABASE_TABLE);
    }
}
