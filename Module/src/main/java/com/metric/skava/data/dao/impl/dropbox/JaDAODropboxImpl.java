package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.data.dao.RemoteJaDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class JaDAODropboxImpl extends DropBoxBaseDAO implements RemoteJaDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public JaDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getJaParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "Q_JA");
        String codigo = readString(foundRecord, "ParameterId");
        return codigo;
    }

    @Override
    public List<Ja> getAllJas() throws DAOException {
        String jaParameterId = getJaParameterId();
        List<Ja> listJas = new ArrayList<Ja>();
        if (jaParameterId != null) {
            String[] names = new String[]{"FkParameterId"};
            String[] values = new String[]{jaParameterId};
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

                Ja.Group jaGroup = Ja.Group.valueOf(groupName);
                Ja newJa = new Ja(jaGroup, code, key, shortDescription, description, value);
                newJa.setShortDescription(shortDescription);
                listJas.add(newJa);
            }
        }
        return listJas;
    }


}
