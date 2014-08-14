package com.metric.skava.uploader.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by metricboy on 8/7/14.
 */

/**
 * Created by metricboy on 3/28/14.
 */
public class SkavaUploaderFileUtils {


    protected Context mContext;

    public SkavaUploaderFileUtils(Context ctx) {
        this.mContext = ctx;
    }


    public File getSkavaDocumentsBaseFolder() {
        return mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
    }


    public File scalePictureToHalf(File originalFile) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
        options.inDither = false;         //Disable Dithering mode
        options.inPurgeable = true;       //Tell to gc that whether it needs free memory, the Bitmap can be cleared
        options.inInputShareable = true;  //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
        options.inTempStorage = new byte[32 * 1024];

        File scaledFile = null;
        if (originalFile != null) {
            String path = originalFile.getPath();
            options.inSampleSize = 2;
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap bitmaped = BitmapFactory.decodeFile(path, options);
            scaledFile = new File(originalFile.getParent(), "scaled_" + originalFile.getName());
            FileOutputStream fos = null;
            try {
                //create a file from the bitmap
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmaped.compress(Bitmap.CompressFormat.PNG, 90 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                //write the bytes in file
                fos = new FileOutputStream(scaledFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(SkavaUploaderConstants.LOG, e.getMessage());
                BugSenseHandler.sendException(e);
            }
        }
        return scaledFile;
    }



}
