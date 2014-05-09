package com.metric.skava.assessment.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.metric.skava.R;
import com.metric.skava.assessment.adapter.AssessmentStageArrayAdapter;
import com.metric.skava.assessment.data.AssesmentStageDataProvider;
import com.metric.skava.assessment.model.AssessmentStage;

import java.util.List;

/**
 * A list fragment representing a list of Stages. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed.
 * <p>
 * Activities containing this fragment MUST implement the {@link com.metric.skava.assessment.fragment.AssessmentStageListFragment.StageSelectionCallback}
 * interface.
 */
public class AssessmentStageListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;


    private AssessmentStageArrayAdapter mStageArrayAdapter;

    private StageSelectionCallback mCallbacks;



    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface StageSelectionCallback {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AssessmentStageListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AssesmentStageDataProvider dataProvider = AssesmentStageDataProvider.getInstance(getActivity());
        List<AssessmentStage> listStages = dataProvider.getAllStages();
        mStageArrayAdapter = new AssessmentStageArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                listStages);
        setListAdapter(mStageArrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //As this is a native ListFragment I don't have defined a layout for my listView
        //so I need to customize it by code
        ListView listView = getListView();
        listView.setBackgroundColor(getResources().getColor(R.color.Gray));
        //        listView.setSelector(R.drawable.apptheme_list_selector_holo_light);
        //        listView.setDivider(new ColorDrawable(Color.WHITE));
        //        listView.setDividerHeight(3); // 3 pixels height

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof StageSelectionCallback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (StageSelectionCallback) activity;
    }



    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        AssessmentStage selectedStage = mStageArrayAdapter.getItem(position);
        mCallbacks.onItemSelected(selectedStage.getCode());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public void enableAllStages(boolean shouldEnableAll) {
        if (shouldEnableAll){
            mStageArrayAdapter.setActivateAllStages(true);
        } else {
            mStageArrayAdapter.setActivateAllStages(false);
        }
    }




}
