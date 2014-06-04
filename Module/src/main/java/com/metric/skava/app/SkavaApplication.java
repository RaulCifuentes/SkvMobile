package com.metric.skava.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.metric.skava.R;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.settings.fragment.SettingsMainFragment;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncStatus;

import java.util.Date;


public class SkavaApplication extends MetricApplication {

    private SkavaContext mSkavaContext;
    SharedPreferences mSharedPreferences;

    boolean wantUnlinkDropboxAccount;
    boolean wantImportAppData;
    boolean wantImportUserData;

    public SkavaContext getSkavaContext() {
        return mSkavaContext;
    }

    public boolean isReloadAppDataPrefered() {
        Boolean updateAppDataModelAutomatically   = mSharedPreferences.getBoolean(SettingsMainFragment.UPDATE_APP_DATA_AUTO_PREFERENCE, false);
        return wantImportAppData || updateAppDataModelAutomatically;
    }

    public boolean isReloadUserDataPrefered() {
        Boolean updateUserDataModelAutomatically = mSharedPreferences.getBoolean(SettingsMainFragment.UPDATE_USER_DATA_AUTO_PREFERENCE, false);
        return wantImportUserData || updateUserDataModelAutomatically;
    }

    public boolean isUnlinkPrefered() {
        Boolean unlinkDropboxOnLogout  = mSharedPreferences.getBoolean(SettingsMainFragment.UNLINK_DROPBOX_PREFERENCE, false);
        return wantUnlinkDropboxAccount || unlinkDropboxOnLogout;
    }

    public void setWantUnlinkDropboxAccount(boolean wantUnlinkDropboxAccount) {
        this.wantUnlinkDropboxAccount = wantUnlinkDropboxAccount;
    }

    public void setWantImportAppData(boolean wantImportAppData) {
        this.wantImportAppData = wantImportAppData;
    }

    public void setWantImportUserData(boolean wantImportUserData) {
        this.wantImportUserData = wantImportUserData;
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


    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
