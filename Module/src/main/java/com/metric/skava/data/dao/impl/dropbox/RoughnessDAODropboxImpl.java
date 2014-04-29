package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.data.dao.RemoteRoughnessDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class RoughnessDAODropboxImpl extends DropBoxBaseDAO implements RemoteRoughnessDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public RoughnessDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getSpaceParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "RMR_Roughness");
        String codigo = foundRecord.getString("ParameterId");
        return codigo;
    }

    @Override
    public List<Roughness> getAllRoughnesses() throws DAOException {
        String spacingparameterId = getSpaceParameterId();
        List<Roughness> listRoughnesss = new ArrayList<Roughness>();
        String[] names = new String[]{"ParameterId"};
        String[] values = new String[]{spacingparameterId};
        List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String code = currentDbxRecord.getString("IndexId");
            String key = currentDbxRecord.getString("IndexCode");
            String shortDescription = currentDbxRecord.getString("IndexShortName");
            String description = currentDbxRecord.getString("IndexName");
            Double value = currentDbxRecord.getDouble("IndexScore");
            Roughness newType = new Roughness(code, key, shortDescription, description, value);
            listRoughnesss.add(newType);
        }
        return listRoughnesss;
    }



}
