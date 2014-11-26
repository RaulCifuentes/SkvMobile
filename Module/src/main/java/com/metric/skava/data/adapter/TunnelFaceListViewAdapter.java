package com.metric.skava.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.TunnelFace;

import java.util.List;

/**
 * Created by metricboy on 3/25/14.
 */
public class TunnelFaceListViewAdapter extends SkavaEntityListViewAdapter<TunnelFace> {

    private Context mContext;
    private int resourceId;

    public TunnelFaceListViewAdapter(Context context, int resource, int textViewResourceId, List<TunnelFace> objects) {
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

        TunnelFace actualFace = getItem(position);

        if (actualFace != null) {

            TextView text = (TextView) tunnelViewItem.findViewById(R.id.project_name_text_view);
            text.setText(actualFace.getCode());

            text = (TextView) tunnelViewItem.findViewById(R.id.tunnel_name_text_view);
            text.setText(actualFace.getName());

            text = (TextView) tunnelViewItem.findViewById(R.id.face_name_text_view);
            text.setText(actualFace.getTunnel().getName());
        }

        return tunnelViewItem;
    }
}
