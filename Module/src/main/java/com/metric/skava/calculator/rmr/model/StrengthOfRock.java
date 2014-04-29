package com.metric.skava.calculator.rmr.model;

import com.metric.skava.calculator.model.MappedIndex;

/**
 * Created by metricboy on 2/16/14.
 */
public class StrengthOfRock extends MappedIndex {

    private Group group;

    public enum Group {
        POINT_LOAD_KEY("Point-load strenght index"),
        UNIAXIAL_KEY("Uniaxial compression strenght");

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
//        setKey(key);
//        getCategoriesAndValues()[0][0] = MappedIndex.DESCRIPTION;
//        getCategoriesAndValues()[1][0] = longDescription;
//        setValue(ratingValue);
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
