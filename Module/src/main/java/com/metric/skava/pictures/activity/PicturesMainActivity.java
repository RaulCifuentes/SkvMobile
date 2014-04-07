package com.metric.skava.pictures.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.pictures.fragment.PicturesMainFragment;

public class PicturesMainActivity extends SkavaFragmentActivity {

//    private Fragment fragment;
    private String FRAGMENT_TAG = "FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pictures_main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PicturesMainFragment(), FRAGMENT_TAG)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // what if I just fire a onActivityresult on the fargment with the requestCode I want?
        int oldRequestCode = requestCode;
        Fragment targetFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        targetFragment.onActivityResult(PicturesMainFragment.TAKE_PHOTO_LEFT_REQUEST_CODE, resultCode, data);

    }
}
