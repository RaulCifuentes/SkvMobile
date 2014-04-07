package com.metric.skava.about.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;

public class AboutMainFragment extends SkavaFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_main_fragment, container, false);
        return rootView;
    }


}
