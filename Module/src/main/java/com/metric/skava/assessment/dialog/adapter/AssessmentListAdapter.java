package com.metric.skava.assessment.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.util.SkavaUtils;

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

        Assessment currentItem = getItem(position);

        if (currentItem != null) {
            if (currentItem.getCode() != null){
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_code);
                text.setText(currentItem.getCode());
            }
            Date date = currentItem.getDate();
            if (date != null) {
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_date);
                text.setText(DateFormat.getDateInstance().format(date));
            }
            ExcavationProject project = currentItem.getProject();
            if (SkavaUtils.isDefined(project)){
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_project);
                text.setText(project.getName());
            }
            Tunnel tunnel = currentItem.getTunnel();
            if (SkavaUtils.isDefined(tunnel)){
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_tunnel);
                text.setText(tunnel.getName());
            }
            TunnelFace face = currentItem.getFace();
            if (SkavaUtils.isDefined(face)){
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_face);
                text.setText(face.getName());
            }
        }


        return assessmentViewItem;
    }
}
