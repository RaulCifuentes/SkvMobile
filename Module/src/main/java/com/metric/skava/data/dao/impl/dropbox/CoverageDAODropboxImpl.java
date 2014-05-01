package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteCoverageDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.instructions.model.Coverage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class CoverageDAODropboxImpl extends DropBoxBaseDAO implements RemoteCoverageDAO {

    private ParametersDropboxTable mParametersTable;

    public CoverageDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }

    @Override
    public List<Coverage> getAllCoverages() throws DAOException {
        List<Coverage> listCoverages = new ArrayList<Coverage>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"Support_Coverage"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            Coverage newType = new Coverage(codigo, nombre);
            listCoverages.add(newType);
        }
        return listCoverages;
    }


}
