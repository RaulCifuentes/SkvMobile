package com.metric.skava.assessment.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Assessment;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public class AssessmentListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Assessment> assessmentList;

    public AssessmentListAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

    @Override
    public int getCount() {
        if (assessmentList != null) {
            return assessmentList.size();
        }
        return 0;
    }

    @Override
    public Assessment getItem(int position) {
        if (assessmentList != null) {
            return assessmentList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View assessmentViewItem;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            assessmentViewItem = mInflater.inflate(R.layout.assessment_dto_list_item, parent, false);
        } else {
            assessmentViewItem = convertView;
        }

        Assessment actualAssessmentItem = getItem(position);

        if (actualAssessmentItem != null) {

            TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_code);
            text.setText(actualAssessmentItem.getInternalCode());

            text = (TextView) assessmentViewItem.findViewById(R.id.assessment_date);
            Date date = actualAssessmentItem.getDate();
            text.setText(DateFormat.getDateInstance().format(date));

            text = (TextView) assessmentViewItem.findViewById(R.id.assessment_project);
            text.setText(actualAssessmentItem.getProject().getName());

            text = (TextView) assessmentViewItem.findViewById(R.id.assessment_tunnel);
            text.setText(actualAssessmentItem.getTunnel().getName());

            text = (TextView) assessmentViewItem.findViewById(R.id.assessment_face);
            text.setText(actualAssessmentItem.getFace().getName());
        }


        return assessmentViewItem;
    }
}
