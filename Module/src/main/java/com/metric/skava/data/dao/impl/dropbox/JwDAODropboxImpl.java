package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.data.dao.RemoteJwDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class JwDAODropboxImpl extends DropBoxBaseDAO implements RemoteJwDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public JwDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getJwParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "Q_JW");
        String codigo = readString(foundRecord, "ParameterId");
        return codigo;
    }

    @Override
    public List<Jw> getAllJws() throws DAOException {
        String jwParameterId = getJwParameterId();
        List<Jw> listJws = new ArrayList<Jw>();
        if (jwParameterId != null) {
            String[] names = new String[]{"FkParameterId"};
            String[] values = new String[]{jwParameterId};
            List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
            for (DbxRecord currentDbxRecord : recordList) {
                String code = currentDbxRecord.getString("IndexId");
                String key = currentDbxRecord.getString("IndexCode");
                String shortDescription = currentDbxRecord.getString("IndexShortName");
                String description = currentDbxRecord.getString("IndexName");
                Double value = currentDbxRecord.getDouble("IndexScore");

                Jw newJw = new Jw(code, key, shortDescription, description, value);
                newJw.setShortDescription(shortDescription);
                listJws.add(newJw);
            }
        }
        return listJws;
    }


}
