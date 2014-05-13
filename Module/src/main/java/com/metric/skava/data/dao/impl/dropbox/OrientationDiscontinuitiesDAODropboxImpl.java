package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.model.Group;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.data.dao.RemoteOrientationDiscontinuitiesDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class OrientationDiscontinuitiesDAODropboxImpl extends DropBoxBaseDAO implements RemoteOrientationDiscontinuitiesDAO {

    private RmrParametersDropboxTable mParametersTable;
    private RmrIndexesDropboxTable mIndexesTable;

    public OrientationDiscontinuitiesDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mParametersTable = new RmrParametersDropboxTable(getDatastore());
        this.mIndexesTable = new RmrIndexesDropboxTable(getDatastore());
    }


    private String getOrientationParameterId() throws DAOException {
        DbxRecord foundRecord = mParametersTable.findRecordByCandidateKey("ParameterName", "RMR_Orientation");
        String codigo = readString(foundRecord, "ParameterId");
        return codigo;
    }

    @Override
    public List<OrientationDiscontinuities> getAllOrientationsDiscontinuities() throws DAOException {
        String orientationParameterId = getOrientationParameterId();
        List<OrientationDiscontinuities> listOrientations = new ArrayList<OrientationDiscontinuities>();
        if (orientationParameterId != null) {
            String[] names = new String[]{"FkParameterId"};
            String[] values = new String[]{orientationParameterId};
            List<DbxRecord> recordList = mIndexesTable.findRecordsByCriteria(names, values);
            for (DbxRecord currentDbxRecord : recordList) {
                String code = currentDbxRecord.getString("IndexId");
                String key = currentDbxRecord.getString("IndexCode");
                String shortDescription = currentDbxRecord.getString("IndexShortName");
                String description = currentDbxRecord.getString("IndexName");
                Double value = currentDbxRecord.getDouble("IndexScore");
                String groupCode = currentDbxRecord.getString("FkCategoryId");

                Group group = getDAOFactory().getLocalGroupDAO().getGroupByCode(groupCode);
                String groupName = group.getKey();

                //tx the model.group into the Class.Group
                if (groupName.equalsIgnoreCase("a")) {
                    groupName = OrientationDiscontinuities.Group.TUNNELS_MINES.name();
                }
                if (groupName.equalsIgnoreCase("b")) {
                    groupName = OrientationDiscontinuities.Group.FOUNDATIONS.name();
                }
                if (groupName.equalsIgnoreCase("c")) {
                    groupName = OrientationDiscontinuities.Group.SLOPES.name();
                }
                OrientationDiscontinuities.Group orientationGroup = OrientationDiscontinuities.Group.valueOf(groupName);
                OrientationDiscontinuities newOrientation = new OrientationDiscontinuities(orientationGroup, code, key, shortDescription, description, value);
                newOrientation.setShortDescription(shortDescription);

                listOrientations.add(newOrientation);
            }
        }
        return listOrientations;
    }


}
