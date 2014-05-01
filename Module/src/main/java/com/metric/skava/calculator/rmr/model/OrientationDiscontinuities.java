package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class OrientationDiscontinuities extends MappedIndex implements Serializable {

    public static final String INDEX_CODE = "Orientation";

    private Group group;

    public enum Group {
        TUNNELS_MINES("Tunnels & Mines"),
        FOUNDATIONS("Foundations"),
        SLOPES("Slopes");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public OrientationDiscontinuities(Group group, String code, String key, String shortDescription, String longDescription, Double value) {
        super(code, key, shortDescription, longDescription,value);
        setGroup(group);
    }


    @Override
    public String getGroupName() {
        return this.group.name();
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
