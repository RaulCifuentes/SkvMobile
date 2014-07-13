package com.metric.skava.pictures.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by metricboy on 2/24/14.
 */
public class SkavaPictureFilesUtils extends SkavaFilesUtils   {

    public SkavaPictureFilesUtils(Context ctx) {
        super(ctx);
    }

    public File getSkavaPicturesBaseFolder() {
        return mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }


    public Uri getOutputUri(String assessmenCode, String suggestedName) throws SkavaSystemException {
        File theOutputFile = getOutputFile(assessmenCode, suggestedName);
        Uri targetUri = Uri.fromFile(theOutputFile);
        return targetUri;
    }


    public File getSkavaPicturesFolder(){
        File mediaStorageDir = new File(getSkavaPicturesBaseFolder(), SkavaConstants.IMAGE_DIRECTORY_NAME);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.e(SkavaConstants.LOG, "Failed to create directory : " + SkavaConstants.IMAGE_DIRECTORY_NAME );
                throw new SkavaSystemException("Failed to create directory : " + SkavaConstants.IMAGE_DIRECTORY_NAME );
            }
        }
        return mediaStorageDir;
    }

    public File getOutputFile(String assessmentCode, String suggestedName) throws SkavaSystemException {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File skavaPicturesFolder = getSkavaPicturesFolder();
        File assessmentPicturesFolder = new File(skavaPicturesFolder, assessmentCode);
        // Create the storage directory if it does not exist
        if (! assessmentPicturesFolder.exists()){
            if (! assessmentPicturesFolder.mkdirs()){
                Log.e(SkavaConstants.LOG, "Failed to create directory : " + assessmentPicturesFolder.getPath() );
                throw new SkavaSystemException("Failed to create directory : " + assessmentPicturesFolder.getPath() );
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(SkavaUtils.getCurrentDate());

        File mediaFile = new File(assessmentPicturesFolder.getPath() + File.separator + suggestedName + timeStamp + ".jpg");
        return mediaFile;

    }



    public Bitmap getBitmapFromUri(Uri uri) throws SkavaSystemException {
        Bitmap originalSizeBitmap = null;
        try {
            // HACK: In order to avoid Out Of Memory exceptions, image is resampled prior to decoding.
            // Value was chosen taking into account image size and display size.
            
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;

            String path = getExistingFileFromUri(uri).getPath();
//            originalSizeBitmap = BitmapFactory.decodeFile(path, options);
            originalSizeBitmap = BitmapFactory.decodeFile(path);
        } catch (OutOfMemoryError e) {
            throw new SkavaSystemException(e);
        }
        return originalSizeBitmap;
    }


    public Bitmap getScaledBitmapFromUri(Uri uri, int reqWidth, int reqHeight ) {
        String path = getExistingFileFromUri(uri).getPath();
        Bitmap originalSizeBitmap = BitmapFactory.decodeFile(path);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalSizeBitmap, reqWidth, reqHeight, false);
        return resizedBitmap;
    }


    public Bitmap getSampledBitmapFromFile(Uri uri, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        String path = getExistingFileFromUri(uri).getPath();
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public int[] calculateNewDimensionForMaxSize(Uri uri,int maxSize){
        int outWidth;
        int outHeight;
        String path = getExistingFileFromUri(uri).getPath();
        Bitmap originalSizeBitmap = BitmapFactory.decodeFile(path);
        int inWidth = originalSizeBitmap.getWidth();
        int inHeight = originalSizeBitmap.getHeight();
        if (inWidth > inHeight) {
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }
        return new int[]{outWidth, outHeight};
    }


    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


}
