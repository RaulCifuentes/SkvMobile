package com.metric.skava.app.model;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 2/16/14.
 */
public class ExcavationProject extends SkavaEntity {

    private String internalCode;
    private Client client;

    public ExcavationProject(String id, String name, String skavaInternalCode) {
        super(id, name);
        this.internalCode = skavaInternalCode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
