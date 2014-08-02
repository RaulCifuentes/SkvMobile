package com.metric.skava.settings.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;

import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.home.activity.HomeMainActivity;

import java.text.SimpleDateFormat;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class SettingsMainFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String UNLINK_DROPBOX_PREFERENCE = "UNLINK_DROPBOX";
    public static final String TARGET_ENVIRONMENT_PREFERENCE = "TARGET_ENVIRONMENT";

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.preferences);
        getDefaultSharedPreferences(this.getActivity().getApplicationContext()).registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        Preference environmentPref = findPreference(TARGET_ENVIRONMENT_PREFERENCE);
        String newValue = sharedPreferences.getString(TARGET_ENVIRONMENT_PREFERENCE, "");
        environmentPref.setSummary(newValue);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(TARGET_ENVIRONMENT_PREFERENCE)) {
            Preference environmentPref = findPreference(key);
            String newValue = sharedPreferences.getString(key, "");
            environmentPref.setSummary(newValue);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, newValue);
            editor.commit();
            ((SkavaApplication)getActivity().getApplication()).setNeedImportAppData(true);
            ((SkavaApplication)getActivity().getApplication()).setNeedImportUserData(true);
            //Move the saveState to the body of the setters on Application object itself
//            ((SkavaApplication)getActivity().getApplication()).saveState();
            restartApp();
        }
        if (key.equals(UNLINK_DROPBOX_PREFERENCE)) {
            Boolean newValue = sharedPreferences.getBoolean(key, false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, newValue);
            editor.commit();
        }

    }




    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void restartApp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateAsString = sdf.format(SkavaUtils.getCurrentDate());
        Log.d(SkavaConstants.LOG, "********** onRestart ***** " + dateAsString);
        Intent mStartActivity = new Intent(getActivity().getApplicationContext(), HomeMainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }



    public static void restartOneTry(Context context, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        Log.e(SkavaConstants.LOG, "Restarting application ...");
        Intent restartIntent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName() );
        PendingIntent intent = PendingIntent.getActivity(
                context, 0,
                restartIntent, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
        System.exit(2);
    }

}