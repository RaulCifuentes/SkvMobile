package com.metric.skava.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Tunnel;

import java.util.List;

/**
 * Created by metricboy on 3/25/14.
 */
public class TunnelListViewAdapter extends SkavaEntityListViewAdapter<Tunnel> {

    private Context mContext;
    private int resourceId;

    public TunnelListViewAdapter(Context context, int resource, int textViewResourceId, List<Tunnel> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceId = resource;
        mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View tunnelViewItem;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            tunnelViewItem = inflater.inflate(resourceId, parent, false);
        } else {
            tunnelViewItem = convertView;
        }

        Tunnel actualTunnel = getItem(position);

        if (actualTunnel != null) {

            TextView text = (TextView) tunnelViewItem.findViewById(R.id.first_column_text_view);
            text.setText(actualTunnel.getCode());

            text = (TextView) tunnelViewItem.findViewById(R.id.second_column_text_view);
            text.setText(actualTunnel.getName());

            text = (TextView) tunnelViewItem.findViewById(R.id.third_column_text_view);
            text.setText(actualTunnel.getProject().getName());
        }

        return tunnelViewItem;
    }
}
