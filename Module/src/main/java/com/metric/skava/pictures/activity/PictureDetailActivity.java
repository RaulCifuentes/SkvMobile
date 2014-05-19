package com.metric.skava.pictures.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.assessment.activity.AssessmentStageListActivity;
import com.metric.skava.pictures.util.SkavaPictureFilesUtils;

import java.util.List;

public class PictureDetailActivity extends SkavaFragmentActivity {

    public static final String ARG_SELECTED_IMAGE = "ARG_SELECTED_IMAGE";
    private static final int EDIT_PICTURE_REQUEST_CODE = 300;

    private SkavaPictureFilesUtils mPictureFilesUtils;
    private Assessment mAssessment;
    private Uri mSeletedPictureUri;
    private int mSelectedPictureIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_detail_activity);

        // Set the current item based on the extra passed in to this activity
        mSelectedPictureIndex = getIntent().getIntExtra(ARG_SELECTED_IMAGE, -1);

        mPictureFilesUtils = new SkavaPictureFilesUtils(this);

        mAssessment = ((SkavaApplication) getApplication()).getSkavaContext().getAssessment();

        mSeletedPictureUri = mAssessment.getPictureUriList().get(mSelectedPictureIndex);

        ImageView mImageView = (ImageView) findViewById(R.id.imageView);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

//        // For this sample we'll use half of the longest width to resize our images. As the
//        // image scaling ensures the image is larger than this, we should be left with a
//        // resolution that is appropriate for both portrait and landscape. For best image quality
//        // we shouldn't divide by 2, but this will use more memory and require a larger memory
//        // cache.
//        final int longest = (height > width ? height : width) / 2;
//        Bitmap scaledBitmap = mPictureFilesUtils.getScaledBitmapFromUri(mSeletedPictureUri, width, height);

        Bitmap originalSizeBitmap = null;
        try {
            originalSizeBitmap = getBitmapFromUri();
        } catch (SkavaSystemException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
        mImageView.setImageBitmap(originalSizeBitmap);

    }

    private Bitmap getBitmapFromUri() {

        Bitmap bitmapFromUri = mPictureFilesUtils.getBitmapFromUri(mSeletedPictureUri);

        return bitmapFromUri;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picture_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_edit_picture) {
            editWithAviaryApplication();
            return true;
        }
        if (id == R.id.action_delete_picture) {
            deletePicture();
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            backToPicturesMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void deletePicture(){
        //dereference
        mAssessment.getPictureUriList().set(mSelectedPictureIndex, null);
        //delete physically
        mPictureFilesUtils.deleteFileFromUri(mSeletedPictureUri);
        backToPicturesMenu();
    }

    public void editWithAviaryApplication() {
        Intent newIntent = new Intent("aviary.intent.action.EDIT");
        newIntent.setDataAndType(mSeletedPictureUri, "image/*"); // required
        newIntent.putExtra("app-id", getPackageName());
        startActivityForResult(newIntent, EDIT_PICTURE_REQUEST_CODE);
        //newIntent.putExtra("EXTRA_OUTPUT", Uri.parse("file:///mnt/sdcard/..."));
        newIntent.putExtra("EXTRA_TOOLS_LIST", new String[]{"TEXT","DRAWING"});
    }

    public void startAviaryApplication() {
        final String AVIARY_PACKAGE_NAME = "com.aviary.android.feather";
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, 0);

            for (ResolveInfo info : resolveInfoList)
                if (info.activityInfo.packageName.equalsIgnoreCase(AVIARY_PACKAGE_NAME)) {
                    Intent sketchIntent = new Intent("MainActivity.class");
                    sketchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    sketchIntent.setAction(Intent.ACTION_MAIN);
                    sketchIntent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
                    sketchIntent.setType("image/*");
                    sketchIntent.putExtra(Intent.EXTRA_STREAM, mSeletedPictureUri);
                    startActivityForResult(sketchIntent, EDIT_PICTURE_REQUEST_CODE);
                    return;
                }

            // No match, so application is not installed
            showInMarket(AVIARY_PACKAGE_NAME);
        } catch (Exception e) {
            showInMarket(AVIARY_PACKAGE_NAME);
        }
    }


    private void showInMarket(String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_PICTURE_REQUEST_CODE) {
                int whichOneWasEdited = 0;
                if (data.hasExtra("WHICH_ONE")) {
                    whichOneWasEdited = data.getIntExtra("WHICH_ONE", 0);
                }
                Uri editedPictureURI = data.getData();
                //clone the editedPhoto overriding the initial photo on Skava folder
                boolean success = mPictureFilesUtils.copyFileFromUriToUri(editedPictureURI, mSeletedPictureUri);
                if (success) {
                    backToPicturesMenu();
                } else {
                    onBackPressed();
                }
            }
        } else {
            Log.d(SkavaConstants.LOG, "onActivityResult >> RequestCode  :" + requestCode + ",  ResultCode: " + resultCode + ", Intent: " + data);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    private void backToPicturesMenu() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.putExtra(AssessmentStageListActivity.REDIRECT_FROM_PICTURES, true);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            // This activity is NOT part of this app's task, so create a new task
            // when navigating up, with a synthesized back stack.
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                    .startActivities();
        } else {
            // This activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            NavUtils.navigateUpTo(this, upIntent);
        }

    }

}
