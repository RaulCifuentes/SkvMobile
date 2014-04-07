package com.metric.skava.app.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by metricboy on 2/17/14.
 */
public class ParcelableData implements Parcelable {

    private int mData;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<ParcelableData> CREATOR
            = new Parcelable.Creator<ParcelableData>() {
        public ParcelableData createFromParcel(Parcel in) {
            return new ParcelableData(in);
        }

        public ParcelableData[] newArray(int size) {
            return new ParcelableData[size];
        }
    };

    private ParcelableData(Parcel in) {
        mData = in.readInt();
    }
}