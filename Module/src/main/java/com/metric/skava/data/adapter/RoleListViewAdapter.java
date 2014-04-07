package com.metric.skava.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Role;

import java.util.List;

/**
 * Created by metricboy on 3/25/14.
 */
public class RoleListViewAdapter extends SkavaEntityListViewAdapter<Role> {

    private Context mContext;
    private int resourceId;

    public RoleListViewAdapter(Context context, int resource, int textViewResourceId, List<Role> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceId = resource;
        mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View roleViewItem;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            roleViewItem = inflater.inflate(resourceId, parent, false);
        } else {
            roleViewItem = convertView;
        }

        Role actualRole = getItem(position);

        if (actualRole != null) {

            TextView text = (TextView) roleViewItem.findViewById(R.id.first_column_text_view);
            text.setText(actualRole.getCode());

            text = (TextView) roleViewItem.findViewById(R.id.second_column_text_view);
            text.setText(actualRole.getName());

        }

        return roleViewItem;
    }
}
