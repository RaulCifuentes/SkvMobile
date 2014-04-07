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

    private PictureGridViewAdapter mAdapter;
    private Assessment mAssessment;
    private SkavaPictureFilesUtils mPictureFilesUtils;
    private Uri[] uriGenerated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPictureFilesUtils = new SkavaPictureFilesUtils(getActivity());
        mAssessment = ((SkavaApplication) getActivity().getApplication()).getSkavaContext().getAssessment();
        uriGenerated = new Uri[5];
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
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        //Check its not an empty picture
        if (mAssessment.getPictureUriList().isEmpty()) {
            // do nothing
        } else {
            if (mAssessment.getPictureUriList().get(position) == null) {
                //do nothing
            } else {
                final Intent i = new Intent(getActivity(), PictureDetailActivity.class);
                i.putExtra(PictureDetailActivity.ARG_SELECTED_IMAGE, (int) position);
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

    }


    public void onNewPhotoTaken() {
        mAdapter.notifyDataSetChanged();
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
        String suggestedName = getCurrentAssessment().getInternalCode();
        if (id == R.id.action_add_face_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= "-FACE-";
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[0] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_FACE_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_add_left_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= "-LEFT-";
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[1] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_LEFT_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_add_right_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= "-RIGHT-";
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[2] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_RIGHT_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_add_roof_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= "-ROOF-";
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[3] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            }
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            startActivityForResult(photoIntent, TAKE_PHOTO_ROOF_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_add_extra_picture) {
            Intent photoIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            suggestedName+= "-EXTRA-";
            Uri targetUri = null;
            try {
                targetUri = mPictureFilesUtils.getOutputUri(suggestedName);
                uriGenerated[4] = targetUri;
            } catch (SkavaSystemException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
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
                    mAssessment.getPictureUriList().set(0, pictureURI);
                    mAdapter.notifyDataSetChanged();
                    break;

                case TAKE_PHOTO_LEFT_REQUEST_CODE:
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[1];
                    }
                    mAssessment.getPictureUriList().set(1, pictureURI);
                    mAdapter.notifyDataSetChanged();
                    break;

                case TAKE_PHOTO_RIGHT_REQUEST_CODE:
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[2];
                    }
                    mAssessment.getPictureUriList().set(2, pictureURI);
                    mAdapter.notifyDataSetChanged();
                    break;

                case TAKE_PHOTO_ROOF_REQUEST_CODE:
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[3];
                    }
                    mAssessment.getPictureUriList().set(3, pictureURI);
                    mAdapter.notifyDataSetChanged();
                    break;

                case TAKE_PHOTO_EXTRA_REQUEST_CODE:
                    if (data != null) {
                        pictureURI = data.getData();
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                        }
                    } else {
                        pictureURI = uriGenerated[4];
                    }
                    int currentNumberOfPictures = mAssessment.getPictureUriList().size();
                    mAssessment.getPictureUriList().add(currentNumberOfPictures, pictureURI);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        } else {
            Log.d(SkavaConstants.LOG, "onActivityResult >> RequestCode  :" + requestCode + ",  ResultCode: " + resultCode + ", Intent: " + data);
        }
    }
}
