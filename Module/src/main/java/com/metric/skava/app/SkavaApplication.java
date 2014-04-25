package com.metric.skava.app;

import android.util.Log;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.exception.SkavaExceptionHandler;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.data.MappedIndexDataProvider;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncStatus;


public class SkavaApplication extends MetricApplication {


    private int customThemeId;
    private boolean requiresRestart;

    private SkavaContext mSkavaContext;

//    private SkavaDataProvider mSkavaDataProvider;
    private MappedIndexDataProvider mMappedIndexDataProvider;

    public boolean isRequiresRestart() {
        return requiresRestart;
    }

    public void setRequiresRestart(boolean requiresRestart) {
        this.requiresRestart = requiresRestart;
    }


    public SkavaContext getSkavaContext() {
        return mSkavaContext;
    }

//    public SkavaDataProvider getSkavaDataProvider() {
//        return mSkavaDataProvider;
//    }

    public MappedIndexDataProvider getMappedIndexDataProvider() {
        return mMappedIndexDataProvider;
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

        DAOFactory daoFactory = DAOFactory.getInstance(this);
        SyncLoggingDAO syncLoggingDAO = null;
        try {
            syncLoggingDAO = daoFactory.getSyncLoggingDAO();
            SyncLogEntry globalLastSuccess = syncLoggingDAO.getLastSyncByState(SyncLogEntry.Domain.GLOBAL, SyncLogEntry.Status.SUCCESS);
            SyncLogEntry nonSpecificLastSuccess = syncLoggingDAO.getLastSyncByState(SyncLogEntry.Domain.NON_USER_SPECIFIC, SyncLogEntry.Status.SUCCESS);
            SyncStatus syncStatus = new SyncStatus();
            syncStatus.setGlobal(globalLastSuccess);
            syncStatus.setNonUserSpecific(nonSpecificLastSuccess);
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
//        Thread.setDefaultUncaughtExceptionHandler(handler);

    }
}
