package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

/**
 * Created by metricboy on 2/16/14.
 */
public class StrengthOfRock extends MappedIndex {

    public static final String INDEX_CODE = "Strength";
    private Group group;

    public enum Group {
        POINT_LOAD_KEY("a. Point-load strenght index"),
        UNIAXIAL_KEY("b. Uniaxial compression strenght");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public StrengthOfRock(Group group, String code, String key, String shortDescription, String longDescription, Double ratingValue) {
        super(code, key, shortDescription, longDescription, ratingValue);
        setGroup(group);
    }


    @Override
    public String getGroupName() {
        return this.group.name();
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
}
