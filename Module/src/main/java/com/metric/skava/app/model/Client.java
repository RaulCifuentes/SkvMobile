package com.metric.skava.app.model;

import android.net.Uri;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 2/16/14.
 */
public class Client extends SkavaEntity {

    private Uri clientLogo;

    public Client(String id, String name) {
        super(id, name);
    }

    public Uri getClientLogo() {
        return clientLogo;
    }

    public void setClientLogo(Uri clientLogo) {
        this.clientLogo = clientLogo;
    }
}
