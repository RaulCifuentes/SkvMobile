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
public class ImportUserDataModelTask extends AsyncTask<SyncTask.Domain, Long, Long> {

    private final SkavaContext mContext;
    private SkavaFragmentActivity mActivity;

    private SyncLogEntry errorCondition;
    private Long totalRecordsToImport;
    private ImportDataHelper importHelper;

    public ImportUserDataModelTask(SkavaContext context, SkavaFragmentActivity activity) {
        this.mContext = context;
        this.mActivity = activity;
    }


    @Override
    protected void onPreExecute() {
        importHelper = new ImportDataHelper(mContext.getSyncHelper());
        String message = mActivity.getString(R.string.syncing_user_data_progress);
        mActivity.onPreExecuteImportUserData();
        mActivity.showProgressBar(true, message, true);
    }

    @Override
    protected Long doInBackground(SyncTask.Domain... params) {
        Long numRecordsCreated = 0L;
        try {
            //FIND OUT THE TOTAL NUMBER OF RECORDS
            totalRecordsToImport = mContext.getSyncHelper().findOutNumberOfRecordsToImport(params);
            if (totalRecordsToImport > 0) {
                // **** IMPORT THEN USER RELATED DATA **** //
                for (SyncTask.Domain currentDomainToImport : params) {
                    switch (currentDomainToImport) {
                        case ROLES:
                            numRecordsCreated += importHelper.importRoles();
                            publishProgress(numRecordsCreated);
                            break;
                        case CLIENTS:
                            numRecordsCreated += importHelper.importClients();
                            publishProgress(numRecordsCreated);
                            break;
                        case EXCAVATION_PROJECTS:
                            numRecordsCreated += importHelper.importProjects();
                            publishProgress(numRecordsCreated);
                            break;
                        case TUNNELS:
                            numRecordsCreated += importHelper.importTunnels();
                            publishProgress(numRecordsCreated);
                            break;
                        case SUPPORT_REQUIREMENTS:
                            numRecordsCreated += importHelper.importSupportRequirements();
                            publishProgress(numRecordsCreated);
                            break;
                        case TUNNEL_FACES:
                            numRecordsCreated += importHelper.importTunnelFaces();
                            publishProgress(numRecordsCreated);
                            break;
                        case USERS:
                            numRecordsCreated += importHelper.importUsers();
                            publishProgress(numRecordsCreated);
                            break;
                        case ALL_USER_DATA_TABLES:
                            numRecordsCreated += importHelper.importRoles();
                            publishProgress(numRecordsCreated);
                            numRecordsCreated += importHelper.importClients();
                            publishProgress(numRecordsCreated);
                            numRecordsCreated += importHelper.importProjects();
                            publishProgress(numRecordsCreated);
                            numRecordsCreated += importHelper.importTunnels();
                            publishProgress(numRecordsCreated);
                            numRecordsCreated += importHelper.importSupportRequirements();
                            publishProgress(numRecordsCreated);
                            numRecordsCreated += importHelper.importTunnelFaces();
                            publishProgress(numRecordsCreated);
                            numRecordsCreated += importHelper.importUsers();
                            publishProgress(numRecordsCreated);
                            break;
                    }
                }
            }
        } catch (SyncDataFailedException e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            errorCondition = e.getEntry();
        } catch (DAOException e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            errorCondition = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncTask.Domain.ALL_APP_DATA_TABLES, SyncTask.Source.DROPBOX_LOCAL_DATASTORE, SyncTask.Status.FAIL, 0L);
        }
        return numRecordsCreated;
    }


    @Override
    protected void onProgressUpdate(Long... progress) {
        //mostrar avance
        Long value = (Long) progress[0];
        mActivity.showProgressBar(true, +value + " of " + totalRecordsToImport + " user data records imported so far.", false);
    }

    @Override
    protected void onPostExecute(Long result) {
        if (errorCondition == null) {
            //mostrar que termino exitosamente
            mActivity.onPostExecuteImportUserData(true, result);
        } else {
            mActivity.onPostExecuteImportUserData(false, result);
            AlertDialog.Builder messageBox = new AlertDialog.Builder(mActivity);
            messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
            messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
            messageBox.setCancelable(false);
            messageBox.setNeutralButton("OK", null);
            messageBox.show();
        }
    }
}
