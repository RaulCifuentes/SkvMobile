package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.data.dao.RemoteStrengthDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class StrengthDAODropboxImpl extends DropBoxBaseDAO implements RemoteStrengthDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public StrengthDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getStrengthParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "RMR_Strength");
        String codigo = foundRecord.getString("ParameterId");
        return codigo;
    }

    @Override
    public List<StrengthOfRock> getAllStrengths() throws DAOException {
        String strengthParameterId = getStrengthParameterId();
        List<StrengthOfRock> listStrengths = new ArrayList<StrengthOfRock>();
        String[] names = new String[]{"FkParameterId"};
        String[] values = new String[]{strengthParameterId};
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
            StrengthOfRock.Group strengthGroup = StrengthOfRock.Group.valueOf(groupName);
            StrengthOfRock newStrength = new StrengthOfRock(strengthGroup, code, key, shortDescription, description, value);
            newStrength.setShortDescription(shortDescription);

            listStrengths.add(newStrength);
        }
        return listStrengths;
    }



}
