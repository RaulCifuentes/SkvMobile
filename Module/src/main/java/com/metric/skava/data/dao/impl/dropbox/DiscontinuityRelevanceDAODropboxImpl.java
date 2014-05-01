package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class DiscontinuityRelevanceDAODropboxImpl extends DropBoxBaseDAO implements RemoteDiscontinuityRelevanceDAO {

    private ParametersDropboxTable mParametersTable;

    public DiscontinuityRelevanceDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());

    }


    @Override
    public List<DiscontinuityRelevance> getAllDiscontinuityRelevances() throws DAOException {
        List<DiscontinuityRelevance> listDiscontinuityRelevances = new ArrayList<DiscontinuityRelevance>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"DSystem_Relevance"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            Long ordinal = currentDbxRecord.getLong("ParameterOrder");
            DiscontinuityRelevance newType = new DiscontinuityRelevance(codigo, nombre);
            listDiscontinuityRelevances.add(newType);
        }
        return listDiscontinuityRelevances;
    }



}
