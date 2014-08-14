package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.util.Log;

import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.pictures.util.SkavaFilesUtils;

import java.io.File;

public class SkavaDatabase {

    private static SkavaDatabase sInstance;

    // Database open/upgrade helper
    private SkavaDBHelper skavaDBHelper;

    public static SkavaDatabase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SkavaDatabase(context);
        }
        return sInstance;
    }


    private SkavaDatabase(Context context) {
        SkavaFilesUtils skavaFilesUtils = new SkavaFilesUtils(context);
        File databaseStorageDir = new File(skavaFilesUtils.getSkavaDocumentsBaseFolder(), SkavaConstants.DATABASE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (! databaseStorageDir.exists()){
            if (! databaseStorageDir.mkdirs()){
                Log.e(SkavaConstants.LOG, "Failed to create directory : " + SkavaConstants.DATABASE_DIRECTORY_NAME);
            }
        }
        File dbFile = new File(databaseStorageDir.getPath() + File.separator + SkavaDBHelper.DATABASE_NAME);

        skavaDBHelper = new SkavaDBHelper(context, dbFile.getPath(), null,
                SkavaDBHelper.DATABASE_VERSION);
    }


    public SkavaDBHelper getSkavaDBHelper() {
        return skavaDBHelper;
    }

    // Called when you no longer need access to the database.
    public void closeDatabase() {
        skavaDBHelper.close();
    }


}