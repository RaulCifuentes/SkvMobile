package com.metric.skava.pictures.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.pictures.fragment.PicturesMainFragment;
import com.metric.skava.pictures.util.SkavaPictureFilesUtils;

import java.util.List;

/**
 * Created by metricboy on 2/24/14.
 */
public class PictureGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private final Assessment mAssessment;
    private SkavaPictureFilesUtils mPictureFilesUtils;
    private LayoutInflater mInflater;

    public PictureGridViewAdapter(Context c, Assessment assessment) {
        super();
        mContext = c;
        mAssessment = assessment;
        mPictureFilesUtils = new SkavaPictureFilesUtils(mContext);
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        int currentNumberOfPictures = 0;
        if (!mAssessment.getPictureUriList().isEmpty()) {
            currentNumberOfPictures = mAssessment.getPictureUriList().size();
        }
        return currentNumberOfPictures < 4 ? 4 : currentNumberOfPictures;
    }

    public Bitmap getItem(int position) {
        Bitmap thumnailBitmap;
        List<Uri> pictureList = mAssessment.getPictureUriList();
        if (pictureList.isEmpty() || position >= pictureList.size()) {
            thumnailBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.skava_shadow);
        } else {
            Uri picture = pictureList.get(position);
            if (picture == null){
                thumnailBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.skava_shadow);
            } else {
            Resources resources = mContext.getResources();
            Float maxSize = resources.getDimension(R.dimen.max_image_thumbnail_size);
            Float prefThumbnailWidth =  resources.getDimension(R.dimen.prefered_image_thumbnail_width);
            Float prefThumbnailHeight = resources.getDimension(R.dimen.prefered_image_thumbnail_height);
            //TODO probar con el ScaledBitmap y con el SampledBitmap
//            thumnailBitmap = mPictureFilesUtils.getScaledBitmapFromUri(picture, prefThumbnailWidth.intValue(), prefThumbnailHeight.intValue());
            thumnailBitmap = mPictureFilesUtils.getSampledBitmapFromFile(picture, prefThumbnailWidth.intValue(), prefThumbnailHeight.intValue());
            }
        }
        return thumnailBitmap;
    }


    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View pictureView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            pictureView = mInflater.inflate(R.layout.grid_view_item, parent, false);
        } else {
            pictureView = convertView;
        }
        ImageView picture = (ImageView) pictureView.findViewById(R.id.picture);
        TextView label = (TextView) pictureView.findViewById(R.id.text);
        picture.setImageBitmap(getItem(position));
        String picLabel = "Label";
        switch (position) {
            case 0:
                picLabel = PicturesMainFragment.FACE_PICTURE_NAME;
                break;
            case 1:
                picLabel = PicturesMainFragment.LEFT_WALL_PICTURE_NAME;
                break;
            case 2:
                picLabel = PicturesMainFragment.RIGHT_WALL_PICTURE_NAME;
                break;
            case 3:
                picLabel = PicturesMainFragment.ROOF_PICTURE_NAME;
                break;
            default:
                picLabel = PicturesMainFragment.EXTRA_PICTURE_NAME + " " + (position - 3);
                break;
        }
        label.setText(picLabel);
        return pictureView;
    }


}
