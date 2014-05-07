package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.data.dao.RemoteRockQualityDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.Q_RockQualityDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RMR_RockQualityDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class RockQualityDAODropboxImpl extends DropBoxBaseDAO implements RemoteRockQualityDAO {

    private Q_RockQualityDropboxTable mQ_rockQualityTable;
    private RMR_RockQualityDropboxTable mRMR_rockQualityTable;

    public RockQualityDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mQ_rockQualityTable = new Q_RockQualityDropboxTable(getDatastore());
        this.mRMR_rockQualityTable = new RMR_RockQualityDropboxTable(getDatastore());
    }


    @Override
    public List<RockQuality> getAllRockQualities() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            List<RockQuality> listRockQualitys = new ArrayList<RockQuality>();
            List<DbxRecord> qRockQualities = mQ_rockQualityTable.findAll();
            for (DbxRecord currentRockQualityRecord : qRockQualities) {
                String codigo = currentRockQualityRecord.getString("RockQualityId");
                String name = currentRockQualityRecord.getString("NAME");
                Double lowerBound = currentRockQualityRecord.getDouble("Q_LOW_BOUND");
                Double upperBound = currentRockQualityRecord.getDouble("Q_HIGH_BOUND");
                RockQuality newRockQuality = new RockQuality(RockQuality.AccordingTo.Q, codigo, name, lowerBound, upperBound);
                listRockQualitys.add(newRockQuality);
            }
            List<DbxRecord> rmrRockQualities = mRMR_rockQualityTable.findAll();
            for (DbxRecord currentRockQualityRecord : rmrRockQualities) {
                String codigo = currentRockQualityRecord.getString("RockQualityId");
                String name = currentRockQualityRecord.getString("NAME");
                Double lowerBound = currentRockQualityRecord.getDouble("RMR_LOW_BOUND");
                Double upperBound = currentRockQualityRecord.getDouble("RMR_HIGH_BOUND");
                String classification = currentRockQualityRecord.getString("CLASS");
                RockQuality newRockQuality = new RockQuality(RockQuality.AccordingTo.RMR, codigo, name, lowerBound, upperBound);
                newRockQuality.setClassification(classification);
                listRockQualitys.add(newRockQuality);
            }

            return listRockQualitys;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }


}
