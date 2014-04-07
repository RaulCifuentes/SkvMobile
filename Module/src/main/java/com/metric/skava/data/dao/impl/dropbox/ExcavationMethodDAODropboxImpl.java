package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.data.dao.RemoteExcavationMethodDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.MethodDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class ExcavationMethodDAODropboxImpl extends DropBoxBaseDAO implements RemoteExcavationMethodDAO {

    private MethodDropboxTable mMethodsTable;

    public ExcavationMethodDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mMethodsTable = new MethodDropboxTable(getDatastore());
    }

    @Override
    public List<ExcavationMethod> getAllExcavationMethods() throws DAOException {
        List<ExcavationMethod> listMethods = new ArrayList<ExcavationMethod>();
        List<DbxRecord> recordList = mMethodsTable.findAll();
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("code");
            String nombre = currentDbxRecord.getString("name");
            ExcavationMethod newMethod = new ExcavationMethod(codigo, nombre);
            listMethods.add(newMethod);
        }
        return listMethods;
    }


}
