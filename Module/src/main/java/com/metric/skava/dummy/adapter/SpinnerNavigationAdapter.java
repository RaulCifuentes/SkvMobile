package com.metric.skava.dummy.adapter;

import java.util.ArrayList;

import com.metric.skava.R;
import com.metric.skava.dummy.model.DummySpinnerItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SpinnerNavigationAdapter extends BaseAdapter {
 
    private ImageView imgIcon;
    private TextView txtTitle;
    private ArrayList<DummySpinnerItem> spinnerNavItem;
    private Context context;
 
    public SpinnerNavigationAdapter(Context context,
            ArrayList<DummySpinnerItem> spinnerNavItem) {
        this.spinnerNavItem = spinnerNavItem;
        this.context = context;
    }
 
    @Override
    public int getCount() {
        return spinnerNavItem.size();
    }
 
    @Override
    public Object getItem(int index) {
        return spinnerNavItem.get(index);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) { 
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.dummy_spinner_item, null);
        }
         
        imgIcon = (ImageView) convertView.findViewById(R.id.img_icon);
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
         
        imgIcon.setImageResource(spinnerNavItem.get(position).getIcon());
        imgIcon.setVisibility(View.GONE);
        txtTitle.setText(spinnerNavItem.get(position).getTitle());
        return convertView;
    }
     
 
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.dummy_spinner_item, null);
        }
         
        imgIcon = (ImageView) convertView.findViewById(R.id.img_icon);
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
         
        imgIcon.setImageResource(spinnerNavItem.get(position).getIcon());        
        txtTitle.setText(spinnerNavItem.get(position).getTitle());
        return convertView;
    }
 
}