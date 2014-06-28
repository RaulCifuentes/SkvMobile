package com.metric.skava.home.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;

public class MainFragment extends SkavaFragment {

    private ActionBar mActionBar;
    private View mSyncingStatusView;
    private TextView mSyncingStatusMessageView;
    private ImageView mBackgroudImage;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mActionBar = getActivity().getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater
                .inflate(R.layout.welcome_main_fragment, container, false);
        mSyncingStatusView = mainView.findViewById(R.id.syncing_status);
        mSyncingStatusMessageView = (TextView) mainView.findViewById(R.id.syncing_status_message);
        mBackgroudImage = (ImageView) mainView.findViewById(R.id.imageView);
        return mainView;
    }

    public View getSyncingStatusView() {
        return mSyncingStatusView;
    }

    public TextView getSyncingStatusMessageView() {
        return mSyncingStatusMessageView;
    }

    public ImageView getBackgroudImage() {
        return mBackgroudImage;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
