package com.metric.skava.pictures.model;

import android.net.Uri;

/**
 * Created by metricboy on 6/10/14.
 */
public class SkavaPicture {

    public enum PictureTag {FACE, LEFT, RIGHT, ROOF, EXTRA, EXPANDED_TUNNEL};

    private Uri pictureLocation;
    private PictureTag pictureTag;
    private boolean original;

    public SkavaPicture(PictureTag pictureTag, Uri pictureLocation, boolean original) {
        this.pictureTag = pictureTag;
        this.pictureLocation = pictureLocation;
        this.original = original;
    }

    public Uri getPictureLocation() {
        return pictureLocation;
    }

    public void setPictureLocation(Uri pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

    public PictureTag getPictureTag() {
        return pictureTag;
    }

    public void setPictureTag(PictureTag pictureTag) {
        this.pictureTag = pictureTag;
    }

    public boolean isOriginal() {
        return original;
    }

    public void setOriginal(boolean edited) {
        this.original = edited;
    }
}
