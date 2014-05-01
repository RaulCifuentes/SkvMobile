package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteFractureTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.rockmass.model.FractureType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class FractureTypeDAODropboxImpl extends DropBoxBaseDAO implements RemoteFractureTypeDAO {

    private ParametersDropboxTable mParametersTable;

    public FractureTypeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }

    @Override
    public List<FractureType> getAllFractureTypes() throws DAOException {
        List<FractureType> listFractureTypes = new ArrayList<FractureType>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"RockMass_type"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            FractureType newType = new FractureType(codigo, nombre);
            listFractureTypes.add(newType);
        }
        return listFractureTypes;
    }


}
