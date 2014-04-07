package com.metric.skava.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class SkavaAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        SkavaAuthenticator authenticator = new SkavaAuthenticator(this);
        return authenticator.getIBinder();
    }
}
