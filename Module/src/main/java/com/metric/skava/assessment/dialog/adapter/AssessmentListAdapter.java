package com.metric.skava.assessment.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.util.PegNumberFormat;
import com.metric.skava.app.util.SkavaUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static com.metric.skava.app.model.Assessment.DATA_SENT_TO_CLOUD;
import static com.metric.skava.app.model.Assessment.DATA_SENT_TO_DATASTORE;
import static com.metric.skava.app.model.Assessment.PICS_SENT_TO_CLOUD;
import static com.metric.skava.app.model.Assessment.PICS_SENT_TO_DATASTORE;

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

            ExcavationProject project = currentItem.getProject();
            if (SkavaUtils.isDefined(project)){
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_project);
                text.setText(project.getName());
            }

            if (currentItem.getPseudoCode() != null){
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_pseudo_code);
                text.setText(currentItem.getPseudoCode());
            }
            if (currentItem.getCode() != null){
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_code);
                text.setText(currentItem.getCode());
            }

            Double initChainage = currentItem.getInitialPeg();
            Double finalChainage = currentItem.getFinalPeg();
            if (initChainage != null && finalChainage != null){
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_chainage);
                PegNumberFormat pegNumberFormat = new PegNumberFormat();
                String initialText = pegNumberFormat.format(initChainage);
                String finalText = pegNumberFormat.format(finalChainage);
                text.setText(initialText + " - " + finalText );
            }

            Date date = currentItem.getDateTime().getTime();
            if (date != null) {
                TextView text = (TextView) assessmentViewItem.findViewById(R.id.assessment_date);
                text.setText(DateFormat.getDateTimeInstance().format(date));
            }

            ImageView imageView = (ImageView) assessmentViewItem.findViewById(R.id.assessment_sent);

            switch (currentItem.getSentToCloud()){
                case DATA_SENT_TO_CLOUD:
                    if (SkavaUtils.hasPictures(currentItem.getPicturesList())){
                        imageView.setImageResource(R.drawable.cloud_striped);
                    } else {
                        imageView.setImageResource(R.drawable.cloud_checked);
                    }
                    break;
                case PICS_SENT_TO_CLOUD:
                    imageView.setImageResource(R.drawable.cloud_checked);
                    break;
                case DATA_SENT_TO_DATASTORE:
                case PICS_SENT_TO_DATASTORE:
                    imageView.setImageResource(R.drawable.cloud_sync);
                    break;
                default:
                    imageView.setImageResource(R.drawable.tablet);
            }
        }

        return assessmentViewItem;
    }
}
