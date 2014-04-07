package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastore;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 3/24/14.
 */
public class DropBoxBaseDAO {

    private SkavaContext skavaContext;
    protected Context mContext;

    public DropBoxBaseDAO(Context context, SkavaContext skavaContext) throws DAOException {
        this.mContext = context;
        this.skavaContext = skavaContext;
    }

    public SkavaContext getSkavaContext() {
        return skavaContext;
    }

    public void setSkavaContext(SkavaContext skavaContext) {
        this.skavaContext = skavaContext;
    }

    public DbxDatastore getDatastore() throws DAOException {
        return getSkavaContext().getDatastoreHelper().getDatastore();
    }
}