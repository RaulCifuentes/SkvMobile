package com.metric.skava.instructions.model;

import com.metric.skava.app.data.SkavaEntity;

/**
  * Created by metricboy on 5/5/14.
  */
 public class SupportPatternType extends SkavaEntity {

    public enum Group {ROOF, WALL}

    private Group group;

    public SupportPatternType(String code, String name) {
        super(code, name);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
