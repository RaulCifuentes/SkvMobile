package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxTable;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.RemoteMetadataDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.AssessmentDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ClientDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.DiscontinuitiesFamilyDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ExcavationProjectDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.QBartonCalculationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RMRCalculationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RoleDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRecommendationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRequirementDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelFaceDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.UserDropboxTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by metricboy on 3/6/14.
 */
public class MetadataDAODropboxImpl extends DropBoxBaseDAO implements RemoteMetadataDAO {

    public MetadataDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
    }


    public Long getAllAssessmentRelatedRecordsCount() throws DAOException {
        try {
            Long totalRecords = 0L;
            List<DbxTable> allTables = new ArrayList<DbxTable>();
            allTables.add(getDatastore().getTable(AssessmentDropboxTable.ASSESSMENT_TABLE));
            allTables.add(getDatastore().getTable(DiscontinuitiesFamilyDropboxTable.DISCONTINUITIES_TABLE));
            allTables.add(getDatastore().getTable(QBartonCalculationDropboxTable.Q_CALCULATION_TABLE));
            allTables.add(getDatastore().getTable(RMRCalculationDropboxTable.RMR_CALCULATION_TABLE));
            allTables.add(getDatastore().getTable(SupportRecommendationDropboxTable.SUPPORT_RECOMMENDATION_TABLE));
            for (DbxTable aTable : allTables) {
                DbxTable.QueryResult justCountQuery = aTable.query();
                int numRecords = justCountQuery.count();
                totalRecords += numRecords;
            }
            return totalRecords;
        } catch (DbxException e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
            }
    }


    public Long getAssessmentRelatedRecordsCount(String assessmentCode) throws DAOException {
        try {
            Long totalRecords = 0L;

            DbxFields criteria = new DbxFields();
            criteria.set("code", assessmentCode);
            DbxTable.QueryResult justCountQuery = getDatastore().getTable(AssessmentDropboxTable.ASSESSMENT_TABLE).query(criteria);
            int numRecords = justCountQuery.count();
            totalRecords += numRecords;

            criteria = new DbxFields();
            criteria.set("assessmentCode", assessmentCode);
            justCountQuery = getDatastore().getTable(DiscontinuitiesFamilyDropboxTable.DISCONTINUITIES_TABLE).query(criteria);
            numRecords = justCountQuery.count();
            totalRecords += numRecords;

            List<DbxTable> allTables = new ArrayList<DbxTable>();
            allTables.add(getDatastore().getTable(QBartonCalculationDropboxTable.Q_CALCULATION_TABLE));
            allTables.add(getDatastore().getTable(RMRCalculationDropboxTable.RMR_CALCULATION_TABLE));
            allTables.add(getDatastore().getTable(SupportRecommendationDropboxTable.SUPPORT_RECOMMENDATION_TABLE));
            for (DbxTable aTable : allTables) {
                criteria = new DbxFields();
                criteria.set("assessmentCode", assessmentCode);
                justCountQuery = aTable.query(criteria);
                numRecords = justCountQuery.count();
                totalRecords += numRecords;
            }
            return totalRecords;
        } catch (DbxException e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
        }
    }



    @Override
    public Long getAllAppDataRecordsCount() throws DAOException {
        try {
            Long totalRecords = 0L;
            Set<DbxTable> allTables = getDatastore().getTables();
            for (DbxTable aTable : allTables) {
                DbxTable.QueryResult justCountQuery = aTable.query();
                int numRecords = justCountQuery.count();
                totalRecords += numRecords;
            }
            totalRecords -= getAllUserDataRecordsCount();
            totalRecords -= getAllAssessmentRelatedRecordsCount();
            return totalRecords;
        } catch (DbxException e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
        }
    }


    public Long getAllUserDataRecordsCount() throws DAOException {
        try {
            Long totalRecords = 0L;
            List<DbxTable> allTables = new ArrayList<DbxTable>();
            allTables.add(getDatastore().getTable(RoleDropboxTable.ROLES_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(ClientDropboxTable.CLIENTS_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(ExcavationProjectDropboxTable.PROJECTS_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(TunnelDropboxTable.TUNNELS_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(SupportRequirementDropboxTable.SUPPORT_REQUIREMENTS_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(TunnelFaceDropboxTable.FACES_DROPBOX_TABLE));
            allTables.add(getDatastore().getTable(UserDropboxTable.USERS_DROPBOX_TABLE));

            for (DbxTable aTable : allTables) {
                DbxTable.QueryResult justCountQuery = aTable.query();
                int numRecords = justCountQuery.count();
                totalRecords += numRecords;
            }
            return totalRecords;
        } catch (DbxException e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Long getRecordsCount(String[] dropboxTables) throws DAOException {
        try {
            Long totalRecords = 0L;
            for (int i = 0; i < dropboxTables.length; i++) {
                String dropboxTable = dropboxTables[i];
                DbxTable dbxTable = getDatastore().getTable(dropboxTable);
                DbxTable.QueryResult justCountQuery = dbxTable.query();
                int numRecords = justCountQuery.count();
                totalRecords += numRecords;
            }
            return totalRecords;
        } catch (DbxException e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            throw new DAOException(e);
        }
    }


}
