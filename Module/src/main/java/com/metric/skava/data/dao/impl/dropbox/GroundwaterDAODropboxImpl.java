package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.data.dao.RemoteGroundwaterDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class GroundwaterDAODropboxImpl extends DropBoxBaseDAO implements RemoteGroundwaterDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public GroundwaterDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getGroundwaterParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "RMR_Groundwater");
        String codigo = foundRecord.getString("ParameterId");
        return codigo;
    }

    @Override
    public List<Groundwater> getAllGroundwaters() throws DAOException {
        String groundwaterParameterId = getGroundwaterParameterId();
        List<Groundwater> listGroundwaters = new ArrayList<Groundwater>();
        String[] names = new String[]{"FkParameterId"};
        String[] values = new String[]{groundwaterParameterId};
        List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String code = currentDbxRecord.getString("IndexId");
            String key = currentDbxRecord.getString("IndexCode");
            String shortDescription = currentDbxRecord.getString("IndexShortName");
            String description = currentDbxRecord.getString("IndexName");
            Double value = currentDbxRecord.getDouble("IndexScore");
            String groupCode = currentDbxRecord.getString("FkCategoryId");
            Group group = getDAOFactory().getLocalGroupDAO().getGroupByCode(groupCode);
            String groupName = group.getName();
            Groundwater.Group groundwaterGroup = Groundwater.Group.valueOf(groupName);
            Groundwater newGroundwater = new Groundwater(groundwaterGroup, code, key, shortDescription, description, value);
            newGroundwater.setShortDescription(shortDescription);
            listGroundwaters.add(newGroundwater);
        }
        return listGroundwaters;
    }


}
