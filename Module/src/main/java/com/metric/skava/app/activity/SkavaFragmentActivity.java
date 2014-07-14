package com.metric.skava.app.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.metric.skava.app.helper.ImportAppAndUserDataModelTask;
import com.metric.skava.app.helper.ImportAppDataModelTask;
import com.metric.skava.app.helper.ImportUserDataModelTask;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.navigation.NavigationController;
import com.metric.skava.app.util.DateDisplayFormat;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.AssessmentDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.DataSyncDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.FilesSyncDropboxTable;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.AssessmentSyncTrace;
import com.metric.skava.sync.model.DataToSync;
import com.metric.skava.sync.model.FileToSync;
import com.metric.skava.sync.model.RecordToSync;
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
    protected boolean enoughDataAvailable = false;

    protected SkavaFragment mMainContainedFragment;


    public abstract void onPreExecuteImportAppData();

    public abstract void onPreExecuteImportUserData();

    public abstract void onPostExecuteImportAppData(boolean success, Long result);

    public abstract void onPostExecuteImportUserData(boolean success, Long result);

    public abstract void showProgressBar(final boolean show, String text, boolean longTime);

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

    public boolean isLinkDropboxCompleted() {
        return ((SkavaApplication) getApplication()).isLinkDropboxCompleted();
    }

    public void setLinkDropboxCompleted(boolean linkDropboxCompleted) {
        ((SkavaApplication) getApplication()).setLinkDropboxCompleted(linkDropboxCompleted);
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
    }

    public void onPause() {
        super.onPause();
        if (getSkavaContext().getDatastore() != null) {
            getSkavaContext().getDatastore().removeSyncStatusListener(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getSkavaContext().getDatastore() != null) {
            getSkavaContext().getDatastore().removeSyncStatusListener(this);
        }
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
        editor.putBoolean(getString(R.string.user_data_last_sync_succeed_key), success);
        editor.putLong(getString(R.string.user_data_last_sync_date_key), SkavaUtils.getCurrentDate().getTime());
        editor.commit();
    }

    public void saveAppDataSyncStatus(boolean success) {
        getSkavaContext().getAppDataSyncMetadata().setSuccess(success);
        getSkavaContext().getAppDataSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.app_data_last_sync_succeed_key), success);
        editor.putLong(getString(R.string.app_data_last_sync_date_key), SkavaUtils.getCurrentDate().getTime());
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


    @Override
    public void onDatastoreStatusChange(DbxDatastore store) {
        DbxDatastoreStatus datastoreStatus = store.getSyncStatus();

        if (datastoreStatus.hasIncoming) {
            try {
                Map<String, Set<DbxRecord>> incomingChanges = getSkavaContext().getDatastore().sync();
                //avoid to repeat the import process if there's two or more tables of same category (userData or appData)
                boolean userDataImportExecuted = false, appDataImportExecuted = false;
                SyncLoggingDAO syncLoggingDAO = null;
                syncLoggingDAO = getDAOFactory().getSyncLoggingDAO();

                // Heads up:: Because there's no control on order of execution consider these cases:
                // 1. distinguish where only app data, only user data or (app and user) data needs to be updated -> handle them independently
                // 2. more than one correlated table from app or more than one correlated table from user data -> update them all as a group
                Set<String> incomingChangesTables = incomingChanges.keySet();

                //Any user or app data related chenges that deserve an import (repopulate tables)
                if (SkavaUtils.includesAppOrUserData(incomingChangesTables)){
                    //Are both kind of imports required ??
                    if (SkavaUtils.includesAppAndUserData(incomingChangesTables)){
                        final String alertTitle = "Skava app and user data tables were updated on web admin console";
                        final String textToShow = "There's new data currently not available in your device. When should I try to sync those ? ";
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
                                            //First app data, when finished start the user data
                                            try {
                                                SyncTask.Domain[] syncTarget = new SyncTask.Domain[]{SyncTask.Domain.ALL_APP_DATA_TABLES, SyncTask.Domain.ALL_USER_DATA_TABLES};
                                                ImportAppAndUserDataModelTask dynamicDataTask = new ImportAppAndUserDataModelTask(getSkavaContext(), SkavaFragmentActivity.this);
                                                dynamicDataTask.execute(syncTarget);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                BugSenseHandler.sendException(e);
                                                Log.e(SkavaConstants.LOG, e.getMessage());
                                            }
                                        } else {
                                            //show internet required message
                                            String alertTitle = "Currently you have no Internet connection";
                                            builder.setTitle(alertTitle);
                                            builder.setMessage("In order to download and import the Skava App data, an Internet connection is required");
                                            preventExecution = true;
                                        }
                                    }
                                });
                                builder.setNegativeButton("Postpone to the next app start", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((SkavaApplication)getApplication()).setNeedImportUserData(true);
                                    }
                                });
//                                builder.setCancelable(true);
                                // Create the AlertDialog object and return it
                                return builder.create();
                            }
                        };
                        // Showing Alert Message
                        theDialog.show(getSupportFragmentManager(), "assertUserDataDialog");
                    }

                    //Or is it only user data what was changed?
                    if (SkavaUtils.includesOnlyUserData(incomingChangesTables)){
                        final String alertTitle = "Skava user data tables were updated on web admin console";
                        final String textToShow = "There's new data currently not available in your device. When should I try to sync those ? ";
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
                                            try {
                                                SyncTask.Domain[] syncTarget = new SyncTask.Domain[]{SyncTask.Domain.ALL_USER_DATA_TABLES};
                                                ImportUserDataModelTask dynamicDataTask = new ImportUserDataModelTask(getSkavaContext(), SkavaFragmentActivity.this);
                                                dynamicDataTask.execute(syncTarget);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                BugSenseHandler.sendException(e);
                                                Log.e(SkavaConstants.LOG, e.getMessage());
                                            }
                                        } else {
                                            //show internet required message
                                            String alertTitle = "Currently you have no Internet connection";
                                            builder.setTitle(alertTitle);
                                            builder.setMessage("In order to download and import the Skava App data, an Internet connection is required");
                                            preventExecution = true;
                                        }
                                    }
                                });
                                builder.setNegativeButton("Postpone to the next app start", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((SkavaApplication)getApplication()).setNeedImportUserData(true);
                                    }
                                });
//                                builder.setCancelable(true);
                                // Create the AlertDialog object and return it
                                return builder.create();
                            }
                        };
                        // Showing Alert Message
                        theDialog.show(getSupportFragmentManager(), "assertUserDataDialog");
                    }

                    //Or was it only app data what was changed?
                    if (SkavaUtils.includesOnlyAppData(incomingChangesTables)){
                        final String alertTitle = "Skava app data tables were updated on web admin console";
                        final String textToShow = "There's new data currently not available in your device. When should I try to sync those ? ";
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
                                            try {
                                                SyncTask.Domain[] syncTarget = new SyncTask.Domain[]{SyncTask.Domain.ALL_APP_DATA_TABLES};
                                                ImportAppDataModelTask dynamicDataTask = new ImportAppDataModelTask(getSkavaContext(), SkavaFragmentActivity.this);
                                                dynamicDataTask.execute(syncTarget);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                BugSenseHandler.sendException(e);
                                                Log.e(SkavaConstants.LOG, e.getMessage());
                                            }
                                        } else {
                                            //show internet required message
                                            String alertTitle = "Currently you have no Internet connection";
                                            builder.setTitle(alertTitle);
                                            builder.setMessage("In order to download and import the Skava App data, an Internet connection is required");
                                            preventExecution = true;
                                        }
                                    }
                                });
                                builder.setNegativeButton("Postpone to the next app start", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((SkavaApplication) getApplication()).setNeedImportAppData(true);
                                    }
                                });
//                                builder.setCancelable(true);
                                // Create the AlertDialog object and return it
                                return builder.create();
                            }
                        };
                        // Showing Alert Message
                        theDialog.show(getSupportFragmentManager(), "assertAppDataDialog");
                    }
                }

                for (String tablename : incomingChangesTables) {
                    if (tablename.equalsIgnoreCase(":info")){
                        //skip as this seems to be metadata from Dropbox
                        continue;
                    }
                    if (SkavaUtils.isPartOfAppOrUserData(tablename)){
                        //skip as this was aready handled
                        continue;
                    }
                    if (tablename.equals(DataSyncDropboxTable.DATA_SYNC_TABLE)) {
                        //Basically check if this correspond to a local assessment on this tablet
                        //If so update the local assessment record with the correspondent sent status
                        //Don't forget to update the middleman inbox and/or the Assessment log traces

                        //find what is being aknowledged and remove from the middleman box space
                        Set<DbxRecord> dbxRecords = incomingChanges.get(DataSyncDropboxTable.DATA_SYNC_TABLE);
                        for (DbxRecord dbxRecord : dbxRecords) {
                            String acknowledgedAssessmentCode = dbxRecord.getString("assesmentCode");
                            String acknowledgedRecordID = dbxRecord.getString("dropboxId");
                            //find out if the record being acknowledged exists on the sync queue of this tablet
                            boolean exists = syncLoggingDAO.existsOnSyncTraces(acknowledgedAssessmentCode);
                            if (exists){
                                //update the sync trace from QUEDED to SERVED in the AssessmentSyncTrace table
                                AssessmentSyncTrace assessmentSyncTrace = null;
                                String environment = getTargetEnvironment();
                                try {
                                    assessmentSyncTrace = syncLoggingDAO.getAssessmentSyncTrace(environment, acknowledgedAssessmentCode, DataToSync.Operation.INSERT);
                                    List<RecordToSync> tracedRecords = assessmentSyncTrace.getRecords();
                                    for (RecordToSync tracedRecord : tracedRecords) {
                                        if (tracedRecord.getRecordID().equalsIgnoreCase(acknowledgedRecordID)) {
                                            tracedRecord.setStatus(DataToSync.Status.SERVED);
                                        }
                                    }
                                    syncLoggingDAO.updateAssessmentSyncTrace(assessmentSyncTrace);
                                } catch (DAOException e) {
                                    e.printStackTrace();
                                    Log.e(SkavaConstants.LOG, e.getMessage());
                                }

                                //find what is the record being acknowledged and remove it from the middleman box space
                                SyncQueue middlemanInbox = getSkavaContext().getMiddlemanInbox();
                                List<RecordToSync> pendingRecords = middlemanInbox.getRecords(acknowledgedAssessmentCode);
                                if (pendingRecords != null) {
                                    //iterate over all the data records
                                    //use a array copy for loop to avoid iterator and remove conflict
                                    List<Integer> foundIndexes = new ArrayList<Integer>();
                                    for (int i = 0; i < pendingRecords.size(); i++) {
                                        RecordToSync recordToSync = pendingRecords.get(i);
                                        if (recordToSync.getOperation().equals(DataToSync.Operation.INSERT)){
                                            String recordID = recordToSync.getRecordID();
                                            if (recordID.equalsIgnoreCase(acknowledgedRecordID)) {
                                                foundIndexes.add(i);
                                            }
                                        }
                                    }
                                    //Removes from the list of pending those that was reported by ack table
                                    if (foundIndexes != null && !foundIndexes.isEmpty()) {
                                        for (Integer foundIndex : foundIndexes) {
                                            pendingRecords.remove(foundIndex.intValue());
                                        }
                                    }
                                    if (pendingRecords.isEmpty()) {
                                        try {
                                            LocalAssessmentDAO assessmentDAO = getDAOFactory().getLocalAssessmentDAO();
                                            Assessment uploadedAssessment = assessmentDAO.getAssessment(acknowledgedAssessmentCode);
                                            uploadedAssessment.setDataSentStatus(Assessment.DATA_SENT_TO_CLOUD);
                                            assessmentDAO.updateAssessment(uploadedAssessment, false);

                                            //mostrar que termino exitosamente
                                            notifyUploadSucceed(0, R.drawable.cloud_striped, "Skava Mobile", "Mapping data for " + uploadedAssessment.getPseudoCode() + " was uploaded");

                                        } catch (DAOException e) {
                                            e.printStackTrace();
                                            Log.e(SkavaConstants.LOG, e.getMessage());
                                        }
                                    }
                                }
                            } else {
                                // Just do nothing. Probably comes from another tablet
                            }

                        }
                    }
                    if (tablename.equals(FilesSyncDropboxTable.FILE_SYNC_TABLE)) {
                        //Basically check if this correspond to a local assessment on this tablet
                        // If so find the particular file (picture) currently informed and
                        // update the local the middleman inbox and/or the Assessment log traces with the correspondent sent status
                        // If the full set of pictures have been informed then update the local assessment
                        // with the correspondent update status
                        String acknowledgedAssessmentCode = null;
                        String acknowledgedFileName = null;
                        Set<DbxRecord> dbxRecords = incomingChanges.get(FilesSyncDropboxTable.FILE_SYNC_TABLE);
                        for (DbxRecord dbxRecord : dbxRecords) {
                            acknowledgedAssessmentCode = dbxRecord.getString("assessmentCode");
                            acknowledgedFileName = dbxRecord.getString("fileName");
                            //find out if the record being acknowledged exists on the sync queue of this tablet
                            boolean exists = syncLoggingDAO.existsOnSyncTraces(acknowledgedAssessmentCode);
                            if (exists){
                                //update the sync trace from QUEDED to SERVED in the AssessmentSyncTrace table
                                AssessmentSyncTrace assessmentSyncTrace = null;
                                String environment = getTargetEnvironment();
                                try {
                                    assessmentSyncTrace = syncLoggingDAO.getAssessmentSyncTrace(environment, acknowledgedAssessmentCode, DataToSync.Operation.INSERT);
                                    List<FileToSync> tracedFiles = assessmentSyncTrace.getFiles();
                                    for (FileToSync tracedFile : tracedFiles) {
                                        if (tracedFile.getFileName().equalsIgnoreCase(acknowledgedFileName)) {
                                            tracedFile.setStatus(DataToSync.Status.SERVED);
                                        }
                                    }
                                    syncLoggingDAO.updateAssessmentSyncTrace(assessmentSyncTrace);
                                } catch (DAOException e) {
                                    e.printStackTrace();
                                    Log.e(SkavaConstants.LOG, e.getMessage());
                                }

                                //find what is the file being acknowledged and remove it from the middleman box space
                                SyncQueue middlemanInbox = getSkavaContext().getMiddlemanInbox();
                                List<FileToSync> pendingPictures = middlemanInbox.getFiles(acknowledgedAssessmentCode);
                                if (pendingPictures != null) {
                                    //iterate over all the images
                                    List<Integer> foundIndexes = new ArrayList<Integer>();
                                    //use a for loop to avoid iterator and remove conflict
                                    for (int i = 0; i < pendingPictures.size(); i++) {
                                        FileToSync pictureFileToSync = pendingPictures.get(i);
                                        if(pictureFileToSync.getOperation().equals(DataToSync.Operation.INSERT)){
                                            String fileName = pictureFileToSync.getFileName();
                                            if (fileName.equalsIgnoreCase(acknowledgedFileName)) {
                                                foundIndexes.add(i);
                                            }
                                        }
                                    }
                                    //Removes from the list of pending those that was reported by ack table
                                    if (foundIndexes != null && !foundIndexes.isEmpty()) {
                                        for (Integer foundIndex : foundIndexes) {
                                            pendingPictures.remove(foundIndex.intValue());
                                        }
                                    }
                                    if (pendingPictures.isEmpty()) {
                                        try {
                                            LocalAssessmentDAO assessmentDAO = getDAOFactory().getLocalAssessmentDAO();
                                            Assessment uploadedAssessment = assessmentDAO.getAssessment(acknowledgedAssessmentCode);
                                            uploadedAssessment.setPicsSentStatus(Assessment.PICS_SENT_TO_CLOUD);
                                            assessmentDAO.updateAssessment(uploadedAssessment, false);
                                            //mostrar que termino exitosamente
                                            notifyUploadSucceed(0, R.drawable.cloud_checked, "Skava Uploader", "Pictures for " + uploadedAssessment.getPseudoCode() + " were uploaded");
                                        } catch (DAOException e) {
                                            e.printStackTrace();
                                            Log.e(SkavaConstants.LOG, e.getMessage());
                                        }
                                    }
                                }
                            } else {
                                // Just do nothing. Probably comes from another tablet
                            }
                        }
                    }
                    //CURIOUS about:: Does assessment table records also appears here???
                    if (tablename.equals(AssessmentDropboxTable.ASSESSMENT_TABLE)) {
                        String listenerReportedAssessmentCode = null;
                        Set<DbxRecord> dbxRecords = incomingChanges.get(AssessmentDropboxTable.ASSESSMENT_TABLE);
                        for (DbxRecord dbxRecord : dbxRecords) {
                            if (dbxRecord.isDeleted()){
                                //
                                listenerReportedAssessmentCode = dbxRecord.getString("code");
                                //find out if the record being acknowledged exists on the sync queue of this tablet
                                boolean exists = syncLoggingDAO.existsOnSyncTraces(listenerReportedAssessmentCode);
                                if (exists){
                                    //update the sync trace from QUEDED to SERVED in the AssessmentSyncTrace table
                                    AssessmentSyncTrace assessmentSyncTrace = null;
                                    String environment = getTargetEnvironment();
                                    try {
                                        assessmentSyncTrace = syncLoggingDAO.getAssessmentSyncTrace(environment, listenerReportedAssessmentCode, DataToSync.Operation.DELETE);
                                        List<RecordToSync> tracedRecords = assessmentSyncTrace.getRecords();
                                        for (RecordToSync tracedRecord : tracedRecords) {
                                            if (tracedRecord.getRecordID().equalsIgnoreCase(listenerReportedAssessmentCode)) {
                                                tracedRecord.setStatus(DataToSync.Status.SERVED);
                                            }
                                        }
                                        syncLoggingDAO.updateAssessmentSyncTrace(assessmentSyncTrace);
                                    } catch (DAOException e) {
                                        e.printStackTrace();
                                        Log.e(SkavaConstants.LOG, e.getMessage());
                                    }

                                    //find what is the record being acknowledged and remove it from the middleman box space
                                    SyncQueue middlemanInbox = getSkavaContext().getMiddlemanInbox();
                                    List<RecordToSync> pendingRecords = middlemanInbox.getRecords(listenerReportedAssessmentCode);
                                    if (pendingRecords != null) {
                                        //iterate over all the data records
                                        //use a array copy for loop to avoid iterator and remove conflict
                                        List<Integer> foundIndexes = new ArrayList<Integer>();
                                        for (int i = 0; i < pendingRecords.size(); i++) {
                                            RecordToSync recordToSync = pendingRecords.get(i);
                                            if (recordToSync.getOperation().equals(DataToSync.Operation.DELETE)){
                                                String recordID = recordToSync.getRecordID();
                                                if (recordID.equalsIgnoreCase(listenerReportedAssessmentCode)) {
                                                    foundIndexes.add(i);
                                                }
                                            }
                                        }
                                        //Removes from the list of pending those that was reported by ack table
                                        if (foundIndexes != null && !foundIndexes.isEmpty()) {
                                            for (Integer foundIndex : foundIndexes) {
                                                pendingRecords.remove(foundIndex.intValue());
                                            }
                                        }
                                    }
                                } else {
                                    // Just do nothing. Probably comes from another tablet
                                }

                            }

                        }

                    }
                }
            } catch (DbxException e) {
                BugSenseHandler.sendException(e);
                Log.e(SkavaConstants.LOG, e.getMessage());
                e.printStackTrace();
            } catch (DAOException e) {
                BugSenseHandler.sendException(e);
                Log.e(SkavaConstants.LOG, e.getMessage());
                e.printStackTrace();
            }
        }

        if (datastoreStatus.hasOutgoing) {
            try {
//                //Looks like the map returned works from remote->local and no the other way around,
//                //so it's not very useful as it always be empty here
                Map<String, Set<DbxRecord>> outgoingChanges = store.sync();
//                //As there's no way to know exactly what are the assessment marked to sync, update all of them
                //as sent to datastore, that is, delivered to middleman
                Map<String, List<RecordToSync>> assessmentsDataToSync = getSkavaContext().getMiddlemanInbox().getAllRecords();
//                //iterate over the set of ASSESSMENTS domain
                for (String assessmentIterQueue : assessmentsDataToSync.keySet()) {
                    List<RecordToSync> recordList = assessmentsDataToSync.get(assessmentIterQueue);
                    for (RecordToSync assessmentRecordsToSync : recordList) {
                        if (assessmentRecordsToSync.getOperation().equals(DataToSync.Operation.INSERT)) {
                            try {
                                LocalAssessmentDAO assessmentDAO = getDAOFactory().getLocalAssessmentDAO();
                                Assessment uploadedAssessment = null;
                                try {
                                    uploadedAssessment = assessmentDAO.getAssessment(assessmentIterQueue);
                                    uploadedAssessment.setDataSentStatus(Assessment.DATA_SENT_TO_DATASTORE);
                                    assessmentDAO.updateAssessment(uploadedAssessment, false);
                                } catch (DAOException e) {
                                    //Not found assessment with that code
                                    //think for instance that local assessments where deleted while syncing was not yet completed
                                    //so just continue with next one
                                    continue;
                                }
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

    public void notifyUploadSucceed(int idNotification, int icon, String title, String text) {
        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(icon).setContentTitle(title).setContentText(text);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        Notification notification = mBuilder.build();
        mNotifyMgr.notify(idNotification, notification);
    }


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
                                ImportAppDataModelTask fixedDataTask = new ImportAppDataModelTask(getSkavaContext(), SkavaFragmentActivity.this);
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
                } else {
                    //no call to assert user data, so no import user data
                    enoughDataAvailable = true;
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
                                ImportUserDataModelTask dynamicDataTask = new ImportUserDataModelTask(getSkavaContext(), SkavaFragmentActivity.this);
                                dynamicDataTask.execute(SyncTask.Domain.ALL_USER_DATA_TABLES);
                            } else {
                                //show internet required message
                                String alertTitle = "Currently you have no Internet connection";
                                builder.setTitle(alertTitle);
                                builder.setMessage("In order to download and import the Skava users data, an Internet connection is required");
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
                enoughDataAvailable = true;
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
