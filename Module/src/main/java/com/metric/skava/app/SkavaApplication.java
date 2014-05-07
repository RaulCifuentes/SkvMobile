package com.metric.skava.app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.exception.SkavaExceptionHandler;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.settings.fragment.SettingsMainFragment;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncStatus;


public class SkavaApplication extends MetricApplication {

    private int customThemeId;
    private boolean requiresRestart;
//    private boolean updateDatamodelAutomatically;
    private SkavaContext mSkavaContext;

    public boolean isUpdateDatamodelAutomatically() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean updateDatamodelAutomatically  = sharedPref.getBoolean(SettingsMainFragment.UPDATE_AUTO_PREFERENCE, false);
        return updateDatamodelAutomatically;
    }

//    public void setUpdateDatamodelAutomatically(boolean updateDatamodelAutomatically) {
//
//
//    }

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

        SyncLoggingDAO syncLoggingDAO = null;
        try {
            syncLoggingDAO = daoFactory.getSyncLoggingDAO();
            SyncLogEntry globalLastSuccess = syncLoggingDAO.getLastSyncByState(SyncLogEntry.Domain.GLOBAL, SyncLogEntry.Status.SUCCESS);
            SyncLogEntry userSpecificLastSuccess = syncLoggingDAO.getLastSyncByState(SyncLogEntry.Domain.USER_SPECIFIC, SyncLogEntry.Status.SUCCESS);
            SyncLogEntry nonSpecificLastSuccess = syncLoggingDAO.getLastSyncByState(SyncLogEntry.Domain.NON_USER_SPECIFIC, SyncLogEntry.Status.SUCCESS);
            SyncStatus syncStatus = new SyncStatus();
            syncStatus.setGlobal(globalLastSuccess);
            syncStatus.setNonUserSpecific(nonSpecificLastSuccess);
            syncStatus.setUserSpecific(userSpecificLastSuccess);
            mSkavaContext.setSyncMetadata(syncStatus);
        } catch (DAOException daoe){
            Log.d(SkavaConstants.LOG, "DAO Exception " + daoe.getMessage());
        }

        /// TODO: Ver que se le pasa como segundo parámetro a este setter. Definir tb que SkavaContext tenga valores por defecto
//        mSkavaContext.setDatastoreHelper(DatastoreHelper.getInstance(this,));
//        mSkavaDataProvider = SkavaDataProvider.getInstance(this);
//        mMappedIndexDataProvider = MappedIndexDataProvider.getInstance(this);

        Thread.getDefaultUncaughtExceptionHandler();
        SkavaExceptionHandler handler = new SkavaExceptionHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
