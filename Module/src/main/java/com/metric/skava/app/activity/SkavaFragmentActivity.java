package com.metric.skava.app.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.navigation.NavigationController;
import com.metric.skava.app.util.DateDisplayFormat;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ClientDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.DataSyncDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ExcavationProjectDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.FilesSyncDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrCategoriesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RoleDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRequirementDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelFaceDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.UserDropboxTable;
import com.metric.skava.home.helper.ImportDataHelper;
import com.metric.skava.sync.exception.SyncDataFailedException;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.DataToSync;
import com.metric.skava.sync.model.FileToSync;
import com.metric.skava.sync.model.RecordToSync;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncQueue;
import com.metric.skava.sync.model.SyncStatus;
import com.metric.skava.sync.model.SyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class SkavaFragmentActivity extends FragmentActivity implements DbxDatastore.SyncStatusListener {

    protected NavigationController navController;
    protected ActionBar mActionBar;

    protected boolean preventExecution;
    protected boolean lackOfAppData;
    protected boolean lackOfUserData;

    protected boolean assertUserDataNeverCalled = true;
    protected boolean assertAppDataNeverCalled = true;
    protected SkavaFragment mMainContainedFragment;

    protected abstract void onPreExecuteImportAppData();

    protected abstract void onPreExecuteImportUserData();

    protected abstract void onPostExecuteImportAppData();

    protected abstract void onPostExecuteImportUserData();

    protected abstract void showProgressBar(final boolean show, String text, boolean longTime);

    public SkavaContext getSkavaContext() {
        SkavaApplication application = (SkavaApplication) (getApplication());
        return application.getSkavaContext();
    }

    public SyncHelper getSyncHelper() {
        return getSkavaContext().getSyncHelper();
    }

    public DAOFactory getDAOFactory() {
        return getSkavaContext().getDAOFactory();
    }

    public Assessment getCurrentAssessment() {
        return getSkavaContext().getAssessment();
    }

    public boolean shouldImportUserData() {
        return ((SkavaApplication) getApplication()).isImportUserDataNeeded();
    }

    public boolean shouldImportAppData() {
        return ((SkavaApplication) getApplication()).isImportAppDataNeeded();
    }

    public boolean shouldUnlinkOnLogout() {
        return ((SkavaApplication) getApplication()).isUnlinkPrefered();
    }

    public String getTargetEnvironment() {
        return getSkavaContext().getTargetEnvironment();
    }

    private void pseudoInjection() {
        navController = NavigationController.getInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pseudoInjection();
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        if (getSkavaContext().getDatastore() != null) {
            getSkavaContext().getDatastore().addSyncStatusListener(this);
        }
//        if (getSkavaContext().getFileSystem() != null) {
//            getSkavaContext().getFileSystem().addSyncStatusListener(this);
//        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    public void onResume() {
        super.onResume();
        if (getSkavaContext().getDatastore() != null) {
            getSkavaContext().getDatastore().addSyncStatusListener(this);
        }
//        if (getSkavaContext().getFileSystem() != null) {
//            getSkavaContext().getFileSystem().addSyncStatusListener(this);
//        }

    }

    public void onPause() {
        super.onPause();
        if (getSkavaContext().getDatastore() != null) {
            getSkavaContext().getDatastore().removeSyncStatusListener(this);
        }
//        if (getSkavaContext().getFileSystem() != null) {
//            getSkavaContext().getFileSystem().removeSyncStatusListener(this);
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getSkavaContext().getDatastore() != null) {
            getSkavaContext().getDatastore().removeSyncStatusListener(this);
        }
//        if (getSkavaContext().getFileSystem() != null) {
//            getSkavaContext().getFileSystem().removeSyncStatusListener(this);
//        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void saveUserDataSyncStatus(boolean success) {
        getSkavaContext().getUserDataSyncMetadata().setSuccess(success);
        getSkavaContext().getUserDataSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.user_data_last_sync_succeed), success);
        editor.putLong(getString(R.string.user_data_last_sync_date), SkavaUtils.getCurrentDate().getTime());
        editor.commit();
    }

    public void saveAppDataSyncStatus(boolean success) {
        getSkavaContext().getAppDataSyncMetadata().setSuccess(success);
        getSkavaContext().getAppDataSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.app_data_last_sync_succeed), success);
        editor.putLong(getString(R.string.app_data_last_sync_date), SkavaUtils.getCurrentDate().getTime());
        editor.commit();
    }

    public boolean saveDraft() {
        boolean success = false;
        try {
            Assessment currentAssessment = getCurrentAssessment();
            LocalAssessmentDAO localAssessmentDAO = getDAOFactory().getLocalAssessmentDAO();
            localAssessmentDAO.saveAssessment(currentAssessment);
            success = true;
        } catch (DAOException e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return success;
    }

    public class ImportAppDataModelTask extends AsyncTask<SyncTask.Domain, Long, Long> {

        private SyncLogEntry errorCondition;
        private Long totalRecordsToImport;
        private ImportDataHelper importHelper;

        @Override
        protected void onPreExecute() {
            importHelper = new ImportDataHelper(getSyncHelper());
            String message = getString(R.string.syncing_app_data_progress);
            onPreExecuteImportAppData();
            showProgressBar(true, message, true);
        }

        @Override
        protected Long doInBackground(SyncTask.Domain... params) {
            Long numRecordsCreated = 0L;
            try {
                //FIND OUT THE TOTAL NUMBER OF RECORDS
                totalRecordsToImport = getSyncHelper().findOutNumberOfRecordsToImport(params);
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
            showProgressBar(true, +value + " of " + totalRecordsToImport + " app data records imported so far.", false);
        }

        @Override
        protected void onPostExecute(Long result) {
            if (errorCondition == null) {
                //termino exitosamente
                showProgressBar(true, "Finished. " + result + " records imported.", true);
                lackOfAppData = false;
                preventExecution = false;
                saveAppDataSyncStatus(true);
                if (assertUserDataNeverCalled) {
                    try {
                        assertUserDataAvailable();
                    } catch (DAOException e) {
                        BugSenseHandler.sendException(e);
                        Log.e(SkavaConstants.LOG, e.getMessage());
                        e.printStackTrace();
                    }
                }
                onPostExecuteImportAppData();
            } else {
                lackOfAppData = true;
                preventExecution = true;
                saveAppDataSyncStatus(false);
                showProgressBar(false, "Failed after " + result + " records imported.", true);
                AlertDialog.Builder messageBox = new AlertDialog.Builder(SkavaFragmentActivity.this);
                messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
                messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
                messageBox.setCancelable(false);
                messageBox.setNeutralButton("OK", null);
                messageBox.show();
            }
            // now that app data sync has finished call the eventual user data sync
//            setupUserDataModel();
        }
    }


    public class ImportUserDataModelTask extends AsyncTask<SyncTask.Domain, Long, Long> {

        private SyncLogEntry errorCondition;
        private Long totalRecordsToImport;
        private ImportDataHelper importHelper;

        @Override
        protected void onPreExecute() {
            importHelper = new ImportDataHelper(getSyncHelper());
            String message = getString(R.string.syncing_user_data_progress);
            onPreExecuteImportUserData();
            showProgressBar(true, message, true);
        }

        @Override
        protected Long doInBackground(SyncTask.Domain... params) {
            Long numRecordsCreated = 0L;
            try {
                //FIND OUT THE TOTAL NUMBER OF RECORDS
                totalRecordsToImport = getSyncHelper().findOutNumberOfRecordsToImport(params);
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
            showProgressBar(true, +value + " of " + totalRecordsToImport + " user data records imported so far.", false);
        }

        @Override
        protected void onPostExecute(Long result) {
            if (errorCondition == null) {
                //mostrar que termino exitosamente
                lackOfUserData = false;
                preventExecution = false;
                saveUserDataSyncStatus(true);
                showProgressBar(false, "Finished. " + result + " records imported.", true);
            } else {
                lackOfUserData = true;
                preventExecution = true;
                saveUserDataSyncStatus(false);
                showProgressBar(false, "Failed after " + result + " records imported.", true);
                AlertDialog.Builder messageBox = new AlertDialog.Builder(SkavaFragmentActivity.this);
                messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
                messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
                messageBox.setCancelable(false);
                messageBox.setNeutralButton("OK", null);
                messageBox.show();
            }
            onPostExecuteImportUserData();
        }
    }


    @Override
    public void onDatastoreStatusChange(DbxDatastore store) {
        DbxDatastoreStatus datastoreStatus = store.getSyncStatus();

        if (datastoreStatus.hasIncoming) {
            try {
                Map<String, Set<DbxRecord>> incomingChanges = getSkavaContext().getDatastore().sync();
                //avoid to repeat the import process if there's two or more tables of same category (userData or appData)
                boolean userDataImportExecuted = false, appDataImportExecuted = false;
                for (String tablename : incomingChanges.keySet()) {

                    if (tablename.equals(DataSyncDropboxTable.DATA_SYNC_TABLE)) {
                        //find what is being aknowledged and remove from the middleman box space
                        Set<DbxRecord> dbxRecords = incomingChanges.get(DataSyncDropboxTable.DATA_SYNC_TABLE);
                        for (DbxRecord dbxRecord : dbxRecords) {
                            String acknowledgedAssessmentCode = dbxRecord.getString("assesmentCode");
                            String acknowledgedRecordID = dbxRecord.getString("dropboxId");
                            //find what is the file being acknowledged and remove it from the middleman box space
                            SyncQueue whatIwishSync = getSkavaContext().getMiddlemanInbox();
                            List<RecordToSync> pendingRecords = whatIwishSync.getRecords(acknowledgedAssessmentCode);
                            //iterate over all the data records
                            //use a array copy for loop to avoid iterator and remove conflict
                            List<Integer> foundIndexes = new ArrayList<Integer>();
                            for (int i = 0; i < pendingRecords.size(); i++) {
                                RecordToSync recordToSync = pendingRecords.get(i);
                                //those should be FileToSync instances
                                String recordID = recordToSync.getRecordID();
                                if (recordID.equals(acknowledgedRecordID)) {
                                    foundIndexes.add(i);
                                }
                            }
                            //Removes from the list of pending those that was reported by ack table
                            if (foundIndexes != null && !foundIndexes.isEmpty()) {
                                for (Integer foundIndex : foundIndexes) {
                                    pendingRecords.remove(foundIndex.intValue());
                                }
                            }
                            if (pendingRecords != null && pendingRecords.isEmpty()) {
                                try {
                                    LocalAssessmentDAO assessmentDAO = getDAOFactory().getLocalAssessmentDAO();
                                    Assessment uploadedAssessment = assessmentDAO.getAssessment(acknowledgedAssessmentCode);
                                    uploadedAssessment.setSentToCloud(Assessment.DATA_SENT_TO_CLOUD);
                                    assessmentDAO.updateAssessment(uploadedAssessment, false);

                                    //mostrar que termino exitosamente
                                    NotificationCompat.Builder mBuilder;
                                    mBuilder = new NotificationCompat.Builder(this)
                                            .setSmallIcon(R.drawable.cloud_striped)
                                            .setContentTitle("Skava Mobile :: Mapping data delivered to DB Datastore")
                                            .setContentText(uploadedAssessment.getPseudoCode() + " was delivered to DB DataStore");
                                    int mNotificationId = 010;
                                    // Gets an instance of the NotificationManager service
                                    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    mNotifyMgr.notify(mNotificationId, mBuilder.build());

                                } catch (DAOException e) {
                                    e.printStackTrace();
                                    Log.e(SkavaConstants.LOG, e.getMessage());
                                }
                            }

                        }
                    }
                    if (tablename.equals(FilesSyncDropboxTable.FILE_SYNC_TABLE)) {
                        String acknowledgedAssessmentCode = null;
                        String acknowledgedFileName = null;
                        Set<DbxRecord> dbxRecords = incomingChanges.get(FilesSyncDropboxTable.FILE_SYNC_TABLE);
                        for (DbxRecord dbxRecord : dbxRecords) {
                            acknowledgedAssessmentCode = dbxRecord.getString("assessmentCode");
                            acknowledgedFileName = dbxRecord.getString("fileName");

                            //find what is the file being acknowledged and remove it from the middleman box space
                            SyncQueue whatIwishSync = getSkavaContext().getMiddlemanInbox();
                            List<FileToSync> pendingPictures = whatIwishSync.getFiles(acknowledgedAssessmentCode);
                            //iterate over all the images
                            List<Integer> foundIndexes = new ArrayList<Integer>();
                            //use a for loop to avoid iterator and remove conflict
                            for (int i = 0; i < pendingPictures.size(); i++) {
                                FileToSync pictureFileToSync = pendingPictures.get(i);
                                //those should be FileToSync instances
                                String fileName = pictureFileToSync.getFileName();
                                if (fileName.equals(acknowledgedFileName)) {
                                    foundIndexes.add(i);
                                }
                            }
                            //Removes from the list of pending those that was reported by ack table
                            if (foundIndexes != null && !foundIndexes.isEmpty()) {
                                for (Integer foundIndex : foundIndexes) {
                                    pendingPictures.remove(foundIndex.intValue());
                                }
                            }
                            if (pendingPictures != null && pendingPictures.isEmpty()) {
                                try {
                                    LocalAssessmentDAO assessmentDAO = getDAOFactory().getLocalAssessmentDAO();
                                    Assessment uploadedAssessment = assessmentDAO.getAssessment(acknowledgedAssessmentCode);
                                    uploadedAssessment.setSentToCloud(Assessment.PICS_SENT_TO_CLOUD);
                                    assessmentDAO.updateAssessment(uploadedAssessment, false);

                                    //mostrar que termino exitosamente
                                    NotificationCompat.Builder mBuilder;
                                    mBuilder = new NotificationCompat.Builder(this)
                                            .setSmallIcon(R.drawable.cloud_checked)
                                            .setContentTitle("Skava Mobile :: Mapping pictures delivered to DB Datastore")
                                            .setContentText(uploadedAssessment.getPseudoCode() + " was delivered to DB DataStore");
                                    int mNotificationId = 010;
                                    // Gets an instance of the NotificationManager service
                                    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    mNotifyMgr.notify(mNotificationId, mBuilder.build());


                                } catch (DAOException e) {
                                    e.printStackTrace();
                                    Log.e(SkavaConstants.LOG, e.getMessage());
                                }
                            }
                        }
                    }
                    //These list of Domains were created in order to sync only the necessary but
                    //the lack of control on the order of execution of the async task don't help so
                    //it seems better to update all user data OR all app data as a unit
                    List<SyncTask.Domain> syncTargetUserData = new ArrayList<SyncTask.Domain>();
                    List<SyncTask.Domain> syncTargetAppData = new ArrayList<SyncTask.Domain>();
                    //If there's multiple tables involved then sort them referenced entities must be synced first
                    //but sorting the trigger of the AsyncTask does not guarantee the order in execution
                    //so it's better although not optimal to do a full user data import
                    //********** USER RELATED DATA **********/
//                    if (tablename.equals(RoleDropboxTable.ROLES_DROPBOX_TABLE)) {
////                        syncTargetUserData.add(SyncTask.Domain.ROLES);
//                    }
//                    if (tablename.equals(ClientDropboxTable.CLIENTS_DROPBOX_TABLE)) {
////                        syncTargetUserData.add(SyncTask.Domain.CLIENTS);
//                    }
//                    if (tablename.equals(ExcavationProjectDropboxTable.PROJECTS_DROPBOX_TABLE)) {
////                        syncTargetUserData.add(SyncTask.Domain.EXCAVATION_PROJECTS);
//                    }
//                    if (tablename.equals(TunnelDropboxTable.TUNNELS_DROPBOX_TABLE)) {
////                        syncTargetUserData.add(SyncTask.Domain.TUNNELS);
//                    }
//                    if (tablename.equals(SupportRequirementDropboxTable.SUPPORT_REQUIREMENTS_DROPBOX_TABLE)) {
////                        syncTargetUserData.add(SyncTask.Domain.SUPPORT_REQUIREMENTS);
//                    }
//                    if (tablename.equals(TunnelFaceDropboxTable.FACES_DROPBOX_TABLE)) {
////                        syncTargetUserData.add(SyncTask.Domain.TUNNEL_FACES);
//                    }
//                    if (tablename.equals(UserDropboxTable.USERS_DROPBOX_TABLE)) {
////                        syncTargetUserData.add(SyncTask.Domain.USERS);
//                    }
                    if (tablename.equals(RoleDropboxTable.ROLES_DROPBOX_TABLE) ||
                            tablename.equals(ClientDropboxTable.CLIENTS_DROPBOX_TABLE) ||
                            tablename.equals(ExcavationProjectDropboxTable.PROJECTS_DROPBOX_TABLE) ||
                            tablename.equals(TunnelDropboxTable.TUNNELS_DROPBOX_TABLE) ||
                            tablename.equals(SupportRequirementDropboxTable.SUPPORT_REQUIREMENTS_DROPBOX_TABLE) ||
                            tablename.equals(TunnelFaceDropboxTable.FACES_DROPBOX_TABLE) ||
                            tablename.equals(UserDropboxTable.USERS_DROPBOX_TABLE)) {
                        syncTargetUserData.add(SyncTask.Domain.ALL_USER_DATA_TABLES);
                    }
                    //*********** APP DATA ************/
                    if (tablename.equals(ParametersDropboxTable.PARAMETERS_DROPBOX_TABLE)
                            || tablename.equals(RmrParametersDropboxTable.RMR_PARAMETERS_TABLE)
                            || tablename.equals(RmrIndexesDropboxTable.RMR_INDEXES_TABLE)
                            || tablename.equals(RmrCategoriesDropboxTable.RMR_CATEGORIES_TABLE)
                            ) {
                        syncTargetAppData.add(SyncTask.Domain.ALL_APP_DATA_TABLES);
                    }
                    try {
                        // Finally do the import
                        if (!syncTargetUserData.isEmpty() && !userDataImportExecuted) {
                            SyncTask.Domain[] syncTarget = syncTargetUserData.toArray(new SyncTask.Domain[]{});
                            ImportUserDataModelTask dynamicDataTask = new ImportUserDataModelTask();
                            dynamicDataTask.execute(syncTarget);
                            userDataImportExecuted = true;
                        }
                        if (!syncTargetAppData.isEmpty() && !appDataImportExecuted) {
                            SyncTask.Domain[] syncTarget = syncTargetAppData.toArray(new SyncTask.Domain[]{});
                            ImportAppDataModelTask dynamicDataTask = new ImportAppDataModelTask();
                            dynamicDataTask.execute(syncTarget);
                            appDataImportExecuted = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        BugSenseHandler.sendException(e);
                        Log.e(SkavaConstants.LOG, e.getMessage());
                    }
                }
            } catch (DbxException e) {
                BugSenseHandler.sendException(e);
                Log.e(SkavaConstants.LOG, e.getMessage());
                e.printStackTrace();
            }
        }

        if (datastoreStatus.hasOutgoing) {
            try {
                //Looks like the map returned works from remote->local and no the other way around,
                //so it's not very useful as it always be empty here
                Map<String, Set<DbxRecord>> outgoingChanges = store.sync();
                if (!outgoingChanges.isEmpty()) {
                    //Mmm... what are here??
                    System.out.println(outgoingChanges);
                }
                //As there's no way to know exactly what are the assessment marked to sync, update all of them
                Map<String, List<RecordToSync>> assessmentsDataToSync = getSkavaContext().getMiddlemanInbox().getAllRecords();
                //iterate over the set of ASSESSMENTS domain
                for (String assessmentIterQueue : assessmentsDataToSync.keySet()) {
                    List<RecordToSync> recordList = assessmentsDataToSync.get(assessmentIterQueue);
                    for (RecordToSync assessmentRecordsToSync : recordList) {
                        if (assessmentRecordsToSync.getOperation().equals(DataToSync.Operation.INSERT)) {
                            try {
                                LocalAssessmentDAO assessmentDAO = getDAOFactory().getLocalAssessmentDAO();
                                Assessment uploadedAssessment = assessmentDAO.getAssessment(assessmentIterQueue);
                                uploadedAssessment.setSentToCloud(Assessment.DATA_SENT_TO_DATASTORE);
                                assessmentDAO.updateAssessment(uploadedAssessment, false);
                            } catch (DAOException e) {
                                e.printStackTrace();
                                BugSenseHandler.sendException(e);
                                Log.e(SkavaConstants.LOG, e.getMessage());
                            }
                        }
                    }
                }

            } catch (DbxException e) {
                BugSenseHandler.sendException(e);
                Log.e(SkavaConstants.LOG, e.getMessage());
                e.printStackTrace();
            }
        }

        if (datastoreStatus.isUploading) {
            //Is actually going into the cloud
            Log.d(SkavaConstants.LOG, "uploading");

        }
        if (datastoreStatus.isDownloading) {
            //Is actually reading from the cloud
            Log.d(SkavaConstants.LOG, "downloading");

        }

    }


//    @Override
//    public void onSyncStatusChange(DbxFileSystem fs) {
//        DbxSyncStatus fileSystemStatus = null;
//        try {
//            fileSystemStatus = fs.getSyncStatus();
//
//            if (fileSystemStatus.anyInProgress()) {
//
//                DbxSyncStatus.OperationStatus metadata = fileSystemStatus.metadata;
//                if (metadata.inProgress) {
//                    //is syncing just the metadata, at least this tell us it is connected and ready...
//                    // I don't download anything yet
//                    Log.d(SkavaConstants.LOG, "metadata.inProgress >>>" + metadata.toString());
//                }
//
//                DbxSyncStatus.OperationStatus uploadStatus = fileSystemStatus.upload;
//                if (uploadStatus.inProgress) {
//                    Log.d(SkavaConstants.LOG, "uploadStatus.inProgress >>>" + uploadStatus.toString());
//                    //is uploading
////                    fs.syncNowAndWait();
//                    //HOW TO KNOW WHAT EXACTLY IS THE FILE CURRENTLY UPLOADING??
//                    //For now just mark ALL the pendind files as SENT TO DATASTORE
//                    //using a counter as in order to know if all the pictures in the assessment has been sent
//                    //As there's no way to know exactly what are the assessment marked to sync, update all of them
//                    Map<String, List<FileToSync>> assessmentsDataToSync = getSkavaContext().getMiddlemanInbox().getAllFiles();
//                    //iterate over the set of ASSESSMENTS domain
//                    for (String assessmentIterQueue : assessmentsDataToSync.keySet()) {
//                        List<FileToSync> recordList = assessmentsDataToSync.get(assessmentIterQueue);
//                        for (FileToSync assessmentFilesToSync : recordList) {
//                            if (assessmentFilesToSync.getOperation().equals(DataToSync.Operation.INSERT)) {
//                                try {
//                                    LocalAssessmentDAO assessmentDAO = getDAOFactory().getLocalAssessmentDAO();
//                                    Assessment uploadedAssessment = assessmentDAO.getAssessment(assessmentIterQueue);
//                                    uploadedAssessment.setSentToCloud(Assessment.PICS_SENT_TO_DATASTORE);
//                                    assessmentDAO.updateAssessment(uploadedAssessment, false);
//                                } catch (DAOException e) {
//                                    e.printStackTrace();
//                                    BugSenseHandler.sendException(e);
//                                    Log.e(SkavaConstants.LOG, e.getMessage());
//                                }
//                            }
//                        }
//                    }
//                }
//
//                DbxSyncStatus.OperationStatus dowloadStatus = fileSystemStatus.download;
//                if (dowloadStatus.inProgress) {
//                    //is downloading ...
//                    Log.d(SkavaConstants.LOG, "dowloadStatus.inProgress >>>" + dowloadStatus.toString());
//                    // I don't download anything yet
//                }
//
//            }
//        } catch (DbxException e) {
//            BugSenseHandler.sendException(e);
//            Log.e(SkavaConstants.LOG, e.getMessage());
//            e.printStackTrace();
//            DbxThrowable problem = fileSystemStatus.anyFailure();
//            if (problem != null) {
//                Log.e(SkavaConstants.LOG, problem.getMessage());
//            }
//        }
//    }

    protected void assertAppDataAvailable() throws DAOException {
        SyncStatus lastSyncState = getSkavaContext().getAppDataSyncMetadata();
        if (getSyncHelper().areAppDataTablesEmpty()) {
            lackOfAppData = true;
        }
        if (lackOfAppData) {
            final String alertTitle = "Skava App data tables are empty";
            final String textToShow = "Skava Mobile needs a minimal set of configuration data that is currently not available in your device. In order to download it ensure Internet is available !!";
            Log.d(SkavaConstants.LOG, textToShow);
            DialogFragment theDialog = new DialogFragment() {
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(alertTitle);
                    builder.setMessage(textToShow);
                    builder.setPositiveButton("Download now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (isNetworkAvailable()) {
                                ImportAppDataModelTask fixedDataTask = new ImportAppDataModelTask();
                                fixedDataTask.execute(SyncTask.Domain.ALL_APP_DATA_TABLES);
                            } else {
                                //show internet required message
                                String alertTitle = "Currently you have no Internet connection";
                                builder.setTitle(alertTitle);
                                builder.setMessage("In order to download and import the Skava App data, an Internet connection is required");
                                preventExecution = true;
                            }
                        }
                    });
                    builder.setCancelable(true);
                    // Create the AlertDialog object and return it
                    return builder.create();
                }
            };
            // Showing Alert Message
            theDialog.show(getSupportFragmentManager(), "assertAppDataDialog");
        } else {
            // The app tables seems to be populated, so ...
            if (isNetworkAvailable()) {
                //Operate normally, the listener will take care and keep the app data updated
                if (assertUserDataNeverCalled) {
                    try {
                        assertUserDataAvailable();
                    } catch (DAOException e) {
                        BugSenseHandler.sendException(e);
                        Log.e(SkavaConstants.LOG, e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                //However if there no Internet let the user know this won happen
                final String alertTitle = "Skava App will operate without Internet connection";
                String textToShow = "You can work offline but the synchronization will not be possible until the Internet connection is restored.";
                //Just a final check on how good the last syn resulted
                if (lastSyncState != null && lastSyncState.isSuccess()) {
                    String dateAsString = DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastSyncState.getLastExecution());
                    textToShow += "Be aware that local app data (master parameters) were last succeeded synced on " + dateAsString;
                    Log.d(SkavaConstants.LOG, textToShow);
                }
                if (lastSyncState != null && !lastSyncState.isSuccess()) {
                    String dateAsString = DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastSyncState.getLastExecution());
                    textToShow += "Be aware that last app data (master parameters) sync failed on " + dateAsString + "so operate on this conditions is discouraged";
                    preventExecution = true;
                    Log.d(SkavaConstants.LOG, textToShow);
                }
                final String finalTextToShow = textToShow;
                DialogFragment theDialog = new DialogFragment() {
                    @Override
                    public Dialog onCreateDialog(Bundle savedInstanceState) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(alertTitle);
                        builder.setMessage(finalTextToShow).setPositiveButton("OK", null);
                        // Create the AlertDialog object and return it
                        return builder.create();
                    }
                };
                theDialog.show(getSupportFragmentManager(), "assertAppDataDialog");
            }
        }
        assertAppDataNeverCalled = false;
    }

    protected void assertUserDataAvailable() throws DAOException {
        SyncStatus lastSyncState = getSkavaContext().getUserDataSyncMetadata();
        if (getSyncHelper().areUserDataTablesEmpty()) {
            lackOfUserData = true;
        }
        if (lackOfUserData) {
            final String alertTitle = "Skava App user data tables are empty";
            final String textToShow = "Skava Mobile needs users data that is currently not available in your device. In order to download it ensure Internet is available !!";
            Log.d(SkavaConstants.LOG, textToShow);
            DialogFragment theDialog = new DialogFragment() {
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(alertTitle);
                    builder.setMessage(textToShow).setPositiveButton("Download now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (isNetworkAvailable()) {
                                ImportUserDataModelTask dynamicDataTask = new ImportUserDataModelTask();
                                dynamicDataTask.execute(SyncTask.Domain.ALL_USER_DATA_TABLES);
                            } else {
                                //show internet required message
                                String alertTitle = "Currently you have no Internet connection";
                                builder.setTitle(alertTitle);
                                builder.setMessage("In order to download and import the Skava App data, an Internet connection is required");
                                preventExecution = true;
                            }
                        }
                    });
                    builder.setCancelable(true);
                    // Create the AlertDialog object and return it
                    return builder.create();
                }
            };
            // Showing Alert Message
            theDialog.show(getSupportFragmentManager(), "assertUserDataDialog");
        } else {
            // The user tables seems to be populated, so ...
            if (isNetworkAvailable()) {
                //Operate normally, the listener will take care and keep the app data updated
            } else {
                //However if there no Internet let the user know this won happen
                final String alertTitle = "Skava App will operate without Internet connection";
                String textToShow = "You can work offline but the synchronization will not be possible until the Internet connection is restored.";
                //Just a final check on how good the last syn resulted
                if (lastSyncState != null && lastSyncState.isSuccess()) {
                    String dateAsString = DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastSyncState.getLastExecution());
                    textToShow += "Be aware that user data (users, projects, tunnels, faces, etc) were last succeeded synced on " + dateAsString;
                    Log.d(SkavaConstants.LOG, textToShow);
                }
                if (lastSyncState != null && !lastSyncState.isSuccess()) {
                    String dateAsString = DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastSyncState.getLastExecution());
                    textToShow += "Be aware that last user data (users, projects, tunnels, faces, etc) sync failed on " + dateAsString + "so operate on this conditions is discouraged";
                    preventExecution = true;
                    Log.d(SkavaConstants.LOG, textToShow);
                }
                final String finalTextToShow = textToShow;
                DialogFragment theDialog = new DialogFragment() {
                    @Override
                    public Dialog onCreateDialog(Bundle savedInstanceState) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(alertTitle);
                        builder.setMessage(finalTextToShow).setPositiveButton("OK", null);
                        // Create the AlertDialog object and return it
                        return builder.create();
                    }
                };
                theDialog.show(getSupportFragmentManager(), "assertUserDataDialog");
            }
        }
        assertUserDataNeverCalled = false;
    }

}
