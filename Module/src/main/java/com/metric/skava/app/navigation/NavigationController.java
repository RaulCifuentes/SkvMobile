package com.metric.skava.app.navigation;

import android.support.v4.app.FragmentManager;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.home.fragment.MainFragment;


public class NavigationController {

    public static final String HOME_FRAGMENT_TAG = "home";

    private static NavigationController instance;

    private NavigationController() {
	}

    public static NavigationController getInstance() {
        if (instance == null) {
            instance = new NavigationController();
        }
        return instance;
    }

    public void goHomeFragment( SkavaFragmentActivity activity) {
    	FragmentManager fm = activity.getSupportFragmentManager();
    	for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {    
    	    fm.popBackStack();
    	}    	
        fm.beginTransaction()
                .replace(R.id.nav_drawer_main_layout_content_frame, new MainFragment(), HOME_FRAGMENT_TAG).commit();
    }



}