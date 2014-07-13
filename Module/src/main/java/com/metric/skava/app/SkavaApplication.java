package com.metric.skava.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.metric.skava.R;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.settings.fragment.SettingsMainFragment;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncQueue;
import com.metric.skava.sync.model.SyncStatus;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SkavaApplication extends MetricApplication {

    private SkavaContext mSkavaContext;
    private SharedPreferences mSharedPreferences;

    boolean wantUnlinkDropboxAccount;
    boolean needImportAppData;
    boolean needImportUserData;

    public SkavaContext getSkavaContext() {
        return mSkavaContext;
    }

    public boolean isUnlinkPrefered() {
        Boolean unlinkDropboxOnLogout  = mSharedPreferences.getBoolean(SettingsMainFragment.UNLINK_DROPBOX_PREFERENCE, false);
        return wantUnlinkDropboxAccount || unlinkDropboxOnLogout;
    }

    public void setWantUnlinkDropboxAccount(boolean wantUnlinkDropboxAccount) {
        this.wantUnlinkDropboxAccount = wantUnlinkDropboxAccount;
    }

    public void setNeedImportAppData(boolean wantImportAppData) {
        this.needImportAppData = wantImportAppData;
    }

    public void setNeedImportUserData(boolean wantImportUserData) {
        this.needImportUserData = wantImportUserData;
    }

    public boolean isImportAppDataNeeded() {
        return needImportAppData ;
    }

    public boolean isImportUserDataNeeded() {
        return needImportUserData;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateAsString = sdf.format(SkavaUtils.getCurrentDate());
        Log.d(SkavaConstants.LOG, "********** SkavaApplication ::: onCreate ***** " + dateAsString);

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, false);
        mSharedPreferences = getSharedPreferences(getString(R.string.settings_preference_file_key), Context.MODE_PRIVATE);
        //The context is rebuild on each app restart so
        //use a persistence bucket (android preferences file) to save/restore
        // the state of the context, in particular thinshs like the targetEnvironment,
        // the lastImportExecution info, etc
        mSkavaContext = new SkavaContext();

        String targetEnvironment  = mSharedPreferences.getString(SettingsMainFragment.TARGET_ENVIRONMENT_PREFERENCE, "");
        mSkavaContext.setTargetEnvironment(targetEnvironment);

        SharedPreferences persistenceBucket = this.getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);

        boolean lastUserDataSyncStatus = persistenceBucket.getBoolean(getString(R.string.user_data_last_sync_succeed_key), false);
        Long lastUserDataSyncDate = persistenceBucket.getLong(getString(R.string.user_data_last_sync_date_key), 0);

        SyncStatus userDataSyncStatus = new SyncStatus();
        userDataSyncStatus.setSuccess(lastUserDataSyncStatus);
        userDataSyncStatus.setLastExecution(new Date(lastUserDataSyncDate));
        mSkavaContext.setUserDataSyncMetadata(userDataSyncStatus);

        boolean lastAppDataSyncStatus = persistenceBucket.getBoolean(getString(R.string.app_data_last_sync_succeed_key), false);
        Long lastAppDataSyncDate = persistenceBucket.getLong(getString(R.string.app_data_last_sync_date_key), 0);

        SyncStatus appDataSyncStatus = new SyncStatus();
        appDataSyncStatus.setSuccess(lastAppDataSyncStatus);
        appDataSyncStatus.setLastExecution(new Date(lastAppDataSyncDate));
        mSkavaContext.setAppDataSyncMetadata(appDataSyncStatus);

        boolean reimportAppDate = persistenceBucket.getBoolean(getString(R.string.needs_reimport_app_data_key), false);
        this.setNeedImportAppData(reimportAppDate);
        boolean reimportUserDate = persistenceBucket.getBoolean(getString(R.string.needs_reimport_user_data_key), false);
        this.setNeedImportUserData(reimportUserDate);

        DAOFactory daoFactory = DAOFactory.getInstance(this, mSkavaContext);
        mSkavaContext.setDAOFactory(daoFactory);

        SyncHelper syncHelper = SyncHelper.getInstance(mSkavaContext);
        mSkavaContext.setSyncHelper(syncHelper);

        //The SyncQueue should be saved/restored from somewhere, so use the table of AssesmentSyncTraces ..
        try {
            SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
            SyncQueue restoredSyncQueue = syncLoggingDAO.getSyncQueue();
            mSkavaContext.setMiddlemanInbox(restoredSyncQueue);
        } catch (DAOException e) {
            e.printStackTrace();
            Log.d(SkavaConstants.LOG, e.getMessage());
        }
    }


    public void saveState(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.needs_reimport_app_data_key), isImportAppDataNeeded());
        editor.putBoolean(getString(R.string.needs_reimport_user_data_key), isImportUserDataNeeded());
        editor.commit();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
