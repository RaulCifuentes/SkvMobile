package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalEsrDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationFactorTable;
import com.metric.skava.data.dao.impl.sqllite.table.TunnelTable;
import com.metric.skava.rocksupport.model.ESR;
import com.metric.skava.rocksupport.model.ExcavationFactors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class TunnelDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Tunnel> implements LocalTunnelDAO {

    private LocalTunnelFaceDAO faceDAO;

    public TunnelDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        faceDAO = DAOFactory.getInstance(getContext()).getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
    }


    @Override
    protected List<Tunnel> assemblePersistentEntities(Cursor cursor) throws DAOException {

        List<Tunnel> list = new ArrayList<Tunnel>();

        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(TunnelTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(TunnelTable.NAME_COLUMN, cursor);
            String projectCode = CursorUtils.getString(TunnelTable.PROJECT_CODE_COLUMN, cursor);

            LocalExcavationProjectDAO projectDAO = DAOFactory.getInstance(getContext()).getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE);
            ExcavationProject project = projectDAO.getExcavationProjectByCode(projectCode);

            Tunnel newInstance = new Tunnel(code, name);
            newInstance.setProject(project);

            ExcavationFactors excavationFactors = getExcavationFactorsByTunnelCode(code);
            newInstance.setExcavationFactors(excavationFactors);
            list.add(newInstance);
        }
        return list;
    }

    ExcavationFactors getExcavationFactorsByTunnelCode(String tunnelCode) throws DAOException {
        List<ExcavationFactors> factorList = new ArrayList<ExcavationFactors>();
        Cursor excavationFactorsCursor = getRecordsFilteredByColumn(ExcavationFactorTable.FACTOR_DATABASE_TABLE, ExcavationFactorTable.TUNNEL_CODE_COLUMN, tunnelCode, null);
        while (excavationFactorsCursor.moveToNext()) {
            String esrCode = CursorUtils.getString(ExcavationFactorTable.ESR_CODE_COLUMN, excavationFactorsCursor);
            Double span = CursorUtils.getDouble(ExcavationFactorTable.SPAN_COLUMN, excavationFactorsCursor);
            LocalEsrDAO localEsrDAO = DAOFactory.getInstance(getContext()).getLocalEsrDAO(DAOFactory.Flavour.SQLLITE);
            ESR esr = localEsrDAO.getESR(esrCode);
            ExcavationFactors factor = new ExcavationFactors(esr);
            factor.setSpan(span);
            factorList.add(factor);
        }
        ExcavationFactors factors = null;
        if (!factorList.isEmpty()) {
            if (factorList.size() == 1) {
                factors = factorList.get(0);
            } else {
                throw new DAOException("Multiple Excavation Factors (ESR, Span) records for same tunnel code: " + tunnelCode);
            }
        }
        return factors;
    }


    @Override
    public List<Tunnel> getTunnelsByProject(ExcavationProject project) throws DAOException {
        String projectCode = null;
        if (project != null) {
            projectCode = project.getCode();
        }
        Cursor cursor = getRecordsFilteredByColumn(TunnelTable.TUNNEL_DATABASE_TABLE, TunnelTable.PROJECT_CODE_COLUMN, projectCode, null);
        List<Tunnel> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }

    @Override
    public List<Tunnel> getTunnelsByProject(ExcavationProject project, User user) throws DAOException {
        if (user == null) {
            return getTunnelsByProject(project);
        } else {
            List<Tunnel> projectList = new ArrayList<Tunnel>();
            List<Tunnel> allTunnelsOfUser = getTunnelsByUser(user);
            for (Tunnel currTunnel : allTunnelsOfUser) {
                if (currTunnel.getProject().equals(project)) {
                    projectList.add(currTunnel);
                }
            }
            return projectList;
        }
    }

    @Override
    public List<Tunnel> getTunnelsByUser(User user) throws DAOException {
        List<Tunnel> tunnelList = new ArrayList<Tunnel>();
        //find the faces of this user
        List<TunnelFace> faces = faceDAO.getTunnelFacesByUser(user);
        //find the correspondant tunnels
        for (TunnelFace currentFace : faces) {
            Tunnel tunnel = currentFace.getTunnel();
            tunnelList.add(tunnel);
        }
        return tunnelList;
    }

    @Override
    public Tunnel getTunnelByCode(String code) throws DAOException {
        Tunnel tunnel = getIdentifiableEntityByCode(TunnelTable.TUNNEL_DATABASE_TABLE, code);
        return tunnel;
    }

    @Override
    public List<Tunnel> getAllTunnels() throws DAOException {
        List<Tunnel> list = getAllPersistentEntities(TunnelTable.TUNNEL_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveTunnel(Tunnel newTunnel) throws DAOException {
        savePersistentEntity(TunnelTable.TUNNEL_DATABASE_TABLE, newTunnel);
    }

    @Override
    protected void savePersistentEntity(String tableName, Tunnel newSkavaEntity) throws DAOException {
        String[] colNames = {TunnelTable.PROJECT_CODE_COLUMN, TunnelTable.CODE_COLUMN, TunnelTable.NAME_COLUMN};
        String[] colValues = {newSkavaEntity.getProject().getCode(), newSkavaEntity.getCode(), newSkavaEntity.getName() };
        saveRecord(tableName, colNames, colValues);
    }

    @Override
    public boolean deleteTunnel(String code) {
        return false;
    }

    @Override
    public int deleteAllTunnels() {
        return deleteAllPersistentEntities(TunnelTable.TUNNEL_DATABASE_TABLE);
    }


}
