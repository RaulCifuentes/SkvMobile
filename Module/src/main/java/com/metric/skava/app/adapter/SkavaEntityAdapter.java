package com.metric.skava.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.metric.skava.app.data.SkavaEntity;

import java.util.List;

/**
 * Created by metricboy on 2/21/14.
 */
public class SkavaEntityAdapter<T extends SkavaEntity> extends ArrayAdapter<T> {

    // Your sent mContext
    private Context mContext;
    // Your custom values for the spinner
    private int mLayoutResourceId;
    private int mTextViewResourceId;


    //default value textViewResourceId android.R.id.text1
    public SkavaEntityAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mTextViewResourceId = textViewResourceId;
    }


    public void setValues(List<T> values) {
        clear();
        addAll(values);
    }

    public int getCount() {
        return super.getCount();
    }

    public T getItem(int position) {
        return super.getItem(position);
    }

    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View wholeRowView = super.getView(position, convertView, parent);
        int mWidth = 280;
        wholeRowView.setMinimumWidth(mWidth);
        ViewGroup.LayoutParams layoutParams = wholeRowView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = mWidth;
        }
        return wholeRowView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View dropdownView = null;
        if (position == getCount() - 1) {
            TextView tv = new TextView(getContext());
            tv.setHeight(0);
            tv.setVisibility(View.GONE);
            dropdownView = tv;
        } else {
            dropdownView = super.getDropDownView(position, null, parent);
        }
        parent.setVerticalScrollBarEnabled(false);
        int mWidth = 280;
        dropdownView.setMinimumWidth(mWidth);
        ViewGroup.LayoutParams layoutParams = dropdownView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = mWidth;
        }
        return dropdownView;

    }


}
