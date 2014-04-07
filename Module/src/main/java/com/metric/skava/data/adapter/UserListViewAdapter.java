package com.metric.skava.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.User;

import java.util.List;

/**
 * Created by metricboy on 3/25/14.
 */
public class UserListViewAdapter extends SkavaEntityListViewAdapter<User> {

    private Context mContext;
    private int resourceId;

    public UserListViewAdapter(Context context, int resource, int textViewResourceId, List<User> objects) {
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

        User actualUser = getItem(position);

        if (actualUser != null) {

            TextView text = (TextView) userViewItem.findViewById(R.id.first_column_text_view);
            text.setText(actualUser.getName());

            text = (TextView) userViewItem.findViewById(R.id.second_column_text_view);
            text.setText(actualUser.getEmail());

            text = (TextView) userViewItem.findViewById(R.id.third_column_text_view);
            List<Role> roles = actualUser.getRoles();
            String listOfRoles = "";
            for (Role role : roles) {
                listOfRoles += role.getName() + ",";
            }
            int pos = listOfRoles.lastIndexOf(",");
            if (pos != -1 ){
                listOfRoles =  listOfRoles.substring(0, pos);
            }
            text.setText(listOfRoles);
        }


        return userViewItem;
    }
}
