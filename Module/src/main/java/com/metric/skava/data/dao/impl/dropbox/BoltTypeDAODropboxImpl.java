package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteBoltTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.instructions.model.BoltType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class BoltTypeDAODropboxImpl extends DropBoxBaseDAO implements RemoteBoltTypeDAO {

    private ParametersDropboxTable mParametersTable;

    public BoltTypeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }

    @Override
    public List<BoltType> getAllBoltTypes() throws DAOException {
        List<BoltType> listBoltTypes = new ArrayList<BoltType>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"Support_BoltType"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            BoltType newType = new BoltType(codigo, nombre);
            listBoltTypes.add(newType);
        }
        return listBoltTypes;
    }


}
