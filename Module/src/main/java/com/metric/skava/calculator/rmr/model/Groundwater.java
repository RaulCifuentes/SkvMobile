package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

/**
 * Created by metricboy on 2/16/14.
 */
public class Groundwater extends MappedIndex {

    public static final String INDEX_CODE = "Groundwater";

    private Group group;

    public enum Group {
        INFLOW_LENGHT("a. Inflow per 10 m tunnel length (l/m)"),
        JOINT_PRESS_PRINCIPAL("b. Joint water press / Major principal(σ)"),
        GENERAL_CONDITIONS("c. General Conditions");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public Groundwater(Group group, String code, String key, String shortDescription, String longDescription, Double ratingValue) {
        super(code, key, shortDescription, longDescription, ratingValue);
        setGroup(group);
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String getGroupName() {
        return this.group.name();
    }
}
