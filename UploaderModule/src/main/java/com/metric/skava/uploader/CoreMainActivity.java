package com.metric.skava.uploader;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.android.AuthActivity;
import com.metric.skava.uploader.app.SkavaUploaderActivity;
import com.metric.skava.uploader.app.SkavaUploaderApplication;
import com.metric.skava.uploader.app.util.SkavaUploaderConstants;

public class CoreMainActivity extends SkavaUploaderActivity {

    // Android widgets
    private Button mSubmit;
    private boolean callingLogIn;
    private DropboxAPI<AndroidAuthSession> mApi;
    private boolean mLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(getApplicationContext(), SkavaUploaderConstants.BUGSENSE_API_KEY);
        mLoggedIn = getSkavaUploaderApplication().isLoggedIn();

        // Basic Android widgets
        setContentView(R.layout.activity_core_main);
        mSubmit = (Button)findViewById(R.id.auth_button);

        if (mLoggedIn) {
            setLoggedIn(true);
        } else {
            setLoggedIn(false);
            callingLogIn = true;
            logIn();
        }

        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // This logs you out if you're logged in, or vice versa
                if (mLoggedIn) {
                    logOut();
                    setLoggedIn(false);
                } else {
                    callingLogIn = true;
                    logIn();
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (callingLogIn){
            AndroidAuthSession session = mApi.getSession();
            if (session.authenticationSuccessful()) {
                try {
                    // Mandatory call to complete the auth
                    session.finishAuthentication();
                    // Store it locally in our app for later use
                    getSkavaUploaderApplication().storeAuth(session);
                    getSkavaUploaderApplication().setDropboxAPI(mApi);
                    setLoggedIn(true);
                } catch (IllegalStateException e) {
                    showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
                    Log.i(SkavaUploaderConstants.LOG, "Error authenticating", e);
                }
            }
        }
    }


    private void logIn(){
        checkAppKeySetup();
        //Check if there's
        AndroidAuthSession session = getSkavaUploaderApplication().buildSession();
        mApi = new DropboxAPI<AndroidAuthSession>(session);
        if (SkavaUploaderConstants.USE_OAUTH1) {
            mApi.getSession().startAuthentication(CoreMainActivity.this);
        } else {
            mApi.getSession().startOAuth2Authentication(CoreMainActivity.this);
        }
    }


    private void logOut() {
        ((SkavaUploaderApplication)getApplication()).logOut();
    }


    private void checkAppKeySetup() {
        // Check if the app has set up its manifest properly.
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        String scheme = "db-" + SkavaUploaderConstants.DROBOX_APP_KEY;
        String uri = scheme + "://" + AuthActivity.AUTH_VERSION + "/test";
        testIntent.setData(Uri.parse(uri));
        PackageManager pm = getPackageManager();
        if (0 == pm.queryIntentActivities(testIntent, 0).size()) {
            showToast("URL scheme in your app's " +
                    "manifest is not set up correctly. You should have a " +
                    "com.dropbox.client2.android.AuthActivity with the " +
                    "scheme: " + scheme);
            finish();
        }
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        error.show();
    }

    /**
     * Convenience function to change UI state based on being logged in
     */
    private void setLoggedIn(boolean loggedIn) {
        mLoggedIn = loggedIn;
        if (loggedIn) {
            mSubmit.setText("Unlink from Dropbox");
        } else {
            mSubmit.setText("Link with Dropbox");
        }
    }

}