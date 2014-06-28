package com.metric.skava.pictures.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.metric.skava.app.util.MediaUtils;
import com.metric.skava.app.util.SkavaConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by metricboy on 3/28/14.
 */
public class SkavaFilesUtils {


    protected Context mContext;

    public SkavaFilesUtils(Context ctx) {
        this.mContext = ctx;
    }

    public File getNewFileFromUri(Uri uri) {
        File file = new File(uri.getPath());
        if (!file.exists()) {
            try {
                boolean success = file.createNewFile();
                if (success) {
                    return file;
                }
            } catch (IOException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                e.printStackTrace();
            }
        }
        return file;
    }


    public File getExistingFileFromUri(Uri uri) {
        File file = new File(uri.getPath());
        if (file.exists()) {
            return file;
        } else {
            MediaUtils mediaUtils = new MediaUtils(mContext);
            String alternativePath = mediaUtils.getRealPathFromURI(uri);
            file = new File(alternativePath);
            return file;
        }
    }

    public boolean copyFile(File from, File to) {
        try {
            InputStream in = new FileInputStream(from);
            OutputStream out = new FileOutputStream(to);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            return false;
        }
    }


    public boolean deleteFileFromUri(Uri deletionTarget) {
        File deleteFile = getExistingFileFromUri(deletionTarget);
        return deleteFile.delete();
    }

    public boolean copyFileFromUriToUri(Uri sourceUri, Uri targetUri, boolean createFileIfNeeded) {
        boolean success = false;
        try {
            File srcFile = null;
            File targetFile = null;
            try {
                srcFile = getExistingFileFromUri(sourceUri);
            } catch (Exception e) {
                if (createFileIfNeeded){
                    srcFile = getNewFileFromUri(sourceUri);
                }
            }
            try {
                targetFile = getExistingFileFromUri(targetUri);
            } catch (Exception e) {
                if (createFileIfNeeded){
                    targetFile = getNewFileFromUri(targetUri);
                }
            }
            success = copyFile(srcFile, targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(SkavaConstants.LOG, e.getMessage());
        }
        return success;
    }


    public File getSkavaDocumentsBaseFolder() {
        return mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
    }

    public File getPivateExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }


}
