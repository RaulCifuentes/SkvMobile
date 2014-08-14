package com.metric.skava.uploader.data.dao.impl.sqllite;

import android.content.Context;
import android.util.Log;

import com.metric.skava.uploader.app.util.SkavaUploaderConstants;
import com.metric.skava.uploader.app.util.SkavaUploaderFileUtils;

import java.io.File;

public class SkavaUploaderDatabase {

    private static SkavaUploaderDatabase sInstance;

    // Database open/upgrade helper
    private SkavaUploaderDBHelper skavaDBHelper;

    public static SkavaUploaderDatabase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SkavaUploaderDatabase(context);
        }
        return sInstance;
    }


    private SkavaUploaderDatabase(Context context) {
        SkavaUploaderFileUtils skavaFilesUtils = new SkavaUploaderFileUtils(context);
        File databaseStorageDir = new File(skavaFilesUtils.getSkavaDocumentsBaseFolder(), SkavaUploaderConstants.DATABASE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (! databaseStorageDir.exists()){
            if (! databaseStorageDir.mkdirs()){
                Log.e(SkavaUploaderConstants.LOG, "Failed to create directory : " + SkavaUploaderConstants.DATABASE_DIRECTORY_NAME);
            }
        }
        File dbFile = new File(databaseStorageDir.getPath() + File.separator + SkavaUploaderDBHelper.DATABASE_NAME);

        skavaDBHelper = new SkavaUploaderDBHelper(context, dbFile.getPath(), null,
                SkavaUploaderDBHelper.DATABASE_VERSION);
    }


    public SkavaUploaderDBHelper getSkavaDBHelper() {
        return skavaDBHelper;
    }

    // Called when you no longer need access to the database.
    public void closeDatabase() {
        skavaDBHelper.close();
    }


}