package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.RemoteMeshTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.instructions.model.MeshType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class MeshTypeDAODropboxImpl extends DropBoxBaseDAO implements RemoteMeshTypeDAO {

    private ParametersDropboxTable mParametersTable;

    public MeshTypeDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new ParametersDropboxTable(getDatastore());
    }

    @Override
    public List<MeshType> getAllMeshTypes() throws DAOException {
        List<MeshType> listMeshTypes = new ArrayList<MeshType>();
        String[] names = new String[]{"ParameterName"} ;
        String[] values = new String[]{"Support_MeshType"};
        List<DbxRecord> recordList = mParametersTable.findRecordsByCriteria(names, values);
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("ParameterId");
            String nombre = currentDbxRecord.getString("ParameterValue");
            MeshType newType = new MeshType(codigo, nombre);
            listMeshTypes.add(newType);
        }
        return listMeshTypes;
    }


}
