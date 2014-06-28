package com.metric.skava.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Assessment;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by metricboy on 3/25/14.
 */
public class AssessmentListViewAdapter extends ArrayAdapter<Assessment> {

    private Context mContext;
    private int resourceId;

    public AssessmentListViewAdapter(Context context, int resource, int textViewResourceId, List<Assessment> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceId = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View assessmentViewItem;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assessmentViewItem = inflater.inflate(resourceId, parent, false);
        } else {
            assessmentViewItem = convertView;
        }

        Assessment actualAssessment = getItem(position);

        if (actualAssessment != null) {

            TextView text = (TextView) assessmentViewItem.findViewById(R.id.first_column_text_view);
            text.setText(actualAssessment.getInternalCode());

            text = (TextView) assessmentViewItem.findViewById(R.id.second_column_text_view);
            text.setText(actualAssessment.getCode());

            if (actualAssessment.getDateTime() != null) {
                text = (TextView) assessmentViewItem.findViewById(R.id.third_column_text_view);
                DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
                String dateAsString = formatter.format(actualAssessment.getDateTime().getTime());
                text.setText(dateAsString);
            }
        }

        return assessmentViewItem;
    }
}
