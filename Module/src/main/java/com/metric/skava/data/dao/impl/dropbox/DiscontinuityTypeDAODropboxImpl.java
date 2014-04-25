package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteDiscontinuityTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.discontinuities.model.DiscontinuityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class DiscontinuityTypeDAODropboxImpl extends DropBoxBaseDAO implements RemoteDiscontinuityTypeDAO {

    private ParametersDropboxTable mParametersTable;

    public DiscontinuityTypeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());

    }


    @Override
    public List<DiscontinuityType> getAllDiscontinuityTypes() throws DAOException {
        List<DiscontinuityType> listDiscontinuityTypes = new ArrayList<DiscontinuityType>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"DSystem_Type"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            DiscontinuityType newType = new DiscontinuityType(codigo, nombre);
            listDiscontinuityTypes.add(newType);
        }
        return listDiscontinuityTypes;
    }



}
