package com.metric.skava.uploader.data.dao;

import android.content.Context;

import com.metric.skava.uploader.data.dao.exception.SkavaUploaderDAOException;
import com.metric.skava.uploader.sync.dao.SkavaUploaderSyncLoggingDAO;
import com.metric.skava.uploader.sync.dao.SkavaUploaderSyncLoggingDAOsqlLiteImpl;

/**
 * Created by metricboy on 8/11/14.
 */
public class SkavaUploaderDAOFactory {

    private Context mContext;

    private static SkavaUploaderDAOFactory instance;

    public static SkavaUploaderDAOFactory getInstance(Context context) {
        if (instance == null) {
            instance = new SkavaUploaderDAOFactory(context);
        }
        return instance;
    }


    private SkavaUploaderDAOFactory(Context context) {
        this.mContext = context;
    }

    public SkavaUploaderSyncLoggingDAO getSyncLoggingDAO() throws SkavaUploaderDAOException {
        return new SkavaUploaderSyncLoggingDAOsqlLiteImpl(mContext);
    }


}
