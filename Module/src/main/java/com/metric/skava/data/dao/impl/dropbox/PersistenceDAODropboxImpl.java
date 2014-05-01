package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.data.dao.RemotePersistenceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class PersistenceDAODropboxImpl extends DropBoxBaseDAO implements RemotePersistenceDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public PersistenceDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getPersistenceParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "RMR_Persistance");
        String codigo = foundRecord.getString("ParameterId");
        return codigo;
    }

    @Override
    public List<Persistence> getAllPersistences() throws DAOException {
        String spacingparameterId = getPersistenceParameterId();
        List<Persistence> listPersistences = new ArrayList<Persistence>();
        String[] names = new String[]{"FkParameterId"};
        String[] values = new String[]{spacingparameterId};
        List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String code = currentDbxRecord.getString("IndexId");
            String key = currentDbxRecord.getString("IndexCode");
            String shortDescription = currentDbxRecord.getString("IndexShortName");
            String description = currentDbxRecord.getString("IndexName");
            Double value = currentDbxRecord.getDouble("IndexScore");
            Persistence newType = new Persistence(code, key, shortDescription, description, value);
            listPersistences.add(newType);
        }
        return listPersistences;
    }



}
