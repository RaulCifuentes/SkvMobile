package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteShotcreteTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.instructions.model.ShotcreteType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class ShotcreteTypeDAODropboxImpl extends DropBoxBaseDAO implements RemoteShotcreteTypeDAO {

    private ParametersDropboxTable mParametersTable;

    public ShotcreteTypeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }

    @Override
    public List<ShotcreteType> getAllShotcreteTypes() throws DAOException {
        List<ShotcreteType> listShotcreteTypes = new ArrayList<ShotcreteType>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"Support_ShotcreteType"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            ShotcreteType newType = new ShotcreteType(codigo, nombre);
            listShotcreteTypes.add(newType);
        }
        return listShotcreteTypes;
    }


}
