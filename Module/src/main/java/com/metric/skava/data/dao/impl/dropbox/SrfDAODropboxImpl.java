package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.data.dao.RemoteSrfDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class SrfDAODropboxImpl extends DropBoxBaseDAO implements RemoteSrfDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public SrfDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getSrfParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "Q_SRF");
        String codigo = readString(foundRecord, "ParameterId");
        return codigo;
    }

    @Override
    public List<SRF> getAllSrfs() throws DAOException {
        String srfParameterId = getSrfParameterId();
        List<SRF> listSrfs = new ArrayList<SRF>();
        if (srfParameterId != null) {
            String[] names = new String[]{"FkParameterId"};
            String[] values = new String[]{srfParameterId};
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

                SRF.Group jaGroup = SRF.Group.valueOf(groupName);
                SRF newSrf = new SRF(jaGroup, code, key, shortDescription, description, value);
                newSrf.setShortDescription(shortDescription);
                listSrfs.add(newSrf);
            }
        }
        return listSrfs;
    }


}
