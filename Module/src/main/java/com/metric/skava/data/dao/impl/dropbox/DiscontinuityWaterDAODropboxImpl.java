package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteDiscontinuityWaterDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class DiscontinuityWaterDAODropboxImpl extends DropBoxBaseDAO implements RemoteDiscontinuityWaterDAO {

    private ParametersDropboxTable mParametersTable;

    public DiscontinuityWaterDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());

    }


    @Override
    public List<DiscontinuityWater> getAllDiscontinuityWaters() throws DAOException {
        List<DiscontinuityWater> listDiscontinuityWaters = new ArrayList<DiscontinuityWater>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"DSystem_Water"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            DiscontinuityWater newWater = new DiscontinuityWater(codigo, nombre);
            listDiscontinuityWaters.add(newWater);
        }
        return listDiscontinuityWaters;
    }



}
