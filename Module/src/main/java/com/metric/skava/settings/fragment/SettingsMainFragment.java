package com.metric.skava.settings.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.metric.skava.BuildConfig;
import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.util.SkavaConstants;


public class SettingsMainFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
//        ((YourApplication) getActivity().getApplication()).inject(this);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.
                getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        ((SkavaApplication) getActivity().getApplication()).setRequiresRestart(true);
        if (key.equals("PREFERENCE_SECURITY")) {
            if (sharedPreferences.getBoolean(key, true)) {
                if (BuildConfig.DEBUG) {
                    Log.d(SkavaConstants.LOG, "settings PREFERENCE_SECURITY changed: true()");
                }
            } else {
                if (BuildConfig.DEBUG) {
                    Log.d(SkavaConstants.LOG, "settings PREFERENCE_SECURITY changed: false");
                }
            }
        }
        if (key.equals("PREFERENCE_THEME")) {
            String newValue = sharedPreferences.getString(key, "DEFAULT");
            if (newValue.equals("DEFAULT_THEME")) {
                if (BuildConfig.DEBUG) {
                    Log.d(SkavaConstants.LOG, "settings PREFERENCE_THEME changed: DEFAULT_THEME");
                }
                ((SkavaApplication) getActivity().getApplication()).setCustomThemeId(R.style.CustomAppTheme);
            } else if (newValue.equals("DAY_THEME")) {
                if (BuildConfig.DEBUG) {
                    Log.d(SkavaConstants.LOG, "settings PREFERENCE_THEME changed: DAY_THEME");
                }
                ((SkavaApplication) getActivity().getApplication()).setCustomThemeId(R.style.DayAppTheme);
            } else if (newValue.equals("NIGHT_THEME")) {
                if (BuildConfig.DEBUG) {
                    Log.d(SkavaConstants.LOG, "settings PREFERENCE_THEME changed: NIGHT_THEME");
                }
                ((SkavaApplication) getActivity().getApplication()).setCustomThemeId(R.style.NightAppTheme);
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
