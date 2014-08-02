package com.metric.skava.mapping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.metric.skava.mapping.model.MappingStage;

import java.util.List;

/**
 * Created by metricboy on 7/31/14.
 */
public class MappingStageArrayAdapter extends ArrayAdapter<MappingStage> {

    private Context mContext;
    private boolean mActivateAllStages;

    public MappingStageArrayAdapter(Context context, int resource, int textViewResourceId, List<MappingStage> objects) {
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
        if (position == 0) {
            return true;
        } else {
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
