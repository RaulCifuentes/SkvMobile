package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteEsrDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;
import com.metric.skava.rocksupport.model.ESR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class EsrDAODropboxImpl extends DropBoxBaseDAO implements RemoteEsrDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public EsrDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getESRParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "Q_ESR");
        String codigo = readString(foundRecord, "ParameterId");
        return codigo;
    }

    @Override
    public List<ESR> getAllESRs() throws DAOException {
        String ESRParameterId = getESRParameterId();
        List<ESR> listESRs = new ArrayList<ESR>();
        if (ESRParameterId != null) {
            String[] names = new String[]{"FkParameterId"};
            String[] values = new String[]{ESRParameterId};
            List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
            for (DbxRecord currentDbxRecord : recordList) {
                String code = currentDbxRecord.getString("IndexId");
                String key = currentDbxRecord.getString("IndexCode");
                String shortDescription = currentDbxRecord.getString("IndexShortName");
                String description = currentDbxRecord.getString("IndexName");
                Double value = currentDbxRecord.getDouble("IndexScore");

//            ESR Currently has not groups
//            String groupCode = currentDbxRecord.getString("FkCategoryId");
//            Group group = getDAOFactory().getLocalGroupDAO().getGroupByCode(groupCode);
//            String groupName = group.getKey();
//            ESR.Group esrGroup = ESR.Group.valueOf(groupName);
                ESR.Group esrGroup = null;

                ESR newESR = new ESR(esrGroup, code, key, shortDescription, description, value);
                newESR.setShortDescription(shortDescription);
                listESRs.add(newESR);
            }
        }
        return listESRs;
    }


}
