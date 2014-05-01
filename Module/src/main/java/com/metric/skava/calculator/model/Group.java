package com.metric.skava.calculator.model;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 4/28/14.
 */
public class Group extends SkavaEntity {

    private Index index;
    private String key;

    public Group(String code, String key, String name) {
        super(code, name);
        this.key = key;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
