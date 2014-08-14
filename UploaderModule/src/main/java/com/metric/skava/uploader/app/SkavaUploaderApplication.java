package com.metric.skava.uploader.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.metric.skava.uploader.app.util.SkavaUploaderConstants;

/**
 * Created by metricboy on 6/25/14.
 */
public class SkavaUploaderApplication extends Application {

    private SharedPreferences mSharedPreferences;

    private DropboxAPI<AndroidAuthSession> mDropboxAPI;


    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = getSharedPreferences(SkavaUploaderConstants.PERSISTENCE_BUCKET_FILE, Context.MODE_PRIVATE);

        AndroidAuthSession session = buildSession();
        mDropboxAPI = new DropboxAPI<AndroidAuthSession>(session);

    }

    public DropboxAPI<AndroidAuthSession> getDropboxAPI() {
        return mDropboxAPI;
    }

    public void setDropboxAPI(DropboxAPI<AndroidAuthSession> dropboxAPI) {
        this.mDropboxAPI = dropboxAPI;
    }

    public void logOut(){
        // Remove credentials from the session
        getDropboxAPI().getSession().unlink();
        // Clear our stored keys
        clearKeys();
    }


    public boolean isLoggedIn() {
        boolean isLoggedIn = false;
        if (getDropboxAPI() != null) {
            AndroidAuthSession session = getDropboxAPI().getSession();
            if (session != null) {
                isLoggedIn = session.isLinked();
           }
        }
        return isLoggedIn;
    }

    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    public void loadAuth(AndroidAuthSession session) {
        String key = mSharedPreferences.getString(SkavaUploaderConstants.ACCESS_KEY_NAME, null);
        String secret = mSharedPreferences.getString(SkavaUploaderConstants.ACCESS_SECRET_NAME, null);
        if (key == null || secret == null || key.length() == 0 || secret.length() == 0) {
            return;
        }
        if (key.equals("oauth2:")) {
            // If the key is set to "oauth2:", then we can assume the token is for OAuth 2.
            session.setOAuth2AccessToken(secret);
        } else {
            // Still support using old OAuth 1 tokens.
            session.setAccessTokenPair(new AccessTokenPair(key, secret));
        }
    }

    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    public void storeAuth(AndroidAuthSession session) {
        // Store the OAuth 2 access token, if there is one.
        String oauth2AccessToken = session.getOAuth2AccessToken();
        if (oauth2AccessToken != null) {
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString(SkavaUploaderConstants.ACCESS_KEY_NAME, "oauth2:");
            edit.putString(SkavaUploaderConstants.ACCESS_SECRET_NAME, oauth2AccessToken);
            edit.commit();
            return;
        }
        // Store the OAuth 1 access token, if there is one.  This is only necessary if
        // you're still using OAuth 1.
        AccessTokenPair oauth1AccessToken = session.getAccessTokenPair();
        if (oauth1AccessToken != null) {
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString(SkavaUploaderConstants.ACCESS_KEY_NAME, oauth1AccessToken.key);
            edit.putString(SkavaUploaderConstants.ACCESS_SECRET_NAME, oauth1AccessToken.secret);
            edit.commit();
            return;
        }
    }

    private void clearKeys() {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.clear();
        edit.commit();
    }

    public AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(SkavaUploaderConstants.DROBOX_APP_KEY, SkavaUploaderConstants.DROBOX_APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        loadAuth(session);
        return session;
    }


}
