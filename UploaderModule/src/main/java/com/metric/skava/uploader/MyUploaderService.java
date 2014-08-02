package com.metric.skava.uploader;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxFileSizeException;
import com.dropbox.client2.exception.DropboxIOException;
import com.dropbox.client2.exception.DropboxParseException;
import com.dropbox.client2.exception.DropboxPartialFileException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.metric.skava.uploader.app.SkavaUploaderApplication;
import com.metric.skava.uploader.app.SkavaUploaderConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by metricboy on 6/25/14.
 */
public class MyUploaderService extends IntentService {

    private Context mContext;

    public MyUploaderService() {
        super(SkavaUploaderConstants.INTENT_SERVICE_TAG);
    }

    public MyUploaderService(String name) {
        super(name);
        mContext = getApplicationContext();
    }

    public SkavaUploaderApplication getSkavaUploaderApplication() {
        SkavaUploaderApplication application = (SkavaUploaderApplication) getApplication();
        return application;
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        DropboxAPI<AndroidAuthSession> dropboxAPI = getSkavaUploaderApplication().getDropboxAPI();

        if (dropboxAPI != null && intent != null) {

            String operation = intent.getStringExtra(SkavaUploaderConstants.EXTRA_OPERATION);
            String assessmentCode = intent.getStringExtra(SkavaUploaderConstants.EXTRA_ASSESSMENT_CODE);
            String folderName = intent.getStringExtra(SkavaUploaderConstants.EXTRA_ENVIRONMENT_NAME);
            String internalCode = intent.getStringExtra(SkavaUploaderConstants.EXTRA_INTERNAL_CODE);

            String skavaFolderPath = SkavaUploaderConstants.REMOTE_FOLDER_SEPARATOR + folderName;
            String projectPath = skavaFolderPath + SkavaUploaderConstants.REMOTE_FOLDER_SEPARATOR + internalCode;
            String assessmentPath = projectPath + SkavaUploaderConstants.REMOTE_FOLDER_SEPARATOR + assessmentCode;

            if (operation.equalsIgnoreCase(SkavaUploaderConstants.EXTRA_OPERATION_DELETE)) {
                deletePicturesForAssessment(dropboxAPI, assessmentCode, assessmentPath);
            }

            if (operation.equalsIgnoreCase(SkavaUploaderConstants.EXTRA_OPERATION_INSERT)){
                ArrayList<String> picturesURIs = intent.getStringArrayListExtra(SkavaUploaderConstants.EXTRA_PICTURES);
                ArrayList<File> fileList = new ArrayList<File>();
                if (picturesURIs != null) {
                    //Create the list of Files
                    for (String eachSkavaPictureURI : picturesURIs) {
                        if (eachSkavaPictureURI != null) {
                            URI uri = URI.create(eachSkavaPictureURI);
                            File file = new File(uri);
                            //File file = new File(eachSkavaPictureURI);
                            if (file.exists()) {
                                fileList.add(file);
                            }
                        }
                    }
                }
                uploadPicturesForAssessment(dropboxAPI, assessmentCode, assessmentPath, fileList);
                //************** Eventually UploadPictures will run as AsyncTask **********//
                //UploadPictures upload = new UploadPictures(this, dropboxAPI, assessmentCode, assessmentPath, fileList);
                //upload.execute();
                //************** UploadPictures is an AsyncTask **********//
            }
        }
    }


    public void deletePicturesForAssessment(DropboxAPI<?> api, String assessmentCode, String dropboxPath){
        try {
            String remoteFilePath = dropboxPath;
            Log.d(SkavaUploaderConstants.LOG, "Deleting pictures " + remoteFilePath + " for assessment " + assessmentCode);
            if (api.getSession().isLinked()) {
                api.delete(remoteFilePath);
                Log.d(SkavaUploaderConstants.LOG, "Delete seems to finish successfully");
            } else {
                Log.d(SkavaUploaderConstants.LOG, "Dropbox for uploader service is not linked");
                //Notify the user of this is a bad thing
                notifyDropboxUnlinked(0, "Skava Uploader", "Dropbox for uploader service is not linked");
            }
        } catch (DropboxException e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean uploadPicturesForAssessment(DropboxAPI<?> api, String assessmentCode, String dropboxPath, ArrayList<File> fileList){
        String mErrorMsg;
        boolean success = false;
        try {
            for (File mFile : fileList) {
                //First create a reduced (centered thumbnail) of each file to upload that small size picture first
                //original file name has a structure like this ASSESSMENT_CODE_TAG_DATE_TIME.jpg,
                //for instance: 052f4269-a273-4c1f-035ceb7afe62_FACE_2014_07_09_16_04_05.jpg
                String originalFileName = mFile.getName();

                if(originalFileName.contains("TUNNEL")){
                   // the extended view does not need a thumbnail version of the image
                   // so do nothing
                } else {
                    //The thumbnail will have the word THUMBNAIL appended to the end of the file name
                    String thumbnailFileName = originalFileName.replace(".jpg", "_THUMBNAIL.jpg");
                    File thumbnailFile = new File(mFile.getParent(), thumbnailFileName);
                    // Set the name of the file in the remote Dropbox folder
                    String thumbnailRemoteFilePath = dropboxPath + SkavaUploaderConstants.REMOTE_FOLDER_SEPARATOR + thumbnailFileName;
                    // Begin create a thumbnail
                    Bitmap thumbnailImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mFile.getPath()), SkavaUploaderConstants.THUMBSIZE_WIDTH, SkavaUploaderConstants.THUMBSIZE_HEIGHT);
                    FileOutputStream fos = null;
                    try {
                        //create a file from the bitmap
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        thumbnailImage.compress(Bitmap.CompressFormat.PNG, 90 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();
                        //write the bytes in file
                        fos = new FileOutputStream(thumbnailFile);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(SkavaUploaderConstants.LOG, e.getMessage());
                        BugSenseHandler.sendException(e);
                    }
                    //Put a file on the Dropbox repo
                    long fileLength = thumbnailFile.length();
                    Log.d(SkavaUploaderConstants.LOG, "Uploading " + thumbnailRemoteFilePath + " which size is " + fileLength);
                    FileInputStream fis = new FileInputStream(thumbnailFile);
                    if (api.getSession().isLinked()) {
                        api.putFileOverwrite(thumbnailRemoteFilePath, fis, fileLength, null);
                        Log.d(SkavaUploaderConstants.LOG, "Uploading " + thumbnailRemoteFilePath + " finished successfully");
                        success = true;
                    } else {
                        Log.d(SkavaUploaderConstants.LOG, "Dropbox for uploader service is not linked");
                        success = false;
                        //Notify the user of this is a bad thing
                        notifyDropboxUnlinked(0, "Skava Uploader", "Dropbox for uploader service is not linked");
                    }
                }
                // Set the name of the file in the remote Dropbox folder
                String fullSizeRemoteFilePath = dropboxPath + SkavaUploaderConstants.REMOTE_FOLDER_SEPARATOR + originalFileName;
                long fileLength = mFile.length();
                Log.d(SkavaUploaderConstants.LOG, "Uploading " + fullSizeRemoteFilePath + " which size is " + fileLength);
                FileInputStream fis = new FileInputStream(mFile);
                if (api.getSession().isLinked()) {
                    api.putFileOverwrite(fullSizeRemoteFilePath, fis, fileLength, null);
                    Log.d(SkavaUploaderConstants.LOG, "Uploading " + fullSizeRemoteFilePath + " finished successfully");
                    success = true;
                } else {
                    Log.d(SkavaUploaderConstants.LOG, "Dropbox for uploader service is not linked");
                    success = false;
                    //Notify the user of this is a bad thing
                    notifyDropboxUnlinked(0, "Skava Uploader", "Dropbox for uploader service is not linked");
                }
            }
        } catch (DropboxUnlinkedException e) {
            // This session wasn't authenticated properly or user unlinked
            mErrorMsg = "This app wasn't authenticated properly.";
            BugSenseHandler.sendException(e);
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            e.printStackTrace();
            success = false;
        } catch (DropboxFileSizeException e) {
            // File size too big to upload via the API
            mErrorMsg = "This file is too big to upload";
            success = false;
            BugSenseHandler.sendException(e);
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            e.printStackTrace();
        } catch (DropboxPartialFileException e) {
            // We canceled the operation
            mErrorMsg = "Upload canceled";
            success = false;
            BugSenseHandler.sendException(e);
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            e.printStackTrace();
        } catch (DropboxServerException e) {
            e.printStackTrace();
            success = false;
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
            }
            // This gets the Dropbox error, translated into the user's language
            mErrorMsg = e.body.userError;
            if (mErrorMsg == null) {
                mErrorMsg = e.body.error;
//                Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            }
        } catch (DropboxIOException e) {
            // Happens all the time, probably want to retry automatically.
            success = false;
            e.printStackTrace();
            mErrorMsg = "Network error.  Try again.";
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            BugSenseHandler.sendException(e);
//            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
        } catch (DropboxParseException e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            e.printStackTrace();
            // Probably due to Dropbox server restarting, should retry
            mErrorMsg = "Dropbox error.  Try again.";
//            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
        } catch (DropboxException e) {
            success = false;
            e.printStackTrace();
            BugSenseHandler.sendException(e);
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            mErrorMsg = "Unknown error.  Try again.";
        } catch (FileNotFoundException e) {
            success = false;
            BugSenseHandler.sendException(e);
            Log.e(SkavaUploaderConstants.LOG, e.getMessage());
            e.printStackTrace();
        }
        return success;
    }


    public void notifyDropboxUnlinked(int idNotification, String title, String text){
        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(mContext);
        Intent notifyIntent = new Intent();
        notifyIntent.setComponent(new ComponentName(this, CoreMainActivity.class));
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mBuilder.setSmallIcon(R.drawable.cloud_icon).setContentTitle(title).setContentText(text);

        // Creates the PendingIntent
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(notifyPendingIntent);
//        mBuilder.addAction(R.drawable.cloud_icon, "Dropbox for the Skava uploader service is currently unlinked", intent);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        Notification notification = mBuilder.build();
        mNotifyMgr.notify(idNotification, notification);
    }

    //This notification case will be managed by the Datastore listener on the new records
    //so here this method it not invoked at all
    public void notifyUploadSucceed(int idNotification, String title, String text){
        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.drawable.cloud_icon).setContentTitle(title).setContentText(text);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        Notification notification = mBuilder.build();
        mNotifyMgr.notify(idNotification, notification);
    }

}