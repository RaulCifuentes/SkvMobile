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
import com.metric.skava.pictures.model.SkavaPicture;
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
        //mapReduceNumberOfPictures represents the elements of pictures on the grid
        //the originals will be hidden by the edited if any, so just half of the size
        //is needed
        int mapReduceNumberOfPictures = 0;
        if (!mAssessment.getPicturesList().isEmpty()) {
            int realNumberOfPictures = mAssessment.getPicturesList().size();
            //picture list will host by default 8 pictures
            //2 for FACE, 2 for LEFT, 2 for RIGHT, 2 for ROOF
            //if theres one EXTRA the size of the list will be greater
            //strarting at 9 and a new gris element will be required, so
            mapReduceNumberOfPictures = (int) Math.ceil((double)realNumberOfPictures / 2);
        }
        return mapReduceNumberOfPictures <= 4 ? 4 : mapReduceNumberOfPictures;
    }

    //fictionalPosition is the position on the grid, ie. 0 for FACE; 1 for LEFT; 2 for RIGHT; 3 for ROOF; 4,5,..n  for EXTRAs
    public Bitmap getItem(int fictionalPosition) {
        Bitmap thumnailBitmap = null;
        List<SkavaPicture> pictureList = mAssessment.getPicturesList();
        int resolvedPosition = fictionalPosition * 2;
        if (pictureList.isEmpty() || resolvedPosition >= pictureList.size()) {
            thumnailBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.skava_shadow);
        } else {
            //First check the border case when an EXTRA picture
            // has just been added to the list
            SkavaPicture picture = null;
            if (resolvedPosition == pictureList.size() - 1){
                picture = pictureList.get(resolvedPosition);
            } else {
                //ok, if the list is big enough then
                //try first an edited picture,
                picture = pictureList.get(resolvedPosition + 1);
                if (picture == null || picture.getPictureLocation() == null){
                    //there no edited version of this picture, use the original
                    picture = pictureList.get(resolvedPosition);
                }
            }
            if (picture == null || picture.getPictureLocation() == null) {
                thumnailBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.skava_shadow);
            } else {
                Uri pictureLocation = picture.getPictureLocation();
                Resources resources = mContext.getResources();
                Float maxSize = resources.getDimension(R.dimen.max_image_thumbnail_size);
                Float prefThumbnailWidth = resources.getDimension(R.dimen.prefered_image_thumbnail_width);
                Float prefThumbnailHeight = resources.getDimension(R.dimen.prefered_image_thumbnail_height);
                //TODO probar con el ScaledBitmap y con el SampledBitmap
//            thumnailBitmap = mPictureFilesUtils.getScaledBitmapFromUri(picture, prefThumbnailWidth.intValue(), prefThumbnailHeight.intValue());
                thumnailBitmap = mPictureFilesUtils.getSampledBitmapFromFile(pictureLocation, prefThumbnailWidth.intValue(), prefThumbnailHeight.intValue());
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
        String picLabel;
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
