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

import com.aviary.android.feather.library.Constants;
import com.bugsense.trace.BugSenseHandler;
import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.assessment.activity.AssessmentStageListActivity;
import com.metric.skava.pictures.model.SkavaPicture;
import com.metric.skava.pictures.util.SkavaPictureFilesUtils;

import java.util.List;

public class PictureDetailActivity extends SkavaFragmentActivity {

    public static final String ARG_SELECTED_IMAGE = "ARG_SELECTED_IMAGE";
    private static final int EDIT_PICTURE_REQUEST_CODE = 300;

    private SkavaPictureFilesUtils mPictureFilesUtils;
    private Assessment mAssessment;
    private SkavaPicture mSelectedPicture;
    private int mSelectedPictureIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_detail_activity);

        // Set the current item based on the extra passed in to this activity
        mSelectedPictureIndex = getIntent().getIntExtra(ARG_SELECTED_IMAGE, -1);

        mPictureFilesUtils = new SkavaPictureFilesUtils(this);

        mAssessment = ((SkavaApplication) getApplication()).getSkavaContext().getAssessment();

        mSelectedPicture = mAssessment.getPicturesList().get(mSelectedPictureIndex);

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
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        mImageView.setImageBitmap(originalSizeBitmap);

    }

    private Bitmap getBitmapFromUri() {
        // HACK: In order to avoid Out Of Memory exceptions, image is resampled instead of scaled.
//        Bitmap bitmapFromUri = mPictureFilesUtils.getBitmapFromUri(mSelectedPicture.getPictureLocation());
//        640x480-----4:3
//        800x600-----4:3
//        1024x768-----4:3
        //Bitmap bitmapFromUri = mPictureFilesUtils.getScaledBitmapFromUri(mSelectedPicture.getPictureLocation(), 800, 600);
        Bitmap bitmapFromUri = mPictureFilesUtils.getSampledBitmapFromFile(mSelectedPicture.getPictureLocation(), 800, 600);
        return bitmapFromUri;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picture_detail_menu, menu);
        if (mSelectedPictureIndex % 2 == 0) {
            menu.findItem(R.id.action_back_original).setVisible(false);
        }
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
        if (id == R.id.action_back_original) {
            deletePicture(false);
            return true;
        }
        if (id == R.id.action_delete_picture) {
            deletePicture(true);
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


    public void deletePicture(boolean deleteByPair) {
        //dereference or remove depending on the photo classification
        //0..7 should be always present that's why those are set to null
        //8 or greater are extras, that means optional, so they can be removed
        if (mSelectedPictureIndex < 8) {
            mAssessment.getPicturesList().set(mSelectedPictureIndex, null);
            if (deleteByPair) {
                //the array of pictures has the structure {[original_1],[edited_1],[original_n],[edited_n]}
                if (mSelectedPictureIndex % 2 == 0) {
                    mAssessment.getPicturesList().set(mSelectedPictureIndex + 1, null);
                }
            }
        } else {
            mAssessment.getPicturesList().remove(mSelectedPictureIndex);
            if (deleteByPair) {
                //the array of pictures has the structure {[original_1],[edited_1],[original_n],[edited_n]}
                if (mSelectedPictureIndex % 2 == 0) {
                    mAssessment.getPicturesList().remove(mSelectedPictureIndex + 1);
                }
            }
        }
        //TODO Delete physically
        //delete physically this is too early for delete physizally. what if this is an edition
        //of a previously saved assessment, the deletions must be centralized on the saving of the
        //assessment
        //mPictureFilesUtils.deleteFileFromUri(mSelectedPicture.getPictureLocation());
        backToPicturesMenu();
    }

    public void editWithAviaryApplication() {
        Intent newIntent = new Intent("aviary.intent.action.EDIT");
        newIntent.setDataAndType(mSelectedPicture.getPictureLocation(), "image/*"); // required
        newIntent.putExtra("app-id", getPackageName());
        String[] tools = new String[]{"BRIGHTNESS", "CROP", "DRAW", "TEXT"};
        newIntent.putExtra(Constants.EXTRA_TOOLS_LIST, tools);
        newIntent.putExtra(Constants.EXTRA_IN_API_KEY_SECRET, SkavaConstants.AVIARY_APP_KEY);
        //this will remove from the Aviary Editor the feedback button and the Aviary logo
        newIntent.putExtra(Constants.EXTRA_WHITELABEL, "");
        //I put the assessment code as an extra but it is not present on the data Intent available in the onActivityResult
//        newIntent.putExtra("ASSESSMENT_CODE", mAssessment.getCode());
        startActivityForResult(newIntent, EDIT_PICTURE_REQUEST_CODE);
        //newIntent.putExtra("EXTRA_OUTPUT", Uri.parse("file:///mnt/sdcard/..."));
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
                    sketchIntent.putExtra(Intent.EXTRA_STREAM, mSelectedPicture.getPictureLocation());
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
                //This is the URI data from Aviary (image editor)
                Uri fileFromImageEditorURI = data.getData();
                //Create a URL similar to the original picture to store the edited one
                String originalPictureName = mSelectedPicture.getPictureLocation().getLastPathSegment();
                //clone the editedPhoto to store later in a private skava folder
                String newPictureSuggestedName = originalPictureName.substring(0, originalPictureName.lastIndexOf(mSelectedPicture.getPictureTag().name()));
                newPictureSuggestedName += mSelectedPicture.getPictureTag().name();
//                String assessmenCode = data.getStringExtra("ASSESSMENT_CODE");
                String assessmentCode = getCurrentAssessment().getCode();
                Uri skavaEditedPicture = mPictureFilesUtils.getOutputUri(assessmentCode, newPictureSuggestedName,  "_EDITED");
                boolean success = false;
                try {
                    success = mPictureFilesUtils.copyFileFromUriToUri(fileFromImageEditorURI, skavaEditedPicture, true);
                } catch (Exception e) {
                    BugSenseHandler.sendException(e);
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                SkavaPicture editedPicture = new SkavaPicture(mSelectedPicture.getPictureTag(), skavaEditedPicture, false);
                //todo check that double editions of a picture overwrites the edition and not creates a thrird one
                if (mSelectedPictureIndex %2 == 0){
                    getCurrentAssessment().getPicturesList().set(mSelectedPictureIndex + 1, editedPicture);
                } else {
                    getCurrentAssessment().getPicturesList().set(mSelectedPictureIndex, editedPicture);
                }
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

    public void showProgressBar(final boolean show, String text, boolean longTime) {

    }

    public void onPreExecuteImportAppData() {

    }

    public void onPreExecuteImportUserData() {

    }

    @Override
    public void onPostExecuteImportAppData(boolean success, Long result) {

    }

    @Override
    public void onPostExecuteImportUserData(boolean success, Long result) {

    }


}
