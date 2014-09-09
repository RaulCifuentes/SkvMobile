package com.metric.skava.app.helper;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.home.helper.ImportDataHelper;
import com.metric.skava.sync.exception.SyncDataFailedException;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncTask;

/**
 * Created by metricboy on 7/8/14.
 */
public class ImportAppDataModelTask extends AsyncTask<SyncTask.Domain, Long, Long> {

    protected final SkavaContext mContext;
    protected SkavaFragmentActivity mActivity;

    protected SyncLogEntry errorCondition;
    private Long totalRecordsToImport;
    private ImportDataHelper importHelper;

    public ImportAppDataModelTask(SkavaContext context, SkavaFragmentActivity activity) {
        this.mContext = context;
        this.mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        importHelper = new ImportDataHelper(mContext.getSyncHelper());
        String message = mActivity.getString(R.string.syncing_app_data_progress);
        mActivity.onPreExecuteImportAppData();
        mActivity.showProgressBar(true, message, true);
        mContext.setWorkInProgress(true);
    }

    @Override
    protected Long doInBackground(SyncTask.Domain... params) {
        Long numRecordsCreated = 0L;
        try {
            //FIND OUT THE TOTAL NUMBER OF RECORDS
            totalRecordsToImport = mContext.getSyncHelper().findOutNumberOfRecordsToImport(params);
            if (totalRecordsToImport > 0) {
                // **** IMPORT GENERAL DATA FIRST *** //
                numRecordsCreated += importHelper.importMethods();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importSections();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importDiscontinuityTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importRelevances();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importIndexes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importGroups();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importSpacings();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importPersistences();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importApertures();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importShapes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importRoughnesses();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importInfillings();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importWeatherings();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importWaters();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importStrengths();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importGroundwaters();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importOrientations();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importJns();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importJrs();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importJas();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importJws();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importSRFs();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importFractureTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importBoltTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importShotcreteTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importMeshTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importCoverages();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importArchTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importESRs();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importSupportPatternTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importHelper.importRockQualities();
                publishProgress(numRecordsCreated);
            }
        } catch (SyncDataFailedException e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            errorCondition = e.getEntry();
            if (errorCondition == null) {
                errorCondition = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ALL_APP_DATA_TABLES, SyncTask.Source.DROPBOX_LOCAL_DATASTORE, SyncTask.Status.FAIL, 0L);
            }
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            e.printStackTrace();
            errorCondition = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ALL_APP_DATA_TABLES, SyncTask.Source.DROPBOX_LOCAL_DATASTORE, SyncTask.Status.FAIL, 0L);
        }
        return numRecordsCreated;
    }


    @Override
    protected void onProgressUpdate(Long... progress) {
        //mostrar avance
        Long value = (Long) progress[0];
        mActivity.showProgressBar(true, +value + " of " + totalRecordsToImport + " app data records imported so far.", false);
    }

    @Override
    protected void onPostExecute(Long result) {
        if (errorCondition == null) {
            mActivity.onPostExecuteImportAppData(true, result);
        } else {
            mActivity.onPostExecuteImportAppData(false, result);
            AlertDialog.Builder messageBox = new AlertDialog.Builder(mActivity);
            messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
            messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
            messageBox.setCancelable(false);
            messageBox.setNeutralButton("OK", null);
            messageBox.show();
        }
        mContext.setWorkInProgress(false);
    }
}