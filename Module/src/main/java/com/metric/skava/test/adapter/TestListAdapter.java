package com.metric.skava.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.test.model.TestListItem;

import java.util.List;

/**
 * Created by metricboy on 2/27/14.
 */
public class TestListAdapter extends ArrayAdapter<TestListItem> {

    private List<TestListItem> listItems;
    private Context mContext;

    public TestListAdapter(Context context, int resource, List<TestListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        this.listItems = objects;
    }

    @Override
    public TestListItem getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // Get the number of items in the enum
        return TestListItem.RowType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        // Use getViewType from the Item interface
        return getItem(position).getViewType().ordinal();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View wholeRowView = convertView;
        if (wholeRowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (getItem(position).getViewType().equals(TestListItem.RowType.LIST_ITEM)){
                wholeRowView = inflater.inflate(R.layout.test_list_item, parent, false);
            }
            if (getItem(position).getViewType().equals(TestListItem.RowType.HEADER_ITEM)){
                wholeRowView = inflater.inflate(R.layout.test_list_header, parent, false);
            }

        }
        TestListItem dataItem = getItem(position);
        // Use getView from the Item interface
        if (getItem(position).getViewType().equals(TestListItem.RowType.LIST_ITEM)){
            TextView text1 = (TextView) wholeRowView.findViewById(R.id.list_content1);
            TextView text2 = (TextView) wholeRowView.findViewById(R.id.list_content2);
            text1.setText(dataItem.getData());
            text2.setText(SkavaUtils.getCurrentDate().toString());

        }
        if (getItem(position).getViewType().equals(TestListItem.RowType.HEADER_ITEM)){
            TextView text = (TextView) wholeRowView.findViewById(R.id.headerText);
            text.setText(dataItem.getData());
        }
        return wholeRowView;
    }


}
