package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.data.dao.RemoteInfillingDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class InfillingDAODropboxImpl extends DropBoxBaseDAO implements RemoteInfillingDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public InfillingDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getSpaceParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "RMR_Infilling");
        String codigo = readString(foundRecord, "ParameterId");
        return codigo;
    }

    @Override
    public List<Infilling> getAllInfillings() throws DAOException {
        String spacingparameterId = getSpaceParameterId();
        List<Infilling> listInfillings = new ArrayList<Infilling>();
        if (spacingparameterId != null) {
            String[] names = new String[]{"FkParameterId"};
            String[] values = new String[]{spacingparameterId};
            List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
            for (DbxRecord currentDbxRecord : recordList) {
                String code = currentDbxRecord.getString("IndexId");
                String key = currentDbxRecord.getString("IndexCode");
                String shortDescription = currentDbxRecord.getString("IndexShortName");
                String description = currentDbxRecord.getString("IndexName");
                Double value = currentDbxRecord.getDouble("IndexScore");
                Infilling newType = new Infilling(code, key, shortDescription, description, value);
                listInfillings.add(newType);
            }
        }
        return listInfillings;
    }


}
