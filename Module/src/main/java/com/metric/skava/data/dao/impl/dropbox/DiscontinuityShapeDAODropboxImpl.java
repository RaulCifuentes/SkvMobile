package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteDiscontinuityShapeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.discontinuities.model.DiscontinuityShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class DiscontinuityShapeDAODropboxImpl extends DropBoxBaseDAO implements RemoteDiscontinuityShapeDAO {

    private ParametersDropboxTable mParametersTable;

    public DiscontinuityShapeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());

    }


    @Override
    public List<DiscontinuityShape> getAllDiscontinuityShapes() throws DAOException {
        List<DiscontinuityShape> listDiscontinuityShapes = new ArrayList<DiscontinuityShape>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"DSystem_Shape"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            DiscontinuityShape newType = new DiscontinuityShape(codigo, nombre);
            listDiscontinuityShapes.add(newType);
        }
        return listDiscontinuityShapes;
    }



}
