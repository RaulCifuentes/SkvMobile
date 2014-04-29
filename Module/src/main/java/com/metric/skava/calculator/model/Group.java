package com.metric.skava.calculator.model;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 4/28/14.
 */
public class Group extends SkavaEntity {

    private Index index;

    public Group(String code, String name) {
        super(code, name);
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }
}
