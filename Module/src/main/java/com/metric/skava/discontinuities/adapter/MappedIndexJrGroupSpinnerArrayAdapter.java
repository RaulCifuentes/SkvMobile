package com.metric.skava.discontinuities.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.calculator.barton.model.Jr;

import java.util.List;


/**
 * Created by metricboy on 3/1/14.
 */
public class MappedIndexJrGroupSpinnerArrayAdapter<Group> extends ArrayAdapter<Jr.Group> {

    private final Context mContext;
    private int mLayoutResourceId;
    private int mTextViewResourceId;


    public MappedIndexJrGroupSpinnerArrayAdapter(Context context, int rowsLayoutResourceId, int textViewResourceId, List<Jr.Group> objects) {
        super(context, rowsLayoutResourceId, textViewResourceId, objects);
        this.mContext = context;
        this.mLayoutResourceId = rowsLayoutResourceId;
        this.mTextViewResourceId = textViewResourceId;
    }

    @Override
    public Jr.Group getItem(int position) {
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
//        targetSpinnerTextView.setText(getItem(position).name());
        Jr.Group groupItem = getItem(position);
        String groupName = null;
        switch (groupItem) {
            case a:
                groupName = mContext.getResources().getString(R.string.jr_a_group);
                break;
            case b:
                groupName = mContext.getResources().getString(R.string.jr_b_group);
                break;
            case c:
                groupName = mContext.getResources().getString(R.string.jr_c_group);
                break;
        }
        targetSpinnerTextView.setText(groupName);
        return wholeRowView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View dropdownView;
        dropdownView = super.getDropDownView(position, null, parent);
        parent.setVerticalScrollBarEnabled(false);

        return dropdownView;
    }
}