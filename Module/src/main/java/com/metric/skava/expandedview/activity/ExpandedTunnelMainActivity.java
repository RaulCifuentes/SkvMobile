package com.metric.skava.expandedview.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aviary.android.feather.library.Constants;
import com.bugsense.trace.BugSenseHandler;
import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.assessment.activity.AssessmentStageListActivity;
import com.metric.skava.expandedview.fragment.ExpandedTunnelMainFragment;
import com.metric.skava.pictures.util.SkavaPictureFilesUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by metricboy on 7/1/14.
 */
public class ExpandedTunnelMainActivity  extends SkavaFragmentActivity {

    private static final int EDIT_EXPANDED_TUNNEL_REQUEST_CODE = 700;

    private Assessment mAssessment;
    //TODO a list instead of a single expanded view, so that is possible to see
    // the expanded views of previous mappings
//    private List<Uri> mTunnelExpandedViews;
    private Uri mTunnelExpandedView;
    private SkavaPictureFilesUtils mPictureFilesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanded_tunnel_main_activity);
        mAssessment = ((SkavaApplication)getApplication()).getSkavaContext().getAssessment();
        mTunnelExpandedView = mAssessment.getTunnelExpandedView();
        mPictureFilesUtils = new SkavaPictureFilesUtils(getApplicationContext());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ExpandedTunnelMainFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expanded_tunnel_main_menu, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mTunnelExpandedView == null) {
            menu.findItem(R.id.action_clear_extended_view).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_edit_extended_view) {
            editWithAviaryApplication();
            return true;
        }
        if (id == R.id.action_clear_extended_view) {
            clearExpandedView();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            backToStagesMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void clearExpandedView(){
        mAssessment.setTunnelExpandedView(null);
        //delete physically
        mPictureFilesUtils.deleteFileFromUri(mTunnelExpandedView);
        //reload the fragment imageView
        ExpandedTunnelMainFragment myFragment = (ExpandedTunnelMainFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        myFragment.updateImageView();
        invalidateOptionsMenu();
    }

    public void editWithAviaryApplication() {
        if (mTunnelExpandedView == null){
            Bitmap baseAsBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.expanded_tunnel_dark);
            //show the base grid template
//            final DisplayMetrics displayMetrics = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            int width = displayMetrics.widthPixels;
//            int height = displayMetrics.heightPixels;
//            int scaledW = (int) (width  * .7);
//            int scaledH = (int) (height * .7);
////                final int longest = (height > width ? height : width) / 2;
//            Bitmap resizedBaseGridAsBitmap = Bitmap.createScaledBitmap(baseAsBitmap, scaledW, scaledH, false);
            String assessmenCode = getCurrentAssessment().getCode();
            String extendedViewSuggestedName =  assessmenCode + "_TUNNEL_";
            File extendedViewFile = mPictureFilesUtils.getOutputFile(assessmenCode, extendedViewSuggestedName);
            FileOutputStream fos = null;
            try {
                //create a file from the bitmap
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                baseAsBitmap.compress(Bitmap.CompressFormat.PNG, 90 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                //write the bytes in file
                fos = new FileOutputStream(extendedViewFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mTunnelExpandedView = Uri.fromFile(extendedViewFile);
        }
        Intent newIntent = new Intent("aviary.intent.action.EDIT");
        newIntent.setDataAndType(mTunnelExpandedView, "image/*"); // required
        newIntent.putExtra("app-id", getPackageName());
        String[] tools = new String[]{"BRIGHTNESS", "CROP", "DRAW", "TEXT"};
        newIntent.putExtra(Constants.EXTRA_TOOLS_LIST, tools);
        newIntent.putExtra(Constants.EXTRA_IN_API_KEY_SECRET, SkavaConstants.AVIARY_APP_KEY);
        //this will remove from the Aviary Editor the feedback button and the Aviary logo
        newIntent.putExtra(Constants.EXTRA_WHITELABEL, "");
        startActivityForResult(newIntent, EDIT_EXPANDED_TUNNEL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_EXPANDED_TUNNEL_REQUEST_CODE) {
                //This is the URI data from Aviary (image editor)
                Uri fileFromImageEditorURI = data.getData();
                Uri overrideMe = mTunnelExpandedView;
                //Create a URL similar to the original picture to store the edited one
                boolean success = false;
                try {
                    //overwrite the previous extended file
                    success = mPictureFilesUtils.copyFileFromUriToUri(fileFromImageEditorURI, overrideMe, true);
                } catch (Exception e) {
                    BugSenseHandler.sendException(e);
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                getCurrentAssessment().setTunnelExpandedView(overrideMe);
                if (success) {
                    backToStagesMenu();
                } else {
                    onBackPressed();
                }
            }
        } else {
            Log.d(SkavaConstants.LOG, "onActivityResult >> RequestCode  :" + requestCode + ",  ResultCode: " + resultCode + ", Intent: " + data);
        }

    }

    private void backToStagesMenu() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.putExtra(AssessmentStageListActivity.REDIRECT_FROM_EXPANDED, true);
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

    public void onPreExecuteImportAppData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    public void onPreExecuteImportUserData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    @Override
    public void onPostExecuteImportAppData(boolean success, Long result) {

    }

    @Override
    public void onPostExecuteImportUserData(boolean success, Long result) {

    }


    public void showProgressBar(final boolean show, String text, boolean longTime) {

    }

}
