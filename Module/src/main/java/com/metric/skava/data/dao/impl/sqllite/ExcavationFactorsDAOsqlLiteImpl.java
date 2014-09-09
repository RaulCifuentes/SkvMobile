package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.LocalEsrDAO;
import com.metric.skava.data.dao.LocalExcavationFactorDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationFactorTable;
import com.metric.skava.rocksupport.model.ESR;
import com.metric.skava.rocksupport.model.ExcavationFactors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class ExcavationFactorsDAOsqlLiteImpl extends SqlLiteBasePersistentEntityDAO<ExcavationFactors> implements LocalExcavationFactorDAO {


    private LocalTunnelDAO localTunnelDAO;
    private LocalEsrDAO localEsrDAO;


    public ExcavationFactorsDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        localTunnelDAO = getDAOFactory().getLocalTunnelDAO();
        localEsrDAO = getDAOFactory().getLocalEsrDAO();
    }


    @Override
    protected List<ExcavationFactors> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<ExcavationFactors> list = new ArrayList<ExcavationFactors>();
        while (cursor.moveToNext()) {
            String tunnelCode = CursorUtils.getString(ExcavationFactorTable.TUNNEL_CODE_COLUMN, cursor);
            String esrCode = CursorUtils.getString(ExcavationFactorTable.ESR_CODE_COLUMN, cursor);
            Double span = CursorUtils.getDouble(ExcavationFactorTable.SPAN_COLUMN, cursor);

            //Currently the relation is mantained by the Tunnel side of the relations
            Tunnel tunnel = localTunnelDAO.getTunnelByUniqueCode(tunnelCode);

            ESR esr = localEsrDAO.getESRByUniqueCode(esrCode);

            ExcavationFactors newInstance = new ExcavationFactors(esr, span);
            list.add(newInstance);
        }
        return list;
    }

    @Override
    protected void savePersistentEntity(String tableName, ExcavationFactors newExcavationFactors) throws DAOException {
        savePersistentEntity(null, tableName, newExcavationFactors);
    }


    protected void savePersistentEntity(String tunnelCode, String tableName, ExcavationFactors newExcavationFactors) throws DAOException {
        String[] columns = new String[]{ExcavationFactorTable.TUNNEL_CODE_COLUMN, ExcavationFactorTable.ESR_CODE_COLUMN, ExcavationFactorTable.SPAN_COLUMN};
        //Currently the relation is mantained by the Tunnel side of the relations so ...
        Object[] values = new Object[]{tunnelCode, newExcavationFactors.getEsr().getCode(), newExcavationFactors.getSpan()};
        saveRecord(tableName, columns, values);
    }

    @Override
    public List<ExcavationFactors> getAllExcavationFactors() throws DAOException {
        List<ExcavationFactors> list = getAllPersistentEntities(ExcavationFactorTable.FACTOR_DATABASE_TABLE);
        return list;
    }

    @Override
    public ExcavationFactors getExcavationFactorsByTunnel(Tunnel tunnel) throws DAOException {
        List<ExcavationFactors> list;
        Cursor cursor = getRecordsFilteredByColumn(ExcavationFactorTable.FACTOR_DATABASE_TABLE, ExcavationFactorTable.TUNNEL_CODE_COLUMN, tunnel.getCode(), null);
        list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Excavation Factors not found for Tunnel  : " + tunnel );
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same tunnel. [Tunnel  : " + tunnel + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    public void saveExcavationFactors(String tunnelCode, ExcavationFactors newExcavationFactors) throws DAOException {
        savePersistentEntity(tunnelCode, ExcavationFactorTable.FACTOR_DATABASE_TABLE, newExcavationFactors);
    }

    @Override
    public boolean deleteExcavationFactorsByTunnel(Tunnel tunnel) {
        int numDeleted = deletePersistentEntitiesFilteredByColumn(ExcavationFactorTable.FACTOR_DATABASE_TABLE, ExcavationFactorTable.TUNNEL_CODE_COLUMN, tunnel.getCode());
        return numDeleted != -1;
    }

    @Override
    public int deleteAllExcavationFactors() {
        return deleteAllPersistentEntities(ExcavationFactorTable.FACTOR_DATABASE_TABLE);
    }
}
