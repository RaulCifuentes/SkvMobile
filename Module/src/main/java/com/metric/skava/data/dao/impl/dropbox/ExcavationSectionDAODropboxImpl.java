package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.data.dao.RemoteExcavationSectionDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class ExcavationSectionDAODropboxImpl extends DropBoxBaseDAO implements RemoteExcavationSectionDAO {

    private ParametersDropboxTable mParametersTable;

    public ExcavationSectionDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }


    @Override
    public List<ExcavationSection> getAllExcavationSections() throws DAOException {
        List<ExcavationSection> listSections = new ArrayList<ExcavationSection>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"GenInfo_Section"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            ExcavationSection newSection = new ExcavationSection(codigo, nombre);
            listSections.add(newSection);
        }
        return listSections;
    }



}
