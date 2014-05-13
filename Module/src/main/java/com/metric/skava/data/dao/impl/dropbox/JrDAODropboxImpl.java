package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.data.dao.RemoteJrDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class JrDAODropboxImpl extends DropBoxBaseDAO implements RemoteJrDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public JrDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getJrParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "Q_JR");
        String codigo = readString(foundRecord, "ParameterId");
        return codigo;
    }

    @Override
    public List<Jr> getAllJrs() throws DAOException {
        String jrParameterId = getJrParameterId();
        List<Jr> listJrs = new ArrayList<Jr>();
        if (jrParameterId != null) {
            String[] names = new String[]{"FkParameterId"};
            String[] values = new String[]{jrParameterId};
            List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
            for (DbxRecord currentDbxRecord : recordList) {
                String code = currentDbxRecord.getString("IndexId");
                String key = currentDbxRecord.getString("IndexCode");
                String shortDescription = currentDbxRecord.getString("IndexShortName");
                String description = currentDbxRecord.getString("IndexName");
                Double value = currentDbxRecord.getDouble("IndexScore");
                String groupCode = currentDbxRecord.getString("FkCategoryId");
                Group group = getDAOFactory().getLocalGroupDAO().getGroupByCode(groupCode);
                String groupName = group.getKey();

                Jr.Group jrGroup = Jr.Group.valueOf(groupName);
                Jr newJr = new Jr(jrGroup, code, key, shortDescription, description, value);
                newJr.setShortDescription(shortDescription);
                listJrs.add(newJr);
            }
        }
        return listJrs;
    }


}
