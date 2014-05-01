package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteArchTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.instructions.model.ArchType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class ArchTypeDAODropboxImpl extends DropBoxBaseDAO implements RemoteArchTypeDAO {

    private ParametersDropboxTable mParametersTable;

    public ArchTypeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }

    @Override
    public List<ArchType> getAllArchTypes() throws DAOException {
        List<ArchType> listArchTypes = new ArrayList<ArchType>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"Support_ArchType"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            ArchType newType = new ArchType(codigo, nombre);
            listArchTypes.add(newType);
        }
        return listArchTypes;
    }


}
