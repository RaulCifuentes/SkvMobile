package com.metric.skava.calculator.model;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 4/28/14.
 */
public class Index extends SkavaEntity {

    private String key;

    public Index(String code, String key, String name) {
        super(code, name);
        setKey(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
