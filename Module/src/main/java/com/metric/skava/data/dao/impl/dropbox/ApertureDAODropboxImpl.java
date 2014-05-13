package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.data.dao.RemoteApertureDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class ApertureDAODropboxImpl extends DropBoxBaseDAO implements RemoteApertureDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public ApertureDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getApertureParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "RMR_Aperture");
        String codigo = readString(foundRecord, "ParameterId");
        return codigo;
    }

    @Override
    public List<Aperture> getAllApertures() throws DAOException {
        String spacingparameterId = getApertureParameterId();
        List<Aperture> listApertures = new ArrayList<Aperture>();
        if (spacingparameterId != null) {
            String[] names = new String[]{"FkParameterId"};
            String[] values = new String[]{spacingparameterId};
            List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
            for (DbxRecord currentDbxRecord : recordList) {
                String code = currentDbxRecord.getString("IndexId");
                String key = currentDbxRecord.getString("IndexCode");
                String shortDescription = currentDbxRecord.getString("IndexShortName");
                String description = currentDbxRecord.getString("IndexName");
                Double value = currentDbxRecord.getDouble("IndexScore");
                Aperture newType = new Aperture(code, key, shortDescription, description, value);
                listApertures.add(newType);
            }
        }
        return listApertures;
    }


}
