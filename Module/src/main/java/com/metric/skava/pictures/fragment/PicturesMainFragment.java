package com.metric.skava.pictures.fragment;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.pictures.activity.PictureDetailActivity;
import com.metric.skava.pictures.adapter.PictureGridViewAdapter;
import com.metric.skava.pictures.model.SkavaPicture;
import com.metric.skava.pictures.util.SkavaPictureFilesUtils;

/**
 * Created by metricboy on 2/24/14.
 */
public class PicturesMainFragment extends SkavaFragment implements AdapterView.OnItemClickListener {

    // Container Activity must implement this interface
    public static final int TAKE_PHOTO_FACE_REQUEST_CODE = 100;
    public static final String FACE_PICTURE_NAME = "Face";

    public static final int TAKE_PHOTO_LEFT_REQUEST_CODE = 200;
    public static final String LEFT_WALL_PICTURE_NAME = "Left Wall";

    public static final int TAKE_PHOTO_RIGHT_REQUEST_CODE = 300;
    public static final String RIGHT_WALL_PICTURE_NAME = "Right Wall";

    public static final int TAKE_PHOTO_ROOF_REQUEST_CODE = 400;
    public static final String ROOF_PICTURE_NAME = "Roof";

    public static final int TAKE_PHOTO_EXTRA_REQUEST_CODE = 500;
    public static final String EXTRA_PICTURE_NAME = "Extra";

    public static final String ROOF_FILE_NAME_HINT = "_ROOF_";
    public static final String LEFT_FILE_NAME_HINT = "_LEFT_";
    public static final String RIGHT_FILE_NAME_HINT = "_RIGHT_";
    public static final String FACE_FILE_NAME_HINT = "_FACE_";
    public static final String EXTRA_FILE_NAME_HINT = "_EXTRA_";
    public static final String WEDGE_FILE_NAME_HINT = "_WEDGE_";
    public static final String EXPANDED_FILE_NAME_HINT = "_EXPANDED_";

    private PictureGridViewAdapter mAdapter;
    private Assessment mAssessment;
    private SkavaPictureFilesUtils mPictureFilesUtils;
    private Uri[] uriGenerated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPictureFilesUtils = new SkavaPictureFilesUtils(getActivity());
        mAssessment = ((SkavaApplication) getActivity().getApplication()).getSkavaContext().getAssessment();
        uriGenerated = new Uri[9];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pictures_main_fragment, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);

        mAdapter = new PictureGridViewAdapter(getActivity(), mAssessment);

        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);

        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int fictionalPosition, long id) {

        //Check its not an empty picture
        if (mAssessment.getPicturesList().isEmpty()) {
            // do nothing
        } else {
            int resolvedPosition = fictionalPosition * 2;
            //check first the edited version of picture
            resolvedPosition+=1;
            if (mAssessment.getPicturesList().get(resolvedPosition) == null) {
                //mm.. so it has no edited, perhaps it has original
                resolvedPosition-=1;
                if (mAssessment.getPicturesList().get(resolvedPosition) == null){
                    return;
                }
            }
            final Intent i = new Intent(getActivity(), PictureDetailActivity.class);
            i.putExtra(PictureDetailActivity.ARG_SELECTED_IMAGE, (int) resolvedPosition);
            if (SkavaUtils.hasJellyBean()) {
                // makeThumbnailScaleUpAnimation() looks kind of ugly here as the loading spinner may
                // show plus the thumbnail image in GridView is cropped. so using
                // makeScaleUpAnimation() instead.
                ActivityOptions options =
                        ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
                getActivity().startActivity(i, options.toBundle());
            } else {
                startActivity(i);
            }

        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pictures_main_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        String suggestedName = getCurrentAssessment().getCode();
        if (id == R.id.action_add_face_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= FACE_FILE_NAME_HINT;
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[0] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_FACE_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_add_left_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= LEFT_FILE_NAME_HINT;
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[2] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_LEFT_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_add_right_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= RIGHT_FILE_NAME_HINT;
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[4] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_RIGHT_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_add_roof_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= ROOF_FILE_NAME_HINT;
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[6] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_ROOF_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_add_extra_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= EXTRA_FILE_NAME_HINT;
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[8] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_EXTRA_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_FACE_REQUEST_CODE:
                    Uri pictureURI;
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[0];
                    }
                    SkavaPicture picture = new SkavaPicture(SkavaPicture.PictureTag.FACE, pictureURI, true);
                    mAssessment.getPicturesList().set(0, picture);
                    mAdapter.notifyDataSetChanged();
                    break;

                case TAKE_PHOTO_LEFT_REQUEST_CODE:
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[2];
                    }
                    picture = new SkavaPicture(SkavaPicture.PictureTag.LEFT, pictureURI, true);
                    mAssessment.getPicturesList().set(2, picture);
                    mAdapter.notifyDataSetChanged();
                    break;

                case TAKE_PHOTO_RIGHT_REQUEST_CODE:
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[4];
                    }
                    picture = new SkavaPicture(SkavaPicture.PictureTag.RIGHT, pictureURI, true);
                    mAssessment.getPicturesList().set(4, picture);
                    mAdapter.notifyDataSetChanged();
                    break;

                case TAKE_PHOTO_ROOF_REQUEST_CODE:
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[6];
                    }
                    picture = new SkavaPicture(SkavaPicture.PictureTag.ROOF, pictureURI, true);
                    mAssessment.getPicturesList().set(6, picture);
                    mAdapter.notifyDataSetChanged();
                    break;

                case TAKE_PHOTO_EXTRA_REQUEST_CODE:
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[8];
                    }
                    int currentNumberOfPictures = mAssessment.getPicturesList().size();
                    picture = new SkavaPicture(SkavaPicture.PictureTag.EXTRA, pictureURI, true);
                    mAssessment.getPicturesList().add(currentNumberOfPictures, picture);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        } else {
            Log.d(SkavaConstants.LOG, "onActivityResult >> RequestCode  :" + requestCode + ",  ResultCode: " + resultCode + ", Intent: " + data);
        }
    }
}
