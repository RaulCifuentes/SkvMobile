package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.data.dao.RemoteExcavationMethodDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class ExcavationMethodDAODropboxImpl extends DropBoxBaseDAO implements RemoteExcavationMethodDAO {

    private ParametersDropboxTable mParametersTable;

    public ExcavationMethodDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }

    @Override
    public List<ExcavationMethod> getAllExcavationMethods() throws DAOException {
        List<ExcavationMethod> listMethods = new ArrayList<ExcavationMethod>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"GenInfo_Method"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            ExcavationMethod newMethod = new ExcavationMethod(codigo, nombre);
            listMethods.add(newMethod);
        }
        return listMethods;
    }


}
