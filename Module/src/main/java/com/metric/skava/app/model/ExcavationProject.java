package com.metric.skava.app.model;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 2/16/14.
 */
public class ExcavationProject extends SkavaEntity {

    private Client client;

    public ExcavationProject(String id, String name) {
        super(id, name);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
