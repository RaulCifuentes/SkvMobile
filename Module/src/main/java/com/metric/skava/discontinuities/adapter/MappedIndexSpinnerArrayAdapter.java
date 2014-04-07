package com.metric.skava.discontinuities.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.metric.skava.calculator.model.MappedIndex;

import java.util.List;

/**
 * Created by metricboy on 3/1/14.
 */
public class MappedIndexSpinnerArrayAdapter<T extends MappedIndex> extends ArrayAdapter<T> {

    private final Context mContext;
    private int mLayoutResourceId;
    private int mTextViewResourceId;


    public MappedIndexSpinnerArrayAdapter(Context context, int rowsLayoutResourceId, int textViewResourceId, List<T> objects) {
        super(context, rowsLayoutResourceId, textViewResourceId, objects);
        this.mContext = context;
        this.mLayoutResourceId = rowsLayoutResourceId;
        this.mTextViewResourceId = textViewResourceId;
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public int getCount() {
        return super.getCount();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View wholeRowView = super.getView(position,  convertView, parent);
        TextView targetSpinnerTextView = (TextView) wholeRowView.findViewById(mTextViewResourceId);
        targetSpinnerTextView.setText(getItem(position).getShortDescription());
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
        View dropdownView;
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