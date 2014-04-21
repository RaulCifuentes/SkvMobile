package com.metric.skava.app;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.data.SkavaDataProvider;
import com.metric.skava.app.exception.SkavaExceptionHandler;
import com.metric.skava.calculator.data.MappedIndexDataProvider;
import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;


public class SkavaApplication extends MetricApplication {


    private int customThemeId;
    private boolean requiresRestart;

    private SkavaContext mSkavaContext;

    private SkavaDataProvider mSkavaDataProvider;
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

    public SkavaDataProvider getSkavaDataProvider() {
        return mSkavaDataProvider;
    }

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
        /// TODO: Ver que se le pasa como segundo parámetro a este setter. Definir tb que SkavaContext tenga valores por defecto
        mSkavaContext.setDatastoreHelper(DatastoreHelper.getInstance(this, null));
        mSkavaDataProvider = SkavaDataProvider.getInstance(this);
        mMappedIndexDataProvider = MappedIndexDataProvider.getInstance(this);

        Thread.getDefaultUncaughtExceptionHandler();
        SkavaExceptionHandler handler = new SkavaExceptionHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(handler);

    }
}
