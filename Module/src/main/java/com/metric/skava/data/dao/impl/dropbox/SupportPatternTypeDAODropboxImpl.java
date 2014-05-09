package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteSupportPatternTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.instructions.model.SupportPatternType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class SupportPatternTypeDAODropboxImpl extends DropBoxBaseDAO implements RemoteSupportPatternTypeDAO {

    private ParametersDropboxTable mParametersTable;

    public SupportPatternTypeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }


    @Override
    public List<SupportPatternType> getAllSupportPatternTypes() throws DAOException {

        List<SupportPatternType> listPatterns = new ArrayList<SupportPatternType>();

        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"Support_PatternType"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            SupportPatternType newSection = new SupportPatternType(codigo, nombre);
            listPatterns.add(newSection);
        }

        names = new String[]{"ParameterName"} ;
        values = new String[]{"Support_RoofType"};
        recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            SupportPatternType newSection = new SupportPatternType(codigo, nombre);
            listPatterns.add(newSection);
        }

        names = new String[]{"ParameterName"} ;
        values = new String[]{"Support_WallType"};
        recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            SupportPatternType newSection = new SupportPatternType(codigo, nombre);
            listPatterns.add(newSection);
        }

        return listPatterns;
    }



}
