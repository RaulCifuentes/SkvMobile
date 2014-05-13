package com.metric.skava.discontinuities.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.metric.skava.calculator.barton.model.Ja;

import java.util.List;

/**
 * Created by metricboy on 3/1/14.
 */
public class MappedIndexJaGroupSpinnerArrayAdapter<Group> extends ArrayAdapter<Ja.Group> {

    private final Context mContext;
    private int mLayoutResourceId;
    private int mTextViewResourceId;


    public MappedIndexJaGroupSpinnerArrayAdapter(Context context, int rowsLayoutResourceId, int textViewResourceId, List<Ja.Group> objects) {
        super(context, rowsLayoutResourceId, textViewResourceId, objects);
        this.mContext = context;
        this.mLayoutResourceId = rowsLayoutResourceId;
        this.mTextViewResourceId = textViewResourceId;
    }

    @Override
    public Ja.Group getItem(int position) {
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
//        targetSpinnerTextView.setText(getItem(position).toString());
        targetSpinnerTextView.setText(getItem(position).name());
        int mWidth = 140;
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
        dropdownView = super.getDropDownView(position, null, parent);
        parent.setVerticalScrollBarEnabled(false);
        int mWidth = 140;
        dropdownView.setMinimumWidth(mWidth);
        ViewGroup.LayoutParams layoutParams = dropdownView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = mWidth;
        }
        return dropdownView;
    }
}