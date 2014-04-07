package com.metric.skava.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.ExcavationProject;

import java.util.List;

/**
 * Created by metricboy on 3/25/14.
 */
public class ProjectListViewAdapter extends ArrayAdapter<ExcavationProject> {

    private Context mContext;
    private int resourceId;

    public ProjectListViewAdapter(Context context, int resource, int textViewResourceId, List<ExcavationProject> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceId = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View userViewItem;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            userViewItem = inflater.inflate(resourceId, parent, false);
        } else {
            userViewItem = convertView;
        }

        ExcavationProject actualProject = getItem(position);

        if (actualProject != null) {

            TextView text = (TextView) userViewItem.findViewById(R.id.first_column_text_view);
            text.setText(actualProject.getName());

            if (actualProject.getClient() != null) {
                text = (TextView) userViewItem.findViewById(R.id.second_column_text_view);
                text.setText(actualProject.getClient().getName());
            }
            text = (TextView) userViewItem.findViewById(R.id.third_column_text_view);
        }
        return userViewItem;
    }
}
