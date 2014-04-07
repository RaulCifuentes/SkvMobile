package com.metric.skava.data.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.metric.skava.app.data.SkavaEntity;

import java.util.List;

/**
 * Created by metricboy on 3/25/14.
 */
public class SkavaEntityListViewAdapter<T extends SkavaEntity> extends ArrayAdapter<T> {


    public SkavaEntityListViewAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
