package com.metric.skava.uploader.data.dao.impl.sqllite;

/**
 * Created by metricboy on 8/7/14.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.metric.skava.uploader.app.util.SkavaUploaderConstants;
import com.metric.skava.uploader.data.dao.impl.sqllite.table.SkavaUploaderSyncTraceFilesTable;

/**
 * Listing 8-2: Implementing an SQLite Open Helper
 */
public class SkavaUploaderDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mySkavaUploaderDatabase.db";
    public static final int DATABASE_VERSION = 2;

    public SkavaUploaderDBHelper(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(SkavaUploaderConstants.LOG, "Database mySkavaUploaderDatabase created succesfully ");
    }


    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(SkavaUploaderConstants.LOG, "onCreate :: ");
        db.execSQL(SkavaUploaderSyncTraceFilesTable.CREATE_SYNC_TRACE_FILES_TABLE);
    }


    // Called when there is a database version mismatch meaning that
    // the version of the database on disk needs to be upgraded to
    // the current version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log the version upgrade.
        Log.d(SkavaUploaderConstants.LOG, "Upgrading from version " +
                oldVersion + " to " +
                newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + SkavaUploaderSyncTraceFilesTable.SYNC_TRACE_FILES_TABLE);

        // Create a new one.
        onCreate(db);

    }



}