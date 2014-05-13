package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class Ja extends MappedIndex implements Serializable {

    public static final String INDEX_CODE = "Ja";

    private Group group;

    public enum Group {
        a("a. Rock-wall contact (no mineral fillings, only coatings)"),
        b("b. Rock-wall contact before 10 cm shear (thin mineral fillings)"),
        c("c. No rock-wall contact when sheared (thick mineral fillings)");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }


	public Ja(Group group, String code, String key, String shortDescription, String longDescription, Double value) {
        super(code, key, shortDescription, longDescription, value);
        setGroup(group);
	}

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String getGroupName() {
        return this.group.name();
    }

}
