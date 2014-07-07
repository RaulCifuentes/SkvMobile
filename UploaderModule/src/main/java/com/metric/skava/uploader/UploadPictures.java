/*
 * Copyright (c) 2011 Dropbox, Inc.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */


package com.metric.skava.uploader;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxFileSizeException;
import com.dropbox.client2.exception.DropboxIOException;
import com.dropbox.client2.exception.DropboxParseException;
import com.dropbox.client2.exception.DropboxPartialFileException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.metric.skava.uploader.app.SkavaUploaderConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This is not fully reviewed. This is only a scafold if an AsyncTask is required
 */
public class UploadPictures extends AsyncTask<Void, Long, Boolean> {

    private String mAssessmentCode;
    private DropboxAPI<?> mApi;
    private String mRemoteFolderPath;
    private ArrayList<File> mFiles;

    private Context mContext;

    private String mErrorMsg;
    private DropboxAPI.Entry mEntry;


    public UploadPictures(Context context, DropboxAPI<?> api, String assessmentCode, String dropboxPath,
                          ArrayList<File> fileList) {
        // We set the context this way so we don't accidentally leak activities
        mContext = context.getApplicationContext();
        if (api.getSession().isLinked()) {
            mApi = api;
            mAssessmentCode = assessmentCode;
            mRemoteFolderPath = dropboxPath;
            mFiles = fileList;
        } else {
            Log.d(SkavaUploaderConstants.LOG, "Heey Dropbox is not linked");
        }

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean success = null;
        try {
            // By creating a request, we get a handle to the putFile operation,
            // so we can cancel it later if we want to
            for (File mFile : mFiles) {
                String remoteFilePath = mRemoteFolderPath + "/" + mFile.getName();
                long fileLength = mFile.length();
                Log.d(SkavaUploaderConstants.LOG, "Uploading " + remoteFilePath + " which size is " + fileLength);
                FileInputStream fis = new FileInputStream(mFile);
                if (mApi.getSession().isLinked()) {
                    mEntry = mApi.putFileOverwrite(remoteFilePath, fis, fileLength, null);
                    success = true;
                } else {
                    Log.d(SkavaUploaderConstants.LOG, "mApi.getSession().isLinked() is FALSE. Soo its disconnected");
                    success =  false;
                }
            }
            return success;

        } catch (DropboxUnlinkedException e) {
            // This session wasn't authenticated properly or user unlinked
            mErrorMsg = "This app wasn't authenticated properly.";
            e.printStackTrace();
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
        } catch (DropboxFileSizeException e) {
            // File size too big to upload via the API
            mErrorMsg = "This file is too big to upload";
            e.printStackTrace();
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
        } catch (DropboxPartialFileException e) {
            // We canceled the operation
            mErrorMsg = "Upload canceled";
            e.printStackTrace();
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
        } catch (DropboxServerException e) {
            e.printStackTrace();
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
            // Server-side exception.  These are examples of what could happen,
            // but we don't do anything special with them here.
            if (e.error == DropboxServerException._401_UNAUTHORIZED) {
                // Unauthorized, so we should unlink them.  You may want to
                // automatically log the user out in this case.
            } else if (e.error == DropboxServerException._403_FORBIDDEN) {
                // Not allowed to access this
            } else if (e.error == DropboxServerException._404_NOT_FOUND) {
                // path not found (or if it was the thumbnail, can't be
                // thumbnailed)
            } else if (e.error == DropboxServerException._507_INSUFFICIENT_STORAGE) {
                // user is over quota
            } else {
                // Something else
                e.printStackTrace();
                Log.e(SkavaUploaderConstants.LOG, e.getMessage());
                BugSenseHandler.sendException(e);
            }
            // This gets the Dropbox error, translated into the user's language
            mErrorMsg = e.body.userError;
            if (mErrorMsg == null) {
                mErrorMsg = e.body.error;
                e.printStackTrace();
                Log.e(SkavaUploaderConstants.LOG, e.getMessage());
                BugSenseHandler.sendException(e);
            }
        } catch (DropboxIOException e) {
            // Happens all the time, probably want to retry automatically.
            mErrorMsg = "Network error.  Try again.";
            e.printStackTrace();
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
        } catch (DropboxParseException e) {
            e.printStackTrace();
            // Probably due to Dropbox server restarting, should retry
            mErrorMsg = "Dropbox error.  Try again.";
            e.printStackTrace();
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
        } catch (DropboxException e) {
            e.printStackTrace();
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
            mErrorMsg = "Unknown error.  Try again.";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Log.e(SkavaUploaderConstants.LOG, "onPostExecute RESULT OK");
            //Notify success
            //Update the assessment SNT TO CLOUD
            NotificationCompat.Builder mBuilder;
            mBuilder = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(R.drawable.cloud_icon)
                    .setContentTitle("Skava Mobile notifies")
                    .setContentText("Picture uploading succeed !!");
            int mNotificationId = 010;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        } else {
            Log.e(SkavaUploaderConstants.LOG, "onPostExecute RESULT NOT NOT NOT OK --- FAIL");
            Log.e(SkavaUploaderConstants.LOG, mErrorMsg);
            //Notify failure
            NotificationCompat.Builder mBuilder;
            mBuilder = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(R.drawable.cloud_icon)
                    .setContentTitle("Skava Mobile notifies")
                    .setContentText("Picture uploading failed :( ");
            // Sets an ID for the notification
            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }
    }


}
