package com.metric.skava.assessment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.metric.skava.assessment.model.AssessmentStage;

import java.util.List;

/**
 * Created by metricboy on 2/21/14.
 */
public class AssessmentStageArrayAdapter extends ArrayAdapter<AssessmentStage> {

    private Context mContext;
    private boolean mActivateAllStages;

    public AssessmentStageArrayAdapter(Context context, int resource, int textViewResourceId, List<AssessmentStage> objects) {
        super(context, resource, textViewResourceId, objects);
        //true mientras que hago algo mejor para el enable disable
        mContext = context;
        mActivateAllStages = false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        //identification is first in the list
        if (position == 0){
            return true;
        }
        else {
         return isActivateAllStages();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = super.getView(position, convertView, parent);
        if (view != null) {
//            view.setBackgroundColor(mContext.getResources().getColor(R.color.LightBlue));
        }
        return view;
    }


    public boolean isActivateAllStages() {
        return mActivateAllStages;
    }

    public void setActivateAllStages(boolean activateAllStages) {
        this.mActivateAllStages = activateAllStages;
    }
}
