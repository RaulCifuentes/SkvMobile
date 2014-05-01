package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Jr extends MappedIndex implements Serializable {

    public static final String INDEX_CODE = "Jr";

    private Group group;

    public enum Group {
        a("Rock-wall contact"),
        b("Rock-wall contact before 10 cm shear"),
        c("No rock-wall contact when sheared");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

	public Jr(Group group, String code, String key, String shortDescription, String longDescription, Double value) {
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
