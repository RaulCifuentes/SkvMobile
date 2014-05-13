package com.metric.skava.settings.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.util.SkavaConstants;


public class SettingsMainFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String UPDATE_AUTO_PREFERENCE = "UPDATE_AUTO";
    public static final String UNLINK_DROPBOX_PREFERENCE = "UNLINK_DROPBOX";

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals(UPDATE_AUTO_PREFERENCE)) {
            if (BuildConfig.DEBUG) {
                Log.d(SkavaConstants.LOG, "Changing settings for " + key  +", new value: " + sharedPreferences.getBoolean(key, false));
            }
        }

}


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().
                getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


}
