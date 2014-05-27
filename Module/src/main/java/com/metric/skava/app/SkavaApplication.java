package com.metric.skava.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.metric.skava.R;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.exception.SkavaExceptionHandler;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.settings.fragment.SettingsMainFragment;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncStatus;

import java.util.Date;


public class SkavaApplication extends MetricApplication {

    private int customThemeId;
    private boolean requiresRestart;
    private SkavaContext mSkavaContext;

    public boolean isReloadPrefered() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean updateDatamodelAutomatically  = sharedPref.getBoolean(SettingsMainFragment.UPDATE_AUTO_PREFERENCE, false);
        return updateDatamodelAutomatically;
    }

    public boolean isUnlinkPrefered() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean unlinkDropboxOnLogout  = sharedPref.getBoolean(SettingsMainFragment.UNLINK_DROPBOX_PREFERENCE, false);
        return unlinkDropboxOnLogout;
    }



    public boolean isRequiresRestart() {
        return requiresRestart;
    }

    public void setRequiresRestart(boolean requiresRestart) {
        this.requiresRestart = requiresRestart;
    }

    public SkavaContext getSkavaContext() {
        return mSkavaContext;
    }

    public int getCustomThemeId() {
        return customThemeId;
    }

    public void setCustomThemeId(int customThemeId) {
        this.customThemeId = customThemeId;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mSkavaContext = new SkavaContext();
        DAOFactory daoFactory = DAOFactory.getInstance(this, mSkavaContext);
        mSkavaContext.setDAOFactory(daoFactory);
        SyncHelper syncHelper = SyncHelper.getInstance(mSkavaContext);
        mSkavaContext.setSyncHelper(syncHelper);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean syncSucceed = sharedPref.getBoolean(getString(R.string.last_sync_succeed), false);
        Long when = sharedPref.getLong(getString(R.string.last_sync_date), 0);

        SyncStatus syncStatus = new SyncStatus();
        syncStatus.setSuccess(syncSucceed);
        syncStatus.setLastExecution(new Date(when));

        mSkavaContext.setSyncMetadata(syncStatus);

        Thread.getDefaultUncaughtExceptionHandler();
        SkavaExceptionHandler handler = new SkavaExceptionHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);

    }



    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
