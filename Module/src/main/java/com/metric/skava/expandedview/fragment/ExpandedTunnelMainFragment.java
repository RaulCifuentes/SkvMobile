package com.metric.skava.expandedview.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bugsense.trace.BugSenseHandler;
import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.pictures.util.SkavaPictureFilesUtils;

/**
 * Created by metricboy on 7/1/14.
 */
public class ExpandedTunnelMainFragment extends SkavaFragment {

    private Assessment mAssessment;
    //TODO a list instead of a single expanded view, so that is possible to see
    // the expanded views of previous mappings
//    private List<Uri> mTunnelExpandedViews;
    private SkavaPictureFilesUtils mPictureFilesUtils;
    private ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAssessment = ((SkavaApplication) getActivity().getApplication()).getSkavaContext().getAssessment();
        mPictureFilesUtils = new SkavaPictureFilesUtils(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expanded_tunnel_main_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        updateImageView();
    }

    public void updateImageView(){
        Uri mTunnelExpandedView = mAssessment.getTunnelExpandedView();
        Bitmap expandedViewAsBitmap = null;
        try {
            if (mTunnelExpandedView == null){
                Bitmap baseAsBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.expanded_tunnel_dark);
                //show the base grid template
//                final DisplayMetrics displayMetrics = new DisplayMetrics();
//                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int width = displayMetrics.widthPixels;
//                int height = displayMetrics.heightPixels;
//                int scaledW = (int) (width  * .7);
//                int scaledH = (int) (height * .7);
////                final int longest = (height > width ? height : width) / 2;
//                Bitmap resizedBitmap = Bitmap.createScaledBitmap(baseAsBitmap, scaledW, scaledH, false);
                expandedViewAsBitmap = baseAsBitmap;
            } else {
                expandedViewAsBitmap = mPictureFilesUtils.getBitmapFromUri(mTunnelExpandedView);
            }
        } catch (SkavaSystemException e) {
            e.printStackTrace();
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
        }
        mImageView.setImageBitmap(expandedViewAsBitmap);
    }


}
