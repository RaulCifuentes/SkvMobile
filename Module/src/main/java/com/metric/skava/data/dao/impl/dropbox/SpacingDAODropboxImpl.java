package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.data.dao.RemoteSpacingDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class SpacingDAODropboxImpl extends DropBoxBaseDAO implements RemoteSpacingDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public SpacingDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getSpaceParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "RMR_Spacing");
        String codigo = foundRecord.getString("ParameterId");
        return codigo;
    }

    @Override
    public List<Spacing> getAllSpacings() throws DAOException {
        String spacingparameterId = getSpaceParameterId();
        List<Spacing> listSpacings = new ArrayList<Spacing>();
        String[] names = new String[]{"ParameterId"};
        String[] values = new String[]{spacingparameterId};
        List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("IndexCode");
            String nombre = currentDbxRecord.getString("IndexName");
            Double value = currentDbxRecord.getDouble("IndexScore");
            Spacing newType = new Spacing(codigo, nombre, value);
            listSpacings.add(newType);
        }
        return listSpacings;
    }



}
