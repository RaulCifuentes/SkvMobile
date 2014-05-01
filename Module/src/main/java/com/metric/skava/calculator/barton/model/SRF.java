package com.metric.skava.calculator.barton.model;

import com.metric.skava.calculator.model.MappedIndex;

import java.io.Serializable;

public class SRF extends MappedIndex implements Serializable {

    public static final String INDEX_CODE = "SRF";

    private Group group;

    public enum Group {
        a("Weak zones intersecting the underground opening, which may cause loosening of rock mass"),
        b("Competent, mainly massive rock, stress problems"),
        c("Squeezing rock: plastic deformation in incompetent rock under the influence of high pressure"),
        d("Swelling rock: chemical swelling activity depending on the presence of water");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public SRF(Group group, String code, String key, String shortDescription, String longDescription, Double value) {
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
