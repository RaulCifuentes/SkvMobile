package com.metric.skava.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Client;

import java.util.List;

/**
 * Created by metricboy on 3/25/14.
 */
public class ClientListViewAdapter extends SkavaEntityListViewAdapter<Client> {

    private Context mContext;
    private int resourceId;

    public ClientListViewAdapter(Context context, int resource, int textViewResourceId, List<Client> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceId = resource;
        mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View clientViewItem;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            clientViewItem = inflater.inflate(resourceId, parent, false);
        } else {
            clientViewItem = convertView;
        }

        Client actualClient = getItem(position);

        if (actualClient != null) {

            TextView text = (TextView) clientViewItem.findViewById(R.id.first_column_text_view);
            text.setText(actualClient.getCode());

            text = (TextView) clientViewItem.findViewById(R.id.second_column_text_view);
            text.setText(actualClient.getName());

            if (actualClient.getClientLogo() != null) {
                text = (TextView) clientViewItem.findViewById(R.id.third_column_text_view);
                text.setText(actualClient.getClientLogo().getPath());
            }
        }

        return clientViewItem;
    }
}
