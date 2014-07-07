package com.metric.skava.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.metric.skava.R;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.settings.fragment.SettingsMainFragment;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncQueue;
import com.metric.skava.sync.model.SyncStatus;

import java.util.Date;


public class SkavaApplication extends MetricApplication {

    private SkavaContext mSkavaContext;
    SharedPreferences mSharedPreferences;

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

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, false);
        mSharedPreferences = getSharedPreferences(getString(R.string.settings_preference_file_key), Context.MODE_PRIVATE);

        mSkavaContext = new SkavaContext();

        DAOFactory daoFactory = DAOFactory.getInstance(this, mSkavaContext);
        mSkavaContext.setDAOFactory(daoFactory);

        SyncHelper syncHelper = SyncHelper.getInstance(mSkavaContext);
        mSkavaContext.setSyncHelper(syncHelper);

        SharedPreferences persistenceBucket = this.getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);

        boolean lastUserDataSyncStatus = persistenceBucket.getBoolean(getString(R.string.user_data_last_sync_succeed), false);
        Long lastUserDataSyncDate = persistenceBucket.getLong(getString(R.string.user_data_last_sync_date), 0);

        SyncStatus userDataSyncStatus = new SyncStatus();
        userDataSyncStatus.setSuccess(lastUserDataSyncStatus);
        userDataSyncStatus.setLastExecution(new Date(lastUserDataSyncDate));
        mSkavaContext.setUserDataSyncMetadata(userDataSyncStatus);

        boolean lastAppDataSyncStatus = persistenceBucket.getBoolean(getString(R.string.app_data_last_sync_succeed), false);
        Long lastAppDataSyncDate = persistenceBucket.getLong(getString(R.string.app_data_last_sync_date), 0);

        SyncStatus appDataSyncStatus = new SyncStatus();
        appDataSyncStatus.setSuccess(lastAppDataSyncStatus);
        appDataSyncStatus.setLastExecution(new Date(lastAppDataSyncDate));
        mSkavaContext.setAppDataSyncMetadata(appDataSyncStatus);

        String targetEnvironment  = mSharedPreferences.getString(SettingsMainFragment.TARGET_ENVIRONMENT_PREFERENCE, "");
        mSkavaContext.setTargetEnvironment(targetEnvironment);


        //The SyncQueue should be saved/restored from a table so..
        try {
            SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
            SyncQueue restoredSyncQueue = syncLoggingDAO.getSyncQueue();
            mSkavaContext.setMiddlemanInbox(restoredSyncQueue);
        } catch (DAOException e) {
            e.printStackTrace();
            Log.d(SkavaConstants.LOG, e.getMessage());
        }


    }



    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
