package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.data.dao.RemoteJnDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class JnDAODropboxImpl extends DropBoxBaseDAO implements RemoteJnDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public JnDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getJnParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "Q_JN");
        String codigo = foundRecord.getString("ParameterId");
        return codigo;
    }

    @Override
    public List<Jn> getAllJns() throws DAOException {
        String jnParameterId = getJnParameterId();
        List<Jn> listJns = new ArrayList<Jn>();
        String[] names = new String[]{"FkParameterId"};
        String[] values = new String[]{jnParameterId};
        List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String code = currentDbxRecord.getString("IndexId");
            String key = currentDbxRecord.getString("IndexCode");
            String shortDescription = currentDbxRecord.getString("IndexShortName");
            String description = currentDbxRecord.getString("IndexName");
            Double value = currentDbxRecord.getDouble("IndexScore");

            Jn newJn = new Jn(code, key, shortDescription, description, value);
            newJn.setShortDescription(shortDescription);
            listJns.add(newJn);
        }
        return listJns;
    }


}
